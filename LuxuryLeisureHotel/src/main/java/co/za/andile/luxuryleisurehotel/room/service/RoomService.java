/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.service;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import java.util.List;

/**
 *
 * @author T440
 */
public interface RoomService {
    List<Room> getAllAvailableRooms();
    boolean createRoom(Room room);
    List<Room> getALLRooms();
    boolean editRoom(int id);
    List<Room> getRoomTypes();
}
