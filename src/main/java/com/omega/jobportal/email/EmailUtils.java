package com.omega.jobportal.email;

public class EmailUtils {
    private final static String host = "http://localhost:8080";


    public static String getAccountVerificationEmailMessage(String name, String token) {
        String message = "Hello " + name + "\n \n You new account has been created. Please click the link below to verify your account. \n \n"
                .concat(getVerificationURL(token));
        return message;
    }

    public static String getResetPasswordEmailMessage(String name, String resetPasswordCode) {
        String message = "Hello" + name + "\n \n Here is your reset password link. Please click the link below to verify your accout." +
                " \n \n Please note that the link will expire in 15 minutes".concat(getResetPasswordURL(resetPasswordCode));
        return message;
    }

    private static String getVerificationURL(String token) {
        return host.concat("/api/v1/users/verify-email?token=".concat(token));
    }

    private static String getResetPasswordURL(String token) {
        return host.concat("/api/v1/users/reset-password?token=".concat(token));
    }
}
