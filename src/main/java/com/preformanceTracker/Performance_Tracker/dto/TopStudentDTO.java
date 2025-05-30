package com.preformanceTracker.Performance_Tracker.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopStudentDTO {

    private String subjectName;
    private String studentName;
    private int rollNumber;
    private int marks;
    private int year;

    public TopStudentDTO() {
    }

    public TopStudentDTO(String subjectName, String studentName, int rollNumber, int marks, int year) {
        this.subjectName = subjectName;
        this.studentName = studentName;
        this.rollNumber = rollNumber;
        this.marks = marks;
        this.year = year;
    }

    @Override
    public String toString() {
        return "TopStudentDTO{" +
                "subjectName='" + subjectName + '\'' +
                ", studentName='" + studentName + '\'' +
                ", rollNumber=" + rollNumber +
                ", marks=" + marks +
                ", year=" + year +
                '}';
    }
}
