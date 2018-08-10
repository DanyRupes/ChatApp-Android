package com.example.techie_dany.letconnect.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.MainActivity;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.message_DBHelper;

import java.net.URI;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class Message_Adapter extends RecyclerView.Adapter<Message_Adapter.ViewHolder> {

    private static final String TAG = "danyy";
    ArrayList<message_DBHelper> values;
    public int itemSize;


    public Message_Adapter(){}


    public Message_Adapter (ArrayList<message_DBHelper> message_dbHelpers){

        values = message_dbHelpers;
        itemSize = values.size();
        Log.i(TAG, " "+itemSize);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, phone,message,MrollNo,message_date;
        public View messageLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            messageLayout = itemView;
            name = (TextView) messageLayout.findViewById(R.id.Mname);
            phone = (TextView) messageLayout.findViewById(R.id.Mphone);
            message = (TextView) messageLayout.findViewById(R.id.Mmessage);
            MrollNo = (TextView) messageLayout.findViewById(R.id.MrollNo);
            message_date = (TextView) messageLayout.findViewById(R.id.message_date);
//            Log.i(TAG, "Check 1");

            FrameLayout message_frame = messageLayout.findViewById(R.id.frame_message);
            message_frame.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

    //                    getting input from user
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Enter Your Message");

                    View inflater = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_message_input, (ViewGroup) (view).getRootView(), false);

                    final EditText edit_meesage = (EditText) inflater.findViewById(R.id.edit_meesage);

                    builder.setView(inflater);


                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //   going to send by positive button
                            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(view.getContext());
                            Log.i(TAG, "onClick: " + defaultSmsPackageName);


                        if (defaultSmsPackageName != null) {
                            Log.i(TAG, "onClick: check");
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setType("vnd.android-dir/mms-sms");
                            sendIntent.putExtra("address", phone.getText().toString().trim());
                            sendIntent.putExtra("sms_body", edit_meesage.getText().toString());

                            sendIntent.setPackage(defaultSmsPackageName);
                            view.getContext().startActivity(sendIntent);
                        }
                        else // For early versions,
                        {
                            Log.i(TAG, "onClick: is it...............");
                            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                            smsIntent.setType("vnd.android-dir/mms-sms");
                            smsIntent.putExtra("address", phone.getText().toString().trim());
                            smsIntent.putExtra("sms_body", edit_meesage.getText().toString());
                            view.getContext().startActivity(smsIntent);
                        }


                        dialog.dismiss();
                 }
            });


            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(view.getContext(), "Cancelled",Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
            });
            builder.show();
        }


        }
    }

    @NonNull
    @Override
    public Message_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_message, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
//        Log.i(TAG, "Check 2");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Message_Adapter.ViewHolder holder, int position) {
//        Log.i(TAG, "check 3");
        Log.i(TAG, "values" +values.get(position).getDate());
        holder.name.setText(values.get(position).getName());
        holder.phone.setText(values.get(position).getPhone());
        holder.message.setText(values.get(position).getMessage());
        holder.MrollNo.setText(""+(position+1));
        holder.message_date.setText(values.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return itemSize;
    }



}




//            Intent in = new Intent(Intent.ACTION_VIEW);
////            in.setData(Uri.parse("sms:"));
//            in.setType("vnd.android-dir/mms-sms");
//            in.putExtra("address",phone.getText().toString().trim());
//            in.putExtra("sms_body","Hello User Type your Message....");
//            view.getContext().startActivity(in);
//            Toast.makeText(view.getContext(),"Helloo "+phone.getText().toString(),Toast.LENGTH_LONG).show();
//                    edit_meesage.requestFocusFromTouch();
//                    edit_meesage.requestFocus();
//                     view.getContext().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
