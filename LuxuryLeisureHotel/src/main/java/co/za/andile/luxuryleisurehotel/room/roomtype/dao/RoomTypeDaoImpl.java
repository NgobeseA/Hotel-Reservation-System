/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.roomtype.dao;

import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440
 */
public class RoomTypeDaoImpl implements RoomTypeDao{
    private Connection connection;

    public RoomTypeDaoImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<RoomType> getRoomTypes() {
        List<RoomType> roomtypes = new ArrayList<>();
        if(connection != null){
            String sql = "SELECT roomtype_id, type, picture_url, price FROM roomtypes";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                        RoomType rt = new RoomType();
                        rt.setId(resultSet.getInt("roomtype_id"));
                        rt.setRoom_type(resultSet.getString("type"));
                        rt.setPicture_url(resultSet.getString("picture_url"));
                        rt.setPrice_per_night(resultSet.getDouble("price"));
                        System.out.println(rt.getPrice_per_night());
                        System.out.println("picture: "+rt.getPicture_url());
                        roomtypes.add(rt);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RoomTypeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return roomtypes;
    }

    @Override
    public boolean addRoomType(RoomType roomType) {
        if(connection != null){
            String sql = "INSERT INTO roomtypes(type, picture_url, price) VALUES(?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,roomType.getRoom_type());
                preparedStatement.setString(2,roomType.getPicture_url());
                preparedStatement.setDouble(3, roomType.getPrice_per_night());
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(RoomTypeDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
