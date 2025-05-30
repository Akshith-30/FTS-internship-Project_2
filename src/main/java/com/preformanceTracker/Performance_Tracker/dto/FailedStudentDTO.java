package com.preformanceTracker.Performance_Tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailedStudentDTO {
    private String name;
    private String rollNumber;
    private int year;
    private String section;
    private Map<String, Integer> subjectMarks; // subject -> marks
}


