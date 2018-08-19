package com.example.techie_dany.letconnect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.techie_dany.letconnect.activities.AvatarTesting;
import com.example.techie_dany.letconnect.adapters.CalLog_Adapter;
import com.example.techie_dany.letconnect.helpers.callLog_helper;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Call_Log extends Fragment {

    private static final String TAG = "calog";
    public RecyclerView calLog_recyle;
    public RecyclerView.Adapter calLog_Adapter;
    public RecyclerView.LayoutManager calLog_layout;
    boolean startMe = false;


    public Call_Log() {
    }

    public Call_Log newInstance() {
        Call_Log callFrog = new Call_Log();

        return callFrog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    ArrayList getMeLog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call_log, container, false);

        calLog_recyle = view.findViewById(R.id.recycler_log);
        calLog_recyle.setHasFixedSize(true);
        calLog_layout = new LinearLayoutManager(getContext());
        calLog_recyle.setLayoutManager(calLog_layout);


        getMeLog = getLogs(getContext());






//        Log.i(TAG, ""+getLogs(getContext()));


        return view;
    }


    public ArrayList<callLog_helper> getLogs(Context context) {

        Uri callLogUri = Uri.parse("content://call_log/calls");
        StringBuffer sb = new StringBuffer();
        Cursor cursor = null;
        boolean startMe = false;
        try {
         cursor = context.getContentResolver()
                .query(callLogUri, null, null, null, CallLog.Calls.DATE + " DESC");
            startMe = true;
        }
        catch(Exception e){
            Log.i(TAG, "s        ss  s s s ss  s ");

//            Toast.makeText(context,"Please Give Permission to Read Contacts",Toast.LENGTH_LONG).show();
//            startActivity(new Intent(context, AvatarTesting.class));
        }
        if(startMe){
        //adding all contacts to Map to further bind (ph_number and Name)
        HashMap<String, String> logMap= new HashMap<String, String>();

        ArrayList<callLog_helper> callLogHelpers = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        while (cursor.moveToNext()){
            String ph_Number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
            String getType = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
            String unix_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));

            //getting call type
            int caseTypes = Integer.parseInt(getType);
            String type = null;
            switch (caseTypes){
                case CallLog.Calls.OUTGOING_TYPE:
                    type = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    type = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    type = "Missed";
                    break;
            }

                 date.setTimeInMillis(Long.parseLong(unix_date));
                     int day = date.get(Calendar.DAY_OF_MONTH);
                     int month = date.get(Calendar.MONTH);
                     int year = date.get(Calendar.YEAR);
                     int hr = date.get(Calendar.HOUR_OF_DAY);
                     int minute = date.get(Calendar.MINUTE);

                     int ampm  =date.get(Calendar.AM_PM);
                     String period = null,finalDate=null;
                     int hour = 0;

                         if(ampm==0 && hr<12){
                             if(hr==0){
                                 hour =12;
                                 period = "AM";
                             }
                             else {
                             period = "AM";
                             hour = hr;
                             }
                         }
                         else {
                                     hour = hr-12;
                                     period = "PM";

                         }
                            String caller_name=null;

                            caller_name =cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));

                            if(caller_name == null){
                             caller_name = "unKnown";
                             }

                              finalDate = day+"/"+month+"/"+year+"   "+hour+"."+minute+" "+period;
//                              Log.i(TAG, "getLogs: " +caller_name+" "+ph_Number+" "+type+" "+finalDate);
                              callLog_helper CSK = new callLog_helper(caller_name, ph_Number, type,finalDate);
                              callLogHelpers.add(CSK);

                       }

                           cursor.close();

            return callLogHelpers;
        }
        else  {
//            startActivity(new Intent(context, AvatarTesting.class));
            return null;
        }
    }
    
    
    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart: " );


        try {
            Log.i(TAG, " " +getMeLog.get(0));
            Object a = getMeLog.get(0);
            Log.i(TAG, "Fine ");
            calLog_Adapter = new CalLog_Adapter(getMeLog);
            calLog_recyle.setAdapter(calLog_Adapter);
        }
        catch (Exception e){
            Log.i(TAG, "oops "+e );
//            Toast.makeText(getContext(),"Please Give Permission to Read Call Logs",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), AvatarTesting.class));
        }


    }
    @Override
    public void onResume() {

        super.onResume();
        Log.i(TAG, "onResume: ");
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






//                             Log.i(TAG, "getLogs: "+logMap.get(ph_Number));
//                             caller_name = logMap.get(ph_Number);

    //Phone book
//    Cursor contact_cur = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null, null);
//
//        while (contact_cur.moveToNext()){
//                String name = contact_cur.getString(contact_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String number = contact_cur.getString(contact_cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                String _id = contact_cur.getString(contact_cur.getColumnIndex(ContactsContract.Contacts._ID));
//
////            if contact have more than one number -- binding
////            Log.i(TAG, "getLogs: " +number);
////            Log.i(TAG, "getLogs: " );
//
//                Log.i(TAG, " : "+_id);
//
//
//
//                logMap.put(number, name);
////            contact_cur.moveToNext();
//                }
////        Log.i(TAG, "cont: " +logMap.size());
//ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC"
//            if(contact_cur.getInt(contact_cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
//                    Log.i(TAG, "get inside  : "+name+_id);
//
//                    Cursor phk = getContext().getContentResolver().query(
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null,
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = ?",
//                    new String[] { _id },null);
//
//                    Log.i(TAG, "phk: count " +phk.getCount());
//
//                    if(phk.getCount()>1){
//                    int i=0;
//                    String[] phNum = new String[phk.getCount()];
//                    while (phk.moveToNext()){
//                    phNum[i] = phk.getString(phk.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    }
//                    Log.i(TAG, "phNum: " +phNum);
//                    i++;
//                    }
//                    }

//                 String logDate = date.get(Calendar.DAY_OF_MONTH)+" ."+date.get(Calendar.MONTH)+" ."+date.get(Calendar.HOUR)+date.get(Calendar.MINUTE)+" ."+date.get(Calendar.AM_PM);

//            sb.append(ph_Number+" "+getType+" "+date+" "+duration+" ");       //+918838744681 Outgoing 1533715402667 0
//converting UNIX time into

//                Date date1 = new java.util.Date(Long.parseLong(unix_date)*1000);
//                SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd HH:mm:ss z"); //ss-seconds   z- zone yyyy-MM-dd HH:mm:ss z
//
//                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
//                String formattedDate = sdf.format(date1);

//                Log.i(TAG, "getLogs: " +formattedDate);
//                String day =
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

//    Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//                fragment.setArguments(args);
//                return fragment;
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
//        @SuppressLint("MissingPermission") Cursor cursor = context.getContentResolver()
//                .query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");