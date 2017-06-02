package com.ppm.imagine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StartMenuActivity extends GoogleApiActivity {
    ChildEventListener onAddMirrorListener;

    ArrayList<Mirror> userMirrors = new ArrayList<>();
    ArrayAdapter<Mirror> mirrorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        setOnAddMirrorListener();

        ImageButton configuratorButton = (ImageButton) findViewById(R.id.buttonImageConfiguratorMode);
        ImageButton mirrorButton = (ImageButton) findViewById(R.id.buttonImageMirrorMode);

        ImageButton closeSessionButton= (ImageButton) findViewById(R.id.close_session);
        closeSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSessionDialog();
            }
        });

        mirrorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    selectMirrorDialog(MirrorActivity.class);
            }
        });

        //LISTENER OF THE CONFIGURATOR BUTTON
        configuratorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMirrorDialog(ConfiguratorActivity.class);
            }
        });

    }

    public void closeSessionDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.Close))
                .setMessage(getResources().getString(R.string.Close_Session_Message))
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

        alertDialog.show();
    }

    public void selectMirrorDialog(final Class nextActivityClass) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.configure_mirror_msg))
                    .setPositiveButton(getResources().getString(R.string.manyMirrors_accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MirrorCreateFragment createMirrordg = new MirrorCreateFragment();
                            createMirrordg.setNextActivityClass(nextActivityClass);

                            createMirrordg.show(getSupportFragmentManager(), "MIRROR CREATOR");
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.configure_mirror_denegar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            ListView modeList = new ListView(this);

            class MirrorsAdapter extends ArrayAdapter<Mirror> {
                int resource;
                int textViewResourceId;

                public MirrorsAdapter(Context context, int resource, int textViewResourceId, ArrayList<Mirror> mirrors) {
                    super(context, resource, textViewResourceId, mirrors);
                    this.resource = resource;
                    this.textViewResourceId = textViewResourceId;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    Mirror mirror = getItem(position);

                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                    }

                    ((TextView) convertView.findViewById(textViewResourceId)).setText(mirror.name);

                    return convertView;
                }
            }

            mirrorsAdapter = new MirrorsAdapter(this, R.layout.mirror_list_selector_to_configurate, R.id.itemMirrorsList, userMirrors);
            modeList.setAdapter(mirrorsAdapter);

            modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Configurator.currentMirror = ((Mirror) parent.getItemAtPosition(position));
                    startActivity(new Intent(getApplicationContext(), nextActivityClass));
                }
            });

            builder.setView(modeList);

            builder.create().show();

    }

    public void setOnAddMirrorListener(){

        onAddMirrorListener = FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mirror mirror = dataSnapshot.getValue(Mirror.class);
                userMirrors.add(mirror);
                if(mirrorsAdapter != null)
                    mirrorsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).removeEventListener(onAddMirrorListener);
        moveTaskToBack(true);
    }
}