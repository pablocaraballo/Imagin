package com.ppm.imagine;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MirrorActivity extends GoogleApiActivity {

    //LISTADO DE WIDGETS
    String widgetTime= "Widget_Time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        System.out.println("ESPEJOOOO" + User.mirrors.get(Configurator.espejoActual).toString());

        final TextView hora= new TextView(this);
        final WidgetTime wt= (WidgetTime) User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime();

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hora.setText(WidgetTime.timeNow(wt.getHoraActual()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);

        hora.setText(wt.getHoraActual());
        hora.setX(wt.getPosXinMirror());
        hora.setY(wt.getPosYinMirror());

        RelativeLayout layout= (RelativeLayout) findViewById(R.id.activity_mirror);
        layout.addView(hora);
    }

}
