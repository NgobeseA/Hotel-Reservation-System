/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomexception;

/**
 *
 * @author T440
 */
public class RoomExistException extends Exception{

    public RoomExistException() {
        this("invalid room number");
    }

    public RoomExistException(String string) {
        super(string);
    }
}
