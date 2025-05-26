package com.preformanceTracker.Performance_Tracker.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StudentDTO {
    private String name;
    private String rollNumber;
    private int year;
    private String section;
    private List<SubjectMarkDTO> subjects;

    public Map<Object, Object> getMarks() {
        return null;
    }
}
