package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.TopStudentDTO;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectToppersService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<TopStudentDTO> getTop3StudentsPerSubjectPerYear() {
        List<Object[]> rawResults = gradeRepository.findAllSortedForTop3();

        List<TopStudentDTO> result = new ArrayList<>();

        for (Object[] row : rawResults) {
            if (row.length < 5) continue;

            String subjectName = row[0] != null ? row[0].toString() : "Unknown Subject";
            String studentName = row[1] != null ? row[1].toString() : "Unknown Student";

            int rollNumber = 0;
            int marks = 0;
            int year = 0;

            try {
                rollNumber = Integer.parseInt(row[2].toString());
                marks = Integer.parseInt(row[3].toString());
                year = Integer.parseInt(row[4].toString());
            } catch (Exception e) {
                continue; // Skip invalid row
            }

            result.add(new TopStudentDTO(subjectName, studentName, rollNumber, marks, year));
        }

        return result;
    }
}
