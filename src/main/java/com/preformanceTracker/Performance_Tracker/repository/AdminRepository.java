package com.preformanceTracker.Performance_Tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.preformanceTracker.Performance_Tracker.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByAdminId(String adminId);
}
