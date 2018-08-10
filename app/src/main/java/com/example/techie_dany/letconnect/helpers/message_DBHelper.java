package com.example.techie_dany.letconnect.helpers;

public class message_DBHelper {


    private String name;
    private String phone;
    private String message;
    private String date;



    private String id;

    public static final String TABLE_NAME = "messages";



    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String MESSAGE ="MESSAGE";
    public static final String DATE = "DATE";

    public static final String CREATE_TABLE = "CREATE TABLE "+message_DBHelper.TABLE_NAME
            +"("+message_DBHelper.ID+" INTEGER  PRIMARY KEY AUTOINCREMENT,"+message_DBHelper.NAME+" TEXT,"+message_DBHelper.PHONE+
            " TEXT,"+message_DBHelper.MESSAGE+" TEXT,"+message_DBHelper.DATE+" TEXT"+")";

    public  message_DBHelper (){}

    public message_DBHelper(String ID,String name, String phone, String message, String date){

        this.name = name;
        this.phone = phone;
        this.message = message;
        this.date = date;
        this.id  = ID;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


//for avaidong repeated numbers from contact page

//    public static final String CREATE_TABLE = "CREATE TABLE "+message_DBHelper.TABLE_NAME
//            +"("+message_DBHelper.ID+" INTEGER ,"+message_DBHelper.NAME+" TEXT PRIMARY KEY,"+message_DBHelper.PHONE+
//            " TEXT,"+message_DBHelper.MESSAGE+" TEXT,"+message_DBHelper.DATE+" TEXT"+")";