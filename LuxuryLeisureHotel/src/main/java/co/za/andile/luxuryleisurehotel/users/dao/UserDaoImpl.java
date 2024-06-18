/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.dao;

import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.users.encryption.UserEncryptServiceImpl;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Train 01
 */
public class UserDaoImpl implements UserDao{
    private Connection connection;
    
    public UserDaoImpl(Connection connection){
        this.connection = connection;
    }
   
    @Override
    public boolean addUser(User user, String token, String password){
        int i =0;
        if(connection != null){
            String sql = "INSERT INTO users (name, surname, email, contact, address, password, admin, registration_token, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getContact());
                preparedStatement.setString(5, user.getAddress());
                preparedStatement.setString(6, password);
                preparedStatement.setBoolean(7, user.isAdmin());
                preparedStatement.setString(8, token);
                preparedStatement.setBoolean(9, user.isVerified());
                
                i = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return i > 0;
    }

    @Override
    public User getUser(String email, String password) {
        User user = new User();
        if(connection != null){
            String sql = "SELECT id, name, surname, email, contact, address, password, admin, registration_token, verified FROM users WHERE email = ?";
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, email);
                ResultSet resultSet = ps.executeQuery();
                if(resultSet.next()){
                   if(new UserEncryptServiceImpl().passwordVerification(password, resultSet.getString("password"))){ // verifying password
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setSurname(resultSet.getString("surname"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAddress(resultSet.getString("address"));
                        System.out.println(resultSet.getBoolean("admin") +" from DAO");
                        user.setAdmin(resultSet.getBoolean("admin"));
                        //user.setEmailToken(resultSet.getString("registration_token"));
                        user.setVerified(resultSet.getBoolean("verified"));
                        user.setContact(resultSet.getString("contact"));
                   }
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return user;
    }

    @Override
    public void duplicateUser(String email) throws DuplicateUserException {
        if(connection != null){
            String sql = "SELECT id FROM users WHERE email =?";
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, email);
                
                ResultSet resultSet = ps.executeQuery();
                
                if(resultSet.next()){
                    throw new DuplicateUserException();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }

    @Override
    public boolean verifyToken(String token) {
       int i =0; 
       
       if(connection != null){
            try{
                String sql = "SELECT id FROM users WHERE verification_token=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, token);

                ResultSet resultSet = ps.executeQuery();

                if(resultSet.next()){
                    int userId = resultSet.getInt("id");

                    sql = "UPDATE users SET verified = TRUE WHERE id =?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, userId);

                    i = ps.executeUpdate();

                }
            } catch (SQLException ex) {
             Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return i >0;
    }

    @Override
    public boolean editUser(User user) {
        if(connection != null){
            String sql = "UPDATE users SET name = ?, surname =?, email =?, contact=?, address=?, admin=? WHERE id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getAddress());
                preparedStatement.setBoolean(5, user.isAdmin());
                preparedStatement.setInt(6, user.getId());
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
