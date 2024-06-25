/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.service;

import co.za.andile.luxuryleisurehotel.room.dao.RoomDao;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomexception.RoomExistException;
//import co.za.andile.luxuryleisurehotel.room.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDao;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author T440
 */
public class RoomServiceImpl implements RoomService{
    private RoomDao roomDao;
    private RoomTypeDao roomTypeDao;

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public RoomServiceImpl(RoomTypeDao roomTypeDao) {
        this.roomTypeDao = roomTypeDao;
    }
    
    @Override
    public List<Room> getAllAvailableRooms(LocalDateTime check_in, LocalDateTime check_out) {
        
        return roomDao.getAvailableRooms(check_in, check_out);
    }

    @Override
    public boolean createRoom(Room room) throws RoomExistException{
        if(roomExists(room.getRoomNumber()))
            throw new RoomExistException("Room with "+ room.getRoomNumber() +" already exists");
        return roomDao.addRoom(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomDao.getAllRooms();
    }

    @Override
    public boolean editRoom(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RoomType> getRoomTypes() {
        return roomTypeDao.getRoomTypes();
    }

    @Override
    public RoomType getRoomType(int id) {
        return roomTypeDao.getRoomTypes()
                .stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }
    private boolean roomExists(String room_number){
        return getAllRooms().stream()
                .filter(s -> s.getRoomNumber().equalsIgnoreCase(room_number))
                .findFirst()
                .orElse(null) != null;
    }

    @Override
    public boolean editRoomAvailabity(int room_id, boolean available) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room getRoomById(int room_id) {
        return getAllRooms().stream()
                .filter(room -> room.getId() == room_id)
                .findFirst()
                .orElse(null);
    }
}
