/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.service;

import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;

/**
 *
 * @author T440
 */
public interface PaymentService {
    Reservation getReservation(int reservation_id, int user_id);
    double calcTotalAmount(Reservation reservation);
    boolean makePayment(Payment payment);
    boolean changeStatusReservation(int id);
    Payment getPayment(Reservation reservation);
}
