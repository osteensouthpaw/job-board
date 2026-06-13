package com.omega.jobportal.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class EmailUtils {
    @Value("${app.frontend-url}")
    private final String FRONTEND_URL = "http://localhost:3000";
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");


    public String getAccountVerificationEmailMessage(String name, String token) {
        String message = "Hello " + name + "\n \n You new account has been created. Use this verification code below to verify your account. \n \n"
                .concat(token);
        return message;
    }

    public String getWelcomeEmailMessage(String name) {
        return "Hi " + name + ",\n\n"
                + "Welcome to JobMega. Your account is ready.\n\n"
                + "You can now build your profile, browse job listings, and start applying "
                + "to opportunities that match your skills and experience.\n\n"
                + "A few things to get you started:\n"
                + "  → Complete your profile to increase your visibility to employers\n"
                + "  → Upload your resume so applications are faster\n"
                + "  → Browse open roles: " + FRONTEND_URL + "/job-listings\n\n"
                + "The more complete your profile, the better your chances of standing out.\n\n"
                + "The JobMega Team";
    }

    public String getPasswordChangedConfirmationEmailMessage(String name) {
        return "Hi " + name + ",\n\n"
                + "This is a confirmation that your JobMega account password was successfully changed.\n\n"
                + "If you made this change, no further action is needed.\n\n"
                + "If you did not change your password, your account may be compromised. "
                + "Please reset your password immediately:\n\n"
                + FRONTEND_URL + "/auth/reset-password\n\n"
                + "If you need help, reply to this email.\n\n"
                + "The JobMega Team";
    }

    public String getJobApplicationConfirmationEmailMessage(
            String name,
            String jobTitle,
            String companyName,
            LocalDateTime applicationDate) {
        return "Hi " + name + ",\n\n"
                + "Your application has been submitted successfully.\n\n"
                + "  Role:       " + jobTitle + "\n"
                + "  Company:    " + companyName + "\n"
                + "  Applied on: " + applicationDate.format(DATE_FORMATTER) + "\n\n"
                + "Your application is now with the employer for review. "
                + "If they would like to move forward, you will hear from them directly through JobMega.\n\n"
                + "In the meantime, keep your profile updated — "
                + "employers often review it alongside your application.\n\n"
                + "Track your applications: " + FRONTEND_URL + "/dashboard/applications\n\n"
                + "Good luck.\n\n"
                + "The JobMega Team";
    }

    public String getNewApplicationAlertEmailMessage(
            String employerName,
            String applicantName,
            String jobTitle,
            LocalDateTime applicationDate) {
        return "Hi " + employerName + ",\n\n"
                + "Someone just applied for one of your open roles.\n\n"
                + "  Applicant: " + applicantName + "\n"
                + "  Role:      " + jobTitle + "\n"
                + "  Applied:   " + applicationDate.format(DATE_FORMATTER) + "\n\n"
                + "You can review their profile, experience, and uploaded resume "
                + "from your employer dashboard.\n\n"
                + "Review application: " + FRONTEND_URL + "/dashboard/applications\n\n"
                + "The JobMega Team";
    }

    public String getApplicationUnderReviewEmailMessage(
            String name,
            String jobTitle,
            String companyName) {
        return "Hi " + name + ",\n\n"
                + "Good news — " + companyName + " is reviewing your application "
                + "for the " + jobTitle + " role.\n\n"
                + "No action is needed from you at this stage. "
                + "If they would like to move forward, you will be notified here.\n\n"
                + "Track your application: " + FRONTEND_URL + "/dashboard/applications\n\n"
                + "The JobMega Team";
    }


    public String getApplicationAcceptanceEmailMessage(
            String name,
            String jobTitle,
            String companyName) {
        return "Hi " + name + ",\n\n"
                + "Great news.\n\n"
                + companyName + " has reviewed your application for the "
                + jobTitle + " role and would like to move forward with you.\n\n"
                + "Message from " + companyName + ":\n"
                + "Log in to your dashboard to view the full details "
                + "and respond to the employer.\n\n"
                + FRONTEND_URL + "/dashboard/applications\n\n"
                + "Congratulations, " + name + ". This is what the work was for.\n\n"
                + "The JobMega Team";
    }

    public String getApplicationRejectionEmailMessage(
            String name,
            String jobTitle,
            String companyName) {
        return "Hi " + name + ",\n\n"
                + "Thank you for applying for the " + jobTitle
                + " role at " + companyName + ".\n\n"
                + "After careful review, they have decided to move forward "
                + "with other candidates at this time.\n\n"
                + "This is not a reflection of your potential — "
                + "hiring decisions involve many factors beyond qualifications alone. "
                + "We encourage you to keep applying.\n\n"
                + "Browse more opportunities: " + FRONTEND_URL + "/job-listings\n\n"
                + "Don't stop.\n\n"
                + "The JobMega Team";
    }

    public String getResetPasswordEmailMessage(String name, String resetPasswordCode) {
        String message = "Hello " + name.toLowerCase(Locale.ENGLISH) + "\n \n Here is your password reset code. Please use the code below to verify your account." +
                "\n \n Please note that the code will expire in 15 minutes \n \n".concat(resetPasswordCode);
        return message;
    }
}
