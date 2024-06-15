/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.dao;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440
 */
public class ReservationDaoImpl implements ReservationDao{
    private Connection connection;
    
    private ReservationDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean addReservation(Reservation reservation) {
        if(connection != null){
            String sql = "INSERT INTO bookings (user_id, room_id, check_in, check_out, status) VALUES(?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, reservation.getUser_id().getId());
                preparedStatement.setInt(2, reservation.getRoom_id().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getCheck_in()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(reservation.getCheck_out()));
                preparedStatement.setString(5, reservation.getStatus().name());
                
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
    
}
