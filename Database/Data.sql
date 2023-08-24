CREATE TABLE users (
    email   VARCHAR(100)    NOT NULL,
    name    VARCHAR(100)    NOT NULL,
    entryno VARCHAR(100)   ,
    mobileno VARCHAR(100)   ,
    password VARCHAR(100)    NOT NULL,
    role    INTEGER         NOT NULL,

    PRIMARY KEY (email)
);
CREATE TABLE department (
  name    VARCHAR(20) NOT NULL,
  
  PRIMARY KEY (name)
);
CREATE TABLE student (
    email_id          VARCHAR(100)  NOT NULL,
    name              VARCHAR(100)  NOT NULL,
    dept_name         VARCHAR(100)  NOT NULL,
    batch             INTEGER       NOT NULL,
    
    PRIMARY KEY (email_id),
    FOREIGN KEY (dept_name) REFERENCES department(name)
);
CREATE TABLE course_catalogue (
    course_id     VARCHAR(5)    NOT NULL,
    name          VARCHAR(100)  NOT NULL,
    lectures      INTEGER       NOT NULL,
    tutorials     INTEGER       NOT NULL,
    practice      INTEGER       NOT NULL,
    credit        DECIMAL       NOT NULL,
    core          VARCHAR(100)  NOT NULL,  
    PRIMARY KEY (course_id)
);
CREATE TABLE prerequisites (
    course_id           VARCHAR(5) NOT NULL,
    prerequisite_id     VARCHAR(5) NOT NULL,
    
    FOREIGN KEY (course_id) REFERENCES course_catalogue(course_id),
    FOREIGN KEY (prerequisite_id) REFERENCES course_catalogue(course_id)
);
CREATE TABLE professor (
    email_id        VARCHAR(100)  NOT NULL, 
    name            VARCHAR(100)  NOT NULL, 
    dept_name       VARCHAR(100)  NOT NULL, 
    
    PRIMARY KEY (email_id),
    FOREIGN KEY (dept_name) REFERENCES department(name)
);
CREATE TABLE course_offering(
    offering_id       SERIAL,
    course_id         VARCHAR(5)      NOT NULL,
    name              VARCHAR(100)    NOT NULL,
    year              INTEGER         NOT NULL,
    semester          INTEGER         NOT NULL,
    instructor_id     VARCHAR(100)    NOT NULL,
    cgpa_req          DECIMAL         NOT NULL,
    core              VARCHAR(100)    NOT NULL,
    l                 INTEGER         NOT NULL,
    t                 INTEGER         NOT NULL,
    p                 INTEGER         NOT NULL,
    c                 DECIMAL         NOT NULL,

    PRIMARY KEY (offering_id)
);

CREATE TABLE student_courses (
    studentid           VARCHAR(100)     NOT NULL,
    offering_id         INTEGER          NOT NULL,
    course_id           VARCHAR(5)       NOT NULL,
    core                VARCHAR(100)     NOT NULL,
    grade               INTEGER          NOT NULL,
    credit              DECIMAL          NOT NULL,

    PRIMARY KEY (studentid, offering_id)

);
CREATE TABLE final_data (
    studentid       VARCHAR(100) NOT NULL,
    course_id       VARCHAR(5)   NOT NULL,
    core            VARCHAR(100) NOT NULL,
    grade           INTEGER      NOT NULL,
    credit          DECIMAL      NOT NULL,
    sem             INTEGER      NOT NULL,
    year            INTEGER      NOT NULL
);
INSERT INTO department VALUES   ('CSE');
INSERT INTO department VALUES   ('EE');
INSERT INTO department VALUES   ('MNC');
INSERT INTO department VALUES   ('CE');
INSERT INTO department VALUES   ('CH');
INSERT INTO department VALUES   ('ME');
INSERT INTO department VALUES   ('MMB');

CREATE TABLE systeminfo (
    state VARCHAR(100) NOT NULL,
    sem   INTEGER      NOT NULL,
    year  INTEGER      NOT NULL,

    PRIMARY KEY (state)
);
INSERT INTO systeminfo VALUES ('Academic Session Starts..', 1, 2020);

INSERT INTO department VALUES   ('CSE');
INSERT INTO department VALUES   ('EE');
INSERT INTO department VALUES   ('MNC');
INSERT INTO department VALUES   ('CE');
INSERT INTO department VALUES   ('CH');
INSERT INTO department VALUES   ('ME');
INSERT INTO department VALUES   ('MMB');


//        -- Types of Phase in a Semester
//        -- Academic Session
//        -- Offer Courses
//        -- Register courses
//        -- Classes Start
//        -- Grade Submission
//        -- Semester Ends




















