package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentLogin {
    String email;
    String password;
    Connection c;
    PrintStream p;

    Scanner read;
    public StudentLogin (String email, String password, Connection c, PrintStream p)
    {
        this.email = email;
        this.password = password;
        this.c = c;
        this.p = p;
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
    public void home() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c,p);
        while(true) {
            System.out.println("Welcome, Logged In as: " + this.email);
            System.out.println("Current Status: " + systemServices.getstatus());
            System.out.println("\n");
            System.out.println("Press Corresponding number key for navigation: ");
            System.out.println("[1]  View Grades.");
            System.out.println("[2]  Current Offering");
            System.out.println("[3]  View Course Catalog");
            System.out.println("[4]  Register Course");
            System.out.println("[5]  Drop Course");
            System.out.println("[6]  Compute CGPA");
            System.out.println("[7]  My course Registrations");
            System.out.println("[8]  Change Password");
            System.out.println("[9]  Logout");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            String fin_option = check_regex(option, "[1-9]");
            switch (fin_option) {
                case "1":
                    viewGrades();
                    break;
                case "2":
                    offerCourses();
                    break;
                case "3":
                    viewCatalog();
                    break;
                case "4":
                    registerCourse();
                    break;
                case "5":
                    dropCourse();
                    break;
                case "6":
                    getCGPA();
                    break;
                case "7":
                    viewRegist();
                    break;
                case "8":
                    changePass();
                    break;
                case "9":
                    logout();
                    break;
            }
        }
    }
    public  void viewGrades() throws SQLException, CsvValidationException, IOException {
        cls();
        System.out.println("Viewing Grades.. \n");
        Student student = new Student(this.email, c, p);
        student.viewRecord();

        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }
    public void  offerCourses() throws CsvValidationException, SQLException, IOException {
        cls();
        System.out.println("Offered Courses.. \n");
        Student student = new Student(this.email, c, p);
        student.courseOfferView();

        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }
    public void viewCatalog() throws CsvValidationException, SQLException, IOException {
        cls();
        System.out.println("Course Catalog.. \n");
        Student student = new Student(this.email, c,p );
        student.catalogView();

        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }
    public void registerCourse() throws CsvValidationException, SQLException, IOException {
        cls();
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Register courses")) {
            System.out.println("Register for a Course..\n");
            Student student = new Student(email, c, p);
            System.out.print("Enter the Course ID: ");
            String id = read.next();
            student.registerCourse(id, systemServices.getsem(), systemServices.getyear());
        }
        else
        {
            System.out.println("You can't register a course in this phase: " + state);
        }
        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }

    public void dropCourse() throws CsvValidationException, SQLException, IOException {
        cls();
        cls();
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Register courses")) {
            System.out.println("Drop Course.. \n");

            Student student = new Student(email, c, p);
            System.out.print("Enter the Course ID: ");
            String id = read.next();
            student.dropCourse(id, systemServices.getsem(), systemServices.getyear());
        }
        else
        {
            System.out.println("You can't drop a course in this phase: " + state);
        }
        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }
    public void getCGPA() throws CsvValidationException, SQLException, IOException {
        cls();
        Student student = new Student( email,c ,p);
        double gpa = student.getCgpa();
        System.out.println("Your GPA: " + gpa + "\n");


        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();
    }

    public void viewRegist() throws CsvValidationException, SQLException, IOException {
        cls();
        System.out.println("Courses in which you are Registered..\n");

        Student student = new Student( email, c, p);
        SystemServices systemServices = new SystemServices(c, p);
        student.viewRegCourse(systemServices.getsem(), systemServices.getyear());

        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();

    }
    public void changePass() throws SQLException, CsvValidationException, IOException {
        cls();
        System.out.println("Change Password..\n");

        System.out.print("Enter Old Pass: ");
        String oldpass = read.next();
        System.out.print("Enter New Pass: ");
        String newpass = read.next();
        if(!oldpass.equals(this.password))
        {
            System.out.println("Old Password incorrect");
        }
        else
        {
            Statement st = this.c.createStatement();
            String cps = "UPDATE users SET password = '" + newpass + "' WHERE email = '" + this.email + "';";
            st.executeUpdate(cps);
            this.password = newpass;
            System.out.println("Password Updated");
        }


        System.out.println("Press any key to continue");
        read.nextLine();
        cls();
        home();

    }
    public void logout() throws CsvValidationException, SQLException, IOException {
        Login login = new Login(c, p);
        login.loginhome();
    }


}
