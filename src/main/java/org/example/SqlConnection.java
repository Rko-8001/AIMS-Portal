package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {
    Connection c;
    public SqlConnection()
    {
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
            System.out.println("Connected..");
        }
        catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e);
        }
    }
}
