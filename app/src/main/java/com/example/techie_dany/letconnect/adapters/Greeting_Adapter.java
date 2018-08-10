package com.example.techie_dany.letconnect.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.techie_dany.letconnect.MainActivity;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.activities.GreetingScreen;

import static java.security.AccessController.getContext;

public class Greeting_Adapter extends PagerAdapter {


    private int[] layouts;
    private LayoutInflater layoutInflater;
    private Context context;
    public final static String TAG= "adapt";

    public Button btn_getStart;


    public Greeting_Adapter (int[] layouts, Context context){

        this.context = context;
        this.layouts = layouts;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return layouts.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem  (ViewGroup container, int position){

        final View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        if(position == 3){
            btn_getStart = (Button) view.findViewById(R.id.btn_getStart);
            btn_getStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "wow: ");
                    view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));

                }
            });
        }

        Log.i(TAG, "instantiateItem: " +position);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){

        View view = (View)object;
        container.removeView(view);
    }
}
