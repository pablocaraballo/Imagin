package com.ppm.imagine;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WidgetWeather extends Widget{

    public String pathImagen;
    public String city;
    public String temp;

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public WidgetWeather() {
        super();
        this.posYinMirror = 0;
        this.posXinMirror = 3;
        setCity(city);
        //defWeather(); este constuctor no se si lo utilizo en algun lado
    }

    public WidgetWeather(String name) {
        super(name);
        this.posYinMirror = 0;
        this.posXinMirror = 3;
        setCity(city);
        defWeather();
    }



    public WidgetWeather(String city,String name) {
        super(name);
        this.posYinMirror = 0;
        this.posXinMirror = 3;
        setCity(city);
        getWeather(city);
    }

    public void defWeather(){
        try{
            setCity("Barcelona");
            convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q=Bacelona&appid=2de70b6961f101b94a6655b4856e3921",300000000));
        }catch (Exception e){

        }



    }
    public void getWeather(final String ciudad){

        try{
            setCity(ciudad);
            convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q="+ciudad+"&appid=2de70b6961f101b94a6655b4856e3921",300000000));
        }catch (Exception e){

        }



    }

    public String getJSON(String url, int timeout) {
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
                        sb.append(line);
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

    private void convertToJSON(String object) {
        String ident = null;
        Double tempAux;
        String temperature;

        try {
            JSONObject objcity = new JSONObject(object);
            JSONObject main = objcity.getJSONObject("main");
            temperature = main.getString("temp");

            tempAux = Double.parseDouble(String.valueOf(temperature));
            tempAux = tempAux - 273.15;
            tempAux = Math.rint(tempAux*100)/100;


            int tempFinal= tempAux.intValue();
            temperature =String.valueOf(tempFinal);

            setTemp(temperature);
            Double temSauron = Math.rint((tempAux+300)*100)/100;
            int sauronFinal= temSauron.intValue();


            JSONObject obj = new JSONObject(object);
            JSONArray array = obj.getJSONArray("weather");

            for (int i = 0; i < array.length(); i++) {

                obj = array.getJSONObject(i);
                ident = obj.getString("icon");
                System.out.println("DEFWE id: "+ident);

            }

            if (getCity().equals("Mordor") || getCity().equals("mordor")) {
                setPathImagen("sauron");
                setTemp(String.valueOf(sauronFinal));
                //si falla es por esto
            } else {
                switch (ident) {
                    case "01d":
                        setPathImagen("sol");
                        break;
                    case "01n":
                        setPathImagen("luna");

                        break;
                    case "02d":
                        setPathImagen("nubladob");
                        break;

                    case "02n":
                        setPathImagen("nubladob");
                        break;

                    case "03d":
                        setPathImagen("nubladob");
                        break;

                    case "03n":
                        setPathImagen("viento");
                        break;

                    case "04d":
                        setPathImagen("viento");
                        break;

                    case "04n":
                        setPathImagen("nubladob");
                        break;

                    case "11d":
                        setPathImagen("rayo");
                        break;

                    case "09d":
                        setPathImagen("lluvia");
                        break;

                    case "10d":
                        setPathImagen("arcoiris");
                        break;

                    case "13d":
                        setPathImagen("nieve");
                        break;

                    case "50d":
                        setPathImagen("niebla");
                        break;
                }
            }
        } catch (Throwable t) {
            Log.e("ErrorParsingJSON", "Could not parse malformed JSON: \"" + object + "\"");
        }
    }
}