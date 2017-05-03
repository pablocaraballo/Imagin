package com.ppm.imagine;

public class Widget {

    String id;
    String name;
    Float posXinMirror;
    Float posYinMirror;

    public Widget(String id, String name, Float posYinMirror, Float posXinMirror) {
        this.id = id;
        this.name = name;
        this.posYinMirror = posYinMirror;
        this.posXinMirror = posXinMirror;
    }

    public Widget(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
