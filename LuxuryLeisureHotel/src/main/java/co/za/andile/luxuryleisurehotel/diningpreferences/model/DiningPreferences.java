/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.diningpreferences.model;

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
    private String mealType;
    private String dietaryRestriction;

    public DiningPreferences(String mealType, String dietaryRestriction) {
        this.mealType = mealType;
        this.dietaryRestriction = dietaryRestriction;
    }
}
