/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.dao;

import co.za.andile.luxuryleisurehotel.exceptions.DuplicateUserException;
import co.za.andile.luxuryleisurehotel.users.model.User;
/**
 *
 * @author T440
 */
public interface UserDao {
    boolean addUser(User user, String token, String password);
    User getUser(String email, String password);
    void duplicateUser(String email) throws DuplicateUserException;
    boolean verifyToken(String token);
}
