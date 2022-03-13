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
import ru.geekbrains.lesson7.model.EmailType;
import ru.geekbrains.lesson7.model.HTMLTemplate;
import ru.geekbrains.lesson7.repository.HTMLTemplatesRepository;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
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
    public void sendEmail(EmailType type, Map<String, Object> params, Collection<String> receivers) {
        switch (type) {
            case USER_REGISTRATION -> receivers.forEach(receiver -> sendVerificationEmail(receiver, params));
            case USER_ORDER_CREATED -> receivers.forEach(receiver -> sendOrderDetailsToUser(receiver, params));
            case MANAGER_ORDER_CREATED -> receivers.forEach(receiver -> sendOrderDetailsToManager(receiver, params));
        }
    }

    private void sendOrderDetailsToManager(String receiver, Map<String, Object> params) {
        HTMLTemplate htmlTemplate = htmlTemplatesRepository.getHTMLTemplatesByDef("manager_order_details_email_template");

        try {
            Template template = new Template("template", htmlTemplate.getTemplate(), configuration);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(receiver);
            helper.setFrom("new-order-alert@shop.ru");
            helper.setSubject("Поступил новый заказ");
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

    private void sendOrderDetailsToUser(String receiver, Map<String, Object> params) {
        HTMLTemplate htmlTemplate = htmlTemplatesRepository.getHTMLTemplatesByDef("user_order_details_email_template");

        try {
            Template template = new Template("template", htmlTemplate.getTemplate(), configuration);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(receiver);
            helper.setFrom("orders@shop.ru");
            helper.setSubject("Заказ " + params.get("orderId") + " принят");
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

    private void sendVerificationEmail(String receiver, Map<String, Object> params) {
        HTMLTemplate htmlTemplate = htmlTemplatesRepository.getHTMLTemplatesByDef("registration_verification_email_template");

        try {
            Template template = new Template("template", htmlTemplate.getTemplate(), configuration);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(receiver);
            helper.setFrom("email-verify@shop.ru");
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
