package org.example;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        student.catalogView();
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
    void courseOfferView() throws FileNotFoundException, SQLException {
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
        Student student = new Student("admin@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_offering (course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ( 'FS101', 'FS', 2020, 1, 'ABC', 0.0, 'CSE', 3,4,4,1);");
        assertEquals(1, student.courseOfferView());
        st.executeUpdate("DELETE from course_offering where course_id = 'FS101';");
    }

    @Test
    void credRegis() throws SQLException, FileNotFoundException {

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 1, 'FS101' , 'CSE' , 8, 3.5);");
        assertEquals(3.5,student.credRegis(1, 2020));
        st.executeUpdate("DELETE FROM student_courses where studentid = 'abc@iitrpr.ac.in';");
    }

    @Test
    void prevCreds() throws FileNotFoundException, SQLException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student VALUES ('abc@iitrpr.ac.in' , 'abc', 'CSE', 2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS101' , 'CSE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS201' , 'EE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS301' , 'BTP', 8, 3, 1,2020);");
        assertEquals(11.25, student.prevCreds(1, 2020));
        st.executeUpdate("DELETE FROM student WHERE email_id = 'abc@iitrpr.ac.in';");
        st.executeUpdate("DELETE FROM final_data WHERE studentid = 'abc@iitrpr.ac.in';");
    }

    @Test
    void creditlimit() throws FileNotFoundException, SQLException {

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_offering ( course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ('FS103', 'FS', 2020, 1, 'abc', 0.0, 'CSE', 3,2, 1, 4);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 1, 'FS101' , 'CSE' , 8, 3.5);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 2, 'FS102' , 'CSE' , 8, 4);");

        assertTrue(student.creditlimit(1,2020, 3));
        st.executeUpdate("DELETE FROM student_courses where studentid = 'abc@iitrpr.ac.in';");
    }

    @Test
    void courseOffered() throws FileNotFoundException, SQLException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_offering ( course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ('FS103', 'FS', 2020, 1, 'abc', 0.0, 'CSE', 3,2, 1, 4);");

        assertNotEquals(-1, student.courseOffered("FS103",1, 2020));
        st.executeUpdate("DELETE FROM course_offering where course_id = 'FS103';");
    }

    @Test
    void courseRegistered() throws FileNotFoundException, SQLException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assertFalse(student.courseRegistered("FS103"));

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        assertEquals(0.0, student.getCgpa());
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS301' , 'CSE', 8, 3, 1,2020);");
        assertEquals(8.0, student.getCgpa());
        st.executeUpdate("DELETE FROM final_data WHERE studentid = 'abc@iitrpr.ac.in';");

    }

    @Test
    void courseDone() throws FileNotFoundException, SQLException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assertFalse(student.courseDone("FS101"));
    }

    @Test
    void registerCourse() throws SQLException, FileNotFoundException
    {

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        assertFalse(student.registerCourse("FS101", 1, 2020));
        st.executeUpdate("INSERT INTO course_offering ( course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ('FS103', 'FS', 2020, 1, 'abcd', 0.0, 'CSE', 3,2, 1, 4);");
        assertTrue(student.registerCourse("FS103", 1, 2020));
        st.executeUpdate("DELETE FROM course_offering where course_id = 'FS103';");
    }
    @Test
    void studentdep() {
    }

    @Test
    void viewRecord() throws SQLException, FileNotFoundException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student VALUES ('abc@iitrpr.ac.in' , 'abc', 'CSE', 2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS101' , 'CSE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS201' , 'EE', 8, 3, 1,2020);");
        st.executeUpdate("INSERT INTO final_data VALUES ('abc@iitrpr.ac.in', 'CS301' , 'BTP', 8, 3, 1,2020);");
        assertEquals(1, student.viewRecord() );
        st.executeUpdate("DELETE FROM student WHERE email_id = 'abc@iitrpr.ac.in';");
        st.executeUpdate("DELETE FROM final_data WHERE studentid = 'abc@iitrpr.ac.in';");

    }

    @Test
    void dropCourse() throws FileNotFoundException, SQLException {
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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        assertFalse(student.dropCourse("FS101", 1, 2020));
        st.executeUpdate("INSERT INTO course_offering ( course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ('FS103', 'FS', 2020, 1, 'abcd', 0.0, 'CSE', 3,2, 1, 4);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 3, 'FS103' , 'CSE' , 8, 4);");
        assertTrue(student.dropCourse("FS103", 1, 2020));
        st.executeUpdate("DELETE FROM course_offering where course_id = 'FS103';");
    }

    @Test
    void viewRegCourse() throws SQLException, FileNotFoundException {

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
        Student student = new Student("abc@iitrpr.ac.in", c, log);
        assert c != null;
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO student VALUES ('abc@iitrpr.ac.in' , 'abc', 'CSE', 2020);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 1, 'FS101' , 'CSE' , 8, 3.5);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 2, 'FS102' , 'BTP' , 8, 3.5);");
        st.executeUpdate("INSERT INTO student_courses VALUES('abc@iitrpr.ac.in', 3, 'FS103' , 'EE' , 8, 3.5);");

        assertEquals(1, student.viewRegCourse(1, 2020));
        st.executeUpdate("DELETE FROM student WHERE email_id = 'abc@iitrpr.ac.in';");
        st.executeUpdate("DELETE FROM student_courses WHERE studentid = 'abc@iitrpr.ac.in';");

    }
}