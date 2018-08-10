package com.example.techie_dany.letconnect.helpers;

public class message_phonehelper {
    private String name;
    private String phone;
    private String date;


    public message_phonehelper(){}
    public message_phonehelper(String name, String phone, String message, String date) {
        this.name = name;
        this.phone = phone;
        this.message = message;
        this.date = date;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
