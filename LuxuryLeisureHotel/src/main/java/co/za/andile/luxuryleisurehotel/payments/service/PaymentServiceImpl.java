/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.service;

import co.za.andile.luxuryleisurehotel.payments.dao.PaymentDao;
import co.za.andile.luxuryleisurehotel.payments.model.Payment;
import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDao;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;

/**
 *
 * @author T440
 */
public class PaymentServiceImpl implements PaymentService{
    private ReservationDao reservationDao;
    private PaymentDao paymentDao;

    public PaymentServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }
    
    
    @Override
    public Reservation getReservation(int reservation_id, int user_id) {
        return reservationDao.getUserReservations(user_id)
                .stream()
                .filter(s -> s.getBookingId() == reservation_id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public double calcTotalAmount(Reservation reservation) {
        double amount = reservation.getRoom().getRates();
        double breakfast = 270.0;
        double lunch = 350.0;
        String meal = reservation.getMeal_type();
        
        System.out.println(meal);
        if("both".equalsIgnoreCase(meal))
            amount += 500;
        else if("breakfast".equalsIgnoreCase(meal))
            amount += breakfast;
        else if("lunch".equalsIgnoreCase(meal))
            amount += lunch;
        
        return amount;
    }

    @Override
    public boolean makePayment(Payment payment) {
        return paymentDao.addPayment(payment);
    }

    @Override
    public boolean changeStatusReservation(int id) {
        return reservationDao.updateReservationStatus(id, Status.CONFIRMED);
    }

    @Override
    public Payment getPayment(Reservation reservation) {
        System.out.println(reservation.getBookingId());
        return paymentDao.getPayment(reservation);
    }
    
}
