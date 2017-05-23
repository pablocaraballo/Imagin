package com.ppm.imagine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ConfiguratorWidgetWeatherActivity extends AppCompatActivity {
    public static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurator_widget_weather);

        final EditText editText = (EditText) findViewById(R.id.city);
        ImageButton imageButton = (ImageButton)findViewById(R.id.cityButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                city=editText.getText().toString();

            }
        });

    }

    public void defWeather(){
        String object = getJSON("http://api.openweathermap.org/data/2.5/weather?q=Barcelona&appid=2de70b6961f101b94a6655b4856e3921",3000);
        convertToJSON(object);
    }
    public void getWeather(){

        String object = getJSON("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=2de70b6961f101b94a6655b4856e3921",3000);
        convertToJSON(object);
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

    public static void convertToJSON(String object) {
        Double temp;
        String weather = null;
        String id = null;
        String pathImagen = null;
        WidgetWeather ww = new WidgetWeather();

        try {
            JSONObject objcity = new JSONObject(object);
            JSONObject main = objcity.getJSONObject("main");
            temp = main.getDouble("temp");

            temp = temp - 273.15;

            JSONObject obj = new JSONObject(object);
            JSONArray array = obj.getJSONArray("weather");

            for (int i = 0; i < array.length(); i++) {

                obj = array.getJSONObject(i);
                weather = obj.getString("description");
                System.out.println("WEATHER : " + weather);
                id = obj.getString("icon");
                System.out.println("WEATHER : " + id);
            }

            if (city.equals("Mordor") || city.equals("mordor")) {
                pathImagen = "R.drawable.sauron";
                ww.upToFirebase(pathImagen,city,temp);
            } else {
                switch (id) {
                    case "01d":
                        //clear sky
                        pathImagen = "R.drawable.sol";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;
                    case "01n":
                        //clear sky
                        pathImagen = "R.drawable.luna";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;
                    case "02d":
                        //few clouds
                        pathImagen = "R.drawable.nubladob";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "02n":
                        //few clouds
                        pathImagen = "R.drawable.nubladob";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "03d":
                        //scattered clouds
                        pathImagen = "R.drawable.nubladob";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "03n":
                        //scattered clouds
                        pathImagen = "R.drawable.viento";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "04d":
                        //broken clouds
                        //overcast clouds
                        pathImagen = "R.drawable.viento";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "04n":
                        //broken clouds
                        //overcast clouds
                        pathImagen = "R.drawable.nubladob";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "11d":
                        //thunderstorm with light rain
                        //thunderstorm with light rain
                        //thunderstorm with rain
                        //thunderstorm with heavy rain
                        //light thunderstorm
                        //thunderstorm
                        //heavy thunderstorm
                        //ragged thunderstorm
                        //thunderstorm with light drizzle
                        //thunderstorm with drizzle
                        //thunderstorm with heavy drizzle
                        pathImagen = "R.drawable.rayo";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "09d":
                        //light intensity drizzle
                        //drizzle
                        //heavy intensity drizzle
                        //light intensity drizzle rain
                        //drizzle rain
                        //heavy intensity drizzle rain
                        //shower rain and drizzle
                        //heavy shower rain and drizzle
                        //shower drizzle
                        // 	light intensity shower rain
                        // 	shower rain
                        // 	heavy intensity shower rain
                        // 	ragged shower rai
                        pathImagen = "R.drawable.lluvia";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "10d":
                        //light rain 	10d
                        //501 	moderate rain 	10d
                        //502 	heavy intensity rain 	10d
                        //503 	very heavy rain 	10d
                        //504 	extreme rain 	10d
                        pathImagen = "R.drawable.arcoiris";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "13d":
                        //light snow
                        // 	snow
                        // 	heavy snow
                        // 	sleet
                        // 	shower sleet
                        // 	light rain and snow
                        // 	rain and snow
                        // 	light shower snow
                        // 	shower snow
                        // 	heavy shower snow
                        // 	freezing rain
                        pathImagen = "R.drawable.nieve";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                    case "50d":
                        //mist
                        //  	smoke
                        // 	haze
                        // 	sand, dust whirls
                        // 	fog
                        // 	sand
                        // 	dust
                        // 	volcanic ash
                        // 	squalls
                        // 	tornado
                        pathImagen = "R.drawable.niebla";
                        ww.upToFirebase(pathImagen,city,temp);
                        break;

                }
            }


        } catch (Throwable t) {
            Log.e("ErrorParsingJSON", "Could not parse malformed JSON: \"" + object + "\"");
        }
    }

    public void mostrarInfo(String pathImage,String city, Double temp){
        TextView cityView = (TextView)findViewById(R.id.cityView);
        TextView tempView = (TextView)findViewById(R.id.temp);
        ImageView icon = (ImageView)findViewById(R.id.iconWeather);

        //icon.setImageDrawable(pathImage); tiene que ser el path

    }


}
