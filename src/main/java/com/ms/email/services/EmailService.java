package com.ms.email.services;

import com.ms.email.enums.EnumStatus;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(EnumStatus.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(EnumStatus.ERROR);
        } finally {
             return emailRepository.save(emailModel);
        }
    }

    public List<EmailModel> findAll() {
        return emailRepository.findAll();
    }


    public Optional<EmailModel> getById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}
