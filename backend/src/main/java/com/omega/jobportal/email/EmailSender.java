package com.omega.jobportal.email;

import com.omega.jobportal.constants.Constants;

public interface EmailSender {
    void sendSimpleMailMessage(String to, String body, Constants subject);
}
