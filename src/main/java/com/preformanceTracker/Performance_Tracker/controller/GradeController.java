package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
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
}
