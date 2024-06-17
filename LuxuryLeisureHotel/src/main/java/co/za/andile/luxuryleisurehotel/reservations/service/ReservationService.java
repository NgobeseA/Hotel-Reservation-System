/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.service;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;

/**
 *
 * @author T440
 */
public interface ReservationService {
    boolean makeReservation(Reservation reservation);
}
