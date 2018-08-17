package com.example.techie_dany.letconnect.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.techie_dany.letconnect.Exceptions.InjectionException;
import com.example.techie_dany.letconnect.helpers.contact_DBHelper;
import com.example.techie_dany.letconnect.helpers.message_DBHelper;

import java.util.ArrayList;
import java.util.Iterator;


public class SQLiteDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

     private static final String DATABASE_NAME = "letsconnect";
        public final static String TAG = "lited";


     public SQLiteDB(Context context){
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
//         two tables call and message
        db.execSQL(contact_DBHelper.CREATE_TABLE);
        db.execSQL(message_DBHelper.CREATE_TABLE);
//        Log.i(TAG, "sqlite check:........... "+"CREATE TABLE "+message_DBHelper.TABLE_NAME
//                +"("+message_DBHelper.ID+" INTEGER ,"+message_DBHelper.NAME+" TEXT PRIMARY KEY,"+message_DBHelper.PHONE+
//                " TEXT,"+message_DBHelper.MESSAGE+" TEXT,"+message_DBHelper.DATE+" TEXT"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//         db.execSQL("DROP TABLE IF EXISTS",contact_DBHelper.);
    }


//    insertContact
    public long insertContact(String name, String phone, byte[] pic) throws SQLiteException{

         SQLiteDatabase standDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(contact_DBHelper.NAME, name);
        values.put(contact_DBHelper.PHONE, phone);
        values.put(contact_DBHelper.PIC, pic);
        long id = 0;

        try {
            id= standDB.insert(contact_DBHelper.TABLE_NAME, null, values);
            Log.i(TAG, "Nooooo ");
        }
        catch (SQLiteException se){
            Log.i(TAG, "Yesss" +se);
//            return 26;
        }
        catch (Throwable e){
            Log.i(TAG, "insertContact: ");
        }
        standDB.close();
        Log.i(TAG, "insertContact: "+id);
         return id;
    }


//   get added contacts
    public ArrayList<contact_DBHelper> getDBContacts(){

         ArrayList<contact_DBHelper> dbConList = new ArrayList<>();


         String selectQuery = "SELECT * FROM "+ contact_DBHelper.TABLE_NAME +" ORDER BY "
                 +contact_DBHelper.ID + " DESC";

//        Log.i(TAG, "getDBContacts: " +selectQuery);
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor curc = db.rawQuery(selectQuery, null);
//            iterate
         if(curc.moveToFirst()){
             do{

                 contact_DBHelper setValues = new contact_DBHelper();
                 setValues.setName(curc.getString(curc.getColumnIndex(contact_DBHelper.NAME)));
                 setValues.setPhone(curc.getString(curc.getColumnIndex(contact_DBHelper.PHONE)));
                 setValues.setID(curc.getString(curc.getColumnIndex(contact_DBHelper.ID)));
                 setValues.setPhoto(curc.getBlob(curc.getColumnIndex(contact_DBHelper.PIC)));
                 dbConList.add(setValues);
             }while (curc.moveToNext());
         }
         curc.close();

         db.close();
//         returning list of DB helper class
         return dbConList;
    }

//    getDBMessages
    public ArrayList<message_DBHelper> getDBMessage(){

         ArrayList<message_DBHelper> dbMessageList = new ArrayList<>();

         String selecQuery = "SELECT * FROM "+ message_DBHelper.TABLE_NAME;
//            Log.i(TAG, "selectQuery "+selecQuery);

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery(selecQuery,null);

            if (cur.moveToFirst()){

                do {
                    message_DBHelper values = new message_DBHelper();
                    values.setName(cur.getString(cur.getColumnIndex(message_DBHelper.NAME)));
                    values.setPhone(cur.getString(cur.getColumnIndex(message_DBHelper.PHONE)));
                    values.setMessage(cur.getString(cur.getColumnIndex(message_DBHelper.MESSAGE)));
                    values.setId(cur.getString(cur.getColumnIndex(message_DBHelper.ID)));
                    values.setDate(cur.getString(cur.getColumnIndex(message_DBHelper.DATE)));
                    dbMessageList.add(values);
//                    Log.i(TAG, "mmmm: " +cur.getString(cur.getColumnIndex(message_DBHelper.MESSAGE)));
                }while (cur.moveToNext());
                cur.close();
//                Log.i(TAG, "DB "+dbMessageList.get(0).getName());
//                Log.i(TAG, "get "+dbMessageList.get(0).getPhone());
//                Log.i(TAG, "get "+dbMessageList.get(0).getId());
//                Log.i(TAG, "get "+dbMessageList.get(0).getMessage());
//                Log.i(TAG, "get "+dbMessageList.get(0).getDate());
                db.close();
            }

         return dbMessageList;
    }

//    insertMessage
    public long insertMessage(String name, String phone, String message, String date) throws InjectionException {

        Log.i(TAG, "going to insert "+name+" "+phone+date);
         SQLiteDatabase db = this.getWritableDatabase();

         ContentValues values = new ContentValues();
         values.put(message_DBHelper.NAME, name);
         values.put(message_DBHelper.PHONE, phone);
         values.put(message_DBHelper.MESSAGE, message);
         values.put(message_DBHelper.DATE, date);


        long id = 0;
        try {

        id = db.insert(message_DBHelper.TABLE_NAME,null, values);
        }
        catch(Exception e){
            e.printStackTrace();
        }
         return  id;
    }


    public long removeThisContact(String number){


        SQLiteDatabase db = this.getWritableDatabase();
            long id = db.delete(contact_DBHelper.TABLE_NAME, contact_DBHelper.PHONE +"=?",new String[]{number});
            Log.i(TAG, "removeThisContact: "+id);
//        Cursor cur = db.query(contact_DBHelper.TABLE_NAME, new String[]{contact_DBHelper.ID, contact_DBHelper.NAME,contact_DBHelper.PHONE},
//                contact_DBHelper.PHONE +"=?",new String[]{String.valueOf(number)}, null, null, null, null);
//
//
//        if (cur !=null){
//            cur.moveToFirst();
//        }


//        contact_DBHelper contacthelp1 = new contact_DBHelper(
//                cur.getString(cur.getColumnIndex(contact_DBHelper.NAME)),
//                cur.getString(cur.getColumnIndex(contact_DBHelper.PHONE)),cur.getBlob(cur.getColumnIndex(contact_DBHelper.PIC)));
        db.close();
        return id;
    }


}

//
//    long id = 0;
//        try {
//
//                id = db.insert(message_DBHelper.TABLE_NAME,null, values);
////        Log.i(TAG, "insertMessage: " +id);
//                }
//                catch(Exception e){
////            id=0;
////            Log.i(TAG, "insertMessage: " +e);
//////            throw new InjectionException();
//                }