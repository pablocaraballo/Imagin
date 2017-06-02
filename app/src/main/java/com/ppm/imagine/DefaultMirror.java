package com.ppm.imagine;


import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.Serializable;
import java.io.SerializablePermission;

public class DefaultMirror extends Mirror {



    public DefaultMirror(Context context){

        configurator = new Configurator();

        WidgetTwitter widgetTwitter=new WidgetTwitter("widgetTwitter");
        widgetTwitter.init(context);

        WidgetTime widgetTime= new WidgetTime("widgetTime");
        widgetTime.init(context);

        WidgetWeather widgetWeather = new WidgetWeather("widgetWeather");
        widgetWeather.init(context);

        configurator.setWidgetTime(widgetTime);
        configurator.setWidgetTwitter(widgetTwitter);
        configurator.setWidgetWeather(widgetWeather);

    }

}

