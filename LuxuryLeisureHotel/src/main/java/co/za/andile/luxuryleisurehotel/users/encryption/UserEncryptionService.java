/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.encryption;

/**
 *
 * @author Train 01
 */
public interface UserEncryptionService {
    String hashingPassword(String password);
    boolean passwordVerification(String password, String hashedPassword);
}
