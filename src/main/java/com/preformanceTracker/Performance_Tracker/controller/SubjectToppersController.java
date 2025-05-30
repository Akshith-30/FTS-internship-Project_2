package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.dto.TopStudentDTO;  // Make sure this matches your actual package
import com.preformanceTracker.Performance_Tracker.service.SubjectToppersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectToppersController {

    @Autowired
    private SubjectToppersService subjectToppersService;  // Changed from GradeService

    @GetMapping("/toppers")
    public ResponseEntity<List<TopStudentDTO>> getTop3PerSubjectPerYear() {
        List<TopStudentDTO> toppers = subjectToppersService.getTop3StudentsPerSubjectPerYear();
        return ResponseEntity.ok(toppers);
    }
}
//
