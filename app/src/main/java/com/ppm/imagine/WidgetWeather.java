package com.ppm.imagine;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by tarda on 16/05/17.
 */

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
                convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q=Bacelona&appid=2de70b6961f101b94a6655b4856e3921",300000000));

            }catch (Exception e){

            }



    }
    public void getWeather(final String ciudad){

        try{
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
        String weathers = null;
        String ident = null;
        Double tempAux;
        String temperature;

        try {
            JSONObject objcity = new JSONObject(object);
            JSONObject main = objcity.getJSONObject("main");
            temperature = main.getString("temp");

            tempAux = Double.parseDouble(String.valueOf(temperature));
            tempAux = tempAux - 273.15;

            temperature =tempAux.toString();

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
                        setPathImagen("sol");
                        System.out.println("DEFWE "+getPathImagen());
                        break;
                    case "01n":
                        setPathImagen("luna");
                        System.out.println("DEFWE "+getPathImagen());
                        break;
                    case "02d":
                        setPathImagen("nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "02n":
                        setPathImagen("nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "03d":
                        setPathImagen("nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "03n":
                        setPathImagen("viento");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "04d":
                        setPathImagen("viento");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "04n":
                        setPathImagen("nubladob");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "11d":
                        setPathImagen("rayo");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "09d":
                        setPathImagen("lluvia");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "10d":
                        setPathImagen("arcoiris");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "13d":
                        setPathImagen("nieve");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                    case "50d":
                        setPathImagen("niebla");
                        System.out.println("DEFWE "+getPathImagen());
                        break;

                }
            //}
            ///actualizar los valores en FireBase
            /*Map<String, Object> newTz = new HashMap<String, Object>();
            newTz.put("pathImagen", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen());
            FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);

            newTz.put("temp", User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getTemp().toString());
            FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getName()).updateChildren(newTz);
*/

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
