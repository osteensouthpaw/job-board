package com.omega.jobportal.constants;

import lombok.Getter;

@Getter
public enum Constants {

    // ─── AUTH ────────────────────────────────────────────────────────────────────
    NEW_USER_ACCOUNT_VERIFICATION("New User Account Verification"),
    RESET_PASSWORD("Reset Password Request"),
    PASSWORD_CHANGED_CONFIRMATION("Your Password Has Been Changed"),

    // ─── JOB SEEKER ──────────────────────────────────────────────────────────────
    WELCOME_EMAIL("Welcome to JobMega"),
    JOB_APPLICATION_CONFIRMATION("Application Received"),
    APPLICATION_ACCEPTED("Congratulations"),
    APPLICATION_REJECTED("Update on Your Application"),

    // ─── EMPLOYER ────────────────────────────────────────────────────────────────
    NEW_APPLICATION_ALERT("New Application for");

    private final String message;

    Constants(String message) {
        this.message = message;
    }
}