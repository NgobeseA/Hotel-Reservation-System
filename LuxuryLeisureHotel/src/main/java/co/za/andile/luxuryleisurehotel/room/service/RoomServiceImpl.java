/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.service;

import co.za.andile.luxuryleisurehotel.room.dao.RoomDao;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import java.util.List;

/**
 *
 * @author T440
 */
public class RoomServiceImpl implements RoomService{
    private RoomDao roomDao;

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
    
    @Override
    public List<Room> getAllAvailableRooms() {
        return roomDao.getAvailableRooms();
    }

    @Override
    public boolean createRoom(Room room) {
        return roomDao.addRoom(room);
    }

    @Override
    public List<Room> getALLRooms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean editRoom(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
