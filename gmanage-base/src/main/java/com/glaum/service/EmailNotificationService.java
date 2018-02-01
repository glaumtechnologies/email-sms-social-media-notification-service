package com.glaum.service;

import com.glaum.model.EmailNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String fromEmail;


    public void send(EmailNotificationRequest eParams, MultipartFile multipartFile) throws MessagingException {
        if (eParams.isHtml()) {
            sendHtmlMail(eParams, multipartFile);
        } else {
            sendPlainTextMail(eParams, multipartFile);
        }
    }

    private void sendHtmlMail(EmailNotificationRequest eParams, MultipartFile multipartFile) throws MessagingException {
        EmailTemplate template = new EmailTemplate("email.html");
        Map<String, String> replacements = new HashMap<>();
        replacements.put("user", eParams.getMessage());
        replacements.put("today", String.valueOf(new Date()));
        // below code will override email template values from the request.
        replacements.putAll(eParams.getReplacements());
        String htmlMessage = template.getTemplate(replacements);
        eParams.setMessage(htmlMessage);
        eParams.setHtml(true);
        sendPlainTextMail(eParams, multipartFile);
    }

    private void sendPlainTextMail(EmailNotificationRequest eParams, MultipartFile multipartFile) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(eParams.getTo().toArray(new String[eParams.getTo().size()]));
        helper.setReplyTo(fromEmail);
        helper.setFrom(fromEmail);
        helper.setSubject(eParams.getSubject());
        if (multipartFile != null && multipartFile.getSize() > 0) {
            helper.addAttachment(extractFileName(multipartFile), new InputStreamSource() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return multipartFile.getInputStream();
                }
            });
        }
        helper.setText(eParams.getMessage(), eParams.isHtml());
        if (eParams.getCc().size() > 0) {
            helper.setCc(eParams.getCc().toArray(new String[eParams.getCc().size()]));
        }
        mailSender.send(message);
    }


    private String extractFileName(MultipartFile multipartFile) {
        int lastIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        String xsdExtension = multipartFile.getOriginalFilename().substring(lastIndex);
        String fileName = multipartFile.getOriginalFilename().substring(0, lastIndex - 1);
        return fileName+ "." + xsdExtension;
    }
}