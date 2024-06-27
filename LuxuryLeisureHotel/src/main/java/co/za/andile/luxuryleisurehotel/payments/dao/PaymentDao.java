/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.dao;

import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;

/**
 *
 * @author T440
 */
public interface PaymentDao {
    boolean addPayment(Payment payment);
    Payment getPayment(Reservation reservation);
}
