package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.FailedStudentDTO;
import com.preformanceTracker.Performance_Tracker.service.FailedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/failed-students")
@CrossOrigin(origins = "*")
public class FailedStudentController {

    @Autowired
    private FailedStudentService failedStudentService;

    // ✅ Handles both /api/failed-students and /api/failed-students/{year}
    @GetMapping({ "", "/{year}" })
    public ResponseEntity<List<FailedStudentDTO>> getFailedStudents(@PathVariable(required = false) Integer year) {
        List<FailedStudentDTO> failedStudents = failedStudentService.getFailedStudentsByYear(year);
        return ResponseEntity.ok(failedStudents);
    }
}
