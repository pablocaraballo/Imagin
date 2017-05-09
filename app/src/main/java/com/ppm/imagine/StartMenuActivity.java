package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class StartMenuActivity extends GoogleApiActivity {

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

        ImageButton b1= (ImageButton) findViewById(R.id.close_session);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showAlertDialog(StartMenuActivity.this);

            }
        });

        mirrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MirrorCreateFragment createMirrordg = new MirrorCreateFragment();
                createMirrordg.show(getSupportFragmentManager(), "MIRROR CREATOR");

            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog alertDialog= new AlertDialog.Builder(getApplicationContext()).create();
        alertDialog.setTitle(getResources().getString(R.string.cerrar_app_title));

        alertDialog.setMessage(getResources().getString(R.string.cerrar_app));

        alertDialog.setButton(getResources().getString(R.string.cerrar_app_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        System.exit(0);
                    }
                });
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    public void showAlertDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.Close));

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.Close_Session_Message));

        // Setting OK Button
        alertDialog.setButton(getResources().getString(R.string.Close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

}

