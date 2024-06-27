/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.service;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.room.exception.NoRoomAvailableException;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author T440
 */
public interface ReservationService {
    boolean makeReservation(Reservation reservation);
    Room getRoomFromRoomType(int roomtype_id, LocalDateTime check_in, LocalDateTime check_out) throws NoRoomAvailableException;
    boolean editRoomStatus(int room_id);
    boolean cancelReservation(int reservation_id);
    List<Reservation> getReservations();
    List<Reservation> filteredReservation();
    List<Reservation> getUserReservation(String email);
    boolean checkInReservation(int reservation_id);
    boolean checkOutReservation(int reservation_id);
    List<Reservation> getUpComingStayReservation(String user_email);
}
