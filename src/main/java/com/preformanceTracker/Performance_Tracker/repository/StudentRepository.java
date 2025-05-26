package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRollNumber(String rollNumber); // optional check
}
