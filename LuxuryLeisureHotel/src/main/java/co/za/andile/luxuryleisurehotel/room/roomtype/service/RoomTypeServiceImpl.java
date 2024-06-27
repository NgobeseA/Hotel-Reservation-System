/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.service;

import co.za.andile.luxuryleisurehotel.room.roomtype.dao.RoomTypeDao;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;

/**
 *
 * @author T440
 */
public class RoomTypeServiceImpl implements RoomTypeService{
    private RoomTypeDao roomDao;

    public RoomTypeServiceImpl(RoomTypeDao roomDao) {
        this.roomDao = roomDao;
    }
    @Override
    public boolean addRoomType(RoomType roomType) {
        return roomDao.addRoomType(roomType);
    }

    @Override
    public RoomType getAllRoomTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
