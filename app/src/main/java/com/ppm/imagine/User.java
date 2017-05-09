package com.ppm.imagine;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class User {

    @Exclude
    String uid_user= new String();
    @Exclude
    HashMap<String, Mirror> mirrors_user = new HashMap<String, Mirror>();

    public User(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    //Add a mirror to Mirror Array

    public void addMirrorToArray(Mirror mirror){

        if (mirror!=null){

            mirrors_user.put(mirror.getId_mirror(), mirror);
        }
    }
}
