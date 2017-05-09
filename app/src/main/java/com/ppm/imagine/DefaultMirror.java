package com.ppm.imagine;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.Serializable;
import java.io.SerializablePermission;

public class DefaultMirror implements Serializable {

    public User user;
    public Mirror mirror;
    public Configurator config;
    public WidgetTime wTime;
    public String mirrorName;

    public DefaultMirror(){

        user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mirror = new Mirror(mirrorName , 2);
        config = new Configurator();
        wTime= new WidgetTime("Widget_Time");
        wTime.setId(String.valueOf((int)Math.random()));
        config.addWidgetToConfigurator(wTime);
        user.addMirrorToArray(mirror);

    }

    public void setmirrorName(String mirrorName){

        this.mirrorName = mirrorName;

    }
}

