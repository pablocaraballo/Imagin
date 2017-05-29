package com.ppm.imagine;

public class Widget {

    String name;
    int posXinMirror;
    int posYinMirror;
    Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Widget(String name, Float posYinMirror, Float posXinMirror) {

        this.name = name;
        this.posYinMirror = 0;
        this.posXinMirror = 0;
    }

    public Widget(String name) {

        this.name = name;
    }

    public Widget(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosXinMirror() {
        return posXinMirror;
    }

    public void setPosXinMirror(int posXinMirror) {
        this.posXinMirror = posXinMirror;
    }

    public int getPosYinMirror() {
        return posYinMirror;
    }

    public void setPosYinMirror(int posYinMirror) {
        this.posYinMirror = posYinMirror;
    }
}
