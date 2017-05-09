package com.ppm.imagine;

import java.util.HashMap;

public class Configurator {

    HashMap<String, Widget> widgetsConfigurator;


    public HashMap<String, Widget> getWidgetsConfigurator() {
        return widgetsConfigurator;
    }

    public void setWidgetsConfigurator(HashMap<String, Widget> widgetsConfigurator) {
        this.widgetsConfigurator = widgetsConfigurator;
    }

    public Configurator(){

        widgetsConfigurator= new HashMap<String, Widget>();

    }

    public void addWidgetToConfigurator(Widget widget){

        if (widget!= null){

            widgetsConfigurator.put(widget.getId(), widget);
        }
    }
}
