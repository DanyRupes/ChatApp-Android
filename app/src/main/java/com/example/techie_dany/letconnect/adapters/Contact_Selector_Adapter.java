package com.example.techie_dany.letconnect.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techie_dany.letconnect.Database.SQLiteDB;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.helpers.contact_DBHelper;
import com.example.techie_dany.letconnect.helpers.contact_phonehelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Contact_Selector_Adapter extends RecyclerView.Adapter<Contact_Selector_Adapter.ViewHolder> {

    private ArrayList<String> data_list;
    private ArrayList<contact_phonehelper> adapter_Contact_phonehelper;
    private int itemCount;
    private static final String TAG = "adop";
    public static int mySelectSize;
    private ClickListener listener;
    SQLiteDB sql_from_contact;


    List<String> liSelect = new ArrayList<>();
    contact_DBHelper ConActivityhelper;


    public Contact_Selector_Adapter (ArrayList<contact_phonehelper> cHelper) {
//        Log.i("adop", ""+cHelper.length);
        itemCount = cHelper.size();
        adapter_Contact_phonehelper = cHelper;
        this.listener  = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView name, phone;
        public ImageView photo;
        public Button addContacts;
        public View con_sl_layout;

        public ViewHolder(View itemView) {
            super(itemView);
//            Log.i("adop", "innn");
            con_sl_layout = itemView;
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
//            rollNo = (TextView) itemView.findViewById(R.id.rollNo);
            addContacts = (Button) itemView.findViewById(R.id.addContacts);
            photo = (ImageView) itemView.findViewById(R.id.cs_avatar);

        }

    }


    @NonNull
    @Override
    public Contact_Selector_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_selector_contact, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Contact_Selector_Adapter.ViewHolder holder, final int position) {

        String tempName = null,tempPhone=null;
        byte[] proPic=null;
        try{

            tempName = adapter_Contact_phonehelper.get(position).getName();


            //just for temp - one phone no:
             tempPhone = adapter_Contact_phonehelper.get(position).getPhone().get(0);


             //name, roll
            holder.name.setText(tempName);
//            holder.rollNo.setText(""+(position+1));


//            phone
            int sizePhone = adapter_Contact_phonehelper.get(position).getPhone().size();
            for(int k=0;k<sizePhone;k++){
                holder.phone.setText(adapter_Contact_phonehelper.get(position).getPhone().get(k));
            }


//          pic setup
            try {
                if(adapter_Contact_phonehelper.get(position).getPhoto()==null){
                    holder.photo.setImageResource(R.drawable.avatarcc);

                    Drawable vectorDrawable  = VectorDrawableCompat.create(holder.con_sl_layout.getResources(),R.drawable.avatarcc, holder.con_sl_layout.getContext().getTheme());
                    Bitmap AvatarBitmap = ((BitmapDrawable) vectorDrawable).getBitmap();
                    proPic = getBytes(AvatarBitmap);
                }

                else{
                    proPic = getBytes(adapter_Contact_phonehelper.get(position).getPhoto());
                    holder.photo.setImageBitmap(adapter_Contact_phonehelper.get(position).getPhoto());
                }
            }
            catch (Exception e){
                Log.i(TAG, "PIC ;////////////" +e);
            }
        }
        catch(Exception e){
            Log.i(TAG, "Not Valid "+e);
        }



        final String finalTempName = tempName;
        final String finalTempPhone = tempPhone;
        final byte[] finalPropic = proPic;


        holder.addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    sql_from_contact =  new SQLiteDB(v.getContext());
                    sql_from_contact.insertContact(finalTempName,finalTempPhone,finalPropic);
                    Toast.makeText(v.getContext(),"Added"+finalTempName,Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Log.i(TAG, "onClick: "+e);
                }
            }
        });
    }

    public static byte[] getBytes(Bitmap bitmap){

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,0,bStream);
        return bStream.toByteArray();
    }
    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void add(String name, String phone, String photo){
//        data_list.add("name");

    }

}

//                Log.i(TAG, ""+ finalTempName +" "+ finalTempPhone);
//                conCat.set(0, new contact_DBHelper("", "", ""));
//
//                mySelectSize++;

//                int Maxposition = ca.getItemCount();
//                for(int y=0;y<)
//                ConActivityhelper = new contact_DBHelper(tempName, tempPhone,"myPic");