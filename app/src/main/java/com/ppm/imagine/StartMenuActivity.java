package com.ppm.imagine;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class StartMenuActivity extends GoogleApiActivity {
    private GoogleApiClient mGoogleApiClient;

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

        Button b1= new Button(this);
        b1.setText("Cerrar sesión");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
            }
        });

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_start_menu);
        layout.addView(b1);


        mirrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // AddMirrorDialog createMirrordg = new AddMirrorDialog();
                //startActivity(new Intent(getApplicationContext(), createMirrordg.getClass()));

            }
        });



    }
}
