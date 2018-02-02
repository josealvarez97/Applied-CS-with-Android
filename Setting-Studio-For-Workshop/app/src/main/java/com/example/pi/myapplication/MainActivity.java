package com.example.pi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ONCREATE", "A simple log message");
        super.onCreate(savedInstanceState);
        Log.d("ONCREATE", "instantiated successfully the activity. 1");
        setContentView(R.layout.activity_main);

        // For using the log function we need to import the following (https://web.archive.org/web/20150703150608/http://www.codelearn.org/android-tutorial/android-log)
        Log.d("ONCREATE", "instantiated successfully the activity. 2");
        // There are other types of logs, such as info, debug, and error logs (i.e., Log.e means error log)
    }
}
