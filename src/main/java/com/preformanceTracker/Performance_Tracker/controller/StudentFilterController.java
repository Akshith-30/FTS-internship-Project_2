package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.StudentDTO;
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
public class StudentFilterController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/filter")
    public ResponseEntity<?> getStudentsByYearAndSection(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String section
    ) {
        try {
            List<StudentDTO> students = studentService.getStudentsByYearAndSection(year, section);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to fetch students");
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
