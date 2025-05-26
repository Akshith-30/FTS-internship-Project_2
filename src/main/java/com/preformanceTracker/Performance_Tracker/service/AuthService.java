package com.preformanceTracker.Performance_Tracker.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.preformanceTracker.Performance_Tracker.entity.Admin;
import com.preformanceTracker.Performance_Tracker.repository.AdminRepository;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean login(String adminId, String adminPass) {
        Admin admin = adminRepository.findByAdminId(adminId);
        if (admin != null && admin.getAdminPass().equals(adminPass)) {
            return true;
        }
        return false;
    }
}
