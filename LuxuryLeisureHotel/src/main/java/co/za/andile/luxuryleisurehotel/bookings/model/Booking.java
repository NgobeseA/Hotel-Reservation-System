/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.bookings.model;

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

@AllArgsConstructor@Getter@Setter@NoArgsConstructor
public class Booking {
    private int bookingId;
    private User user_id;
    private Room room_id;
    private LocalDateTime created_on;
    private LocalDateTime check_in;
    private LocalDateTime check_out;
    private String dinning_pref;
    
    public Booking(User user_id, Room room_id, LocalDateTime created_on, LocalDateTime check_in, LocalDateTime check_out, String dinning_pref) {
        this.user_id = user_id;
        this.room_id = room_id;
        this.created_on = created_on;
        this.check_in = check_in;
        this.check_out = check_out;
        this.dinning_pref = dinning_pref;
    }
}
