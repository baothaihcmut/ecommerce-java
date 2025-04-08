package com.ecommerceapp.libs.mailer;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableConfigurationProperties(MailProperties.class)
@ConditionalOnProperty(name = "dependencies.mailer.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class MailerService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final MailProperties mailProperties;
    private final ObjectMapper objectMapper;

    public void sendEmail(String to, String subject, String templateName, Object model)
            throws Exception {
        Context context = new Context();
        Map<String, Object> modelMap = this.objectMapper.convertValue(model, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        String htmlContent = templateEngine.process(templateName, context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(mailProperties.getUsername());
        mailSender.send(message);
    }
}
