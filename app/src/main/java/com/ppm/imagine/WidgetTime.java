package com.ppm.imagine;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WidgetTime extends Widget {

    String horaActual;
    public static String zonas[]= TimeZone.getAvailableIDs();
    TextView hora;

    public WidgetTime(){


    }

    public WidgetTime(String name) {
        super(name);

        this.posYinMirror = 0;
        this.posXinMirror = 0;

        setHoraActual(new String("Europe/Madrid"));

        for (int i=0; i<zonas.length; i++){

            System.out.println("TIEMPO DE ZONAS "+ zonas[i].toString());
        }
    }

    public String getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(String horaActual) {
        this.horaActual = horaActual;
    }

    public static String timeNow(String timezone){

        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));

        return time;

    }


    public void startThread(Context context){

        final Handler someHandler = new Handler(context.getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hora.setText(WidgetTime.timeNow(getHoraActual()));

                someHandler.postDelayed(this, 50);
            }
        }, 10);
    }

    public void createView(Context context){
        hora = new TextView(context);

        hora.setTextColor(Color.WHITE);
        hora.setTextSize(20);

        hora.setText(WidgetTime.timeNow(getHoraActual()));

    }

    public View getView() {
        return hora;

    }
}
