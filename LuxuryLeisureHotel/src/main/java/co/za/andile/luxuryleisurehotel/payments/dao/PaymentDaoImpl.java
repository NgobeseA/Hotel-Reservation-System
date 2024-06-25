/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.dao;

import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author T440
 */
public class PaymentDaoImpl implements PaymentDao{
    private Connection connection;
    
    public PaymentDaoImpl(Connection connection){
        this.connection = connection;
    }
    
    
    @Override
    public boolean addPayment(Payment payment) {
        if(connection != null){
            String sql = "INSERT INTO payments (booking_id, amount, acc_holder, card_number) "
                    + "VALUES (?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1, payment.getReservation().getBookingId());
                preparedStatement.setDouble(2, payment.getAmount());
                preparedStatement.setString(3, payment.getAccHolder());
                preparedStatement.setString(4,payment.getCardNumber());
                
                if(preparedStatement.executeUpdate() > 0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(PaymentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public Payment getPayment(Reservation reservation) {
        Payment payment = new Payment();
        if(connection != null){
            String sql = "SELECT payment_id, amount, payment_date, acc_holder, card_number FROM payments "
                    + "WHERE booking_id =?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,reservation.getBookingId());
                
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(resultSet.next()){
                        
                        payment.setPayment_id(resultSet.getInt("payment_id"));
                        payment.setAmount(resultSet.getDouble("amount"));
                        payment.setPaymentDate(resultSet.getTimestamp("payment_date").toLocalDateTime());
                        payment.setAccHolder(resultSet.getString("acc_holder"));
                        payment.setCardNumber(resultSet.getString("card_number"));
                        payment.setReservation(reservation);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaymentDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return payment;
    }
    
}
