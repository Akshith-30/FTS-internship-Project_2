package com.preformanceTracker.Performance_Tracker.controller;
import com.preformanceTracker.Performance_Tracker.entity.Student;
import com.preformanceTracker.Performance_Tracker.model.StudentDTO;
import com.preformanceTracker.Performance_Tracker.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody StudentDTO dto) {
        studentService.saveStudentWithMarks(dto);
        return ResponseEntity.ok("Student added");
    }


}
