package com.ppm.imagine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import static com.ppm.imagine.R.id.time;

/**
 * Created by tarda on 06/04/17.
 */

public class SplashActivity extends Activity {

    // Duration of wait in seconds
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /// Called when the activity is first created.
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this,FirebaseConnection.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();

    }
}

