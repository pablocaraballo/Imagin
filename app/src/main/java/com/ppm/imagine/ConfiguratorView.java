package com.ppm.imagine;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;




public class ConfiguratorView extends Activity {

    ImageView twitterInGrid;
    ImageView timeInGrid;
    ImageView weatherInGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurator_view);

        GridLayout layout= (GridLayout) findViewById(R.id.gridLayout);

        twitterInGrid = new ImageView(this);
        twitterInGrid.setLongClickable(true);
        timeInGrid =  new ImageView(this);
        timeInGrid.setLongClickable(true);
        weatherInGrid = new ImageView(this);
        weatherInGrid.setLongClickable(true);

        twitterInGrid.setImageResource(R.drawable.twitter_icon_scaled);
        timeInGrid.setImageResource(R.drawable.relojwidgetlogo_scaled);
        weatherInGrid.setImageResource(R.drawable.timewidgetlogo_scaled);

        layout.addView(twitterInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror(), GridLayout.CENTER)));

        layout.addView(timeInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosYinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosXinMirror(), GridLayout.CENTER)));

        layout.addView(weatherInGrid, new GridLayout.LayoutParams(
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror(), GridLayout.CENTER),
                GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror(), GridLayout.CENTER)));

        twitterInGrid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("ME HAS TOCADO");
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(twitterInGrid);
                //

                v.startDrag(data,myShadow,null,0);

                return true;
            }
        });

        /*timeInGrid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("ME HAS TOCADO");
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(timeInGrid);
                //

                v.startDrag(data,myShadow,null,0);

                return true;
            }
        });

        weatherInGrid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("ME HAS TOCADO");
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(weatherInGrid);
                //

                v.startDrag(data,myShadow,null,0);

                return true;
            }
        });*/

        System.out.println("INICIO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());
        System.out.println("INICIO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror());

        twitterInGrid.setOnDragListener(new View.OnDragListener() {

            @Override
           public boolean onDrag(View v, DragEvent event) {

                System.out.println("INICIODENTRO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("ARRASTRAME!", "Action is DragEvent.ACTION_DRAG_STARTED");
                        System.out.println("POSINIT "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("NOSE1", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        twitterInGrid.setVisibility(View.INVISIBLE);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("LO HE HECHO", "Action is DragEvent.ACTION_DRAG_EXITED");

                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("ESTOY AQUI", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("HE ACABADO", "Action is DragEvent.ACTION_DRAG_ENDED");
                        Display display=getWindowManager().getDefaultDisplay();
                        Point size=new Point();
                        display.getSize(size);
                        int width=size.x;
                        int height =size.y;

                        twitterInGrid.setX(event.getX());
                        twitterInGrid.setY(event.getY());

                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setPosXinMirror((int) event.getX()*6/width);
                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setPosYinMirror((int) event.getY()*6/height);

                        twitterInGrid.setVisibility(View.VISIBLE);
                        System.out.println("SOLTADO1 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());
                        System.out.println("SOLTADO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror());
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("TE SUELTO", "ACTION_DROP event");


                        // Do nothing
                        break;
                    default: return false;
                }

                return true;
            }
        });

        /*timeInGrid.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {

                System.out.println("INICIODENTRO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("ARRASTRAME!", "Action is DragEvent.ACTION_DRAG_STARTED");
                        System.out.println("POSINIT "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("NOSE1", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        timeInGrid.setVisibility(View.INVISIBLE);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("LO HE HECHO", "Action is DragEvent.ACTION_DRAG_EXITED");

                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("ESTOY AQUI", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("HE ACABADO", "Action is DragEvent.ACTION_DRAG_ENDED");
                        Display display=getWindowManager().getDefaultDisplay();
                        Point size=new Point();
                        display.getSize(size);
                        int width=size.x;
                        int height =size.y;

                        timeInGrid.setX(event.getX());
                        timeInGrid.setY(event.getY());

                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().setPosXinMirror((int) event.getX()*6/width);
                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().setPosYinMirror((int) event.getY()*6/height);

                        timeInGrid.setVisibility(View.VISIBLE);
                        System.out.println("SOLTADO1 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosXinMirror());
                        System.out.println("SOLTADO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosYinMirror());
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("TE SUELTO", "ACTION_DROP event");


                        // Do nothing
                        break;
                    default: return false;
                }

                return true;
            }
        });

        weatherInGrid.setOnDragListener(new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {

                System.out.println("INICIODENTRO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d("ARRASTRAME!", "Action is DragEvent.ACTION_DRAG_STARTED");
                        System.out.println("POSINIT "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());

                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("NOSE1", "Action is DragEvent.ACTION_DRAG_ENTERED");
                        weatherInGrid.setVisibility(View.INVISIBLE);
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("LO HE HECHO", "Action is DragEvent.ACTION_DRAG_EXITED");

                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("ESTOY AQUI", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("HE ACABADO", "Action is DragEvent.ACTION_DRAG_ENDED");
                        Display display=getWindowManager().getDefaultDisplay();
                        Point size=new Point();
                        display.getSize(size);
                        int width=size.x;
                        int height =size.y;

                        weatherInGrid.setX(event.getX());
                        weatherInGrid.setY(event.getY());

                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().setPosXinMirror((int) event.getX()*6/width);
                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().setPosYinMirror((int) event.getY()*6/height);

                        weatherInGrid.setVisibility(View.VISIBLE);
                        System.out.println("SOLTADO1 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror());
                        System.out.println("SOLTADO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror());
                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("TE SUELTO", "ACTION_DROP event");


                        // Do nothing
                        break;
                    default: return false;
                }

                return true;
            }
        });*/

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

        final FloatingActionButton profile=(FloatingActionButton) findViewById(R.id.button_profile);
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Nombre de Espejo: "+ User.mirrors.get(Configurator.espejoActual).getName() , Toast.LENGTH_LONG).show();
            }
        });
}
  
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ConfiguratorView.this, StartMenuActivity.class));
    }
}
