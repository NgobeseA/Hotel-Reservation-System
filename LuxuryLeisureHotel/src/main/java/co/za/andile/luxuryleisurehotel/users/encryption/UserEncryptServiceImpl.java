/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.encryption;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Train 01
 */
public class UserEncryptServiceImpl implements UserEncryptionService{

    public UserEncryptServiceImpl() {
    }

    
    @Override
    public String hashingPassword(String password) {
        return BCrypt.hashpw(password.trim(), BCrypt.gensalt());
    }

    @Override
    public boolean passwordVerification(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
}
