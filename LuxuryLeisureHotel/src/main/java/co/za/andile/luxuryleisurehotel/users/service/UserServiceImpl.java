/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.service;

import co.za.andile.luxuryleisurehotel.dbconnect.DBConnection;
import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidData;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidDataException;
import co.za.andile.luxuryleisurehotel.users.dao.UserDao;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author T440
 */
public class UserServiceImpl extends DBConnection implements UserService{
    private UserDao userDao;
    private String EMAIL_VERIFICATION_URL = "http://localhost:8080/verify?token=";
    
    public UserServiceImpl(UserDao userDao){
        super();
        this.userDao = userDao;
    }
    
    @Override
    public String createUser(String name, String surname, String email, String contact, String address, String password, boolean admin, boolean verified){
        String message = null;
        
        try {
            InvalidData.validateData(name, surname, email, contact, address, password);
            userDao.duplicateUser(email);
            String token = generateEmailToken(email);
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setAddress(address);
            user.setContact(contact);
            user.setEmail(email);
            user.setEmailToken(token);
            user.setAdmin(admin);
            user.setVerified(verified);
            user.setPassword(hashingPassword(password));
           
            if(sendVerificationEmail(name, surname, email, token)){
                message = userDao.addUser(user) ? "Succesfully registered" : "failed to register";
            }
            
        } catch (InvalidDataException ex) {
            message = ex.getMessage();
        } catch (DuplicateUserException ex) {
           message = ex.getMessage();
        }
        
        return message;
    }

    @Override
    public User login(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String generateEmailToken(String email){
        return UUID.randomUUID().toString();
    }
    
    @Override
    public boolean sendVerificationEmail(String name, String surname, String email, String token){
        String subject = "Email Verification";
        String body = "Hi! "+name + " "+surname+", click the following link to veridy your email: "+ EMAIL_VERIFICATION_URL + token;
        
        final String from ="andilekngobese@gmail.com";
        final String password = "isniridlrhrwfcxs";
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);
            
            Transport.send(message);
            System.out.println("Message sent successfully");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String tokenVerification(String token) {
        return userDao.verifyToken(token) ? "token verified" : "Failed to to verify token";
    }

    @Override
    public String hashingPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
 
}
