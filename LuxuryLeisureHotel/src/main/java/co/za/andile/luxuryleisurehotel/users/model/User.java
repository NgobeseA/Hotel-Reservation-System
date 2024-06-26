/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author T440
 */
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String contact;
    private String address;
    private boolean admin;
    private boolean verified;
    private String emailToken;
    private String password;
}
