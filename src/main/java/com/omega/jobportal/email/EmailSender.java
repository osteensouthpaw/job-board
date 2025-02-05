package com.omega.jobportal.email;

public interface EmailSender {
    void sendSimpleMailMessage(String name, String to, String token);
}
