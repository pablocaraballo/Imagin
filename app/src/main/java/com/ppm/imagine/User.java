package com.ppm.imagine;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public class User{

    public static HashMap<String, Mirror> mirrors = new HashMap<String, Mirror>();

    public User (){


    }

    //Add a mirror to Mirror Array

    public static void addMirrorToArray(Mirror mirror){

        if (mirror!=null){

            mirrors.put((mirror.getName()), mirror);
        }

    }

    public static void updateConfigToMirror(Configurator newconfig){

        mirrors.get(Configurator.espejoActual).setConfigurator(newconfig);
    }

    public static void QueryMirrorsUser(){

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("/users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

    }

}
