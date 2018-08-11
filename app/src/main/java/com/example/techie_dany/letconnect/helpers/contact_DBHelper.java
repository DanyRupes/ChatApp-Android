package com.example.techie_dany.letconnect.helpers;

public class contact_DBHelper {

    private String Name;
    private String Phone;
    private byte[] Photo;
    private  String Id;
    public static final String TABLE_NAME ="contacts";



    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String PIC="PIC";


    public static final String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME +"("+ID +" INTEGER,"
            +NAME +" TEXT PRIMARY KEY," +PHONE + " TEXT," +PIC +" BLOB"+")";


    public contact_DBHelper(){}

    public contact_DBHelper(String name, String phone, byte[] photo) {
        Name = name;
        Phone = phone;
        Photo = photo;
    }


    public  String getID() {
        return Id;
    }
    public void  setID(String id){
        Id =  id;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }





}
