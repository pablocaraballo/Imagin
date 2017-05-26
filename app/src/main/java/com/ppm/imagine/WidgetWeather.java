package com.ppm.imagine;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarda on 16/05/17.
 */

public class WidgetWeather extends Widget{

    public String pathImagen;
    public String city;
    public Double temp;

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
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
        this.posYinMirror = 0f;
        this.posXinMirror = 1500f;
        //defWeather();
    }

    public WidgetWeather(String name) {
	super(name);

        this.posYinMirror = 0f;
        this.posXinMirror = 1500f;
        defWeather();
    }



    public WidgetWeather(String city,String name) {
	super(name);
        this.posYinMirror = 0f;
        this.posXinMirror = 1500f;
        setCity(city);
        getWeather(city);
    }

    public void defWeather(){
        try{
            convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q=Hawaii&appid=2de70b6961f101b94a6655b4856e3921",300000));

        }catch(Exception e){

        }

    }
    public void getWeather(final String ciudad){
        try{
            convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q="+ciudad+"&appid=2de70b6961f101b94a6655b4856e3921",300000));

        }catch(Exception e){

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
        String weathers = null;
        String ident = null;
        Double temperature;

        try {
            JSONObject objcity = new JSONObject(object);
            JSONObject main = objcity.getJSONObject("main");
            temperature = main.getDouble("temp");

            temperature = temperature - 273.15;
            setTemp(temperature);
            System.out.println("DEFWE "+getTemp());



            JSONObject obj = new JSONObject(object);
            JSONArray array = obj.getJSONArray("weather");

            for (int i = 0; i < array.length(); i++) {

                obj = array.getJSONObject(i);
                weathers = obj.getString("description");
                ident = obj.getString("icon");
                System.out.println("DEFWE id: "+ident);

            }

            /*if (city.equals("Mordor") || city.equals("mordor")) {
                pathImagen = "R.drawable.sauron";  Falla algo del no properties serializer found on class

            } else {*/
                switch (ident) {
                    case "01d":
                        setPathImagen("R.drawable.sol");
                        System.out.println("DEFWE "+getPathImagen());
                        break;
                    case "01n":
                        setPathImagen("R.drawable.luna");
                        System.out.println("DEFWE "+getPathImagen());
                        break;
                    case "02d":
                        setPathImagen("R.drawable.nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "02n":
                        setPathImagen("R.drawable.nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "03d":
                        setPathImagen("R.drawable.nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "03n":
                        setPathImagen("R.drawable.viento");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "04d":
                        setPathImagen("R.drawable.viento");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "04n":
                        setPathImagen("R.drawable.nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "11d":
                        setPathImagen("R.drawable.rayo");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "09d":
                        setPathImagen("R.drawable.lluvia");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "10d":
                        setPathImagen("R.drawable.arcoiris");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "13d":
                        setPathImagen("R.drawable.nieve");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "50d":
                        setPathImagen("R.drawable.niebla");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                }
            //}

        } catch (Throwable t) {
            Log.e("ErrorParsingJSON", "Could not parse malformed JSON: \"" + object + "\"");
        }
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, Void> {
        public String object;

        @Override
        protected Void doInBackground(String... urls) {
            setObject(getJSON(urls[0],300000));
            System.out.println("Object: "+getObject());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("Object2: "+getObject());
            convertToJSON(getObject());
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }
    }


}
