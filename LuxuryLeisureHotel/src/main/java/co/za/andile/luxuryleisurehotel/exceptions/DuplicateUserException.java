/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.exceptions;

/**
 *
 * @author T440
 */
public class DuplicateUserException extends Exception{

    public DuplicateUserException() {
        this("Email already exists.");
    }

    public DuplicateUserException(String message) {
        super(message);
    }
    
}
