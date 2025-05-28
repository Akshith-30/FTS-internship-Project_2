package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import com.preformanceTracker.Performance_Tracker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
    }


    public Map<String, List<Student>> getStudentsByGradeLevel() {
        List<Object[]> avgMarksList = gradeRepository.findAverageMarksPerStudent();

        Map<String, List<Student>> gradeMap = new HashMap<>();
        gradeMap.put("A", new ArrayList<>());
        gradeMap.put("B", new ArrayList<>());
        gradeMap.put("C", new ArrayList<>());

        for (Object[] record : avgMarksList) {
            Long studentId = (record[0] instanceof Integer)
                    ? ((Integer) record[0]).longValue()
                    : (Long) record[0];
            Double avgMarks = (Double) record[1];

            studentRepository.findById(studentId).ifPresent(student -> {
                if (avgMarks >= 80) {
                    gradeMap.get("A").add(student);
                } else if (avgMarks >= 60) {
                    gradeMap.get("B").add(student);
                } else {
                    gradeMap.get("C").add(student);
                }
            });
        }

        return gradeMap;
    }
}
