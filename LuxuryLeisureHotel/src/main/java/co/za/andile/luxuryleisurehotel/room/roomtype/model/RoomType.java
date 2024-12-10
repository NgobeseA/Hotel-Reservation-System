/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author T440
 */
@AllArgsConstructor@Setter@Getter@NoArgsConstructor
public class RoomType {
    private int id;
    private String room_type;
    private String picture_url;
    private double price_per_night;

    public RoomType(String room_type, String picture_url, double price_per_night) {
        this.room_type = room_type;
        this.picture_url = picture_url;
        this.price_per_night = price_per_night;
    }

    @Override
    public String toString() {
        return this.getRoom_type(); //To change body of generated methods, choose Tools | Templates.
    }
}
