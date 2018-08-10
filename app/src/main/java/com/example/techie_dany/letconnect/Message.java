package com.example.techie_dany.letconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Database.SQLiteDB;
import com.example.techie_dany.letconnect.activities.Message_Selector_Activity;
import com.example.techie_dany.letconnect.adapters.Message_Adapter;
import com.example.techie_dany.letconnect.adapters.contact_Adapter;
import com.example.techie_dany.letconnect.adapters.message_selector_adapter;
import com.example.techie_dany.letconnect.helpers.message_DBHelper;

import java.util.ArrayList;


public class Message extends Fragment {


    private static final String TAG = "mess" ;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter message_recycl_adapter;
    public RecyclerView.LayoutManager contact_layoutManager;
//    public ArrayList<message_DBHelper> message_dbHelper;
    private SQLiteDB sqLiteDB;
//    Message messageFrag
    public Message(){}
//
    public  Message newInstances(){

        Message messageFrag = new Message();
        return messageFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){

        View view = layoutInflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.message_cycler);
        recyclerView.setHasFixedSize(true);
        contact_layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(contact_layoutManager);

        try {
            sqLiteDB = new SQLiteDB(this.getContext());
            message_recycl_adapter = new Message_Adapter(sqLiteDB.getDBMessage());
        }
        catch (Exception e){
            Log.i(TAG, "uhoooo" +e);
        }

        recyclerView.setAdapter(message_recycl_adapter);

        FloatingActionButton fbMessage = (FloatingActionButton) view.findViewById(R.id.fab_message);



        fbMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(),Message_Selector_Activity.class));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        message_recycl_adapter = new Message_Adapter(sqLiteDB.getDBMessage());
        recyclerView.setAdapter(message_recycl_adapter);
    }

}

//        if(container.getParent()!=null){
////            Toast.makeText(getContext(),""+container.getId(),Toast.LENGTH_LONG).show();
//            ((ViewGroup)container.getParent()).removeView(container);
//        }
