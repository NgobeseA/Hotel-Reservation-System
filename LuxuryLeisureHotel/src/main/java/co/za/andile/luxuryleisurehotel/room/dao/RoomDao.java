/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.dao;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author T440
 */
public interface RoomDao {
    List<Room> getAllRooms();
    boolean addRoom(Room room);
    boolean editRoom(Room room);
    LocalDateTime findNextAvailableRoom(LocalDateTime check_out, int roomtypeId);
    List<Room> getAvailableRooms(LocalDateTime check_in, LocalDateTime check_out);
    boolean removeRoom(int id);
}
