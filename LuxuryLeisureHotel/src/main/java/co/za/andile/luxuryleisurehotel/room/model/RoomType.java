/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.za.andile.luxuryleisurehotel.room.model;

/**
 *
 * @author T440
 */
 enum RoomType {
    SINGLE,DOUBLE,SUITE;
    private String picture;

    private RoomType() {
    }

    private RoomType(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    
 }