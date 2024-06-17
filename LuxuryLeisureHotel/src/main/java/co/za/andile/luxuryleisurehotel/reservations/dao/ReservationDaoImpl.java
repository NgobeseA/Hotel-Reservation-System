/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.dao;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.model.RoomType;
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
            String sql = "INSERT INTO bookings (user_id, room_id, check_in, check_out, status) VALUES(?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, reservation.getUser().getId());
                preparedStatement.setInt(2, reservation.getRoom().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getCheck_in()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(reservation.getCheck_out()));
                preparedStatement.setString(5, reservation.getStatus().name().toLowerCase());
                
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
                     + "u.id as user_id, u.name as name, u.surname as surname, u.email as email, u.contact as contact, u.admin as admin, u.verified as verified, "
                     + "r.room_id as room_id, r.room_type as room_type, r.available as available, r.rating as rating, r.picture as picture, r.description as description, r.location as location "
                     + "FROM bookings b "
                     + "JOIN users u ON b.user_id = u.id "
                     + "JOIN rooms r ON b.room_id = r.room_id "
                     + "WHERE b.user_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, user_id);
                
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        // Create User object
                        User user = new User();
                        user.setId(rs.getInt("user_id"));
                        user.setName(rs.getString("name"));
                        user.setSurname(rs.getString("surname"));
                        user.setEmail(rs.getString("email"));
                        user.setContact(rs.getString("contact"));
                        user.setAdmin(rs.getBoolean("admin"));
                        user.setVerified(rs.getBoolean("verified"));

                        // Create Room object
                        Room room = new Room();
                        room.setId(rs.getInt("room_id"));
                        room.setRoomType(RoomType.valueOf(rs.getString("room_type").toUpperCase()));
                        room.setAvailable(rs.getBoolean("available"));
                        room.setRating(rs.getInt("rating"));
                        room.setPicture(rs.getString("picture"));
                        room.setDescription(rs.getString("description"));
                        room.setLocation(rs.getString("location"));

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
    
}
