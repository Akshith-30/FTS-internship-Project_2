package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.entity.Subject;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import com.preformanceTracker.Performance_Tracker.repository.StudentRepository;
import com.preformanceTracker.Performance_Tracker.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private GradeRepository gradeRepo;

    public void saveStudentWithMarks(StudentDTO dto) {
        // Basic validation
        if(dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name is required");
        }
        if(dto.getRollNumber() == null || dto.getRollNumber().isEmpty()) {
            throw new IllegalArgumentException("Roll number is required");
        }
        if(dto.getYear() < 1 || dto.getYear() > 4) {
            throw new IllegalArgumentException("Year must be between 1 and 4");
        }
        if(dto.getSection() == null || dto.getSection().isEmpty()) {
            throw new IllegalArgumentException("Section is required");
        }
        if(dto.getMarks() == null || dto.getMarks().isEmpty()) {
            throw new IllegalArgumentException("Marks cannot be empty");
        }

        // Create and save student entity
        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNumber(dto.getRollNumber());
        student.setYear(dto.getYear());
        student.setSection(dto.getSection());

        Student savedStudent = studentRepo.save(student);

        // Save each grade
        for (Map.Entry<String, Integer> entry : dto.getMarks().entrySet()) {
            String subjectName = entry.getKey();
            Integer marks = entry.getValue();

            Optional<Subject> optionalSubject = subjectRepo.findByNameIgnoreCase(subjectName);

            if(optionalSubject.isEmpty()) {
                throw new RuntimeException("Subject not found: " + subjectName);
            }

            Subject subject = optionalSubject.get();

            if (marks == null || marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks for " + subjectName + " must be between 0 and 100");
            }

            Grade grade = new Grade();
            grade.setStudent(savedStudent);
            grade.setSubject(subject);
            grade.setMarks(marks);

            gradeRepo.save(grade);
        }
    }
}
