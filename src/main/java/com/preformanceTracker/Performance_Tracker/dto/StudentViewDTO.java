package com.preformanceTracker.Performance_Tracker.dto;

import lombok.Data;

@Data
public class StudentViewDTO {
    private int id;
    private String name;
    private String rollNumber;
    private int year;
    private String section;
    private String grade;  // Grade A, B, or C based on calculated percentage
}
