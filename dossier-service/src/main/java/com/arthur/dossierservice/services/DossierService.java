package com.arthur.dossierservice.services;

import com.neoflex.creditbank.dealservice.dtos.EmailMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DossierService {

    private final JavaMailSender mailSender;

    final String HOST = "smtp.gmail.com";
    final String ADDRESS = "artur132003@gmail.com";
    final String SMTP_PORT = "465";
    final String TO = "ratmuh2809@gmail.com";
    final String password = "password";

    @Autowired
    public DossierService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    void sendMessage(EmailMessageDto emailMessageDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessageDto.getAddress());
        message.setSubject(emailMessageDto.getTheme().toString());
        message.setText(emailMessageDto.getText());

        mailSender.send(message);
    }

    @KafkaListener(topics = "finish-registration", groupId = "group1")
    public void finishRegistration(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }

    @KafkaListener(topics = "send-documents", groupId = "group1")
    public void sendDocuments(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }

    @KafkaListener(topics = "send-ses", groupId = "group1")
    public void sendSes(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }

    @KafkaListener(topics = "credit-issued", groupId = "group1")
    public void creditIssued(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }

    @KafkaListener(topics = "statement-denied", groupId = "group1")
    public void statementDenied(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }

    @KafkaListener(topics = "create-document", groupId = "group1")
    public void createDocument(EmailMessageDto emailMessageDto) {
        sendMessage(emailMessageDto);
    }
}
