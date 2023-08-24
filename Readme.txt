Mini Project Name:  Academic Portal
Student Name:       Yadwinder Singh
Entry No:           2020csb1143


Files included:
    2020csb1143 - directory with the gradle build project
    UML.png - UML generated diagram
    Table_dia.jpeg - ER diagram
    Jacoco report directory
    Readme.txt
---------------------------------------------------------------------------------------------------------------

How to run?
    Extract the zipped "2020csb1143" folder and cd into the directory then navigate to the following directory: 2020csb1143/miniproject/
    run the following commands:
        gradle build
        gradle run --console=plain

    This will start the CLI application with the login_page
    Follow the instructions as directed to navigate else press 'Ctrl+C' to quit

For further Clarifications regarding Gradle, ping to https://docs.gradle.org/


Jacoco Report:
       open 'Coverage Report' Directory


Brief OverView:
I have implemented a database system for a university academic portal. Following are the
functionalities implemented by us:
1. Students:
    Can register/drop in a course if he/she satisfies the course requirements (prerequisites, cgpa constraint
    for example: only 8+ gpa students can register in a course)

2. Instructors:
    Can offer a course in the current semester and specify eligibility for enrolling in the course.
    Upload grades of the students who registered that course
3. Admin:
    Can add a new course to the course catalog (which can then be offered by an instructor)
    Add new students to the database
    Add new instructors to the database
    Generate transcripts of students
    Open and close add/drop window-

Further, Each student and instructor has a login. A Admin user is present.






















