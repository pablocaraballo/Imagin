package com.ppm.imagine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MirrorActivity extends GoogleApiActivity {

    TextView hora;
    TextView city;
    TextView temp;
    GridLayout layout;
    ListView currentListview;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        //layout= (RelativeLayout) findViewById(R.id.activity_mirror);
        layout= (GridLayout) findViewById(R.id.gridLayoutInMirror);
        setGetMirrorListener();
        System.out.println("ESPEJOOOO" + User.mirrors.get(Configurator.espejoActual).toString());

        hora = new TextView(MirrorActivity.this);
        city =new TextView(MirrorActivity.this);
        temp = new TextView(MirrorActivity.this);
        imageView = new ImageView(MirrorActivity.this);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String resourceName = User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen();

                hora.setTextColor(Color.WHITE);
                hora.setTextSize(50);

                city.setTextColor(Color.GREEN);
                city.setTextSize(100);
                city.setText(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getCity());
                city.setX(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror()-1000);
                city.setY(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror()+100);

                temp.setTextColor(Color.GREEN);
                temp.setTextSize(50);
                temp.setText(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getTemp().toString());
                temp.setX(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror()-20);
                temp.setY(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror()+100);

                imageView.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
                imageView.setX(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror());
                imageView.setY(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror());


                WidgetTime wt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime();

                hora.setText(WidgetTime.timeNow(wt.getHoraActual()));

               // hora.setX(wt.getPosXinMirror());
                // hora.setY(wt.getPosYinMirror());

                if (hora.getParent()!=null){
                    ((ViewGroup)hora.getParent()).removeView(hora);
                }
                layout.addView(hora, new GridLayout.LayoutParams(
                        GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror(), GridLayout.CENTER),
                        GridLayout.spec(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror(), GridLayout.CENTER)));

                if (city.getParent()!=null){
                    ((ViewGroup)city.getParent()).removeView(city);
                }
                layout.addView(city);

                if (temp.getParent()!=null){
                    ((ViewGroup)temp.getParent()).removeView(temp);
                }
                layout.addView(temp);

                if (imageView.getParent()!=null){
                    ((ViewGroup)imageView.getParent()).removeView(imageView);
                }
                layout.addView(imageView);

                someHandler.postDelayed(this, 50);
            }
        }, 10);

    }

    //FALTA QUE EL TWITTER Y EL TIEMPO TAMBIÉN SE UBIQUEN EN EL GRIDLAYOUT TAL Y COMO LO HAGO AQUI ARRIBA CON 'HORA'.
    //HABRÁ QUE PREGUNTAR A GERARD PORQUE PARECE NO CAMBIAR LA POSICIÓN CUANDO SE MODIFICA.

    public void refreshListView(){

        layout.removeView(currentListview);

        System.out.println("WIDGETW DENTRO REFRESH");
        WidgetTwitter wtt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter();

        if (wtt.getActive()) {

            System.out.println("WIDGETW ISACTIVE TRUE");

            //CONTROLAR QUE PASA SI LOS DOS CAMPOS ESTAN RELLENO (ELSE IF)
            SearchTimeline searchTimeline;
            if (wtt.getUserName() == "") {
                searchTimeline = new SearchTimeline.Builder().query(wtt.getHashtag()).build();
            } else {
                searchTimeline = new SearchTimeline.Builder().query(wtt.getUserName()).build();
            }

            TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(MirrorActivity.this, searchTimeline);
            ListView lv = new ListView(MirrorActivity.this);
            lv.setAdapter(timelineAdapter);

            if (lv.getParent() != null) {
                ((ViewGroup) lv.getParent()).removeView(lv);
            }

            currentListview=lv;
            layout.addView(lv);
        }
    }

    public void setGetMirrorListener(){

        FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Mirror m = dataSnapshot.getValue(Mirror.class);

               /* if (User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getHashtag() != m.getConfigurator().getWidgetTwitter().getHashtag() ||
                        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getUserName() != m.getConfigurator().getWidgetTwitter().getUserName()){*/

                    System.out.println("WIDGETW  CHILDCHANGED");
                    refreshListView();

                //}
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
