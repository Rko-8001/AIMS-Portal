package org.example;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SystemServices {
    Connection c;
    PrintStream p;

    public  SystemServices (Connection c, PrintStream p)
    {
        this.c =  c;
        this.p = p;
    }
    public void updateStatus(String status) throws SQLException {
        String que = "UPDATE systeminfo SET state = '" + status + "' where true;";
        Statement st= this.c.createStatement();
        st.executeUpdate(que);
    }
    public void changeSY(int sem, int year) throws SQLException {
        String que = "UPDATE systeminfo SET sem = " + sem + ", year = " + year + " where true;";
        Statement st= this.c.createStatement();
        st.executeUpdate(que);
    }
    public int getsem() throws SQLException {
        String s = "SELECT * FROM systeminfo;";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(s);

        return rs.next() ? rs.getInt("sem") : -1;
    }
    public int getyear() throws SQLException {
        String s = "SELECT * FROM systeminfo;";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(s);
        return rs.next() ? rs.getInt("year") : -1;
    }
    public String getstatus() throws SQLException {
        String s = "SELECT * FROM systeminfo;";
        Statement st = this.c.createStatement();
        ResultSet rs = st.executeQuery(s);

        return rs.next()? rs.getString("state") : "";
    }
    public String nextPhase() throws SQLException {
        String now = getstatus();
        switch (now) {
            case "Academic Session":
                return "Offer Courses";
            case "Offer Courses":
                return "Register courses";
            case "Register courses":
                return "Classes Start";
            case "Classes Start":
                return "Grade Submission";
            case "Grade Submission":
                return "Semester Ends";
            default:
                return "Academic Session";
        }
//        -- Types of Phase in a Semester
//        -- Academic Session
//        -- Offer Courses
//        -- Register courses
//        -- Classes Start
//        -- Grade Submission
//        -- Semester Ends
    }
    public String changeStatus() throws SQLException {
        String futu = nextPhase();
        if (futu.equals("Academic Session"))
        {
            int s = getsem();
            int y = getyear();
            if (s == 2) {
                changeSY(1, y + 1);
            } else {
                changeSY(2, y);
            }
        }
        updateStatus(futu);
        return nextPhase();
    }
}
