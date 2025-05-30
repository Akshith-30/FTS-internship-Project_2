package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.FailedStudentDTO;
import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FailedStudentService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<FailedStudentDTO> getFailedStudentsByYear(Integer year) {
        List<Grade> failedGrades;

        if (year != null) {
            failedGrades = gradeRepository.findByStudentYearAndMarksLessThan(year, 35);
        } else {
            failedGrades = gradeRepository.findByMarksLessThan(35);
        }

        Map<Student, Map<String, Integer>> studentFailures = new HashMap<>();

        for (Grade grade : failedGrades) {
            Student student = grade.getStudent();
            String subjectName = grade.getSubject().getName();
            int marks = grade.getMarks();

            studentFailures
                    .computeIfAbsent(student, k -> new HashMap<>())
                    .put(subjectName, marks);
        }

        List<FailedStudentDTO> result = new ArrayList<>();

        for (Map.Entry<Student, Map<String, Integer>> entry : studentFailures.entrySet()) {
            Student student = entry.getKey();
            Map<String, Integer> failedSubjects = entry.getValue();

            FailedStudentDTO dto = new FailedStudentDTO(
                    student.getName(),
                    student.getRollNumber(),
                    student.getYear(),
                    student.getSection(),
                    failedSubjects
            );
            result.add(dto);
        }

        return result;
    }
}
