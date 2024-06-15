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

/**
 *
 * @author T440
 */
@AllArgsConstructor @Getter@Setter@NoArgsConstructor
public class Room {
    private int id;
    private RoomType roomType;
    private double rates;
    private int occupancy;
    private boolean available;

    public Room(RoomType roomType, double rates, int occupancy, boolean available) {
        this.roomType = roomType;
        this.rates = rates;
        this.occupancy = occupancy;
        this.available = available;
    }
}
