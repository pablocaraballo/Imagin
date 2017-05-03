package com.ppm.imagine;

import java.util.HashMap;

public class Configurator {

    String id_configurator;
    String name_configurator;

    public Configurator(String id_configurator, String name_configurator) {
        this.id_configurator = id_configurator;
        this.name_configurator = name_configurator;
    }

    public String getName_configurator() {
        return name_configurator;
    }

    public void setName_configurator(String name_configurator) {
        this.name_configurator = name_configurator;
    }

    public HashMap<String, Widget> getWidgetsConfigurator() {
        return widgetsConfigurator;
    }

    public void setWidgetsConfigurator(HashMap<String, Widget> widgetsConfigurator) {
        this.widgetsConfigurator = widgetsConfigurator;
    }

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
