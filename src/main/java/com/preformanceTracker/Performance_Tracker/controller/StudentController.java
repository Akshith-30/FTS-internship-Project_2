package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
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
@CrossOrigin(origins = "*")  // Optional: Allow cross-origin if accessing via browser
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ✅ POST: Add a new student with marks
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO dto) {
        try {
            studentService.saveStudentWithMarks(dto);
            return ResponseEntity.ok(Map.of("message", "Student added successfully"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // ✅ GET: Fetch all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    // ✅ NEW: GET top 3 performers by year
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

}
