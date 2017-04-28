package com.ppm.imagine;

import java.util.HashMap;

public class Configurator {

    String id_configurator;
    HashMap<String, Widget> widgetsConfigurator;

    public Configurator(String id_configurator) {
        this.id_configurator = id_configurator;
    }

    public String getId_configurator() {
        return id_configurator;
    }

    public void setId_configurator(String id_configurator) {
        this.id_configurator = id_configurator;
    }

    public void addWidgetToConfigurator(Widget widget){

        if (widget!= null){

            widgetsConfigurator.put(widget.id, widget);
        }
    }
}
