/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.service;

import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDao;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import java.sql.Connection;

/**
 *
 * @author T440
 */
public class ReservationServiceImpl implements ReservationService{
    private ReservationDao reservationDao;

    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
    
    @Override
    public boolean makeReservation(Reservation reservation) {
        return reservationDao.addReservation(reservation);
    }
    
}
