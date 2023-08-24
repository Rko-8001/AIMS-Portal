package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Professor {
    String email;
    Connection c;
    PrintStream log;

    public Professor(String email, Connection cp, PrintStream lp) {
        this.email = email;
        this.c = cp;
        this.log = lp;
    }

    public void catalogView() throws SQLException {
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
            log.print(i + " CourseId: " + courseId + " Name: " + name);
            log.println(" L-T-P: " + l + "-" + t + "-" + p + " Total Credits: " + credit);
            i++;
        }

    }

    public boolean findCourse(String course_id) throws SQLException {

            Statement st = this.c.createStatement();
            String rmc = "SELECT * FROM course_catalogue WHERE course_id = " + "'" + course_id + "';";
            ResultSet rs = st.executeQuery(rmc);
            return rs.next();
    }

    public boolean alreadyOffered(String course_id, int sem, int year) throws SQLException {
            Statement st = this.c.createStatement();
            String rmc = "SELECT * FROM course_offering WHERE course_id = '" + course_id + "' AND semester = " + sem + " AND year = " + year + ";";
            ResultSet rs = st.executeQuery(rmc);
            return rs.next();
    }

    public boolean offerCourse(double cgpa, String courseId, int sem, int year) throws SQLException {
        if (!findCourse(courseId)) {
            log.println("Course not present in course catalog..");
            System.out.println("Course not present in course catalog..");
            return false;
        }
        if (alreadyOffered(courseId, sem, year)) {
            log.println("Course already offered..");
            System.out.println("Course already offered..");
            return false;
        }
        Statement st = this.c.createStatement();
        String gic = "SELECT * FROM course_catalogue WHERE course_id = '" + courseId + "';";
        ResultSet rs = st.executeQuery(gic);
        String name = "";
        String core = "";
        int l = 0, p = 0, t = 0;
        double c = -1.0;
        while (rs.next()) {
            name = rs.getString("name");
            core = rs.getString("core");
            l = rs.getInt("lectures");
            p = rs.getInt("practice");
            t = rs.getInt("tutorials");
            c = rs.getDouble("credit");
        }
        String inc = "INSERT INTO course_offering (course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ( '" + courseId + "', '" + name + "', " + year + ", " + sem + ", '" + this.email + "', " + cgpa + ", '" + core + "', " + l + "," + p + ", " + t + ", " + c + " );";
        int aou = st.executeUpdate(inc);
        System.out.println("Course Offered..");
        return aou == 1;
    }

    public int viewOfferCourse() throws SQLException {
        Statement st = this.c.createStatement();
        String rmc = "SELECT * FROM course_offering WHERE instructor_id = '" + this.email + "';";
        ResultSet rs = st.executeQuery(rmc);
        while (rs.next()) {
            String offering_id = rs.getString("offering_id");
            String course_id = rs.getString("course_id");
            String name = rs.getString("name");
            log.println(offering_id + ") ID: " + course_id + " Name: " + name );
        }
        return 1;
    }
    public String courseOffered(String courseid, int sem, int year) throws SQLException {

        Statement st = this.c.createStatement();
        String cot = "SELECT * FROM course_offering WHERE course_id = '" + courseid + "' AND semester = " + sem + " AND year = " + year + ";";
        ResultSet rs = st.executeQuery(cot);
        return rs.next() ? rs.getString("instructor_id") : " ";
    }
    public boolean removeCourse(String courseId, int sem, int year) throws SQLException {
        String insid = courseOffered(courseId,sem, year);
        System.out.println(insid);
        if(!insid.equals(this.email))
        {
            System.out.println("You haven't offered this course.");
            return false;
        }
        if(!alreadyOffered(courseId, sem, year))
        {
            System.out.println("Course not offered!!");
            return false;
        }
        Statement st = this.c.createStatement();
        st.executeUpdate("DELETE FROM course_offering WHERE course_id = '" + courseId + "';" );
        System.out.println("Course Removed..");
        return true;
    }
    public int updateGrade(String path, String courseid, int sem, int year) throws IOException, CsvValidationException, SQLException {
        //soon updating
        FileReader filereader = new FileReader(path);
        CSVReader csvreader= new CSVReader(filereader);
        String[] nextrecord;

        while((nextrecord=csvreader.readNext())!=null)
        {
            String studentid = nextrecord[0];
            int grade = Integer.parseInt(nextrecord[1]);
            String fr = "SELECT * from student_courses where course_id = '" + courseid + "' AND studentid = '" + studentid + "';";
            Statement st = this.c.createStatement();
            ResultSet rs = st.executeQuery(fr);
            String core = "";
            double credit = 0.0;
            while (rs.next())
            {
                core = rs.getString("core");
                credit = rs.getDouble("credit");
            }
            st.executeUpdate("DELETE from student_courses where course_id = '" + courseid + "' AND studentid = '" + studentid + "';");
            st.executeUpdate("insert into final_data values ('" + studentid + "', '" + courseid + "', '" + core + "', " + grade + ", " + credit + ", " + sem + ", " + year + ");");

        }
        return 1;
    }


}