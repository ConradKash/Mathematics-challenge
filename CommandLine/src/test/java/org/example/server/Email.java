//
// Source code recreated from a .class file by

package org.example.server;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class Email {
    String host = "smtp.gmail.com";
    String username = "eelection3.portal@gmail.com";
    String from = "eelection3.portal@gmail.com";
    String password = "mngv mtyl wmzk exmi";
    Session session;

    public Email() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        String username = this.username;
        String password = this.password;
        this.session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);

            }

        });
        this.session.setDebug(true);
    }

    public void sendParticipantRegistrationRequestEmail(String to, String participantEmail, String username) throws MessagingException {
        MimeMessage message = new MimeMessage(this.session);

        message.setFrom(new InternetAddress(this.from));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject("Notification of registration under your school");

        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append("New participant notification\n\n");
        emailMessage.append("This message is to notify you of a new participant's request to register under your school\n\n");
        emailMessage.append("The participant details are as below\n");
        emailMessage.append("\tUsername: ").append(username).append("\n");
        emailMessage.append("\temail: ").append(participantEmail).append("\n");
        emailMessage.append("\nTo verify this participant please login into the command line and confirm with the commands\n");
        emailMessage.append("\tconfirm with:-> confirm yes/no " + username + "\n");

        message.setText(emailMessage.toString());

        Transport.send(message);

    }

    public void sendPasswordResetEmail(String to, String resetLink) throws MessagingException {
        MimeMessage message = new MimeMessage(this.session);

        message.setFrom(new InternetAddress(this.from));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject("Password Reset");

        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append("Password Reset Request\n\n");
        emailMessage.append("You have requested to reset your password. Please click on the link below to proceed:\n\n");
        emailMessage.append(resetLink);

        message.setText(emailMessage.toString());

        Transport.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException {
        MimeMessage message = new MimeMessage(this.session);

        message.setFrom(new InternetAddress(this.from));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(subject);

        // Create the message body part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Create the attachment body part
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentPath);
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(new File(attachmentPath).getName());

        // Create a multipart message and add the parts to it
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentBodyPart);

        // Set the multipart message as the content of the email
        message.setContent(multipart);

        Transport.send(message);
    }

}