/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.dao;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.model.RoomType;
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
public class RoomDaoImpl implements RoomDao{
    private Connection connection;

    public RoomDaoImpl(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
       if(connection != null){
           String sql = "SELECT id, room_type, rates, occupancy, available FROM room";
           try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
               ResultSet resultSet = preparedStatement.executeQuery();
               
               while(resultSet.next()){
                   Room room = new Room();
                   room.setId(resultSet.getInt("id"));
                   room.setRoomType(Enum.valueOf(RoomType.class, resultSet.getString("room_type")));
                   room.setRates(resultSet.getDouble("rates"));
                   //room.setOccupancy(resultSet.getInt("occupancy"));
                   room.setAvailable(resultSet.getBoolean("available"));
                   
                   rooms.add(room);
               }
           } catch (SQLException ex) {
               Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       return rooms;
    }

    @Override
    public boolean addRoom(Room room) {
        if(connection != null){
            String sql ="INSERT INTO rooms (room_type, price_per_night, available, rating, picture, description, location) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, room.getRoomType().name());
                preparedStatement.setDouble(2, room.getRates());
                preparedStatement.setBoolean(3, room.isAvailable());
                preparedStatement.setInt(4, room.getRating());
                preparedStatement.setString(5, room.getPicture());
                preparedStatement.setString(6, room.getDescription());
                preparedStatement.setString(7, room.getLocation());
                
                if(preparedStatement.executeUpdate() > 0) return true;
            }
          catch (SQLException ex) {
                Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }

    @Override
    public boolean editRoom(Room room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room getRoom(int id) {
        Room room = new Room();
        if(connection != null){
           String sql = "SELECT id, room_type, price_per_night, occupancy, available, rating, picture, description, location FROM rooms WHERE id=?";
           try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
               preparedStatement.setInt(1, id);
               ResultSet resultSet = preparedStatement.executeQuery();
               
               if(resultSet.next()){
                   room.setId(resultSet.getInt("id"));
                   room.setRoomType(Enum.valueOf(RoomType.class,resultSet.getString("roomtype")));
                   room.setRates(resultSet.getDouble("price_per_night"));
                   //room.setOccupancy(resultSet.getInt("occupancy"));
                   room.setAvailable(resultSet.getBoolean("available"));
                   room.setRating(resultSet.getInt("rating"));
                   room.setPicture(resultSet.getString("picture"));
                   room.setDescription(resultSet.getString("description"));
                   room.setLocation(resultSet.getString("location"));
               }
           } catch (SQLException ex) {
               Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        return room;
    }

    @Override
    public List<Room> getAvailableRooms() {
         List<Room> rooms = new ArrayList<>();
       if(connection != null){
           String sql = "SELECT room_id, room_type, price_per_night, available, rating, picture, description FROM rooms WHERE available = TRUE";
           try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
               ResultSet resultSet = preparedStatement.executeQuery();
               
               while(resultSet.next()){
                   Room room = new Room();
                   room.setId(resultSet.getInt("room_id"));
                   room.setRoomType(Enum.valueOf(RoomType.class,resultSet.getString("room_type").toUpperCase()));
                   room.setRates(resultSet.getDouble("price_per_night"));
                   //room.setOccupancy(resultSet.getInt("occupancy"));
                   room.setAvailable(resultSet.getBoolean("available"));
                   room.setRating(resultSet.getInt("rating"));
                   room.setPicture(resultSet.getString("picture"));
                   room.setDescription(resultSet.getString("description"));
                   
                   rooms.add(room);
               }
           } catch (SQLException ex) {
               Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       return rooms;
    }
    
}
