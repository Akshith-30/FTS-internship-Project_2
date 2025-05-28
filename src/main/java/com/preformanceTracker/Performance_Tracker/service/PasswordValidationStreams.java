package com.preformanceTracker.Performance_Tracker.service;

import com.preformanceTracker.Performance_Tracker.exception.InvalidPasswordException;

public class PasswordValidationStreams {

    public static void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long.");
        }

        boolean hasSpecialChar = password.chars()
                .anyMatch(c -> !Character.isLetterOrDigit(c));

        boolean hasDigit = password.chars()
                .anyMatch(Character::isDigit);

        boolean hasUpperCase = password.chars()
                .anyMatch(Character::isUpperCase);

        if (!hasSpecialChar) {
            throw new InvalidPasswordException("Password must contain at least one special character.");
        }

        if (!hasDigit) {
            throw new InvalidPasswordException("Password must contain at least one digit.");
        }

        if (!hasUpperCase) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter.");
        }
    }
}
