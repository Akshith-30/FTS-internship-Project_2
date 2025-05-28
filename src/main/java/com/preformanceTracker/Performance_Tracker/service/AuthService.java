package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.entity.Admin;
import com.preformanceTracker.Performance_Tracker.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AdminRepository adminRepository;

    @Autowired // Optional when using constructor injection in modern Spring
    public AuthService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public boolean login(String adminId, String adminPass) {
        PasswordValidationStreams.validatePassword(adminPass); // Throws if invalid

        Admin admin = adminRepository.findByAdminId(adminId);
        return admin != null && admin.getAdminPass().equals(adminPass);
    }
}
