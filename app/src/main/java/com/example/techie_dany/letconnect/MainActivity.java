package com.example.techie_dany.letconnect;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.example.techie_dany.letconnect.Database.SQLiteDB;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    public static final String TAG="main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


//        Log.i(TAG, "url "+DebugDB.getAddressLog());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));





//            Get cotact from Contact App- reciving by intent
        Intent recive_intent = getIntent();
        String type = recive_intent.getType();
        String action  = recive_intent.getAction();


        if(Intent.ACTION_SEND.equals(action) && type.equals("text/x-vcard")){
            Log.i(TAG, "receving contact type: " +type);
            Log.i(TAG, "receving contact action: " +action);
            Log.i(TAG, "receving contact extra: " +recive_intent.getExtras());
                Log.i(TAG, "receving contact STREAM: " +recive_intent.getParcelableExtra(Intent.EXTRA_STREAM));
            Uri rec_contact_uri = recive_intent.getParcelableExtra(Intent.EXTRA_STREAM);

            InputStream inp = null;
            try {
                inp = getContentResolver().openInputStream(rec_contact_uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            StringBuffer sb = new StringBuffer("");

            int c;
            try {
                while ((c = inp.read()) != -1) {
                    sb.append((char)c);
                }
            }
            catch (Exception e){}
            Log.i(TAG, "onCreate: "+sb.toString());// \\+\\d+
            Pattern pattern = Pattern.compile("[\\+\\d]+[\\d-]+");
            String contact_phone0 = null;
            try {
                Matcher matcher = pattern.matcher(sb.toString());

                matcher.find();
                contact_phone0 = matcher.group(0);
                        Log.i(TAG, "Phone_num  " + contact_phone0);
            }
            catch (Exception e){
                Log.i(TAG, "No Matching Contact  numbers"+e);
            }

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.NUMBER+" =? ", new String[]{contact_phone0.replace("-","")}, null);

            Log.i(TAG, "onCreate: "+contact_phone0.replace("-",""));
            String contact_phone= contact_phone0.replace("-","");
            String contact_name,contact_id;
            Bitmap contact_pic = null;
            byte[] contact_photo_bytes = new byte[0];
            SQLiteDB sqLiteDB = new SQLiteDB(this);
//            int i=0;
//            cursor.moveToNext();
            while (cursor.moveToNext()){
                Log.i(TAG, "innnnnnnnnnnn: ");
                contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                contact_name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                Uri helperUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.parseLong(contact_id));

                Uri Mphoto_uri = Uri.withAppendedPath(helperUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                Cursor photo_cur = getContentResolver().query(Mphoto_uri,new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);

                while (photo_cur.moveToNext()){
                    contact_photo_bytes = photo_cur.getBlob(0);
                }
                photo_cur.close();

//              for default profile pic
//                Bitmap finalPic;
//                if(contact_photo_bytes.length==0){
//
//                }

                Log.i(TAG, "contact_details:" +contact_name+" "+contact_phone+" "+contact_photo_bytes.length);
                try {

                    long id =sqLiteDB.insertContact(contact_name, contact_phone, contact_photo_bytes);
//                    Log.i(TAG, "onCreate: "+id);
                    if(id == -1){
//                        Log.i(TAG, "Already....");
                        Toast.makeText(this, ""+contact_name+" present in LetsConnect",Toast.LENGTH_LONG).show();
                    }
                    else {
//                        Log.i(TAG, "id "+id);
                        Toast.makeText(this, ""+contact_name+" Added to LetsConnect",Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception e){
//                    Log.i(TAG, "onCreate: "+e);
                    Toast.makeText(this, "Cannot Insert Contact",Toast.LENGTH_LONG).show();
                }
               // i++;
            }
            cursor.close();


//            naviagete to contact fragment
            mViewPager.setCurrentItem(1, true);
        }


    }





    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
                    Call_Log callLog = new Call_Log();
                    Contacts contacts = new Contacts();
                    Message message = new Message();
            switch (position){
                case 0:
                    return callLog.newInstance();
                case 1:
                    return contacts.newInstances();
                case 2:
                    return message.newInstances();
                    default:
                    fragment = message.newInstances();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


//            while (cursor.moveToNext()){
//                    contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//                    contact_name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//
//                    Uri helperUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,Long.parseLong(contact_id));
////            Log.i(TAG, "onCreate: " +helperUri);
//
//// this for CommonDataKinds.Phone               08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074
//// this for ContactsContract.Contacts.CONTENT_URI  08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074
//
//
//                    Uri Mphoto_uri = Uri.withAppendedPath(helperUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
////            Log.i(TAG, "onCreate: " +Mphoto_uri);
////this for CommonDataKinds.Phone                08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074/photo
////this for ContactsContract.Contacts.CONTENT_URI  08-17 22:00:31.633 31307-31307/? I/main: onCreate: content://com.android.contacts/data/phones/1074/photo
//
//                    Cursor photo_cur = getContentResolver().query(Mphoto_uri,new String[]{ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
////            Log.i(TAG, "onCreate: "+photo_cur.getCount());
//
//                    while (photo_cur.moveToNext()){
//                    contact_photo_bytes = photo_cur.getBlob(0);
////                if (contact_photo_bytes != null){
////                    InputStream inputStream = new ByteArrayInputStream(contact_photo_bytes);
////                    contact_pic= BitmapFactory.decodeStream(inputStream);
////                }
////                else{ Log.i(TAG, "No Datas"); }
//                    }
//                    photo_cur.close();
////            Contact Name, Phone, Pic found
//                    Log.i(TAG, "contact_details:" +contact_name+" "+contact_phone+" "+contact_photo_bytes);
//                    try {
//
//                    sqLiteDB.insertContact(contact_name, contact_phone, contact_photo_bytes);
//                    }
//                    catch (Exception e){
//                    Log.i(TAG, "onCreate: "+e);
//                    Toast.makeText(this, "Cannot Insert Contact",Toast.LENGTH_LONG).show();
//                    }
//                    }

//    public static final String TABLE_NAME = "messages";
//    public static final String ID = "ID";
//    public static final String NAME = "NAME";
//    public static final String PHONE = "PHONE";
//    public static final String MESSAGE ="MESSAGE";
//    public static final String PIC = "PIC";
//        Log.i(TAG, "onCreate: "+"CREATE TABLE "+TABLE_NAME
//                +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" TEXT,"+PHONE+
//                " TEXT,"+MESSAGE+" TEXT,"+PIC+" TEXT"+")");

//Query Check
//    public static final String TABLE_NAME ="letsconnect";
//    public static final String ID = "ID";
//    public static final String NAME = "NAME";
//    public static final String PHONE = "PHONE";
//    public static final String PIC="PIC";
//        Log.i(TAG, "sqlite check: "+"CREATE TABLE " +TABLE_NAME +"("+ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
//                +NAME +" TEXT," +PHONE + " TEXT," +PIC +" TEXT"+")");





//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));