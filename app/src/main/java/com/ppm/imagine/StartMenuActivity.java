package com.ppm.imagine;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StartMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        ImageButton configuratorButton = (ImageButton) findViewById(R.id.buttonImageConfiguratorMode);
        ImageButton mirrorButton = (ImageButton) findViewById(R.id.buttonImageMirrorMode);

        configuratorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mirrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // AddMirrorDialog createMirrordg = new AddMirrorDialog();
                //startActivity(new Intent(getApplicationContext(), createMirrordg.getClass()));

            }
        });



    }
}
