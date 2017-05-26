package com.ppm.imagine;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Configuration;


import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;


public class WidgetTwitterConfigurator extends AppCompatActivity
implements View.OnClickListener{

    EditText username;
    EditText hashtag;
    Map<String, Object> newTw = new HashMap<String, Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_twitter_configurator);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar_twitterconfig);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        username = (EditText) findViewById(R.id.user_text);
        hashtag =(EditText) findViewById(R.id.hashtag_text);

        // A FIREBASE FALTA COGER LA QUERY DE LA DB Y
        // UNA VEZ HECHO ESTO MIRAR COMO PASARLE EL LISTVIEW PARA MOSTRARLO EN EL MIRROR  (QUE SE LLAME DEL MIRROR)


        findViewById(R.id.accept_user).setOnClickListener(this);

        findViewById(R.id.accept_hashtag).setOnClickListener(this);

        /*runOnUiThread(new Runnable() {
            public void run() {

                    if (username.getText().toString().trim().length() != 0) {
                        acceptHashtag.setEnabled(false);
                    } else if (hashtag.getText().toString().trim().length() != 0) {
                        acceptUser.setEnabled(false);
                    }

            }
        });*/

        /*new Thread(new Runnable(){
            public void run() {

                while(true)
                {
                    try {
                        Thread.sleep(100);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //REST OF CODE HERE//
                }

            }
        }).start();*/
    }

    //Write in memory and Firebase DB  which filter selected
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept_user:
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setUserName(username.getText().toString());
                newTw.put("userName", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getUserName());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);

                System.out.print("FIREBASEREAD "+FirebaseDatabase.getInstance().getReference("/users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+ User.mirrors.get(Configurator.espejoActual).id+"/configurator/"+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw));

                /*Intent intent=new Intent(WidgetTwitterConfigurator.this, TimeLineActivity.class);
                intent.putExtra("username", username.getText().toString());
                startActivity(intent);*/
                break;

            case R.id.accept_hashtag:
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setHashtag(hashtag.getText().toString());
                newTw.put("hashtag", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getHashtag());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);

                /* Intent intent2=new Intent(WidgetTwitterConfigurator.this, TimeLineActivity.class);
                intent2.putExtra("hashtag", hashtag.getText().toString());
                startActivity(intent2);*/
                break;
        }
    }
}


