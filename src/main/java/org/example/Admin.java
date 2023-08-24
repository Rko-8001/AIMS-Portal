package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Admin {

    String email;
    Connection c;
    PrintStream log;
    public Admin(String email, Connection ca, PrintStream pa) {
        this.email = email;
        this.c = ca;
        this.log = pa;
    }
    public void catalogView () throws SQLException {
        Statement st = this.c.createStatement();
        String stview = "SELECT * FROM course_catalogue;";
        ResultSet rs = st.executeQuery(stview);

        log.println("Here are the courses offered: ");
        int i = 1;
        while (rs.next()) {
            String courseId = rs.getString("course_id");
            String name = rs.getString("name");
            int l = rs.getInt("lectures");
            int t = rs.getInt("tutorials");
            int p = rs.getInt("practice");
            float credit = rs.getFloat("credit");
            System.out.print(i + " CourseId: " + courseId + " Name: " + name);
            System.out.println(" L-T-P: " + l + "-" + t + "-" + p + " Total Credits: " + credit);
            i++;
        }
    }
    public boolean findCourse(String course_id) throws SQLException{
        Statement st = this.c.createStatement();
        String rmc = "SELECT * FROM course_catalogue WHERE course_id = " + "'" + course_id + "';";
        ResultSet rs = st.executeQuery(rmc);
        return rs.next();
    }
    public String removeCourse(String course_id) throws SQLException{
        if(!findCourse(course_id)){
            System.out.println("\nCourse you want to delete is not present in database..\n");
            return "Course Not Present.";
        }
        Statement st = this.c.createStatement();
        String rmc = "DELETE FROM course_catalogue WHERE course_id = " + "'" + course_id + "';";
        int dor = st.executeUpdate(rmc);
        System.out.println(course_id + " removed..");
        return "Course Removed.";
    }
    public int addCourse (String course_id, String name, int l, int t, int p, double c, String core) throws SQLException {
        if(findCourse(course_id))
        {
            System.out.println("Course Already present!! You can update it..");
            return -1;
        }
        Statement st = this.c.createStatement();
        String imc = "INSERT INTO course_catalogue VALUES ( '" + course_id + "', '" + name + "', " + l + ", " + p + ", " + t + ", " + c + ", '" + core + "' );";
        log.println(imc);
        int aor = st.executeUpdate(imc);
        System.out.println(course_id + " Added..");
        return aor;
    }

    public int findUser (String email_id) throws SQLException {
        Statement st = this.c.createStatement();
        String rmc = "SELECT * FROM users WHERE email = " + "'" + email_id+ "';";
        ResultSet rs = st.executeQuery(rmc);
        return rs.next() ? rs.getInt("role") : -1;
    }
    public void addProfessor (String email_id, String name, String dept) throws SQLException {
        if(findUser(email_id) == 1)
        {
            System.out.println("Professor Already Present..");
            return;
        }
        Statement st = this.c.createStatement();
        String adp = "INSERT INTO professor VALUES ( '" + email_id + "' , '" + name + "', '" + dept + "' );";

        st.executeUpdate(adp);
        String aiu = "INSERT INTO users ( name, email, password, role) VALUES ( '" + name + "', '" + email_id + "', 'iitrpr', 1);";
        st.executeUpdate(aiu);
        System.out.println("Professor " + name + " Added");
        return;
    }
    public String addProfessorcsv (String path) throws IOException, CsvValidationException, SQLException {
        FileReader filereader = new FileReader(path);
        CSVReader csvreader= new CSVReader(filereader);
        String[] nextrecord;

        while((nextrecord=csvreader.readNext())!=null)
        {
            String email_id = nextrecord[0];
            String name = nextrecord[1];
            String dept = nextrecord[2];
            addProfessor(email_id, name, dept);
        }
        return "Added all";
    }
    public int showProfessors () throws SQLException {
        Statement st = this.c.createStatement();
        String spl = "SELECT * FROM professor;";
        ResultSet rs = st.executeQuery(spl);
        log.println("Here is list of all Pofessor: ");
        while (rs.next())
        {
            String email_id = rs.getString("email_id");
            String name = rs.getString("name");
            String dept = rs.getString("dept_name");
            log.println("Name: " + name + " (Email: "+ email_id + " || Dept: "+ dept + " ) ");
            System.out.println("Name: " + name + " (Email: "+ email_id + " || Dept: "+ dept + " ) ");
        }
        return 1;
    }
    public void addStudent (String email_id, String name, String entryNo, String dept_name, int batch) throws SQLException {
        if(findUser(email_id) == 0)
        {
            System.out.println("Student (" + email_id + ") already present..");
            return;
        }

        Statement st = this.c.createStatement();
        String adu = "INSERT INTO users ( name, email, entryno,  password, role) VALUES ( '" + name + "', '" + email_id + "', '" + entryNo + "', 'iitrpr', 0);";
        String ads = "INSERT INTO student VALUES ( '" + email_id + "', '" + name + "', '" + dept_name + "', " + batch + ");";
        int aou = st.executeUpdate(adu);
        aou = st.executeUpdate(ads);
        System.out.println("Student: " + email_id + " added with password: 'iitrpr' (DEFAULT).");
    }
    public String addStudentscsv(String path) throws IOException, CsvValidationException, SQLException {
        FileReader filereader = new FileReader(path);
        CSVReader csvreader= new CSVReader(filereader);
        String[] nextrecord;

        while((nextrecord=csvreader.readNext())!=null)
        {
            String entryNo = nextrecord[0];
            String name = nextrecord[1];
            String email = nextrecord[2];
            String dept_name = nextrecord[3];
            int batch = Integer.parseInt(nextrecord[4]);
            addStudent(email,name, entryNo, dept_name ,batch);
        }
        return "Added all";
    }
    public int showStudents () throws SQLException {
        Statement st = this.c.createStatement();
        String ss = "SELECT * FROM student;";
        ResultSet rs = st.executeQuery(ss);
        log.println("Students: ");
        while (rs.next())
        {
            String name = rs.getString("name");
            String email = rs.getString("email_id");
            String dept = rs.getString("dept_name");
            int batch = rs.getInt("batch");
            log.println("Name: " + name +  "\tEmail: " + email + " (" + batch + " ," + dept + ")");System.out.println("Name: " + name +  "\tEmail: " + email + " (" + batch + " ," + dept + ")");
        }
        return 1;
    }

    public int courseOfferView() throws SQLException
    {
        Statement st = this.c.createStatement();
        String rmc = "SELECT * FROM course_offering;";
        ResultSet rs = st.executeQuery(rmc);
        while (rs.next())
        {
            String offering_id = rs.getString("offering_id");
            String course_id = rs.getString("course_id");
            String name = rs.getString("name");
            String instructor = rs.getString("instructor_id");

            String core = rs.getString("core");
            double credit = rs.getDouble("c");
            double cgpa = rs.getDouble("cgpa_req");
            int l = rs.getInt("l");
            int t = rs.getInt("t");
            int p = rs.getInt("p");
            String cgpar = String.valueOf(cgpa);
            if(cgpa <= 0.0)
            {
                cgpar = "Offered for All.";
            }
            System.out.println(offering_id + ") ID: " + course_id + " Name: " + name + " Offered by " + instructor);
            System.out.println("CGPA Req: " + cgpar + "Program Core for " + core );
            System.out.println("Course Structure: " + l + "-" + p + "-" + t + "-" + credit + "\n");
        }
        return 1;
    }
    public String studentdep(String id) throws SQLException {
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM student WHERE email_id = '" + id + "';" );
        return rs.next() ? rs.getString("dept_name") : "";
    }
    public int viewStudentRecord(String id) throws SQLException {
        String dep = studentdep(id);
        String vsr = "SELECT * FROM final_data WHERE studentid = '" + id + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(vsr);
        while (rs.next()) {
            String courseid = rs.getString("course_id");
            String core = rs.getString("core");
            int sem = rs.getInt("sem");
            int year = rs.getInt("year");
            int grade = rs.getInt("grade");
            double credit = rs.getDouble("credit");
            this.log.print("CourseID: " + courseid + " Grade: " + grade + " Credit: " + credit);
            this.log.print(" (" + sem + "-" + year + ") [");
            if (core.equals(dep)) {
                this.log.println(" Program Core ]");
            }
            else if(core.equals("BTP"))
            {
                this.log.println(" BTP ]");
            }
            else {
                this.log.println(" Program Elective ]");
            }

        }
        return 1;
    }
    public double getCgpa(String id) throws SQLException {
        double gpa = 0.0;
        double totalCreds = 0;
        String ggt = "SELECT * FROM final_data WHERE studentid = '" + id + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(ggt);

        while (rs.next())
        {
            int grade = rs.getInt("grade");
            double cred = rs.getDouble("credit");
            gpa = gpa +  cred*grade;
            totalCreds += cred;
        }
        if(totalCreds == 0.0)
        {
            return 0.0;
        }
        gpa = gpa/totalCreds;
        return gpa;
    }
    public  void viewStudentRegis(String id) throws SQLException {
        String cr = "SELECT * FROM student_courses WHERE studentid = '" + id + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(cr);
        while(rs.next())
        {
            String courseid = rs.getString("course_id");
            double cred =  rs.getDouble("credit");
            System.out.println("CourseID: " + courseid + " Credits: " + cred);
        }
    }
    public  void viewCourseRegis(String id) throws SQLException {
        String cr = "SELECT * FROM student_courses WHERE course_id = '" + id + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(cr);
        while(rs.next())
        {
            String studentid = rs.getString("studentid");
            System.out.println("StudentID: " + studentid);
        }
    }
    public boolean check_graduation(String studentId) throws SQLException {
        String dep = studentdep(studentId);
        double core_cred = 0.0;
        int btp_cred = 0;
        double ele_cred = 0.0;

        Statement st = this.c.createStatement();
        String dfr = "SELECT * FROM final_data WHERE studentid = '" + studentId + "';";
        ResultSet rs = st.executeQuery(dfr);
        while(rs.next())
        {
            String core = rs.getString("core");
            if(core.equals(dep))
            {
                core_cred += rs.getDouble("credit");
            }
            else if(core.equals("BTP"))
            {
                btp_cred++;
            }
            else
            {
                ele_cred += rs.getDouble("credit");
            }
        }
        System.out.println("Core Credits: " + core_cred + " (Minimum 15 Needed)" );
        System.out.println("BTP Courses: " + btp_cred + " (2 Needed)" );
        System.out.println("Elective Credits: " + ele_cred+ " (Minimum 10 Needed)" );
        return (core_cred >= 15 && btp_cred == 2 && ele_cred >= 10);
    }


}
