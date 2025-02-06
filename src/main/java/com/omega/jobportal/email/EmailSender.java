package com.omega.jobportal.email;

public interface EmailSender {
    void sendSimpleMailMessage(String to, String body);
}
