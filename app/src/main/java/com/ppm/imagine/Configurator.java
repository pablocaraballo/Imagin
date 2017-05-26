package com.ppm.imagine;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Configurator {

    WidgetTime widgetTime;
    WidgetWeather widgetWeather;

    WidgetTwitter widgetTwitter;

    @Exclude
    public static String espejoActual;


    public WidgetTime getWidgetTime() {
        return widgetTime;
    }

    public void setWidgetTime(WidgetTime widgetTime) {
        this.widgetTime = widgetTime;
    }

    public WidgetWeather getWidgetWeather() { return widgetWeather;}

    public void setWidgetWeather(WidgetWeather widgetWeather) {this.widgetWeather = widgetWeather;}
}
