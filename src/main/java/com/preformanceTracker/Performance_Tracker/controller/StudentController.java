package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
import com.preformanceTracker.Performance_Tracker.dto.StudentViewDTO;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO dto) {
        try {
            studentService.saveStudentWithMarks(dto);
            return ResponseEntity.ok(Map.of("message", "Student added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/top-performers")
    public ResponseEntity<?> getTopPerformersByYear() {
        try {
            Map<Integer, List<Map<String, Object>>> topPerformers = studentService.getTop3PerformersByYear();
            return ResponseEntity.ok(topPerformers);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to fetch top performers");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @GetMapping("/with-grades")
    public ResponseEntity<List<StudentViewDTO>> getStudentsWithGrades() {
        try {
            List<StudentViewDTO> studentsWithGrades = studentService.getAllStudentViews();
            return ResponseEntity.ok(studentsWithGrades);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
