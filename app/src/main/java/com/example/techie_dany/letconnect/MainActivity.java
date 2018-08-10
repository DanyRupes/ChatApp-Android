package com.example.techie_dany.letconnect;

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

import com.amitshekhar.DebugDB;

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




        Log.i(TAG, "url "+DebugDB.getAddressLog());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
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