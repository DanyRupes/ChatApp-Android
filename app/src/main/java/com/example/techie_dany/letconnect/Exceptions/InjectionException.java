package com.example.techie_dany.letconnect.Exceptions;

import android.util.Log;
import android.widget.Toast;

public class InjectionException extends Exception {

    public final static String TAG = "ie";
    public InjectionException(){
        Log.i(TAG, "Value Already There Exception...  ");

    }
}
