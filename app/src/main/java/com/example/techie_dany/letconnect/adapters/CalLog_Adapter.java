package com.example.techie_dany.letconnect.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.callLog_helper;

import java.util.ArrayList;

public class CalLog_Adapter extends RecyclerView.Adapter<CalLog_Adapter.ViewHolder> {

    public final static  String TAG ="cap";
    private int itemSize;
    public FrameLayout frame_callog;

    ArrayList<callLog_helper> callLog_helper;

    public CalLog_Adapter(ArrayList<callLog_helper> logs) {
        callLog_helper = logs;
        itemSize = callLog_helper.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cl_name, cl_phone, cl_type, cl_time;
        public View callLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            callLayout = itemView;
            cl_name = (TextView) callLayout.findViewById(R.id.cl_name);
            cl_phone = (TextView) callLayout.findViewById(R.id.cl_phone);
            cl_type = (TextView) callLayout.findViewById(R.id.cl_type);
            cl_time = (TextView) callLayout.findViewById(R.id.cl_time);
            frame_callog = (FrameLayout) callLayout.findViewById(R.id.frame_callog);
            frame_callog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uriCall = "tel:" + cl_phone.getText().toString().trim();
                    Intent in = new Intent(Intent.ACTION_CALL);
                    in.setData(Uri.parse(uriCall));
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Log.i(TAG, "Okayyy Callll: ");
                        Toast.makeText(cl_phone.getContext(), "Permission Not Granted " + cl_phone.getText().toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    v.getContext().startActivity(in);
                }
            });

        }


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_call_log, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalLog_Adapter.ViewHolder holder, int position) {

        holder.cl_name.setText(callLog_helper.get(position).getName());
        holder.cl_time.setText(callLog_helper.get(position).getDate());
        holder.cl_phone.setText(callLog_helper.get(position).getPhone());
        holder.cl_type.setText(callLog_helper.get(position).getType());
//        holder.cl_time.setText();
//        holder.
//        Log.i(TAG, "onBindViewHolder: " +callLog_helper.get(position).getName());
//        Log.i(TAG, "onBindViewHolder: " +callLog_helper.get(position).getDate());
//        Log.i(TAG, "onBindViewHolder: " +callLog_helper.get(position).getPhone());
//        Log.i(TAG, "onBindViewHolder: " +callLog_helper.get(position).getType());
    }


    @Override
    public int getItemCount() {
        return itemSize;
    }


}
