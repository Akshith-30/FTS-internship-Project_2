package com.preformanceTracker.Performance_Tracker.repository;

import com.preformanceTracker.Performance_Tracker.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findByName(String name);
}
