package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProfessorLogin {
    String email;
    String password;
    Connection c;
    PrintStream p;

    Scanner read;

    public ProfessorLogin (String email, String password, Connection c, PrintStream p)
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
            System.out.println("[1]  Offer Course");
            System.out.println("[2]  View My current Offerings");
            System.out.println("[3]  View All Offerings");
            System.out.println("[4]  Withdraw Course");
            System.out.println("[5]  Current Registrations in Course");
            System.out.println("[6]  Update Grade for course");
            System.out.println("[7]  View Course Catalog");
            System.out.println("[8]  Change Password");
            System.out.println("[9]  Logout");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            String fin_option = check_regex(option, "[1-8]");
            switch (fin_option) {
                case "1":
                    OfferCourse();
                    break;
                case "2":
                    viewOfferingmine();
                    break;
                case "3":
                    viewOfferCourse();
                    break;
                case "4":
                    withdrawCourse();
                    break;
                case "5":
                    viewCourseRegist();
                    break;
                case "6":
                    updateGrade();
                    break;
                case "7":
                    viewCatalog();

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
    public void OfferCourse() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Offer Courses")) {
            System.out.println("Offering Course: \n");
            System.out.print("Enter Course ID: ");
            String id = read.next();
            System.out.print("Enter min CGPA (enter 0 if open for all): ");
            double cg = read.nextDouble();
            Professor professor = new Professor(this.email, this.c, this.p);
            professor.offerCourse(cg, id, systemServices.getsem(), systemServices.getyear());
        }
        else
        {
            System.out.println("Can't Offer course after/before Offering Period..");

        }
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    void viewOfferingmine() throws CsvValidationException, SQLException, IOException {
        System.out.println("Offered Courses: \n");
        Professor professor = new Professor(email, c, p);
        professor.viewOfferCourse();
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public  void viewCatalog() throws CsvValidationException, SQLException, IOException {
        System.out.println("Here is the catalog view: ");
        Professor professor = new Professor(email,c, p);
        professor.catalogView();
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public  void viewOfferCourse() throws SQLException, CsvValidationException, IOException {
        cls();
        Admin admin = new Admin(this.email, this.c, this.p);
        admin.courseOfferView();
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void viewCourseRegist() throws SQLException, CsvValidationException, IOException {
        Admin admin = new Admin(this.email, this.c , this.p);
        System.out.println("Enter Course ID: ");
        String id = read.next();
        System.out.println("Here are all registered Student this sem: ");
        admin.viewCourseRegis(id);
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void withdrawCourse() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Offer Courses")) {
            System.out.println("Withdrawing Course.\n");
            System.out.print("Enter Course ID you want to withdraw.: ");
            String id = read.next();
            Professor professor = new Professor(email, c, p);
            professor.removeCourse(id, systemServices.getsem(), systemServices.getyear());
        }
        else
        {
            System.out.println("Can't remove offered course after/before Offering Period..");
        }
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void updateGrade() throws CsvValidationException, SQLException, IOException {
        // under work
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        cls();
        if(state.equals("Grade Submission")) {
            System.out.println("Update Grades..\n");
            System.out.print("Enter path of csv file : ");
            String path = read.next();
            System.out.print("Enter Course id: ");
            String id = read.next();
            Professor professor = new Professor(email, c, p);
            professor.updateGrade(path, id, systemServices.getsem(), systemServices.getyear());
        }
        else
        {
            System.out.println("You can't update grade in this phase: " + state);
        }
        System.out.println("\nPress any key to continue");
        read.next();
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
