package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.Iterator;

public class StartMenuActivity extends GoogleApiActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        setGetMirrorListener();

        ImageButton configuratorButton = (ImageButton) findViewById(R.id.buttonImageConfiguratorMode);
        ImageButton mirrorButton = (ImageButton) findViewById(R.id.buttonImageMirrorMode);

        ImageButton closeSessionButton= (ImageButton) findViewById(R.id.close_session);
        closeSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showAlertDialog(StartMenuActivity.this, 1, false);

            }
        });

        mirrorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (User.mirrors.size() == 0) {

                    System.out.println("CHECKUSERMIRROR USER SIN ESPEJOS" + User.mirrors.size());

                    MirrorCreateFragment createMirrordg = new MirrorCreateFragment();
                    createMirrordg.show(getSupportFragmentManager(), "MIRROR CREATOR");

                } else if (User.mirrors.size() >= 1){


                    System.out.println("CHECKUSERMIRROR USER CON MÁS DE UN ESPEJO" +User.mirrors.size());
                    showAlertDialog(StartMenuActivity.this, 2, true);

                }

            }
        });


        //LISTENER OF THE CONFIGURATOR BUTTON
        configuratorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog(StartMenuActivity.this, 3, false);

            }
        });

    }

    public void showAlertDialog(final Context context, int opcion, final boolean creandoEspejo) {

        switch (opcion){
            // In this case, app show a CloseSesionDialog.
            case 1:
            {

                AlertDialog alertDialog = new AlertDialog.Builder(context)

                        // Setting Dialog Title
                        .setTitle(getResources().getString(R.string.Close))
                        // Setting Dialog Message
                        .setMessage(getResources().getString(R.string.Close_Session_Message))
                        //Setting OnclickActionButtons
                        .setPositiveButton(getResources().getString(R.string.Close), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        FirebaseAuth.getInstance().signOut();
                                        finish();
                                    }
                                });

                            }
                        }).setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        }).create();
                // Showing Alert Message
                alertDialog.show();
                break;
            }
            //Verify if user want to continue with MirrorCreation.
            case 2:
            {
                AlertDialog alertDialog = new AlertDialog.Builder(context)

                        .setTitle(getResources().getString(R.string.manyMirrors))
                        .setMessage(getResources().getString(R.string.manyMirrors_message))
                        .setPositiveButton(getResources().getString(R.string.manyMirrors_accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                MirrorCreateFragment createMirrordg = new MirrorCreateFragment();
                                createMirrordg.show(getSupportFragmentManager(), "MIRROR CREATOR");
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.selectMirror), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //La opcion 'true' informa de que se está creando un espejo, por lo que redigiremos a MirrorActivity.
                                showAlertDialog(StartMenuActivity.this, 3, true);
                            }
                        }).create();

                // Showing Alert Message
                alertDialog.show();
                break;
            }
            case 3:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.configure_mirror_msg))
                        .setNegativeButton(getResources().getString(R.string.configure_mirror_denegar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                ListView modeList = new ListView(this);
                Iterator<Mirror> mirrors;
                String[] mirrorsList= new String[User.mirrors.size()];

                int pos = 0;

                mirrors = User.mirrors.values().iterator();


                while (mirrors.hasNext()){

                    mirrorsList[pos] = mirrors.next().getName();
                    System.out.println("ESPEJITOS " +mirrorsList[pos].toString());
                    pos++;

                }


                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, R.layout.mirror_list_selector_to_configurate, R.id.itemMirrorsList, mirrorsList);
                modeList.setAdapter(modeAdapter);
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        System.out.println("MIRROOOOOR "+ parent.getItemAtPosition(position).toString());
                        Configurator.espejoActual=parent.getItemAtPosition(position).toString();

                        if (creandoEspejo) startActivity(new Intent(getApplicationContext(), MirrorActivity.class));
                        else if (!creandoEspejo) startActivity(new Intent(getApplicationContext(), ConfiguratorView.class));
                    }
                });

                builder.setView(modeList);
                final Dialog dialog = builder.create();

                dialog.show();
        }


    }

    public void setGetMirrorListener(){

        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("WIIIIIDGET"+dataSnapshot.toString());
                dataSnapshot.getKey();
                Mirror m = dataSnapshot.getValue(Mirror.class);
                User.addMirrorToArray(m);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                System.out.println("CAAAAAAAAAAAAAAAAMBIOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" + dataSnapshot.getValue());
                Mirror m = dataSnapshot.getValue(Mirror.class);
                User.updateConfigToMirror(m.getConfigurator());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        moveTaskToBack(true);

    }
}

