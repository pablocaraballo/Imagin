package com.ppm.imagine;

public class Mirror {

    String id_mirror;
    String name_mirror;
    Configurator configurator_mirror;

    public Mirror(String id_mirror, int option) {

        //Dos opciones en el constructor para ver cuando metemos el identificador, o cuando el nombre.
        if (option==1)this.id_mirror = id_mirror;
        else if (option==2) this.name_mirror=id_mirror;
    }

    public Mirror(Configurator configurator_mirror) {
        this.configurator_mirror = configurator_mirror;
    }

    public Mirror(String id_mirror, Configurator configurator_mirror) {
        this.id_mirror = id_mirror;
        this.configurator_mirror = configurator_mirror;
    }

    public String getName_mirror() {
        return name_mirror;
    }

    public void setName_mirror(String name_mirror) {
        this.name_mirror = name_mirror;
    }

    public Mirror(String id_mirror, String name_mirror) {
        this.id_mirror = id_mirror;
        this.name_mirror = name_mirror;
    }

    public Mirror(String name_mirror) {
        this.name_mirror = name_mirror;
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
