package org.example;
import javax.swing.plaf.nimbus.State;
import java.io.PrintStream;
import java.sql.*;
import java.util.stream.StreamSupport;

public class Student {
    String email;
    Connection c;
    PrintStream log;
    public Student(String email, Connection c, PrintStream p) {
        this.email = email;
        this.c = c;
        this.log = p;
    }

    public void catalogView() throws SQLException {
            Statement st = this.c.createStatement();
            String stview = "SELECT * FROM course_catalogue;";
            ResultSet rs = st.executeQuery(stview);

            log.println("Here are the courses offered: ");
            int i = 1;
            while (rs.next())
            {
                String courseId = rs.getString("course_id");
                String name = rs.getString("name");
                int l = rs.getInt("lectures");
                int t = rs.getInt("tutorials");
                int p = rs.getInt("practice");
                float credit = rs.getFloat("credit");
                System.out.print(i + " CourseId: "+ courseId + " Name: " + name);
                System.out.println(" L-T-P: " + l + "-" + t + "-" + p + " Total Credits: " + credit);
                i++;
            }

    }

    public int courseOfferView() throws SQLException {
        Statement st = this.c.createStatement();
        String rmc = "SELECT * FROM course_offering;";
        ResultSet rs = st.executeQuery(rmc);
        while (rs.next()) {
            String offering_id = rs.getString("offering_id");
            String course_id = rs.getString("course_id");
            String name = rs.getString("name");
            String instructor = rs.getString("instructor_id");
            int year = rs.getInt("year");
            int sem = rs.getInt("semester");
            String core = rs.getString("core");
            double credit = rs.getDouble("c");
            double cgpa = rs.getDouble("cgpa_req");
            int l = rs.getInt("l");
            int t = rs.getInt("t");
            int p = rs.getInt("p");
            String cgpar = String.valueOf(cgpa);
            if(cgpa <= 0.0)
            {
                cgpar = "eligible";
            }
            System.out.println(offering_id + ") ID: " + course_id + " Name: " + name + " Offered by " + instructor);
            System.out.println(sem + "-" + year + "CGPA Req: " + cgpar + "Program Core for " + core );
            System.out.println("Course Structure: " + l + "-" + p + "-" + t + "-" + credit + "\n");
        }
        return 1;
    }
    public double credRegis(int sem, int year) throws SQLException {
        double totalcred = 0.0;
        String cot = "SELECT * FROM student_courses WHERE studentid = '" + this.email + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(cot);

        while (rs.next())
        {
            totalcred += rs.getDouble("credit");
        }
        return totalcred;
    }
    public  double prevCreds (int sem, int year) throws SQLException {
        double creds = 0.0;
        String cdn = "SELECT * FROM final_data WHERE studentid = '" + this.email + "' AND sem = " + sem + " AND year = " + year + ";";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(cdn);
        while (rs.next())
        {
            creds += rs.getDouble("credit");
        }
        return 1.25*creds;
    }
    public boolean creditlimit(int sem, int year, int offering_id) throws SQLException {

        int ps = 1;
        int py = year;
        if(sem == 1)
        {
            ps = 2;
            py = year - 1;
        }
        double creds = credRegis(sem,year);

        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM course_offering WHERE offering_id = " + offering_id + ";");
        while(rs.next())
        {
            creds +=  rs.getDouble("c");
        }
        double pcreds = prevCreds(ps,py);
        if(pcreds < 15.0)
        {
            pcreds = 15.0;
        }
        return creds <= pcreds;
    }
    public int courseOffered(String courseid, int sem, int year) throws SQLException {
        Statement st = this.c.createStatement();
        String cot = "SELECT * FROM course_offering WHERE course_id = '" + courseid + "' AND semester = " + sem + " AND year = " + year + ";";
        ResultSet rs = st.executeQuery(cot);
        return rs.next() ? rs.getInt("offering_id"): -1;
    }
    public boolean courseRegistered(String courseID) throws SQLException {
            Statement st = this.c.createStatement();
            String crn = "SELECT * FROM student_courses WHERE course_id = '" + courseID + "' AND studentid = '" + this.email + "';";
            ResultSet rs = st.executeQuery(crn);
        System.out.println(rs.next());
            return rs.next();
    }
    public double getCgpa() throws SQLException {
        double gpa = 0.0;
        int totalCreds = 0;
        String ggt = "SELECT * FROM final_data WHERE studentid = '" + this.email + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(ggt);

        while (rs.next())
        {
            int grade = rs.getInt("grade");
            double cred = rs.getDouble("credit");
            gpa = cred*grade;
            totalCreds += cred;
        }
        if(totalCreds == 0.0)
        {
            return 0.0;
        }
        gpa = gpa/totalCreds;
        return gpa;
    }
    public boolean cgpaSatisfying(int offering_id) throws SQLException {
        double gpa = getCgpa();
        Statement st = this.c.createStatement();
        String csn = "SELECT * FROM course_offering WHERE offering_id = " + offering_id + ";";
        ResultSet rs = st.executeQuery(csn);
        if(rs.next())
        {
            double cgpar = rs.getDouble("cgpa_req");
            return (cgpar <= gpa);
        }
        return false;
    }
    public boolean courseDone(String courseid) throws SQLException {
        Statement st = this.c.createStatement();
        String cdn = "SELECT * FROM final_data WHERE course_id = '" + courseid + "' AND grade >= 5;";
        ResultSet rs = st.executeQuery(cdn);
        return rs.next();

    }
    public boolean prerequisiteDone(int offering_id)
    {
        String courseid = "";
        try
        {
            Statement st = this.c.createStatement();
            String eci = "SELECT * FROM course_offering WHERE offering_id = " + offering_id + ";";
            ResultSet rs = st.executeQuery(eci);
            if(rs.next())
            {
                courseid = rs.getString("course_id");
            }
            String epc = "SELECT * FROM prerequisites WHERE course_id = '" + courseid + "';";
            rs = st.executeQuery(epc);
            while (rs.next())
            {
                String precourseid = rs.getString("prerequisite_id");

                if(!courseDone(precourseid))
                {
                    log.println("Prerequisite: " + courseid + " NOT DONE");
                    return false;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }
    public boolean registerCourse(String courseID, int sem, int year) throws SQLException {
        int offering_id = courseOffered(courseID, sem, year);
        if(offering_id == -1 || courseRegistered(courseID) || !creditlimit(sem, year, offering_id) || !cgpaSatisfying(offering_id) || !prerequisiteDone(offering_id) )
        {
            System.out.println("Course can't be registered.");
            return false;
        }
        Statement st = this.c.createStatement();
        String fci = "SELECT * FROM course_offering WHERE offering_id = " + offering_id + ";";
        ResultSet rs = st.executeQuery(fci);
        String courseid = "", core = "";
        double credit = 0.0;
        while (rs.next())
        {
            courseid = rs.getString("course_id");
            core = rs.getString("core");
            credit = rs.getDouble("c");
        }
        String src = "INSERT INTO student_courses VALUES ( '" + this.email + "', " + offering_id + ", '" + courseid + "', '" + core + "', -1, " + credit + " );";

        st.executeUpdate(src);
        System.out.println("Course Registered..");
        return true;
    }
    public String studentdep() throws SQLException {
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM student WHERE email_id = '" + this.email + "';" );
        if(rs.next())
        {
            return rs.getString("dept_name");
        }
        return "";
    }
    public int viewRecord() throws SQLException {
        String dep = studentdep();
        String vsr = "SELECT * FROM final_data WHERE studentid = '" + this.email + "';";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(vsr);
        while (rs.next()) {
            String courseid = rs.getString("course_id");
            String core = rs.getString("core");
            int sem = rs.getInt("sem");
            int year = rs.getInt("year");
            int grade = rs.getInt("grade");
            double credit = rs.getDouble("credit");

            System.out.print("CourseID: " + courseid + " Grade: " + grade + " Credit: " + credit);
            System.out.print(" (" + sem + "-" + year + ") [");
            if (core.equals(dep)) {
                System.out.println(" Program Core ]");
            }
            else if(core.equals("BTP"))
            {
                System.out.println(" BTP ]");
            }
            else {
                System.out.println(" Program Elective ]");
            }

        }
        return 1;
    }
    public boolean dropCourse(String courseid, int sem, int year) throws SQLException {
        int offering_id = courseOffered(courseid, sem, year);
        if(offering_id == -1)
        {
            System.out.println("Course can't be dropped.");
            return false;
        }
        String doc = "DELETE FROM student_courses WHERE course_id = '" + courseid + "' AND studentid = '" + this.email + "';";
        Statement st = this.c.createStatement();
        st.executeUpdate(doc);
        return true;
    }
    public int viewRegCourse(int sem, int year) throws SQLException {
        String dep = studentdep();
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM student_courses WHERE studentid = '" + this.email + "';");
        while(rs.next())
        {
            String courseid = rs.getString("course_id");
            String core = rs.getString("core");
            double credit = rs.getDouble("credit");
            System.out.print("CourseID: " + courseid + " Credit: " + credit + "[ ");
            if (core.equals(dep)) {
                System.out.println(" Program Core ]");
            }
            else if(core.equals("BTP"))
            {
                System.out.println(" BTP ]");
            }
            else {
                System.out.println(" Program Elective ]");
            }
        }
        return 1;
    }

}