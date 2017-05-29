package com.ppm.imagine;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.Serializable;
import java.io.SerializablePermission;

public class DefaultMirror extends Mirror implements Serializable {


    public DefaultMirror(){

        configurator = new Configurator();

        WidgetTwitter widgetTwitter=new WidgetTwitter("widgetTwitter");
        WidgetTime widgetTime= new WidgetTime("widgetTime");
        WidgetWeather widgetWeather = new WidgetWeather("widgetWeather");
        configurator.setWidgetTime(widgetTime);
        configurator.setWidgetTwitter(widgetTwitter);
        configurator.setWidgetWeather(widgetWeather);


    }

}

