package com.ppm.imagine;

import android.util.Log;

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

    String horaActualLocale;
    public static String zonas[]= TimeZone.getAvailableIDs();

    public WidgetTime(){


    }

    public WidgetTime(String name) {
        super(name);

        setHoraActual(new String("Australia/South"));

        for (int i=0; i<zonas.length; i++){

            System.out.println("TIEMPO DE ZONAS "+ zonas[i].toString());
        }
    } 

    public String getHoraActual() {
        return horaActualLocale;
    }

    public void setHoraActual(String horaActual) {
        this.horaActualLocale = horaActual;
    }

    public static String timeNow(String timezone){

        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar c = Calendar.getInstance(tz);
        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
                String.format("%02d" , c.get(Calendar.MINUTE));

        return time;

    }

    /***
     *
     * Versión antigua del código que se mantiene para informar
     * sobre el funcionamiento de las peticiones GET que retornan JSON.
     *
     ***
    //final String API_KEY= "711065WCUGQR";
    //String resultadoJSON;
    //ArrayList<Time> lista_tiempos;

    public WidgetTime(String name) throws JSONException {
        super(name);

        lista_tiempos = new ArrayList<Time>();
        resultadoJSON= getJSONTimezones("http://api.timezonedb.com/v2/list-time-zone?key="+API_KEY+"&format=json", 3000);
        convertToJSON(resultadoJSON);
    }

    public String getJSONTimezones(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public void convertToJSON(String object){

        try {

            JSONObject obj= new JSONObject(object);
            JSONArray array = obj.getJSONArray("zones");

            for (int i=0; i<array.length();i++){

                Time t1= new Time();
                obj= array.getJSONObject(i);
                t1.setCountryCode(obj.getString("countryCode"));
                t1.setCountryName(obj.getString("countryName"));
                //t1.setHourTime(obj.getString("timestamp"));
                t1.setHourTime(t1.timestampToDate(Long.parseLong(obj.getString("timestamp"))));
                //System.out.println("TIMEEE " + t1.getHourTime());
                addTimetoArray(t1);
                //Log.d("ParsingJSONTime", t1.toString());

            }

        } catch (Throwable t) {
            Log.e("ErrorParsingJSON", "Could not parse malformed JSON: \"" + object + "\"");
        }
    }

    public void addTimetoArray(Time temps){

        if (temps!=null) lista_tiempos.add(temps);

    }*/

}
