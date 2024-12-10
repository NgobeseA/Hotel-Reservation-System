/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.dao;

import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.util.List;

/**
 *
 * @author T440
 */
public interface RoomTypeDao {
    List<RoomType> getRoomTypes();
    boolean addRoomType(RoomType roomType);
}
