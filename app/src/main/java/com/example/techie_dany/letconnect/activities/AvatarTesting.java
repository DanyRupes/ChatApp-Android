package com.example.techie_dany.letconnect.activities;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Call_Log;
import com.example.techie_dany.letconnect.Contacts;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.contact_phonehelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class AvatarTesting extends AppCompatActivity {

    private static final String TAG ="avatar" ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_testing);

        imageView = (ImageView) findViewById(R.id.myAvatar);

//        Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        Log.i(TAG, "avatar : " + cur.getCount());
//
//        Bitmap bitmap = null;
//        while (cur.moveToNext()){
//             String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//             if(id !=null){
//
//                    //getting ID
//                 Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
////                 Log.i(TAG, "contaxturi: " +id); //content://com.android.contacts/contacts/381
//
////                 Getting photo uri
//                 Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
////                 Log.i(TAG, "photouri: " +photoUri);  //content://com.android.contacts/contacts/381/photo
//
//
//
//                 Cursor cursor = getContentResolver().query(photoUri, new String[]{ContactsContract.Contacts.Photo.PHOTO},null,null,null);
//
//                 try {
//                     if(cursor.moveToFirst()){
//                         byte[] data = cursor.getBlob(0);
//
//                         if(data !=null){
//                             InputStream inputStream = new ByteArrayInputStream(data);
//                             Bitmap beet =  BitmapFactory.decodeStream(inputStream);
//                             imageView.setImageBitmap(beet);
//                         }
//                     }
//                 }
//                 finally {
//                     cursor.close();
//                 }
//             }
//        }
//



    }
    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "checking");
        Call_Log call_log = new Call_Log();
        try{
//            Toast.makeText(this,"Give Permission to Read Contacts, Call Logs, Messages",Toast.LENGTH_LONG).show();
            Log.i(TAG, "onStart: " +call_log.getLogs(AvatarTesting.this).size());
            if(call_log.getLogs(AvatarTesting.this).size() !=0){
                Log.i(TAG, "finish()");
                finish();
            }
        }
        catch (Exception e){
            Log.i(TAG, "onStartAvatar: "+e);
        }
    }
}
