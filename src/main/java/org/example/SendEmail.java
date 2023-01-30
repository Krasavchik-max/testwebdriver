package org.example;
import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static org.example.AllCities.cities;
import static org.example.AllCities.textElement;

public class SendEmail {

    public static void main(String[] args){
        sendEmail();
    }
    public static void sendEmail(){
        String username = "put your email";
        String password = "put your password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");



        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("put a sender email "));
            message.setSubject("NEW NOTIFICATION ");
            message.setText("Dear User,"+ textElement +"\n" + cities[6].toUpperCase() +  "\n"
                    + "ДАТЫ ЕСТЬ!!!!!");

            try {
                Transport.send(message);
                System.out.println("Done");
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


}
