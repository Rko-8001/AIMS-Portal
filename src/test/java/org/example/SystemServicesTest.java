package org.example;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class SystemServicesTest {

    @Test
    void getstatus() throws SQLException {
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
            if (c == null)
            {
                System.out.println("Connection Failed");
                return;
            }
            System.out.println("Connected to Database..");
        }
        catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e);
        }
        SystemServices systemServices =  new SystemServices(c, null);
        assertNotEquals("", systemServices.getstatus());
    }

    @Test
    void changeStatus() throws SQLException {
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/";
            String connectionusername = "postgres";
            String connectionpassword = "dbms";
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, connectionusername, connectionpassword);
            if (c == null)
            {
                System.out.println("Connection Failed");
                return;
            }
            System.out.println("Connected to Database..");
        }
        catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e);
        }
        SystemServices systemServices =  new SystemServices(c, null);
        int asem = systemServices.getsem();
        int ayear = systemServices.getyear();
        String aphase = systemServices.getstatus();

        assert c != null;
        Statement st = c.createStatement();

        st.executeUpdate("DELete from systeminfo where true;");
        st.executeUpdate("insert into systeminfo values ('Semester Ends', 2, 2020);");
        assertEquals("Offer Courses", systemServices.changeStatus());
        assertEquals("Register courses", systemServices.changeStatus());
        assertEquals("Classes Start", systemServices.changeStatus());
        assertEquals("Grade Submission", systemServices.changeStatus());
        assertEquals("Semester Ends", systemServices.changeStatus());
        assertEquals("Academic Session", systemServices.changeStatus());
        assertEquals("Offer Courses", systemServices.changeStatus());
        st.executeUpdate("DELete from systeminfo where true;");
        st.executeUpdate("insert into systeminfo values ('" + aphase + "', " + asem + ", " + ayear + ");");


    }
}