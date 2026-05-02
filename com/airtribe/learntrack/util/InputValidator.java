package com.airtribe.learntrack.util;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {

    private InputValidator() {
    }

    public static void validateNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
    }

    public static void validateEmail(String email) {
        validateNonEmpty(email, "Email");
        // Simple sanity check — no full RFC parser needed for this project.
        if (!email.contains("@") || !email.contains(".") || email.length() < 5) {
            throw new InvalidInputException("Email '" + email + "' is not a valid format.");
        }
    }

    public static void validateCourseDuration(int weeks) {
        if (weeks < AppConstants.MIN_COURSE_DURATION_WEEKS
                || weeks > AppConstants.MAX_COURSE_DURATION_WEEKS) {
            throw new InvalidInputException(
                    "Course duration must be between "
                            + AppConstants.MIN_COURSE_DURATION_WEEKS + " and "
                            + AppConstants.MAX_COURSE_DURATION_WEEKS + " weeks.");
        }
    }

    public static int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number.");
        }
    }
}
