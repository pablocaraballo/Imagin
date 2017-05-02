package com.ppm.imagine;

public class Mirror {

    String id_mirror;
    Configurator configurator_mirror;

    public Mirror(String id_mirror) {
        this.id_mirror = id_mirror;
    }

    public Mirror(Configurator configurator_mirror) {
        this.configurator_mirror = configurator_mirror;
    }

    public Mirror(String id_mirror, Configurator configurator_mirror) {
        this.id_mirror = id_mirror;
        this.configurator_mirror = configurator_mirror;
    }

    public String getId_mirror() {
        return id_mirror;
    }

    public void setId_mirror(String id_mirror) {
        this.id_mirror = id_mirror;
    }

    public Configurator getConfigurator_mirror() {
        return configurator_mirror;
    }

    public void setConfigurator_mirror(Configurator configurator_mirror) {
        this.configurator_mirror = configurator_mirror;
    }
}
