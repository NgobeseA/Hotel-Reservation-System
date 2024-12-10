/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.payments.model;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import co.za.andile.luxuryleisurehotel.users.model.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author T440
 */
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class Payment {
    private int payment_id;
    private Reservation reservation;
    private String accHolder;
    private String cardNumber;
    private double amount;
    private LocalDateTime paymentDate;

    public Payment(Reservation reservation, String accHolder, String cardNumber, double amount) {
        this.reservation = reservation;
        this.accHolder = accHolder;
        this.cardNumber = cardNumber;
        this.amount = amount;
    }
    
}
