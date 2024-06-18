/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.service;


import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidData;
import co.za.andile.luxuryleisurehotel.exceptions.InvalidDataException;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDao;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.users.dao.UserDao;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailService;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailServiceImpl;
import co.za.andile.luxuryleisurehotel.users.encryption.UserEncryptionService;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.sql.Connection;
import java.util.List;
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
    private UserDao userDao;
    private EmailService emailService;
    private UserEncryptionService userEncryptService;
    private ReservationDao reservationDao;
    
    
    public UserServiceImpl(UserDao userDao, EmailService emailService, UserEncryptionService userEncryptService){
        this.userDao = userDao;
        this.emailService= emailService;
        this.userEncryptService = userEncryptService;
    }
    
    public UserServiceImpl(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
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
            //user.setEmailToken(token);
            user.setAdmin(admin);
            user.setVerified(verified);
            String userPassword = userEncryptService.hashingPassword(password);
            System.out.println("USER CREDENTIALS GOTTEN");
            System.out.println("\n\n\n sending data to email");
            
            if(emailService.sendVerificationEmail(name, surname, email, token)){
                result= userDao.addUser(user, token, userPassword);
            }
            
        } catch (InvalidDataException ex) {
            
        } catch (DuplicateUserException ex) {
           
        }
        
        return result;
    }

    @Override
    public User login(String email, String password) {

            return userDao.getUser(email.trim(), password.trim());
        
    }

    @Override
    public String tokenVerification(String token) {
        return userDao.verifyToken(token) ? "token verified" : "Failed to to verify token";
    }

    @Override
    public List<Reservation> getUserReservation(int userId) {
        return reservationDao.getUserReservations(userId);
    }

    @Override
    public boolean updateUserProfile(User user) {
        return userDao.editUser(user);
    }
 
}
