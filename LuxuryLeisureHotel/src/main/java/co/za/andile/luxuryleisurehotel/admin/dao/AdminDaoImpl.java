/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.admin.dao;

import co.za.andile.luxuryleisurehotel.admin.model.Admin;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440
 */
public class AdminDaoImpl implements AdminDao{
    private Connection connection;
    
    public AdminDaoImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    public Admin getAdmin(int id) {
        Admin admin = new Admin();
        if(connection != null){
            String sql = "SELECT id, name, surname, email, contact, address, password, admin, registration_token, verified FROM users WHERE id =?";
            try(PreparedStatement ps = connection.prepareStatement(sql)){
               ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next()){
                    if(resultSet.getBoolean("admin")){
                        admin.setId(resultSet.getInt("id"));
                        admin.setName(resultSet.getString("name"));
                        admin.setSurname(resultSet.getString("surname"));
                        admin.setEmail(resultSet.getString("email"));
                        admin.setAddress(resultSet.getString("address"));
                        admin.setPassword(resultSet.getString("password"));
                        admin.setAdmin(resultSet.getBoolean("admin"));
                        admin.setEmailToken(resultSet.getString("registration_token"));
                        admin.setVerified(resultSet.getBoolean("verified"));
                        admin.setContact(resultSet.getString("contact")); 
                    }     
                }
                } catch (SQLException ex) {
                Logger.getLogger(AdminDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return admin;
    }

    @Override
    public boolean addUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
