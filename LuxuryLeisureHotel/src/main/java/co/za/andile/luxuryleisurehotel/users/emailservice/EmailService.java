/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.users.emailservice;

/**
 *
 * @author Train 01
 */
public interface EmailService {
    boolean sendVerificationEmail(String name, String surname, String email, String token);
    String generateEmailToken(String email);
}
