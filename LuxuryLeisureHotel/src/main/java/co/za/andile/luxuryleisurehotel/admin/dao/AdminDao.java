/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.admin.dao;

import co.za.andile.luxuryleisurehotel.admin.model.Admin;
import co.za.andile.luxuryleisurehotel.users.model.User;

/**
 *
 * @author T440
 */
public interface AdminDao {
    Admin getAdmin(int id);
    boolean addUser(User user);
    
}
