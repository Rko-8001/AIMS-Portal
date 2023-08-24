# CS305 Software Engineering

**Yadwinder Singh- 2020CSB1143**

## Functions

* #### Academic Office

  * Create accounts.
  * Edit the course catalog.
  * View grade of all students.
  * Change academic event.
* #### Faculty

  * View grade of all students.
  * Register/deregister courses they would like to offer.
  * Update course grades via .csv files.
* #### Student

  * Register/deregister for a course.
    * not allowed to register for a course without clearing the pre-req.
    * not allowed to register for more than the allowed credit limit.
  * View only their grades.
  * Compute their current CGPA.
  * Generate Transcript

## Flow

* Admin will create account for some students and instructors.
* Admin can add/delete/edit some course in Course Catalog.
* Admin can change Academic Event(Session Change).
* Instructor will float/defloat some courses.
* A student can enroll some courses if he/she pass all the criteria also he/she can withdraw from a course.
* Instructor can update grades of the student.
* Student can generate his/her transcript and check for degree completion.

## How To Run

* #### To Use the Portal

  1. Go in **_AIMS_Portal_CS305\src\main\java\org\CS305\Main.java_** and click on run
  2. Use commands `</br>`
     `gradle run`
* #### For Testing

  1. Go in **_AIMS_Portal_CS305\src\test\java\users_** and click on run with code coverage `</br>`
  2. Use commands `</br>`
     `gradle build jacocotestreport`
     `gradle run`

## Assumptions

* There will be only one Admin
* The User will stick to the input format. The input should be in lower case.
* Admin can only change Core Branches for a course available in the catalog.
* If the grade is -1, it means the course has not been graded yet.
* All batches are allowed to enroll for a course offered.
* For updating a course grades the csv file should be present in **_AIMS_Portal_CS305/src/log/_**
* The grades will be given in numerical form i.e. 1,2,3..
* Grade 0 means F
* To get the degree, a student must complete 20 Program Core credits and 10 Elective credits.
* If a student is in his first or second semester of B.Tech his credit limit to enroll courses will be 21 and from third semester onwards the credit limit will be decided from the credits obtained in last two semesters.
* A student in his first semester of B.Tech does not need to complete any prerequisite and there will be no CG criteria for him/her to enroll for a course.
* Admin will decide the prerequisite for a course and for whom it is core and for whom it is elective and an instructor will only decide CGPA requirement.
* Student will generated his/her transcript himself/herself.

## Directory Structure

* All source code files are present in **_AIMS_Portal_CS305\src\main\java_**
* All testing files are present in **_AIMS_Portal_CS305\src\test\java_**
* Database code is present in **_AIMS_Portal_CS305\\Database/database.sql_**
* All UML diagrams are present in **_AIMS_Portal_CS305\Diagrams\_**
* Code Coverage file will be present in **_AIMS_Portal_CS305\Jacoco Code Coverage Report\index.html_**
* All csv files to update grades should be present in **_AIMS_Portal_CS305\Files\CSV Files_**
* The generated transcripts will be present in **_AIMS_Portal_CS305\Files\Transcripts_**
