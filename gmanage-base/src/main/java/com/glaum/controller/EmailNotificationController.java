package com.glaum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaum.model.EmailNotificationRequest;
import com.glaum.model.NotificationResponse;
import com.glaum.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@Scope(scopeName = "request")
public class EmailNotificationController {

    private EmailNotificationService emailNotificationService;
    private ObjectMapper objectMapper;

    @Autowired
    public EmailNotificationController(EmailNotificationService emailNotificationService,
                                       ObjectMapper objectMapper) {
        this.emailNotificationService = emailNotificationService;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/mail/send", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE}, method = RequestMethod.POST)
    public NotificationResponse sendEmail(@RequestPart("jsonObject") String data,
                                          @RequestPart(value = "file", required = false) MultipartFile file) throws MessagingException, IOException {
        EmailNotificationRequest emailNotificationRequest = objectMapper.readValue(data, EmailNotificationRequest.class);
        emailNotificationService.send(emailNotificationRequest, file);
        return new NotificationResponse(true);
    }

    @RequestMapping(value = "/mail/send", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public NotificationResponse sendEmail(@RequestBody EmailNotificationRequest emailNotificationRequest) throws MessagingException, IOException {
        String jsonData = objectMapper.writeValueAsString(emailNotificationRequest);
        return sendEmail(jsonData, null);
    }

}
