package com.ppm.imagine;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    android.widget.GridLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurator_view);

        GridLayout layout= (GridLayout) findViewById(R.id.gridLayout);


        twitterInGrid = new ImageView(this);
        twitterInGrid.setClickable(true);
        twitterInGrid.setLongClickable(true);

        timeInGrid =  new ImageView(this);
        weatherInGrid = new ImageView(this);
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


        twitterInGrid.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("ME HAS TOCADO");
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(twitterInGrid);


                v.startDrag(dragData,myShadow,null,0);

                return true;
            }
        });

        System.out.println("INICIO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());
        System.out.println("INICIO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror());
        twitterInGrid.setOnDragListener(new View.OnDragListener() {
            @Override
           public boolean onDrag(View v, DragEvent event) {

                int x_cord=0;
                int y_cord=0;

                System.out.println("INICIO "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());
                //String clipData = event.getClipDescription().getLabel().toString();

                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        //layoutParams = (GridLayout.LayoutParams)v.getLayoutParams();
                        Log.d("ARRASTRAME!", "Action is DragEvent.ACTION_DRAG_STARTED");


                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("NOSE1", "Action is DragEvent.ACTION_DRAG_ENTERED");
                         x_cord = (int) event.getX();
                         y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_EXITED :
                        Log.d("LO HE HECHO", "Action is DragEvent.ACTION_DRAG_EXITED");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        layoutParams.leftMargin = x_cord;
                        layoutParams.topMargin = y_cord;
                        v.setLayoutParams(layoutParams);
                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :
                        Log.d("ESTOY AQUI", "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x_cord = (int) event.getX();
                        y_cord = (int) event.getY();
                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        Log.d("HE ACABADO", "Action is DragEvent.ACTION_DRAG_ENDED");

                        break;

                    case DragEvent.ACTION_DROP:
                        Log.d("TE SUELTO", "ACTION_DROP event");

                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setPosXinMirror(x_cord);
                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setPosYinMirror(y_cord);
                        System.out.println("SOLTADO1 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror());
                        System.out.println("SOLTADO2 "+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror());
                        // Do nothing
                        break;
                    default: return false;
                }
                return true;
            }
        });

        //https://www.tutorialspoint.com/android/android_drag_and_drop.htm

        twitterInGrid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(twitterInGrid);

                    System.out.println("ME HAS TOCADO LEVEMENTE");
                    twitterInGrid.startDrag(data, shadowBuilder, twitterInGrid, 0);
                    twitterInGrid.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
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
