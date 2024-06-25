/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.dao;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;

import co.za.andile.luxuryleisurehotel.users.model.User;
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
public class ReservationDaoImpl implements ReservationDao{
    private Connection connection;
    
    public ReservationDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean addReservation(Reservation reservation) {
        if(connection != null){
            String sql = "INSERT INTO booking (user_id, room_id, check_in, check_out, status, meal_type, dietary_restriction) VALUES(?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, reservation.getUser().getId());
                preparedStatement.setInt(2, reservation.getRoom().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getCheck_in()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(reservation.getCheck_out()));
                preparedStatement.setString(5, reservation.getStatus().name().toLowerCase());
                preparedStatement.setString(6,reservation.getMeal_type());
                preparedStatement.setString(7,reservation.getDietary_restriction());
                
                if(preparedStatement.executeUpdate() > 0) return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;   
    }

    @Override
    public boolean editReservation(Reservation reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reservation> getUserReservations(int user_id) {
        List<Reservation> bookings = new ArrayList<>();
        if(connection != null){
            String sql = "SELECT b.reservation_id, b.created_on, b.check_in, b.check_out, b.status, "
                     + "u.id as id, u.name as name, u.surname as surname, u.email as email, u.contact as contact, u.admin as admin, "
                     + "r.room_id as room_id, r.price_per_night as price_per_night, r.room_number as room_number, r.available as available, r.location as location, "
                     + "rt.roomtype_id as roomtype_id, rt.type as type, rt.picture_url as picture_url "
                     + "FROM booking b "
                     + "JOIN users u ON b.user_id = u.id "
                     + "JOIN rooms r ON b.room_id = r.room_id "
                     + "JOIN roomtypes rt ON r.roomtype_id = rt.roomtype_id "
                     + "WHERE b.user_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, user_id);
                System.out.println("User id: "+ user_id);
                
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        // Create User object
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setSurname(rs.getString("surname"));
                        user.setEmail(rs.getString("email"));
                        user.setContact(rs.getString("contact"));
                        user.setAdmin(rs.getBoolean("admin"));
                        
                        // Create dining pref

                        // Create RoomType object
                        RoomType roomType = new RoomType();
                        roomType.setId(rs.getInt("roomtype_id"));
                        roomType.setRoom_type(rs.getString("type"));
                        roomType.setPicture_url(rs.getString("picture_url"));
                        
                        // Create Room object
                        Room room = new Room();
                        room.setId(rs.getInt("room_id"));
                        room.setRates(rs.getDouble("price_per_night"));
                        room.setAvailable(rs.getBoolean("available"));
                        room.setLocation(rs.getString("location"));
                        room.setRoomNumber(rs.getString("room_number"));
                        room.setRoomType(roomType);

                        // Create Booking object
                        Reservation booking = new Reservation();
                        booking.setBookingId(rs.getInt("reservation_id"));
                        booking.setCreated_on(rs.getTimestamp("created_on").toLocalDateTime());
                        booking.setCheck_in(rs.getTimestamp("check_in").toLocalDateTime());
                        booking.setCheck_out(rs.getTimestamp("check_out").toLocalDateTime());
                        booking.setStatus(Enum.valueOf(Status.class, rs.getString("status").toUpperCase()));
                        booking.setUser(user);
                        booking.setRoom(room);

                        // Add Booking to list
                        bookings.add(booking);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookings;
    }

    @Override
    public List<Reservation> getReservations() {
        List<Reservation> bookings = new ArrayList<>();
        if(connection != null){
            String sql = "SELECT b.reservation_id, b.created_on, b.check_in, b.check_out, b.status, "
                     + "u.id as id, u.name as name, u.surname as surname, u.email as email, u.contact as contact, u.admin as admin, "
                     + "r.room_id as room_id, r.price_per_night as price_per_night, r.room_number as room_number, r.available as available, r.location as location, "
                     + "rt.roomtype_id as roomtype_id, rt.type as type, rt.picture_url as picture_url "
                     + "FROM booking b "
                     + "JOIN users u ON b.user_id = u.id "
                     + "JOIN rooms r ON b.room_id = r.room_id "
                     + "JOIN roomtypes rt ON r.roomtype_id = rt.roomtype_id";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                
                
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        // Create User object
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setName(rs.getString("name"));
                        user.setSurname(rs.getString("surname"));
                        user.setEmail(rs.getString("email"));
                        user.setContact(rs.getString("contact"));
                        user.setAdmin(rs.getBoolean("admin"));
                        
                        // Create dining pref

                        // Create RoomType object
                        RoomType roomType = new RoomType();
                        roomType.setId(rs.getInt("roomtype_id"));
                        roomType.setRoom_type(rs.getString("type"));
                        roomType.setPicture_url(rs.getString("picture_url"));
                        
                        // Create Room object
                        Room room = new Room();
                        room.setId(rs.getInt("room_id"));
                        room.setRates(rs.getDouble("price_per_night"));
                        room.setAvailable(rs.getBoolean("available"));
                        room.setLocation(rs.getString("location"));
                        room.setRoomNumber(rs.getString("room_number"));
                        room.setRoomType(roomType);

                        // Create Booking object
                        Reservation booking = new Reservation();
                        booking.setBookingId(rs.getInt("reservation_id"));
                        booking.setCreated_on(rs.getTimestamp("created_on").toLocalDateTime());
                        booking.setCheck_in(rs.getTimestamp("check_in").toLocalDateTime());
                        booking.setCheck_out(rs.getTimestamp("check_out").toLocalDateTime());
                        booking.setStatus(Enum.valueOf(Status.class, rs.getString("status").toUpperCase()));
                        booking.setUser(user);
                        booking.setRoom(room);

                        // Add Booking to list
                        bookings.add(booking);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bookings;
    }

    @Override
    public boolean updateReservationStatus(int id, Status status) {
        if(connection != null){
            String sql = "UPDATE booking SET status = ? WHERE reservation_id =?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, status.name().toLowerCase());
                preparedStatement.setInt(2, id);
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean removeReservation(int reservation_id) {
        if(connection != null){
            String sql ="UPDATE booking SET status = ? WHERE reservation_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, reservation_id);
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ReservationDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

   

    
}
