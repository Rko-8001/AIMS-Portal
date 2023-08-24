package org.example;


import com.opencsv.exceptions.CsvValidationException;

import javax.crypto.spec.PSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ClassLoadingMXBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminLogin {

    String email;
    String password;
    Connection c;
    PrintStream p;

    Scanner read;
    public AdminLogin (String email, String password, Connection c, PrintStream p)
    {
        this.email = email;
        this.password = password;
        this.c = c;
        this.p = p;
        this.read = new Scanner(System.in);
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
    public void cls()
    {
        System.out.println("\033[H\033[2J");
    }
    public void home() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c,p);
        while(true) {
            System.out.println("Welcome, Logged In as: ADMIN");
            System.out.println("Current Status: " + systemServices.getstatus());
            System.out.println("\n");
            System.out.println("Press Corresponding number key for navigation: ");
            System.out.println("[A]  Change Sem Status: " + systemServices.nextPhase());
            System.out.println("[1]  Add Student");
            System.out.println("[2]  Add Faculty");
            System.out.println("[3]  Add/Drop Course");
            System.out.println("[4]  View Students List");
            System.out.println("[5]  View Faculty List");
            System.out.println("[6]  View Course Catalogue");
            System.out.println("[7]  View Course Offerings");
            System.out.println("[8]  Degree Completion Check");
            System.out.println("[9]  View Student Record and Generate Transcript");
            System.out.println("[10] View Student Registrations");
            System.out.println("[11] View particular course Registrations");
            System.out.println("[12] Logout");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            String fin_option = check_regex(option, "[1-9]|A|a|10|11|12");
            switch (fin_option) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    addProfessor();
                    break;
                case "3":
                    addDropCourse();
                    break;
                case "4":
                    showStudents();
                    break;
                case "5":
                    showProfessors();
                    break;
                case "6":
                    viewCourseCatalog();
                    break;
                case "7":
                    viewOfferCourse();
                    break;
                case "8":
                    //under work
                    checkDegree();
                    break;
                case "9":
                    viewStudentRecord();
                    break;
                case "10":
                    viewStudentRegist();
                    break;
                case "11":
                    viewCourseRegist();
                    break;
                case "12":
                    logout();
                    return;
                case "A":
                    systemServices.changeStatus();
                    break;
            }
        }
    }
    public void addStudent() throws SQLException, CsvValidationException, IOException {
        cls();
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Academic Session")) {
            System.out.println("1. Add Student\n");

            System.out.println("[1] Add Student manually");
            System.out.println("[2] Add Student via .csv");
            System.out.println("Press any other key to exit..");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            Admin admin = new Admin(this.email, this.c, this.p);
            if (option.equals("1")) {
                cls();
                System.out.println("Adding Student Manually..\n");
                System.out.print("Enter name: ");
                String name = read.next();
                System.out.print("Enter email: ");
                String email = read.next();
                System.out.print("Enter entryNo: ");
                String entryno = read.next();
                System.out.print("Enter Department: ");
                String dept = read.next();
                System.out.print("Enter Batch: ");
                int batch = read.nextInt();
                // add manually
                admin.addStudent(email, name, entryno, dept, batch);

                System.out.println("Press any key to continue");
                read.next();
                cls();

            } else if (option.equals("2")) {
                // add via csv file
                cls();
                System.out.println("Adding Student via csv file..\n\n");
                System.out.print("Enter path: ");
                String path = read.next();
                admin.addStudentscsv(path);
                System.out.println("\n\nStudent added via csv file..");

                System.out.println("Press any key to continue");
                read.next();
                cls();
//            /home/Yadwinder/IdeaProjects/midsem/src/log
            }
        }
        else {
            System.out.println("Semester Phase doesn't permits.");
            System.out.println("Press any key to continue");
            read.next();
            cls();
        }
        cls();
        home();
    }
    public void addProfessor() throws SQLException, CsvValidationException, IOException {

        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Academic Session")) {

            cls();
            System.out.println("2. ADD Professor\n");

            System.out.println("[1] Add Professor manually");
            System.out.println("[2] Add Professor via .csv");
            System.out.println("Press any other key to exit..");

            System.out.println("\n\n");
            System.out.println("Input: ");
            String option = read.next();
            Admin admin = new Admin(this.email, this.c, this.p);
            if (option.equals("1")) {
                cls();
                System.out.println("Adding Professor Manually..\n");
                System.out.print("Enter name: ");
                String name = read.next();
                System.out.print("Enter email: ");
                String email = read.next();
                System.out.print("Enter Department: ");
                String dept = read.next();
                // add manually
                admin.addProfessor(email, name, dept);

                System.out.println("Press any key to continue");
                read.next();
                cls();

            } else if (option.equals("2")) {
                // add via csv file
                cls();
                System.out.println("Adding Professor via csv file..\n");
                System.out.print("Enter path: ");
                String path = read.next();
                admin.addProfessorcsv(path);

                System.out.println("\n\nProfessor added via csv file..");

                System.out.println("Press any key to continue");
                read.next();
                cls();
//            /home/Yadwinder/IdeaProjects/midsem/src/log
            }
        }
        else
        {
            System.out.println("Semester Phase doesn't permit.");
            System.out.println("Press any key to continue");
            read.next();
            cls();
        }
        cls();
        home();
    }
    public void addDropCourse() throws SQLException, CsvValidationException, IOException {


        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Academic Session")) {

            cls();
            System.out.println("3. Add/ Drop Course..");
            System.out.println("\n");

            System.out.println("[1] Add Course");
            System.out.println("[2] Remove Course");
            System.out.println("Press any other key to exit");
            System.out.println("\n\n");

            System.out.print("Input: ");
            String option = read.next();

            Admin admin = new Admin(email, c, p);
            if (option.equals("1")) {
                // add course
                cls();
                System.out.println("Adding Course to catalog..\n");
                System.out.println("Enter Course ID: ");
                String courseid = read.next();
                System.out.println("Enter Course name: ");
                String coursename = read.next();

                System.out.print("Enter L-T-P-C separated by space: ");
                int l = read.nextInt();
                int t = read.nextInt();
                int p = read.nextInt();
                double cre = read.nextDouble();
                System.out.print("Core for Which Department: ");
                String core = read.next();
                admin.addCourse(courseid, coursename, l, t, p, cre, core);

                System.out.println("Press any key to continue");
                read.next();
                cls();
            } else if (option.equals("2")) {
                cls();
                System.out.println("Deleting course from catalog..\n");
                System.out.print("Enter Course Id to Delete: ");
                String id = read.next();
                admin.removeCourse(id);

                System.out.println("Press any key to continue");
                read.next();
                cls();
            }
        }
        else
        {
            System.out.println("Semester Phase doesn't perimit..");
            System.out.println("Press any key to continue");
            read.next();
            cls();
        }
        cls();
        home();

    }
    public void showStudents() throws CsvValidationException, SQLException, IOException {
        cls();
        Admin admin = new Admin(email, c, p);
        System.out.println("Students List: \n");
        admin.showStudents();

        System.out.println("Press any key to continue");
        read.next();
        cls();
        home();
    }
    public void showProfessors() throws CsvValidationException, SQLException, IOException {
        cls();
        Admin admin = new Admin(email, c, p);
        System.out.println("Faculty List: \n\n");
        admin.showProfessors();
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void viewCourseCatalog() throws SQLException, CsvValidationException, IOException {
        Admin admin = new Admin(email, c, p);
        System.out.println("Course Catalog: ");
        admin.catalogView();
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public  void viewOfferCourse() throws SQLException, CsvValidationException, IOException {
        SystemServices systemServices = new SystemServices(c, p);
        String state = systemServices.getstatus();
        if(state.equals("Offer Courses")) {
            cls();
            Admin admin = new Admin(this.email, this.c, this.p);
            admin.courseOfferView();

        }
        else
        {
            System.out.println("Can't view offered courses during offering.");
        }
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void viewStudentRecord() throws IOException, SQLException, CsvValidationException {
        FileOutputStream abc= new FileOutputStream("/home/Yadwinder/IdeaProjects/midsem/src/log/transcript.txt");
        PrintStream tcp=new PrintStream(abc);
        System.out.print("Enter Student Email: ");
        String id = read.next();

        Admin admin = new Admin(this.email, this.c , tcp);

        System.out.println("Here is " + id + " Data: ");
        tcp.println("Here is " + id + " Data: ");
        System.out.println("CGPA: " + admin.getCgpa(id));
        tcp.println("CGPA: " + admin.getCgpa(id));

        admin.viewStudentRecord(id);
        System.out.println("Transcript Generated.. Path: /home/Yadwinder/IdeaProjects/midsem/src/log/transcript.txt");
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
    public void viewStudentRegist() throws SQLException, CsvValidationException, IOException {
        Admin admin = new Admin(this.email, this.c , this.p);
        System.out.println("Enter Student ID: ");
        String id = read.next();
        System.out.println("Here are all registration for this sem: ");
        admin.viewStudentRegis(id);
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

    public void logout() throws CsvValidationException, SQLException, IOException {
        Login login = new Login(c, p);
        login.loginhome();
    }

    public void checkDegree() throws CsvValidationException, SQLException, IOException {
        Admin admin = new Admin(this.email, this.c , this.p);
        System.out.println("Enter Course ID: ");
        String id = read.next();
        if(admin.check_graduation(id))
        {
            System.out.println("Student: " + id + " have met all condition is ready to graduate..");
        }
        else
        {
            System.out.println("Student: " + id + " haven't met all condition is not ready to graduate..");
        }
        System.out.println("\nPress any key to continue");
        read.next();
        cls();
        home();
    }
}
