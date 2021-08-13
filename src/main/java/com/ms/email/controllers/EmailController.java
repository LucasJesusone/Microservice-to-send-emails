package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import com.ms.email.services.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }


    @GetMapping("/getAllEmails")
    public List<EmailModel> findAll() {
        return emailService.findAll();
    }



    @GetMapping("getEmails/{emailId}")
    public ResponseEntity<EmailModel> getById(@PathVariable final UUID emailId) throws Exception {
        Optional<EmailModel> emailModel = emailService.getById(emailId);
        if (emailModel.isPresent()) {
            return new ResponseEntity<>(emailModel.get(), HttpStatus.OK);
        }
        else {
            throw new Exception();
        }
    }

}
