package com.example.ex5springdavidzaydenbergronelian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.PASSWORD_RESET_MESSAGE;
import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.PASSWORD_RESET_SUBJECT;

/**
 * Service for sending emails.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Sends a password reset email to the specified email address.
     *
     * @param toEmail the email address to send the email to
     * @param resetLink the link to reset the password
     */
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(PASSWORD_RESET_SUBJECT);
        message.setText(PASSWORD_RESET_MESSAGE + resetLink);

        mailSender.send(message);
    }
}