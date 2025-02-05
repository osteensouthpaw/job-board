package com.omega.jobportal.constants;

public enum Constants {
    NEW_USER_ACCOUNT_VERIFICATION("New User Account Verification");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
