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
                   room.setOccupancy(resultSet.getInt("occupancy"));
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
            String sql ="INSERT INTO room (room_type, price_per_night, occupancy, available) VALUES (?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, room.getRoomType().name());
                preparedStatement.setDouble(2, room.getRates());
                preparedStatement.setInt(3, room.getOccupancy());
                preparedStatement.setBoolean(4, room.isAvailable());
                
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
           String sql = "SELECT id, room_type, price_per_night, occupancy, available FROM room WHERE id=?";
           try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
               preparedStatement.setInt(1, id);
               ResultSet resultSet = preparedStatement.executeQuery();
               
               if(resultSet.next()){
                   room.setId(resultSet.getInt("id"));
                   room.setRoomType(Enum.valueOf(RoomType.class,resultSet.getString("roomtype")));
                   room.setRates(resultSet.getDouble("price_per_night"));
                   room.setOccupancy(resultSet.getInt("occupancy"));
                   room.setAvailable(resultSet.getBoolean("available"));
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
           String sql = "SELECT id, room_type, price_per_night, occupancy, available FROM room WHERE available= TRUE";
           try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
               ResultSet resultSet = preparedStatement.executeQuery();
               
               while(resultSet.next()){
                   Room room = new Room();
                   room.setId(resultSet.getInt("id"));
                   room.setRoomType(Enum.valueOf(RoomType.class,resultSet.getString("roomtype")));
                   room.setRates(resultSet.getDouble("price_per_night"));
                   room.setOccupancy(resultSet.getInt("occupancy"));
                   room.setAvailable(resultSet.getBoolean("avaliable"));
                   
                   rooms.add(room);
               }
           } catch (SQLException ex) {
               Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       
       return rooms;
    }
    
}
