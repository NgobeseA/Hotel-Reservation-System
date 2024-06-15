/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.diningpreferences.model;

import co.za.andile.luxuryleisurehotel.reservations.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author T440
 */
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class DiningPreferences {
    private int diningId;
    private Reservation reservation;
    private MealType mealType;
    private DietaryRestriction dietaryRestriction;

    public DiningPreferences(Reservation reservation, MealType mealType, DietaryRestriction dietaryRestriction) {
        this.reservation = reservation;
        this.mealType = mealType;
        this.dietaryRestriction = dietaryRestriction;
    }
}
