/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.service;

import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;

/**
 *
 * @author T440
 */
public interface RoomTypeService {
    boolean addRoomType(RoomType roomType);
    RoomType getAllRoomTypes();
}
