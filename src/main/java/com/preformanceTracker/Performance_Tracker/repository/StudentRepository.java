package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRollNumber(String rollNumber); // optional check

    List<Student> findByYearAndSection(Integer year, String section);
    List<Student> findByYear(Integer year);
    List<Student> findBySection(String section);
}
//