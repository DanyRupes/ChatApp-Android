package com.example.techie_dany.letconnect.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Database.SQLiteDB;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.contact_DBHelper;

import java.util.ArrayList;
import java.util.List;

public class contact_Adapter extends RecyclerView.Adapter<contact_Adapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "cad";
    private ArrayList<contact_DBHelper> values;
    private int myPosition;
    public int itemSize;

    contact_Adapter(){}
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contact_name, contact_phone;
        public View myLayout;
        public ImageView cc_avatar;
        FrameLayout contact_frameLayout;
        public ImageButton contact_rem_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            myLayout = itemView;

            contact_name = (TextView) myLayout.findViewById(R.id.c_name);
            contact_phone = (TextView) myLayout.findViewById(R.id.c_phone_no);
            cc_avatar = (ImageView) myLayout.findViewById(R.id.cc_avatar);
            contact_rem_btn = (ImageButton) myLayout.findViewById(R.id.contact_rem_btn);

            contact_frameLayout = (FrameLayout) myLayout.findViewById(R.id.frame_contact);
            contact_frameLayout.setOnClickListener(this);
//            contact_rem_btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            action call
            switch (v.getId()){
                case R.id.frame_contact :
                    String uriCall = "tel:" + contact_phone.getText().toString().trim();
                    Intent in = new Intent(Intent.ACTION_CALL);
                    in.setData(Uri.parse(uriCall));
                    if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Log.i(TAG, "Okayyy Callll: ");
                        Toast.makeText(myLayout.getContext(), "Permission Not Granted " + contact_name.getText().toString(), Toast.LENGTH_LONG).show();
                        return;
                    }
                        v.getContext().startActivity(in);
                break;
                case R.id.contact_rem_btn :

                break;
            }
        }
    }

//    public void add(int position, contact_DBHelper cname) {
//        this.myPosition = position;
//        values.add(position, cname);
//    }

    public  void remove(int position) {
        values.remove(position);
    }

    public contact_Adapter(ArrayList<contact_DBHelper> myContactList) {
        values = myContactList;
        itemSize = myContactList.size();

//        Log.i(TAG, "size " + itemSize);
    }

    @NonNull
    @Override
    public contact_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.layout_contact, parent, false);


        ViewHolder C_holder = new ViewHolder(v);
        return C_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final contact_Adapter.ViewHolder holder, final int position) {

        final contact_DBHelper name = values.get(position);


        holder.contact_name.setText("" + values.get(position).getName());
        holder.contact_phone.setText("" + values.get(position).getPhone());
//        Set profile pic from db
        try{
            Log.i(TAG, ""+values.get(position).getName());
            Log.i(TAG, ""+values.get(position).getPhoto().length);
            if(values.get(position).getPhoto().length==0){
                Log.i(TAG, "Zero");
                holder.cc_avatar.setImageResource(R.drawable.avatarcc);
            }
            else{
                Log.i(TAG, "else");
                holder.cc_avatar.setImageBitmap(giveMeFreedom(values.get(position).getPhoto()));
            }
        }
        catch (Exception e){
            Log.i(TAG, "onBindViewHolder: "+e);
        }

        holder.contact_rem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDB sql = new SQLiteDB(v.getContext());
                long id = sql.removeThisContact(holder.contact_phone.getText().toString());
//                    values = sql.getDBContacts();
                if(id != 0){
//                        Log.i(TAG, "id"+myLayout.getId());
//                        myLayout.setVisibility(View.GONE);
//                        Log.i(TAG, "onClick: "+itemView);
//                    contact_Adapter ca = new contact_Adapter();
////                        ca.remove(v.getId());
////                        values.remove(0);
//                        itemView.notifyAll();
//                        itemView.notify();
//                        ca.notifyItemRemoved(position);
//                        holder.contact_frameLayout.removeView();
//                     ca.remove(position);
//                    Log.i(TAG, "position: "+position);
                    contact_Adapter.this.notifyItemRemoved(position);
                    contact_Adapter.this.values = sql.getDBContacts();
                    contact_Adapter.this.notifyItemRangeChanged(0, itemSize--);
//                    Log.i(TAG, "itemSize: "+itemSize);
//                    itemSize--;
                    Toast.makeText(v.getContext(), "Removed ", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Log.i(TAG, " is : "+position);
                    Toast.makeText(v.getContext(), "Currently Not!!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


//    converting db byte[] to bitmap
    public static Bitmap giveMeFreedom(byte[] image){

        return BitmapFactory.decodeByteArray(image,0,image.length);
    }

    @Override
    public int getItemCount() {
        return itemSize;
    }


//    for Full recyler onlclick
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.frame_contact) {
            Toast.makeText(v.getContext(), "Hello Frame", Toast.LENGTH_LONG).show();
        }

    }
}






//    public List<contact_DBHelper> getList(){
//        return values;
//    }
//    public int getMaxPosition(){
//        return values.size();
//    }

//}
