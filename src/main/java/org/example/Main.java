package org.example;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException, SQLException, CsvValidationException {
        FileOutputStream logs=new FileOutputStream("/home/Yadwinder/IdeaProjects/aims/src/log/log.txt");
        PrintStream log=new PrintStream(logs);
        Scanner read = new Scanner(System.in);
        SqlConnection sqlConnection = new SqlConnection();
        Connection c = sqlConnection.c;

        Login login = new Login(c,log);
        login.loginhome();
    }

}

