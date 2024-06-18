/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.emailservice;

import java.util.Properties;
import java.util.UUID;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//f
import java.util.Properties;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
    
/**
 *
 * @author Train 01
 */
public class EmailServiceImpl implements EmailService{
//    private final String EMAIL_VERIFICATION_URL = "http://localhost:8080/verify?token=";
//    
//    public boolean sendVerificationEmail(String name, String surname, String email, String token){
//        String subject = "Email Verification";
//        String body = "Hi! "+name + " "+surname+", click the following link to veridy your email: "+ EMAIL_VERIFICATION_URL + token;
//        
//        final String from ="andilekngobese@gmail.com";
//        final String password = "isniridlrhrwfcxs";
//        
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true"); // Enable authentication
//        properties.put("mail.smtp.starttls.enable","true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        properties.put("mail.debug", "true");
//
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//        
//        try{
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
//            message.setSubject(subject);
//            message.setText(body);
//            
//            Transport.send(message);
//            System.out.println("Message sent successfully");
//            return true;
//        } catch (MessagingException e) {
//            System.out.println("Failed to send email: " +e.getMessage());
//        }
//        return false;
//    }
    
    public boolean sendVerificationEmail(String name, String surname, String temail, String token){
//      String from = "andilekngobese@gmail.com";
//        String to = email;
//boolean emailSent = false;
//        String password = "isniridlrhrwfcxs";
//        
//// Add your Gmail password here 
//
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true"); // Enable authentication
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject("Your Subject");
//            message.setText("Hello, this is your email content.");
//
//            Transport.send(message);
//            System.out.println("Email sent successfully!");
//            emailSent = true;
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//      return emailSent;


        

        try {
            // Build the email
            Email email = EmailBuilder.startingBlank()
                    .from("Luxury Leisure Hotel (LLH)", "andilekngobese@gmail.com")
                    .to(temail)
                    .withSubject("test")
                    .withPlainText("fuck this thing")
                    .buildEmail();

            // Configure the mailer
            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "andilekngobese@gmail.com", "isniridlrhrwfcxs")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .buildMailer();

            // Send the email
            mailer.sendMail(email);
            System.out.println("Email sent successfully to " + temail);
            return true;

            // Provide feedback to the user
           

        } catch (Exception e) {
            e.printStackTrace();
            
        }
    return false;
    }
    
    public String generateEmailToken(String email){
        return UUID.randomUUID().toString();
    }
}
