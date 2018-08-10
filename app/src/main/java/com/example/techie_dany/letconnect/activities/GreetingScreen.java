package com.example.techie_dany.letconnect.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.techie_dany.letconnect.BuildConfig;
import com.example.techie_dany.letconnect.R;
import com.example.techie_dany.letconnect.adapters.Greeting_Adapter;


public class GreetingScreen extends AppCompatActivity {


    public ViewPager viewPager;
    private Greeting_Adapter greeting_adapter;
    private int layouts[] = {R.layout.layout_greeting_slide0,R.layout.layout_greeting_slide1,R.layout.layout_greeting_slide2,R.layout.layout_greeting_slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        setContentView(R.layout.activity_greeting_screen);

        viewPager = (ViewPager) findViewById(R.id.greeting_viewpager);

        greeting_adapter = new Greeting_Adapter(layouts, this);

        viewPager.setAdapter(greeting_adapter);
    }

}
