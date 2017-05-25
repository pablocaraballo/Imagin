package com.ppm.imagine;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Configurator {

    WidgetTime widgetTime;
    //WidgetWeather widgetWeather;
    WidgetTwitter widgetTwitter;

    @Exclude
    public static String espejoActual;

    public WidgetTwitter getWidgetTwitter() {
        return widgetTwitter;
    }

    public void setWidgetTwitter(WidgetTwitter widgetTwitter) {
        this.widgetTwitter = widgetTwitter;
    }

    public WidgetTime getWidgetTime() {
        return widgetTime;
    }

    public void setWidgetTime(WidgetTime widgetTime) {
        this.widgetTime = widgetTime;
    }
}
