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

class ProfessorTest {

    @Test
    void catalogView() throws SQLException, FileNotFoundException {
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
        Professor professor = new Professor("abc@iitrpr.ac.in", c, log);
        professor.catalogView();
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
    void offerCourse() throws SQLException, FileNotFoundException {
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
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        Professor professor = new Professor("abc@iitrpr.ac.in", c, log);
        assertFalse(professor.offerCourse(0.0, "FS101", 1, 2020));
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_catalogue VALUES ('FS101', 'Softie6', 3, 1, 3, 6, 'CH');");
        assertTrue(professor.offerCourse(0.0, "FS101", 1, 2020));
        assertFalse(professor.offerCourse(0.0, "FS101", 1, 2020));
        st.executeUpdate("DELETE from course_offering where instructor_id = 'abc@iitrpr.ac.in';");
        st.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'FS101';");
    }

    @Test
    void viewOfferCourse() throws SQLException, FileNotFoundException {
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
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        Professor professor = new Professor("abc@iitrpr.ac.in", c, log);
        Statement st = c.createStatement();
        st.executeUpdate("INSERT INTO course_catalogue VALUES ('FS101', 'Softie6', 3, 1, 3, 6, 'CH');");
        professor.offerCourse(0.0, "FS101", 1, 2020);
        assertEquals(1, professor.viewOfferCourse());
        professor.removeCourse("FS101", 1, 2020);
        st.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'FS101';");
    }

    @Test
    void courseOffered() throws FileNotFoundException, SQLException {
    }
    @Test
    void removeCourse() throws SQLException, FileNotFoundException {

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
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        Statement st = c.createStatement();
        Professor professor = new Professor("abc@iitrpr.ac.in", c, log);
        st.executeUpdate("INSERT INTO course_catalogue VALUES ('FS101', 'Softie6', 3, 1, 3, 6, 'CH');");
        st.executeUpdate("INSERT INTO course_catalogue VALUES ('FS102', 'Softie6', 3, 1, 3, 6, 'CH');");
        professor.offerCourse(0.0, "FS101", 1, 2020);
        st.executeUpdate("INSERT INTO course_offering (course_id, name, year, semester, instructor_id, cgpa_req, core, l, p, t, c ) VALUES ('FS102', 'FS', 2020, 1, 'abc', 0.0, 'CSE', 3,2, 1, 4);");
        assertTrue(professor.removeCourse("FS101", 1, 2020));
        assertFalse(professor.removeCourse("FS101", 1, 2020));
        assertFalse(professor.removeCourse("FS102", 1, 2020));
        st.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'FS101';");
        st.executeUpdate("DELETE FROM course_catalogue WHERE course_id = 'FS102';");
        st.executeUpdate("DELETE FROM course_offering where course_id = 'FS102'");
    }

    @Test
    void updateGrade() {
    }
}