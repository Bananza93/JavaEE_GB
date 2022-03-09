package ru.geekbrains.lesson7.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.geekbrains.lesson7.model.HTMLTemplate;
import ru.geekbrains.lesson7.repository.HTMLTemplatesRepository;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final HTMLTemplatesRepository htmlTemplatesRepository;
    private final JavaMailSender mailSender;
    private final Configuration configuration;

    public EmailService(HTMLTemplatesRepository htmlTemplatesRepository, JavaMailSender mailSender, Configuration configuration) {
        this.htmlTemplatesRepository = htmlTemplatesRepository;
        this.mailSender = mailSender;
        this.configuration = configuration;
    }

    @Async
    public void sendVerificationEmail(String email, String tokenUid) {
        HTMLTemplate htmlTemplate = htmlTemplatesRepository.getHTMLTemplatesByDef("email_template");
        Map<String, Object> model = new HashMap<>();
        model.put("token", tokenUid);

        try {
            Template template = new Template("verify_email_template", htmlTemplate.getTemplate(), configuration);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setFrom("email-confirm@shop.ru");
            helper.setSubject("Email verification");
            helper.setText(html, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Caught error while setting up or sending email: " + e.getMessage());
        } catch (TemplateException e) {
            LOGGER.error("Caught error while rendering email template: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Caught error while creating or reading email template: " + e.getMessage());
        }
    }
}
