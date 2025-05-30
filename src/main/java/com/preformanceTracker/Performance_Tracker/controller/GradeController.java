package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.entity.Subject;
import com.preformanceTracker.Performance_Tracker.repository.StudentRepository;
import com.preformanceTracker.Performance_Tracker.repository.SubjectRepository;
import com.preformanceTracker.Performance_Tracker.repository.GradeRepository;
import com.preformanceTracker.Performance_Tracker.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public GradeController(
            GradeService gradeService,
            GradeRepository gradeRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository) {
        this.gradeService = gradeService;
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/grade-levels")
    public ResponseEntity<?> getStudentsByGradeLevel() {
        try {
            Map<String, List<Student>> result = gradeService.getStudentsByGradeLevel();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to fetch grade-level students");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGrade(@RequestBody Map<String, Object> payload) {
        try {
            Long studentId = Long.valueOf(payload.get("studentId").toString());
            Long subjectId = Long.valueOf(payload.get("subjectId").toString());
            int marks = Integer.parseInt(payload.get("marks").toString());

            Optional<Student> studentOpt = studentRepository.findById(studentId);
            Optional<Subject> subjectOpt = subjectRepository.findById(Math.toIntExact(subjectId));

            if (studentOpt.isEmpty() || subjectOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid student or subject ID");
            }

            Grade grade = new Grade();
            grade.setStudent(studentOpt.get());
            grade.setSubject(subjectOpt.get());
            grade.setMarks(marks);

            gradeRepository.save(grade);

            return ResponseEntity.ok("Grade added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding grade: " + e.getMessage());
        }
    }
}
