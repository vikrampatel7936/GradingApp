package com.example.vikramassignment2;

public class Modal {

    int id;
    String fName;
    String lName;
    String course;
    int credit;
    double grades;

    public Modal(int id, String fName, String lName, String course, int credit, double grades) {

        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.course = course;
        this.credit = credit;
        this.grades = grades;
    }


    public Modal() {
    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getGrades() {
        return grades;
    }

    public void setGrades(double grades) {
        this.grades = grades;
    }

}
