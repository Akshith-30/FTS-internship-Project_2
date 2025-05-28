package com.preformanceTracker.Performance_Tracker.dto;

import lombok.Data;
import java.util.Map;
import java.util.HashMap;

@Data
public class StudentDTO {
    private String name;
    private String rollNumber;
    private int year;
    private String section;
    private Map<String, Integer> marks = new HashMap<>();  // Initialize to avoid null pointer issues
    private String grade;
    private Integer totalMarks;
}
//