/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.service;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.util.List;

/**
 *
 * @author T440
 */
public interface UserService {
    boolean createUser(String name, String surname, String email, String contact, String password, boolean admin);
    User login(String email, String password);
    String tokenVerification(String token);
    boolean updateUserProfile(User user);
}
