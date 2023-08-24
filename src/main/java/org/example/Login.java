package org.example;

import com.opencsv.exceptions.CsvValidationException;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Login {
    Connection c;
    PrintStream p;

    Scanner read;
    public Login(Connection c, PrintStream p)
    {
        this.p = p;
        this.c = c;
        this.read = new Scanner(System.in);
    }
    public void cls()
    {
        System.out.println("\033[H\033[2J");
    }
    public String check_regex(String option, String regex)
    {
        while(!option.matches(regex))
        {
            System.out.println("Wrong Input! Enter Again. ");
            System.out.println("\nInput: ");
            option = read.next();
        }
        return option;
    }
    public void loginhome() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c,p);
        while(true) {
            System.out.println("Welcome to AIMS Portal..\n");
            System.out.println("\n");

            System.out.println("Press Corresponding number key for navigation: ");
            System.out.println("[1]  LogIn as ADMIN");
            System.out.println("[2]  LogIn as Faculty");
            System.out.println("[3]  LogIn as Student");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            String fin_option = check_regex(option, "[1-3]");
            switch (fin_option) {
                case "1":
                    logAdmin();
                    break;
                case "2":
                    logProfessor();
                    break;
                case "3":
                    logStudent();
                    break;
            }
        }
    }
    public  boolean check_user(String email, String pass, int role) throws SQLException {
        Statement st = this.c.createStatement();
        String cul = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + pass + "' AND role = " + role + ";";
        ResultSet rs = st.executeQuery(cul);
        return rs.next();
    }
    public void logAdmin() throws SQLException, CsvValidationException, IOException {
        cls();
        System.out.println("Enter ADMIN Credentials..\n");

        System.out.print("Enter email: ");
        String email = read.next();
        System.out.print("Enter Password: ");
        String pass = read.next();
        if (!check_user(email, pass, 0)) {
            System.out.println("Authentication Failed.. Try Again..");
            cls();
            loginhome();
        }
        System.out.println("Login Success");
        AdminLogin adminLogin = new AdminLogin(email, pass, c, p);
        adminLogin.home();
    }
    public void logProfessor() throws SQLException, CsvValidationException, IOException {
        cls();
        System.out.println("Enter your Credentials..\n");

        System.out.print("Enter email: ");
        String email = read.next();
        System.out.print("Enter Password: ");
        String pass = read.next();
        if (!check_user(email, pass, 1)) {
            System.out.println("Authentication Failed.. Try Again..");
            cls();
            loginhome();
        }
        System.out.println("Login Success");
        ProfessorLogin professorLogin = new ProfessorLogin(email, pass, c, p);
        professorLogin.home();
    }
    public void logStudent() throws SQLException, CsvValidationException, IOException {
        cls();
        System.out.println("Enter your Credentials..\n");

        System.out.print("Enter email: ");
        String email = read.next();
        System.out.print("Enter Password: ");
        String pass = read.next();
        if (!check_user(email, pass, 0)) {
            System.out.println("Authentication Failed.. Try Again..");
            cls();
            loginhome();
        }
        System.out.println("Login Success");
        StudentLogin studentLogin = new StudentLogin(email,pass, c, p);
        studentLogin.home();
    }
}
