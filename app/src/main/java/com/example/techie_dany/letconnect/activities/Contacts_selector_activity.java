package com.example.techie_dany.letconnect.activities;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Contacts;
import com.example.techie_dany.letconnect.MainActivity;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.adapters.Contact_Selector_Adapter;
import com.example.techie_dany.letconnect.helpers.contact_phonehelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Contacts_selector_activity extends AppCompatActivity {


    private RecyclerView cont_selector_cycl;

    private RecyclerView.Adapter Cont_Selector_Adapter;

    private RecyclerView.LayoutManager cont_sl_layout;

    private static final String TAG = "conact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_selector);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            cont_selector_cycl = findViewById(R.id.contact_select_cycler);
            cont_selector_cycl.setHasFixedSize(true);

            cont_sl_layout = new LinearLayoutManager(getApplicationContext());
            cont_selector_cycl.setLayoutManager(cont_sl_layout);


//            getContactList();
            Cont_Selector_Adapter = new Contact_Selector_Adapter(getContactList(Contacts_selector_activity.this));

            cont_selector_cycl.setAdapter(Cont_Selector_Adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
//      returning a List of classes

        private ArrayList<contact_phonehelper> getContactList(Context context) {

        ContentResolver CRS = getContentResolver();

        Cursor cur = CRS.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        ArrayList<contact_phonehelper> Gump = new ArrayList<>();

//        contact_phonehelper CHelper[] = new contact_phonehelper[cur.getCount()];


            while (cur.moveToNext()) {
                Bitmap photo = null;
                String id_str = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String c_name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Bitmap beet = null;
//                08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074
//                08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074/photo

//    08-17 22:23:48.996 32639-32639/com.example.techie_dany.letconnect I/conact: getContactList: content://com.android.contacts/contacts/1221
//    08-17 22:23:48.996 32639-32639/com.example.techie_dany.letconnect I/conact: getContactList: content://com.android.contacts/contacts/1221/photo

//                Log.i(T
// AG, "id_str" +id_str);
//                ID uri by using id_str - /id
                Uri contact_uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.parseLong(id_str));

//                photo uri - single /photo
                Log.i(TAG, "getContactList: " +contact_uri);
                Uri photo_uri = Uri.withAppendedPath(contact_uri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                Log.i(TAG, "getContactList: " +photo_uri);

//                Photo Cursor - single-- with permission /photo_uri:
                Cursor photo_cur = getContentResolver().query(photo_uri,new String[]{ContactsContract.Contacts.Photo.PHOTO},null,null,null);

//                Photo process
                try {
//                    If pic  available
                    if (photo_cur.moveToFirst()){
                        Log.i(TAG, "  : "+1);
                        byte[] data = photo_cur.getBlob(0);
                       if(data !=null){
                           Log.i(TAG, "  : "+"_"+2);
                           InputStream inputStream = new ByteArrayInputStream(data);
                           beet = BitmapFactory.decodeStream(inputStream);
                       }
                       else {
                           Log.i(TAG, " : " );
                       }
                    }
//                    If pic not available
                    else {
                        beet = null;
                    }
                    photo_cur.close();

                }catch (Exception e){
                    Log.i(TAG, "getContactList: "+e);
                }

//                Getting Phone Number Process:
                int c_hasNum = Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));


                contact_phonehelper tempCHhelper = null;
                if (c_hasNum> 0) {
//                    with selection -contact_ID  selection Arguments : id
                    Cursor phone_cur = CRS.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id_str}, null);

//                    list of numbers
                    ArrayList<String> p_number = new ArrayList<String>();
//                      iterate through phone nums
                    if(phone_cur.getCount()>0) {
                        while (phone_cur.moveToNext()) {
                            String phone_no = phone_cur.getString(phone_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            p_number.add(phone_no);
                        }
                    }else{p_number.add("No Numbers");}

                    phone_cur.close();
                            Log.i(TAG, c_name + " " + p_number + " myPic");
                    tempCHhelper = new contact_phonehelper(c_name,p_number,beet);
                }

                Gump.add(tempCHhelper);
            }
        cur.close();
            try {

              Log.i(TAG, "getContactList: " +Gump.get(0));
            }
            catch (Exception e){
                Log.i(TAG, "uohohohooh "+e);
                Toast.makeText(context,"Please Add Permission to read Contacts...and Open Again",Toast.LENGTH_LONG).show();
            }

        return  Gump;
    }


    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume: ");
//        Cont_Selector_Adapter = new Contact_Selector_Adapter(getContactList(Contacts_selector_activity.this));

//         cont_selector_cycl.setAdapter(Cont_Selector_Adapter);
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


        @Override
    public void onStart() {

        super.onStart();
        Log.i(TAG, "onStart: ");
    }
    @Override
    public void onPause() {

        super.onPause();
        Log.i(TAG, "onPause: ");
    }
    public void onStop() {

        super.onStop();
        Log.i(TAG, "onStop: ");
    }
}
