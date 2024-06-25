/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
/**
 *
 * @author T440
 */
@AllArgsConstructor @Getter@Setter@NoArgsConstructor
public class Room {
    private int id;
    //private RoomType roomType;
    private RoomType roomType;
    private double rates;
    private String roomNumber;
    private boolean available;
    //private String picture;
    //private String description;
    //private int rating;
    private String location;
    
    

//    public Room(RoomType roomType, double rates, boolean available, String picture, String description, int rating, String location, String roomNumber) {
//        
//        this.rates = rates;
//        this.available = available;
//        this.picture = picture;
//        this.description = description;
//        this.rating = rating;
//        this.location = location;
//        this.roomNumber = roomNumber;
//    }

    public Room(RoomType roomType, double rates, String roomNumber, boolean available, String location) {
        this.roomType = roomType;
        this.rates = rates;
        this.roomNumber = roomNumber;
        this.available = available;
        this.location = location;
    }
}
