package com.ppm.imagine;

import com.google.firebase.database.Exclude;

public class Mirror {

    public String name;
    public Configurator configurator;

    public Mirror(){


    }

    public Mirror(Configurator configurator) {
        this.configurator = configurator;
    }

    public Mirror(String name) {
        this.name = name;
    }

    @Exclude
    public String getName() {
        return name;
    }

    @Exclude
    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Configurator getConfigurator() {
        return configurator;
    }

    @Exclude
    public void setConfigurator(Configurator configurator) {
        this.configurator = configurator;
    }
}
