/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.exception;

/**
 *
 * @author T440
 */
public class NoRoomAvailableException extends Exception{

    public NoRoomAvailableException() {
        this("No rooms are available");
    }

    public NoRoomAvailableException(String string) {
        super(string);
    }
    
}
