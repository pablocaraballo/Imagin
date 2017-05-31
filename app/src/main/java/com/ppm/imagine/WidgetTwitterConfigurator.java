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

        deleteFilters();

        findViewById(R.id.accept_user).setOnClickListener(this);

        findViewById(R.id.accept_hashtag).setOnClickListener(this);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (username.getText().toString().trim().length() != 0) {
                    hashtag.setEnabled(false);
                    findViewById(R.id.accept_hashtag).setEnabled(false);
                } else if (hashtag.getText().toString().trim().length() != 0) {
                    findViewById(R.id.accept_user).setEnabled(false);
                    username.setEnabled(false);
                }
                else if(username.getText().toString().trim().length() == 0 || hashtag.getText().toString().trim().length() == 0){
                    hashtag.setEnabled(true);
                    findViewById(R.id.accept_hashtag).setEnabled(true);
                    findViewById(R.id.accept_user).setEnabled(true);
                    username.setEnabled(true);
                }
                someHandler.postDelayed(this, 50);
            }
        }, 10);
    }

    //Write in memory and Firebase DB  which filter selected
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept_user:
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setUserName(username.getText().toString());
                newTw.put("userName", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getUserName());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);
                break;

            case R.id.accept_hashtag:
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setHashtag(hashtag.getText().toString());
                newTw.put("hashtag", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getHashtag());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);
                break;
        }
    }

    //Delete all filters in FirebaseDB
    public void deleteFilters(){
        newTw.put("userName","");
        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);

        newTw.put("hashtag", "");
        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);
    }
}


