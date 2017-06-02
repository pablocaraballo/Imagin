package com.ppm.imagine;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.graphics.Point;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MirrorActivity extends GoogleApiActivity {

    MirrorActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        setMirrorChangeListener();

        Display display = getWindowManager().getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        int numColumns = 3;
        int numRows = 5;

        recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));

        adapter = new MirrorActivityAdapter(this, new Widget[numColumns*numRows], numColumns, numRows, size);

        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void updateWidgets(){
        if(adapter != null) {

            Configurator.currentMirror.init(getApplicationContext());

            Arrays.fill(adapter.getDataSet(), null);

            Configurator c = Configurator.currentMirror.getConfigurator();

            adapter.getDataSet()[c.getWidgetTime().getPosition()] = c.getWidgetTime();
            adapter.getDataSet()[c.getWidgetWeather().getPosition()] = c.getWidgetWeather();
            adapter.getDataSet()[c.getWidgetTwitter().getPosition()] = c.getWidgetTwitter();

            adapter.notifyDataSetChanged();
        }
    }

    public void setMirrorChangeListener(){
        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + Configurator.currentMirror.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Configurator.currentMirror = dataSnapshot.getValue(Mirror.class);
                updateWidgets();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}