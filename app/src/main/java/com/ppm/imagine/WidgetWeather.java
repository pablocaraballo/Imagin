package com.ppm.imagine;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.Exclude;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    Boolean meteoExists;
    Boolean horaExists;
    Boolean twitterExists;
    LinearLayout currentMeteo;
    LinearLayout meteo;

    @Exclude
    int icon = R.drawable.timewidgetlogo_scaled;


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
        this.position = 2;
        setCity(city);
        //defWeather(); este constuctor no se si lo utilizo en algun lado
    }

    public void init(Context context){
        createView(context);
    }

    public void createView(Context context){

        meteo = new LinearLayout(context);
        meteo.setOrientation(LinearLayout.VERTICAL);

        TextView city =new TextView(context);
        TextView temp = new TextView(context);
        ImageView imageView = new ImageView(context);

        String resourceName = getPathImagen();

        if (resourceName != null) {
            city.setTextColor(Color.WHITE);
            city.setTextSize(35);
            city.setText(getCity());

            temp.setTextColor(Color.WHITE);
            temp.setTextSize(35);
            temp.setText(getTemp().toString() + "ºC");
            temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            imageView.setImageResource(context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName()));
            imageView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            if (city.getParent() != null) {
                ((ViewGroup) city.getParent()).removeView(city);
            }

            if (temp.getParent() != null) {
                ((ViewGroup) temp.getParent()).removeView(temp);
            }

            if (imageView.getParent() != null) {
                ((ViewGroup) imageView.getParent()).removeView(imageView);
            }

            meteo.addView(imageView);
            meteo.addView(temp);
            meteo.addView(city);
        }
    }

    @Exclude
    @Override
    public View getView() { return meteo; }

    @Exclude
    public int getIcon() { return icon; }

    public WidgetWeather(String name) {
	    super(name);
        this.position = 2;
        setCity(city);
        defWeather();
    }



    public WidgetWeather(String city,String name) {
    	super(name);
        this.position = 2;
        setCity(city);
        getWeather(city);
    }

    public void defWeather(){
            try{
                convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q=Bacelona&appid=2de70b6961f101b94a6655b4856e3921",300000000));
                setCity("Barcelona");
            }catch (Exception e){

            }
    }

    public void getWeather(final String ciudad){

        try{
            convertToJSON(getJSON("http://api.openweathermap.org/data/2.5/weather?q="+ciudad+"&appid=2de70b6961f101b94a6655b4856e3921",300000000));
            setCity(ciudad);
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

            temperature =tempAux.toString();

            setTemp(temperature);
            Double temSauron = Math.rint((tempAux+300)*100)/100;



            JSONObject obj = new JSONObject(object);
            JSONArray array = obj.getJSONArray("weather");

            for (int i = 0; i < array.length(); i++) {

                obj = array.getJSONObject(i);
                ident = obj.getString("icon");
                System.out.println("DEFWE id: "+ident);

            }

           /*if (getCity().equals("Mordor") || getCity().equals("mordor")) {
                setPathImagen("sauron");
                setTemp(temSauron.toString());
                                                            //si falla es por esto

            } else {*/
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
            //}
        } catch (Throwable t) {
            Log.e("ErrorParsingJSON", "Could not parse malformed JSON: \"" + object + "\"");
        }
    }
}
