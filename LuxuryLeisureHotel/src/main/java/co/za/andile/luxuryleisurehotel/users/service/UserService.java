/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.service;

import co.za.andile.luxuryleisurehotel.users.model.User;

/**
 *
 * @author T440
 */
public interface UserService {
    boolean createUser(String name, String surname, String email, String contact, String address, String password, boolean admin, boolean verified);
    User login(String email, String password);
    String tokenVerification(String token);
}
