/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.service;

import co.za.andile.luxuryleisurehotel.reservations.dao.ReservationDao;
import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.reservations.model.Status;
import co.za.andile.luxuryleisurehotel.room.exception.NoRoomAvailableException;
import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.room.roomtype.model.RoomType;
import co.za.andile.luxuryleisurehotel.room.service.RoomService;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author T440
 */
public class ReservationServiceImpl implements ReservationService{
    private ReservationDao reservationDao;
    private RoomService roomService;

    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationServiceImpl(RoomService roomService) {
        this.roomService = roomService;
    }
    
    
    @Override
    public boolean makeReservation(Reservation reservation) {
        return reservationDao.addReservation(reservation);
    }

    @Override
    public Room getRoomFromRoomType(int roomtype_id, LocalDateTime check_in, LocalDateTime check_out) throws NoRoomAvailableException{
        List<Room> availableRooms = roomService.getAllAvailableRooms(check_in, check_out).stream()
                .filter(room -> room.getRoomType().getId() == roomtype_id)
                .collect(Collectors.toList());
        if(availableRooms.isEmpty())
            throw new NoRoomAvailableException("No rooms available between these date try another type of room");
        return availableRooms.get(0);
    }

    @Override
    public boolean editRoomStatus(int room_id) {
        return roomService.editRoom(room_id);
    }

    @Override
    public boolean cancelReservation(int reservation_id) {
        return reservationDao.updateReservationStatus(reservation_id, Status.CANCELLED);
    }

    @Override
    public List<Reservation> getReservations() {
        return reservationDao.getReservations();
    }

    @Override
    public List<Reservation> filteredReservation() {
        return reservationDao.getReservations().stream()
                .filter(r -> !r.getStatus().equals(Status.CANCELLED))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> getUserReservation(String email) {
        return getReservations().stream()
                .filter(r -> email.equalsIgnoreCase(r.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkInReservation(int reservation_id) {
        return reservationDao.updateReservationStatus(reservation_id, Status.CHECKED_IN);
    }

    @Override
    public boolean checkOutReservation(int reservation_id) {
        return reservationDao.updateReservationStatus(reservation_id, Status.CHECKED_OUT);
    }

    @Override
    public List<Reservation> getUpComingStayReservation(String user_email) {
        return getUserReservation(user_email).stream()
                .filter(r -> r.getStatus() != Status.CANCELLED && r.getStatus() != Status.CHECKED_OUT)
                .collect(Collectors.toList());
    }

   
}
