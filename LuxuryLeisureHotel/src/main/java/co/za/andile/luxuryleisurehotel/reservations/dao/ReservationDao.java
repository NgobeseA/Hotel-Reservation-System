/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.dao;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;
import java.util.List;

/**
 *
 * @author T440
 */
public interface ReservationDao {
    boolean addReservation(Reservation reservation);
    boolean editReservation(Reservation reservation);
    List<Reservation> getUserReservations(int user_id);
    List<Reservation> getReservations();
    boolean updateReservationStatus(int id, Status status);
    boolean removeReservation(int reservation_id);
}
