package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
import com.preformanceTracker.Performance_Tracker.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO dto) {
        try {
            studentService.saveStudentWithMarks(dto);
            return ResponseEntity.ok(Map.of("message", "Student added successfully"));
        } catch (Exception e) {
            // Return bad request with error message
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
