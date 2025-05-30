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

    // GET endpoint to fetch failed students by year
    @GetMapping("/{year}")
    public ResponseEntity<List<FailedStudentDTO>> getFailedStudentsByYear(@PathVariable int year) {
        List<FailedStudentDTO> failedStudents = failedStudentService.getFailedStudentsByYear(year);
        return ResponseEntity.ok(failedStudents);
    }
}

