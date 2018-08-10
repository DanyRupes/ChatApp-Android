package com.example.techie_dany.letconnect.adapters;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Database.SQLiteDB;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.contact_DBHelper;
import com.example.techie_dany.letconnect.helpers.message_phonehelper;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;




public class message_selector_adapter extends RecyclerView.Adapter<message_selector_adapter.ViewHolder> {

    private ArrayList<message_phonehelper> message_phone_helper ;
    private int message_size;
    public static final String TAG ="mca";
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteDB sqLiteDB;




    public message_selector_adapter(){ }
    public message_selector_adapter(ArrayList<message_phonehelper> msgHelper){
        message_phone_helper = msgHelper;
        message_size = msgHelper.size();


    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view_layout;
        TextView name,phone,ms_date,rollNo;
        TextView message;
        Button addMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            view_layout = itemView;


            name = (TextView) view_layout.findViewById(R.id.name);
            phone = (TextView) view_layout.findViewById(R.id.phone);
            message = (TextView) view_layout.findViewById(R.id.message);
            ms_date = (TextView) view_layout.findViewById(R.id.ms_date);
            addMessage = (Button) view_layout.findViewById(R.id.addMessage);
            rollNo = (TextView) view_layout.findViewById(R.id.rollNo);
        }
    }


    @NonNull
    @Override
    public message_selector_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.layout_selector_message, parent,false);


        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull message_selector_adapter.ViewHolder holder, int position) {
        Log.i(TAG, "hellllooooo");
        Log.i(TAG, ""+message_phone_helper.get(position).getName());

        holder.name.setText(message_phone_helper.get(position).getName());
        holder.phone.setText(message_phone_helper.get(position).getPhone());
        holder.message.setText(message_phone_helper.get(position).getMessage());
        holder.ms_date.setText(message_phone_helper.get(position).getDate());
        holder.rollNo.setText(""+(position+1));
        final String name = message_phone_helper.get(position).getName();
        final String phone = message_phone_helper.get(position).getPhone();
        final String message = message_phone_helper.get(position).getMessage();
        final String date = message_phone_helper.get(position).getDate();

        holder.addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDB = new SQLiteDB(v.getContext());

                try {
//                    Log.i(TAG, "inserting msges: " +name+phone+message);
                    sqLiteDB = new SQLiteDB(v.getContext());
                    long id = sqLiteDB.insertMessage(name,phone,message,date);
                    Log.i(TAG, "id: " +id);
//                    if(id==-1){
//                        Toast.makeText(v.getContext()," "+name+" Chat Already There.." ,Toast.LENGTH_LONG).show();
//                    }
//                    else{
                    Toast.makeText(v.getContext(),"Added "  +name,Toast.LENGTH_LONG).show();
//                    }
                }

                catch (Exception e){
                    Log.i(TAG, "Error DB_mes" +e);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return message_size;
    }
}
