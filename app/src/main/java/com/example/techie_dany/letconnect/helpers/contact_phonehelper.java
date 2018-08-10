package com.example.techie_dany.letconnect.helpers;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class contact_phonehelper {
    private String name;
    private String id;
    private Bitmap bitly;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    ArrayList<String> phone = new ArrayList<String>();

    public contact_phonehelper(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return bitly;
    }

    public void setPhoto(Bitmap photo) {
        this.bitly = photo;
    }

    public contact_phonehelper(String name, ArrayList phone, Bitmap photo) {
        this.name = name;
        this.phone.addAll(phone);
        this.bitly = photo;
    }

}
