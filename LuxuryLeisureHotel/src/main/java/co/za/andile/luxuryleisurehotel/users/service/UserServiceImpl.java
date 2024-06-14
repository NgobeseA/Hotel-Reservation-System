/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.service;


import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidData;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidDataException;
import co.za.andile.luxuryleisurehotel.users.dao.UserDao;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailService;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailServiceImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.sql.Connection;
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
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    private final EmailService emailService;
    
    
    public UserServiceImpl(UserDao userDao, EmailService emailService){
        this.userDao = userDao;
        this.emailService= emailService;
    }
    
    @Override
    public boolean createUser(String name, String surname, String email, String contact, String address, String password, boolean admin, boolean verified){
        boolean result = false;
        
        try {
            InvalidData.validateData(name, surname, email, contact, address, password);
            userDao.duplicateUser(email);
            String token = emailService.generateEmailToken(email);
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
           
            //if(emailService.sendVerificationEmail(name, surname, email, token)){
                result= userDao.addUser(user);
            //}
            
        } catch (InvalidDataException ex) {
            
        } catch (DuplicateUserException ex) {
           
        }
        
        return result;
    }

    @Override
    public User login(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
