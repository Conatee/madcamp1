package com.example.project1;

import android.graphics.Bitmap;

/**
 * name, number, email attribute를 가지는 ContactData class
 */
public class ContactData {
    private String name, number, email;
    private Bitmap photo;
    private boolean hasEmail = false, hasPhoto = false;

    public ContactData(){
    }

    public ContactData(String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
//
    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void emailExists() { this.hasEmail = true; }

    public boolean hasEmail() { return hasEmail; }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public void photoExists() {
        this.hasPhoto = true;
    }

    public boolean hasPhoto() {
        return hasPhoto;
    }
}