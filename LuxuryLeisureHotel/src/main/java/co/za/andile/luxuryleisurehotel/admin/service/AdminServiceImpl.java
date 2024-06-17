/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.admin.service;

import co.za.andile.luxuryleisurehotel.admin.dao.AdminDao;
import co.za.andile.luxuryleisurehotel.admin.model.Admin;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.users.emailservice.EmailServiceImpl;
import co.za.andile.luxuryleisurehotel.users.encryption.UserEncryptServiceImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440
 */
public class AdminServiceImpl implements AdminService{
    private AdminDao adminDao;
    
    public AdminServiceImpl(AdminDao adminDao){
        this.adminDao = adminDao;
    }
    @Override
    public Admin getAdmin(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createUser(User user, String password) {
        String token = new EmailServiceImpl().generateEmailToken(user.getEmail());
        String hashedPassword = new UserEncryptServiceImpl().hashingPassword(password);
        return adminDao.addUser(user, token, hashedPassword);
    }

    @Override
    public boolean addRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
