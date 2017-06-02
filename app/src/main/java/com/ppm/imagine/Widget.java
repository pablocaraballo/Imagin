package com.ppm.imagine;

import android.content.Context;
import android.view.View;

public abstract class Widget {

    String name;
    int position;
    Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Widget(String name, Float posYinMirror, Float posXinMirror) {
        this.name = name;
        this.position = 0;
    }

    public Widget(String name) {
        this.name = name;
    }

    public Widget(){}

    public void init(Context context){
        System.out.println("initializing widget " + name);
        createView(context);
        startThread(context);
    }

    public abstract void createView(Context context);

    public void startThread(Context context){};

    public abstract View getView();
    public abstract int getIcon();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) { this.position= position; }

}
