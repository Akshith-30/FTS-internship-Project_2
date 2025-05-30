package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.FailedStudentDTO;
import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FailedStudentService {

    @Autowired
    private GradeRepository gradeRepository;

    private static final int PASSING_MARKS = 40;

    public List<FailedStudentDTO> getFailedStudentsByYear(int year) {
        // Fetch all grades where marks < 40 for the specified year
        List<Grade> failedGrades = gradeRepository.findByYearAndMarksLessThan(year, PASSING_MARKS);

        // Group grades by student
        Map<Student, List<Grade>> failedGradesByStudent = failedGrades.stream()
                .collect(Collectors.groupingBy(Grade::getStudent));

        // Convert each student + their failed subjects to FailedStudentDTO
        List<FailedStudentDTO> dtos = new ArrayList<>();

        for (Map.Entry<Student, List<Grade>> entry : failedGradesByStudent.entrySet()) {
            Student student = entry.getKey();
            List<Grade> grades = entry.getValue();

            Map<String, Integer> subjectMarks = grades.stream()
                    .collect(Collectors.toMap(
                            g -> g.getSubject().getName(),
                            Grade::getMarks
                    ));

            FailedStudentDTO dto = new FailedStudentDTO();
            dto.setName(student.getName());
            dto.setRollNumber(student.getRollNumber());
            dto.setYear(year);
            dto.setSection(student.getSection());
            dto.setSubjectMarks(subjectMarks);

            dtos.add(dto);
        }

        return dtos;
    }
}
