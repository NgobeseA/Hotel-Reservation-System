/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.exceptions;

import java.util.Date;

/**
 *
 * @author T440
 */
public class InvalidData {
    public static void validateData(Object... args) throws InvalidDataException{
        for(Object arg: args){
            if(arg instanceof String){
                if(" ".equals(arg) || ((String) arg).isEmpty())
                    throw new InvalidDataException("All field must be filled");
            }else if(arg instanceof Date){
                if(arg.equals(" "))
                    throw new InvalidDataException("Please provide date");
            }
        }
    }
}
