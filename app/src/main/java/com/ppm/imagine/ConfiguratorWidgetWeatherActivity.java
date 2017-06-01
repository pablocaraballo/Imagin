package com.ppm.imagine;


import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class ConfiguratorWidgetWeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurator_widget_weather);

        final EditText editText = (EditText) findViewById(R.id.city);
        final TextView city = (TextView)findViewById(R.id.ciudad);
        final TextView temp = (TextView)findViewById(R.id.temp);
        final Resources res = getResources();
        final ImageView img = (ImageView)findViewById(R.id.imgWeather);
        Button aceptar = (Button)findViewById(R.id.aceptar);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_widgetsWeather);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(res.getString(R.string.titleConfiguratorWidget));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);




        aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().setCity(editText.getText().toString());
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getWeather(editText.getText().toString());

                Map<String, Object> newTz = new HashMap<String, Object>();
                newTz.put("pathImagen", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                newTz.put("temp", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getTemp().toString());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                newTz.put("city", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getCity());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

                city.setText(editText.getText().toString());
                temp.setText(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getTemp());

                String mDrawableName = User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen() ;
                int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
                img.setImageResource(resID);

            }
        });

    }

}
