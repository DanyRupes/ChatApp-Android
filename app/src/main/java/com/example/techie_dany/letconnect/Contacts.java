package com.example.techie_dany.letconnect;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.techie_dany.letconnect.Database.SQLiteDB;
import com.example.techie_dany.letconnect.activities.Contacts_selector_activity;
import com.example.techie_dany.letconnect.adapters.*;
import com.example.techie_dany.letconnect.helpers.contact_DBHelper;

public class Contacts extends Fragment {
        public static String TAG = "contacti";

    public RecyclerView contact_cycler;
    public RecyclerView.Adapter contact_adapter;
    public RecyclerView.LayoutManager contact_layout;
    FloatingActionButton fbContact;

    private SQLiteDB sqLiteDB;
//    public ProgressBar progress_contacts;

    public Contacts() {}

    public Contacts newInstances (){
        Contacts contactFrag = new Contacts();
        return contactFrag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        final View view = layoutInflater.inflate(R.layout.fragment_contacts, container, false);

        Log.d(TAG, "Hello : ");
        contact_cycler = (RecyclerView) view.findViewById(R.id.contact_cyc);
        contact_cycler.setHasFixedSize(true);

        contact_layout = new LinearLayoutManager(getContext());
        contact_cycler.setLayoutManager(contact_layout);

        sqLiteDB = new SQLiteDB(this.getContext());
//        progress_contacts = (ProgressBar) view.findViewById(R.id.progress_contacts);
//        progress_contacts.setVisibility(View.GONE);

        contact_adapter = new contact_Adapter(sqLiteDB.getDBContacts());


        contact_cycler.setAdapter(contact_adapter);
        fbContact = (FloatingActionButton) view.findViewById(R.id.fab_contact);

        fbContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progress_contacts.setVisibility(View.VISIBLE);


                Intent c_Activity = new Intent(getContext(), Contacts_selector_activity.class);
                startActivity(c_Activity);


            }
        });




        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
//        again comes
        contact_adapter = new contact_Adapter(sqLiteDB.getDBContacts());
        contact_cycler.setAdapter(contact_adapter);
//        contact_adapter.notifyDataSetChanged();

    }


}
//        ArrayList<contact_DBHelper> contactList = new ArrayList<contact_DBHelper>();
//        for (int i=0;i<10;i++){
////            contactList.add();
//        }


//        try{
//            //Insert contact into DB
////            long iid = sqLiteDB.insertContact("Dany","8098733326","danypic");
////            Log.i(TAG, "dbid "+iid);
////            Toast.makeText(getContext()," ID ",Toast.LENGTH_LONG).show();
//
//
////            Log.i(TAG, "getContacts"+sqLiteDB.getDBContacts());
//        }
//        catch (Exception e){
//            Log.i(TAG, "ooohoooooo "+e);
//        }

//        Log.i(TAG, "times "+times);
//        times++;
//        if(container.getParent()!=null){
////        Toast.makeText(getContext(),""+(ViewGroup)container.getParent(),Toast.LENGTH_LONG).show();
//        ((ViewGroup)container.getParent()).removeView(container);
//        }