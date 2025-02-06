package com.omega.jobportal.email;

import com.omega.jobportal.constants.Constants;
import com.omega.jobportal.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String username;
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendSimpleMailMessage(String to, String body) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(Constants.NEW_USER_ACCOUNT_VERIFICATION.getMessage());
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
            log.info("email sent successfully");
        } catch (MailException ex) {
            log.error("an error occurred when sending the email {}", ex.getMessage());
            throw new ApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}