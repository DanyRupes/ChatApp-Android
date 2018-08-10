package com.example.techie_dany.letconnect.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Contacts;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.adapters.message_selector_adapter;
import com.example.techie_dany.letconnect.helpers.message_phonehelper;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Message_Selector_Activity extends AppCompatActivity {


    private static final String TAG = "mess";
    private RecyclerView message_select_cycler;
    private RecyclerView.Adapter message_select_adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message__selector_);

        message_select_cycler = (RecyclerView) findViewById(R.id.message_select_cycler);
        message_select_cycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        message_select_cycler.setLayoutManager(layoutManager);
//        getMessageList();
        message_select_adapter = new message_selector_adapter(getMessageList(Message_Selector_Activity.this));


        message_select_cycler.setAdapter(message_select_adapter);
    }



//    getMessageList()=>{}

         public ArrayList<message_phonehelper> getMessageList(Context context) {

        ArrayList<message_phonehelper> phone_messageList = new ArrayList<message_phonehelper>();

//        ContentResolver csv = getContentResolver();
//        Uri URIsms = Uri.parse("content://sms/inbox");


        Uri URIsms = Uri.parse("content://sms/");

        Cursor cur = getContentResolver().query(URIsms, null, null, null, null);
        Cursor phone_cur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null,null);


        ///Settingup contact list - with  his number
        HashMap<String, String> contactMap = new HashMap<String, String>();

        while (phone_cur.moveToNext()){
            String phone = phone_cur.getString(phone_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String  name = phone_cur.getString(phone_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            contactMap.put(phone, name);
        }
             phone_cur.close();

//        Starting message read :
        if(cur.getCount() > 0) {    //cur.modeToFirst()
            while (cur.moveToNext()) {


                String address = cur.getString(cur.getColumnIndex("address"));
                String body = cur.getString(cur.getColumnIndex("body"));
                String tempDate = cur.getString(cur.getColumnIndex("date"));


//                converting date unix fromat to normal date
                Date tempDate2 = new java.util.Date(Long.parseLong(tempDate));
                SimpleDateFormat smp = new SimpleDateFormat("dd-MM-yy hh:mm a");
                String date = smp.format(tempDate2).replace("-","/");

                Log.i(TAG, "date " +date);


                String myName=null;

//                fetching messages with  contact  name
                        try {
                                String name = contactMap.get(address);
                                    if(name != null){
                                        myName = name;   //if name is available get from conatct provider
                                        Log.i(TAG, "Do "+myName);
                                    }
                                    else {
                                        myName = address;  //if name is not available

                                    }
                        }
                        catch (Exception e){
                            Log.i(TAG, "Not " +address);
                        }
//                        Log.i(TAG, "messages "+myName +" " +address);
                        message_phonehelper msg = new message_phonehelper(myName, address, body,date);
                        phone_messageList.add(msg);
            }
        }
        cur.close();

//        if list empty(permission denial)
        try {
//            check
            Log.i(TAG, "getMessageList: " +phone_messageList.get(0));
        }
        catch (Exception e){
            Toast.makeText(context,"Please Add Permission for Read SMS..",Toast.LENGTH_LONG).show();
        }
            return  phone_messageList;

        }

        @Override
        public void onStart(){
            super.onStart();
//            after add permission return to app:
            message_select_adapter = new message_selector_adapter(getMessageList(Message_Selector_Activity.this));


            message_select_cycler.setAdapter(message_select_adapter);
        }


}



//        Log.i(TAG, ""+contactMap.get("+919952607735"));
//for address like VT-VODAFONE...
//                        Log.i(TAG, ""+address);
//                                Boolean checkOnlyNum = Pattern.compile("[a-zA-Z]").matcher(address).matches();
//        for(Map.Entry m:contactMap.entrySet()){
////            Log.i(TAG, ""+m.getKey()+" " +m.getValue());
//                }
//                Log.i(TAG, "address " + address);
//                Log.i(TAG, "body " + body);

//                try {
//                    Pattern pt = Pattern.compile("^[0-9]+$");
//                    Log.i(TAG, "Okay" +checkOnlyNum);
//                    Log.i(TAG, "addr" +address);
//                    int num = Integer.parseInt(address.split("\\+")[1]);
//                    Log.i(TAG, "Num " + num);
//                    myNum = address;
//                } catch (Exception e) {
////                Log.i(TAG, "address" +address);
////                    Log.i(TAG, "uhoh" + e);
//                }
//Boolean checkOnlyNum = Pattern.compile("^[\\+0-9]+$").matcher(address).matches();
//            try {
//                    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
//                    Uri.encode(address));
//                    contactCursor = getContentResolver().query(URIsms,null,null,null,null);
//                    Log.i(TAG, ""+contactCursor.getCount());
//                    if(contactCursor !=null && contactCursor.moveToNext()){
//                    displayName = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
//                    Log.i(TAG, "display Name" +displayName);
//                    }
//                    }
//            catch (Exception e){
//                    e.printStackTrace();
//                    }
//                    finally {
//                    if (contactCursor != null){
//                    contactCursor.close();
//                    }
//                    }
//         Log.i(TAG, "address " + address);
////                Log.i(TAG, "body " + body);
////                message_phonehelper msg = new message_phonehelper(address, body);
////                phone_messageList.add();
//
//                 Cursor phone_cur = csv.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//
//                 String phoneNo;
//        while (phone_cur.moveToNext()) {
//            String allNum = phone_cur.getString(phone_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            if (address.equals(allNum)) {
//                String myName = phone_cur.getString(phone_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                Log.i(TAG, "ohoooooooooooooooooooooooooooooooooooooooooooo " + myName);
//                Log.i(TAG, "ohoooooooo " + address);
//            }
//        }


//        String address, body,name;
///address : 8099594545 body-hii hello how are u
//        while (cur != null && cur.moveToNext()) {
////            address = cur.getString(cur.getColumnIndex("address"));
//            String body = cur.getString(cur.getColumnIndex("body"));
//            String address = cur.getString(cur.getColumnIndex("address"));
//            try {
//            String name = cur.getString(cur.getColumnIndexOrThrow("address"));
//            Log.i(TAG, "name " +name);
//            }
//            catch (Exception e){
//                Log.i(TAG, "x "+e);
////                name = "NOoooo";
//            }
////
////            Log.i(TAG, "address " + address);
////            Log.i(TAG, "body " + body);
//            Log.i(TAG, "             ");
//
//    }