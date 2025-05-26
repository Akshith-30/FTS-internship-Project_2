package com.preformanceTracker.Performance_Tracker.controller;

import com.preformanceTracker.Performance_Tracker.entity.Subject;
import com.preformanceTracker.Performance_Tracker.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepo;

    @GetMapping
    public List<Subject> getSubjects() {
        return subjectRepo.findAll();
    }
}
