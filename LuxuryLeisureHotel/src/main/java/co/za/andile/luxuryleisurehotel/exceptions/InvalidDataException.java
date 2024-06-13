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
public class InvalidDataException extends Exception{

    public InvalidDataException() {
        this("Invalid data provided");
    }

    public InvalidDataException(String message) {
        super(message);
    }
    
}
