package com.omega.jobportal.constants;

import lombok.Getter;

@Getter
public enum Constants {
    NEW_USER_ACCOUNT_VERIFICATION("New User Account Verification"),
    RESET_PASSWORD("Reset Password Request");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

}
