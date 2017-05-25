package com.ppm.imagine;

public class Widget {

    String name;
    Float posXinMirror;
    Float posYinMirror;
    Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Widget(String name, Float posYinMirror, Float posXinMirror) {

        this.name = name;
        this.posYinMirror = 0f;
        this.posXinMirror = 0f;
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

    public Float getPosXinMirror() {
        return posXinMirror;
    }

    public void setPosXinMirror(Float posXinMirror) {
        this.posXinMirror = posXinMirror;
    }

    public Float getPosYinMirror() {
        return posYinMirror;
    }

    public void setPosYinMirror(Float posYinMirror) {
        this.posYinMirror = posYinMirror;
    }
}
