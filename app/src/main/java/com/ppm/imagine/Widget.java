package com.ppm.imagine;

import android.content.Context;
import android.view.View;

public abstract class Widget {

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

    public void init(Context context){
        createView(context);
        startThread(context);
    }

    public void createView(Context context){

    }

    public void startThread(Context context){

    }

    public abstract View getView();

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
