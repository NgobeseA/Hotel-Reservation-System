/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.reservations.model;

import co.za.andile.luxuryleisurehotel.room.model.Room;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Train 01
 */

@AllArgsConstructor@NoArgsConstructor@Setter@Getter
public class Reservation {
    private int bookingId;
    private User user;
    private Room room;
    private LocalDateTime created_on;
    private LocalDateTime check_in;
    private LocalDateTime check_out;
    private Status status;
    
    public Reservation(User user, Room room, LocalDateTime check_in, LocalDateTime check_out, Status status) {
        this.user = user;
        this.room = room;
        this.check_in = check_in;
        this.check_out = check_out;
        this.status = status;
    }
}
