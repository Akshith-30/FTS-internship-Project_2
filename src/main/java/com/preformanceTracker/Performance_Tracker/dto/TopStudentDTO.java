package com.preformanceTracker.Performance_Tracker.dto;

public class TopStudentDTO {

    private String subjectName;
    private String studentName;
    private int marks;
    private int year;

    // No-argument constructor (needed for Jackson and some frameworks)
    public TopStudentDTO() {
    }

    // All-args constructor
    public TopStudentDTO(String subjectName, String studentName, int marks, int year) {
        this.subjectName = subjectName;
        this.studentName = studentName;
        this.marks = marks;
        this.year = year;
    }

    // Getters and Setters
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "TopStudentDTO{" +
                "subjectName='" + subjectName + '\'' +
                ", studentName='" + studentName + '\'' +
                ", marks=" + marks +
                ", year=" + year +
                '}';
    }
}
