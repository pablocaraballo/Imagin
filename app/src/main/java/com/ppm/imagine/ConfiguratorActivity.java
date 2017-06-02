package com.ppm.imagine;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConfiguratorActivity extends Activity implements ConfiguratorActivityAdapter.OnDragStartListener {

    ConfiguratorActivityAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurator);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.configurator_view);
        int numColumns = 3;
        int numRows = 5;

        recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));

        adapter = new ConfiguratorActivityAdapter(this, new Widget[numColumns*numRows], numColumns, numRows, size);
        recyclerView.setAdapter(adapter);

        itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        updateWidgets();
    }

    public void updateWidgets(){
        if(adapter != null) {

            Arrays.fill(adapter.getDataSet(), null);

            Configurator c = Configurator.currentMirror.getConfigurator();

            adapter.getDataSet()[c.getWidgetTime().getPosition()] = c.getWidgetTime();
            adapter.getDataSet()[c.getWidgetWeather().getPosition()] = c.getWidgetWeather();
            adapter.getDataSet()[c.getWidgetTwitter().getPosition()] = c.getWidgetTwitter();

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfiguratorActivity.this, StartMenuActivity.class));
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
