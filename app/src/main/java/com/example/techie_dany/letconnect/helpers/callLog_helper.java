package com.example.techie_dany.letconnect.helpers;

public class callLog_helper {

    private String name,phone, type, date;

    callLog_helper(){}

    public callLog_helper(String name, String phone, String type, String date) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
