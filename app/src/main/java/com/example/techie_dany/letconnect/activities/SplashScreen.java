package com.example.techie_dany.letconnect.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.techie_dany.letconnect.MainActivity;
import com.example.techie_dany.letconnect.R;


public class SplashScreen extends AppCompatActivity {


    private static final String TAG = "splash";
    public ImageView id_letsconnect;
    public static final String SHARE_PREF_NAME = "Attempt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();


        id_letsconnect = (ImageView) findViewById(R.id.id_letsconnect);



        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void run() {
                Log.i(TAG, "isFirst : " +pref.getInt("isFirst", 0));
                if(pref.getInt("isFirst",0)==0){
                    Intent greetMe = new Intent(SplashScreen.this, GreetingScreen.class);
                    startActivity(greetMe);
                    editor.putInt("isFirst", 2);
                    editor.apply();
                    finish();
                }
                else {
                    Log.i(TAG, "isFirst..h: " +pref.getInt("isFirst", 1));
                    Intent home = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(home);
                    finish();
                }
            }
        }, 3000);



    }

}
