package com.ppm.imagine;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;




public class ConfiguratorView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurator_view);

        GridLayout layout= (GridLayout) findViewById(R.id.gridLayout);

        ImageView twitterInGrid = new ImageView(this);
        ImageView timeInGrid =  new ImageView(this);
        ImageView weatherInGrid = new ImageView(this);

        twitterInGrid.setImageResource(R.drawable.twitter_icon_scaled);
        timeInGrid.setImageResource(R.drawable.relojwidgetlogo_scaled);
        weatherInGrid.setImageResource(R.drawable.timewidgetlogo_scaled);

        layout.addView(twitterInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror(), GridLayout.CENTER)));

        layout.addView(timeInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosXinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosYinMirror(), GridLayout.CENTER)));

        layout.addView(weatherInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror(), GridLayout.CENTER)));

        layout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {


                System.out.println("APREEETADOOOOO!!! ");

                return false;
            }
        });

        //DRAG AND DROP // Cambiará la posición del imageView que hará que se modifique en la base de datos y por lo tanto en el espejo.

        twitterInGrid.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });


        //Button Add Widget
        final FloatingActionButton newWidget = (FloatingActionButton) findViewById(R.id.button_widget);
        newWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),WidgetSelectConfiguratorList.class));

            }
        });

        //Button Exit to StartMenuActivity
        final FloatingActionButton exit=(FloatingActionButton) findViewById(R.id.button_exit);
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StartMenuActivity.class));
            }
        });
}
  
    @Override
    public void onBackPressed() {


        startActivity(new Intent(ConfiguratorView.this, StartMenuActivity.class));

    }
}
