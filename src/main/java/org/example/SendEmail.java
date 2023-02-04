package org.example;


import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void main(String[] args){

        sendEmail("Тема","Сообщение");
    }
    public static void sendEmail(String emailSubject,String emailBody){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(LoginAndPassword.emailFrom, LoginAndPassword.emailPassword);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(LoginAndPassword.emailPassword));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(LoginAndPassword.emailTo));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport.send(message);
        } catch (MessagingException e) {
                e.printStackTrace();
            }
    }
}
