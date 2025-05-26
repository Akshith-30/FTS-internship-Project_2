package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    // No need to declare save() method here; JpaRepository already provides it
}
