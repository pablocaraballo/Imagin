package com.ppm.imagine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by tarda on 06/04/17.
 */

public class SplashActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splashscreen);

        /* New Handler to start the Menu-Activity
         * and close this SplashActivity-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        //Una vez acabe el splashScreen, enviamos hacia el menú
        this.startActivity(new Intent(this, StartMenuActivity.class));
    }
}

