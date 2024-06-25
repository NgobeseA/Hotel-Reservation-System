/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.dao;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

        if (connection != null) {
            String sql = "SELECT r.room_id, r.price_per_night, r.room_number, r.available, r.location, " +
                         "t.roomtype_id as roomtype_id, t.type as type, t.picture_url " +
                         "FROM rooms r " +
                         "JOIN roomtypes t ON r.roomtype_id = t.roomtype_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Create RoomType object
                        RoomType roomType = new RoomType();
                        roomType.setId(resultSet.getInt("roomtype_id"));
                        roomType.setRoom_type(resultSet.getString("type"));
                        roomType.setPicture_url(resultSet.getString("picture_url"));

                        // Create Room object
                        Room room = new Room();
                        room.setId(resultSet.getInt("room_id"));
                        room.setRates(resultSet.getDouble("price_per_night"));
                        room.setAvailable(resultSet.getBoolean("available"));
                        room.setLocation(resultSet.getString("location"));
                        room.setRoomNumber(resultSet.getString("room_number"));
                        room.setRoomType(roomType);

                        // Add room to the list
                        rooms.add(room);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, "Error reading result set", ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, "Error preparing statement", ex);
            }
        } else {
            Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, "Database connection is null");
        }
        return rooms;
    }


    @Override
    public boolean addRoom(Room room) {
        if(connection != null){
            String sql = "INSERT INTO rooms(roomtype_id, price_per_night, room_number, available, location) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, room.getRoomType().getId());
                preparedStatement.setDouble(2, room.getRates());
                preparedStatement.setString(3, room.getRoomNumber());
                preparedStatement.setBoolean(4, room.isAvailable());
                preparedStatement.setString(5, room.getLocation());
                
                
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
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
    public boolean updateRoomAvailability(int id, boolean available) {
        if(connection != null){
            String sql = "UPDATE rooms SET available =? WHERE room_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setBoolean(1, available);
                preparedStatement.setInt(1, id);
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public List<Room> getAvailableRooms(LocalDateTime check_in, LocalDateTime check_out) {
        List<Room> availableRooms = new ArrayList<>();

        /*
        Want to check the room if is booked or not on a particular date
        so I have to check from the date provided by the user if there are available rooms for that day
        so i have compare check out date with the check in date in booked rooms, 
        check check in with the check out ---
        doing so to find on the range on those booked room if they are booked or not
        
        added another check for cancelled bookings, cancelled booking the room is considered as available room
        even if you pick same date as booked before it should reflect as available room because that booking is cancelled
        */
        if (connection != null) {
            String sql = "SELECT r.room_id, r.price_per_night, r.room_number, r.available, r.location, "
                       + "rt.roomtype_id as roomtype_id, rt.type as room_type, rt.picture_url "
                       + "FROM rooms r "
                       + "JOIN roomtypes rt ON r.roomtype_id = rt.roomtype_id "
                       + "WHERE r.room_id NOT IN (SELECT b.room_id FROM booking b "
                       + "WHERE ((b.check_in <= ? AND b.check_out >= ?) OR (b.check_in <= ? AND b.check_out >= ?)) "
                        + "AND b.status <> 'cancelled')";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setTimestamp(1, Timestamp.valueOf(check_out));
                preparedStatement.setTimestamp(2, Timestamp.valueOf(check_in));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(check_in));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(check_out));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Create RoomType object
                        RoomType roomType = new RoomType();
                        roomType.setId(resultSet.getInt("roomtype_id"));
                        roomType.setRoom_type(resultSet.getString("room_type"));
                        roomType.setPicture_url(resultSet.getString("picture_url"));

                        // Create Room object
                        Room room = new Room();
                        room.setId(resultSet.getInt("room_id"));
                        room.setRates(resultSet.getDouble("price_per_night"));
                        room.setAvailable(resultSet.getBoolean("available"));
                        room.setLocation(resultSet.getString("location"));
                        room.setRoomNumber(resultSet.getString("room_number"));
                        room.setRoomType(roomType);

                        availableRooms.add(room);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RoomDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(availableRooms.size());
        return availableRooms;
    } 

   
}
