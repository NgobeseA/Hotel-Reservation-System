/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.service;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomexception.RoomExistException;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.time.LocalDateTime;
//import co.za.andile.luxuryleisurehotel.room.model.RoomType;
import java.util.List;
import java.util.Set;

/**
 *
 * @author T440
 */
public interface RoomService {
    List<Room> getAllAvailableRooms(LocalDateTime check_in, LocalDateTime check_out);
    boolean createRoom(Room room) throws RoomExistException;
    List<Room> getAllRooms();
    boolean editRoom(int id);
    List<RoomType> getRoomTypes();
    RoomType getRoomType(int id);
    boolean  editRoomAvailabity(int room_id, boolean available);
}
