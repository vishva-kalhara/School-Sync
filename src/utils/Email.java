/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import config.Env;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author vishv
 */
public class Email {

    private Session session = null;

    public Email() {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", Env.EMAIL_HOST);
        properties.put("mail.smtp.port", Env.EMAIL_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Env.EMAIL_FROM, Env.EMAIL_PASSWORD);
            }
        });
    }

    public void send(String subject, String message, String toEmail) throws MessagingException {

        Message email = new MimeMessage(session);
        email.setFrom(new InternetAddress(Env.EMAIL_FROM));
        email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        email.setSubject(subject);
        email.setText(message);

        Transport.send(email);
        System.out.println("Email sent successfully!");
    }
}
