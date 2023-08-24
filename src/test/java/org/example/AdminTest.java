package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void catalogView() throws FileNotFoundException, SQLException {
        FileOutputStream logs=new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log=new PrintStream(logs);
        Connection c;
        try
        {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);

            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS101', 'Softie1', 3, 1, 3, 1.5, 'CSE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS102', 'Softie2', 3, 1, 3, 2.5, 'EE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS103', 'Softie3', 3, 1, 3, 3.5, 'CE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS104', 'Softie4', 3, 1, 3, 4.5, 'MNC');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS105', 'Softie5', 3, 1, 3, 5.5, 'MMB');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS106', 'Softie6', 3, 1, 3, 6, 'CH');");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        Admin admin = new Admin("abc@iitrpr.ac.in", c, log);
        admin.catalogView();
        try
        {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS101';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS102';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS103';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS104';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS105';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS106';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void removeCourse() throws FileNotFoundException, SQLException {
        FileOutputStream logs=new FileOutputStream("/home/Yadwinder/IdeaProjects/aims/src/log/log.txt");
        PrintStream log=new PrintStream(logs);
        Connection c;
        try
        {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);

            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS101', 'Softie1', 3, 1, 3, 1.5, 'CSE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS102', 'Softie2', 3, 1, 3, 2.5, 'EE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS103', 'Softie3', 3, 1, 3, 3.5, 'CE');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS104', 'Softie4', 3, 1, 3, 4.5, 'MNC');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS105', 'Softie5', 3, 1, 3, 5.5, 'MMB');");
            stmt.executeUpdate("INSERT INTO course_catalogue VALUES ('TS106', 'Softie6', 3, 1, 3, 6, 'CH');");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        Admin admin = new Admin("abc@iitrpr.ac.in", c, log);
        assertEquals("Course Not Present.", admin.removeCourse("TS107"));
        assertEquals("Course Removed.", admin.removeCourse("TS101"));
        try
        {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS101';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS102';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS103';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS104';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS105';");
            stmt.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'TS106';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void addCourse() throws FileNotFoundException, SQLException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            // nothing
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assertNotEquals(-1, admin.addCourse("TS101", "TS", 3,1,2,1.5, "CSE"));
        assertEquals(-1, admin.addCourse("TS101", "TS", 3,1,2,1.5, "CSE"));

        admin.removeCourse("TS101");
    }

    @Test
    void addProfessorcsv() throws IOException, CsvValidationException, SQLException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);

        assertEquals("Added all",admin.addProfessorcsv("/home/Yadwinder/IdeaProjects/midsem/src/log/inputP.csv"));

        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("DELETE FROM professor WHERE email_id = '1007@iitrpr.ac.in'; ");
        st.executeUpdate("DELETE FROM users WHERE email = '1007@iitrpr.ac.in';");
    }

    @Test
    void showProfessors() throws SQLException, FileNotFoundException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assertEquals(1, admin.showProfessors());
    }

    @Test
    void addStudentscsv() throws IOException, SQLException, CsvValidationException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);

        assertEquals("Added all",admin.addStudentscsv("/home/Yadwinder/IdeaProjects/midsem/src/log/input.csv"));

        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("DELETE FROM student WHERE email_id = '2020TST1007@iitrpr.ac.in'; ");
        st.executeUpdate("DELETE FROM users WHERE email = '2020TST1007@iitrpr.ac.in';");

    }

    @Test
    void showStudents() throws FileNotFoundException, SQLException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assertEquals(1, admin.showStudents());
    }

    @Test
    void courseOfferView() throws SQLException, FileNotFoundException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_offering (course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ( 'FS101', 'FS', 2020, 1, 'ABC', 0.0, 'CSE', 3,4,4,1);");
        assertEquals(1, admin.courseOfferView());
        st.executeUpdate("DELETE from course_offering where course_id = 'FS101';");
    }

    @Test
    void viewStudentRecord() throws FileNotFoundException, SQLException {
        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student VALUES ('abc@iitrpr.ac.in' , 'abc', 'CSE', 2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS101' , 'CSE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS201' , 'EE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS301' , 'BTP', 8, 3, 1,2020);");
        assertEquals(1, admin.viewStudentRecord("abc@iitrpr.ac.in") );
        st.executeUpdate("DELETE FROM student WHERE email_id = 'abc@iitrpr.ac.in';");
        st.executeUpdate("DELETE FROM final_data WHERE studentid = 'abc@iitrpr.ac.in';");
    }

    @Test
    void getCgpa() throws SQLException, FileNotFoundException {

        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        assertEquals(0.0, admin.getCgpa("abc@iitrpr.ac.in"));
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS301' , 'CSE', 8, 3, 1,2020);");
        assertEquals(8.0, admin.getCgpa("abc@iitrpr.ac.in"));
        st.executeUpdate("DELETE FROM final_data WHERE studentid = 'abc@iitrpr.ac.in';");


    }

    @Test
    void viewStudentRegis() throws FileNotFoundException, SQLException {

        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 1, 'FS101' , 'CSE' , 8, 3.5);");
        admin.viewStudentRegis("abc@iitrpr.ac.in");
        st.executeUpdate("DELETE FROM student_courses where studentid = 'abc@iitrpr.ac.in';");

    }

    @Test
    void viewCourseRegis() throws FileNotFoundException, SQLException {

        FileOutputStream logs = new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/log.txt");
        PrintStream log = new PrintStream(logs);
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
        } catch (Exception e) {
            System.out.println(e);
        }
        Admin admin = new Admin("admin@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student_courses VALUES ('abc@iitrpr.ac.in', 1, 'FS101' , 'CSE' , 8, 3.5);");
        admin.viewCourseRegis("FS101");
        st.executeUpdate("DELETE FROM student_courses where studentid = 'abc@iitrpr.ac.in';");
    }
}