package com.ppm.imagine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfiguratorWidgetWeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurator_widget_weather);

        final EditText editText = (EditText) findViewById(R.id.city);
        ImageButton imageButton = (ImageButton)findViewById(R.id.cityButton);




        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Configurator.currentMirror.getConfigurator().getWidgetWeather().setCity(editText.getText().toString());
                Configurator.currentMirror.getConfigurator().getWidgetWeather().getWeather(editText.getText().toString());


                 Map<String, Object> newTz = new HashMap<String, Object>();
                newTz.put("pathImagen", Configurator.currentMirror.getConfigurator().getWidgetWeather().getPathImagen());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +Configurator.currentMirror.id +"/configurator/"+ Configurator.currentMirror.getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                newTz.put("temp", Configurator.currentMirror.getConfigurator().getWidgetWeather().getTemp().toString());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +Configurator.currentMirror.id +"/configurator/"+ Configurator.currentMirror.getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                newTz.put("city", Configurator.currentMirror.getConfigurator().getWidgetWeather().getCity());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +Configurator.currentMirror.id +"/configurator/"+ Configurator.currentMirror.getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                Intent ListSelector = new Intent(getApplicationContext(), WidgetSelectConfiguratorList.class);
                startActivity(ListSelector);
            }
        });

    }

}
