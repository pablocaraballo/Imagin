package com.ppm.imagine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static android.os.Looper.getMainLooper;

public class MirrorActivity extends GoogleApiActivity {

    TextView currentHora;
    TextView hora;
    TextView city;
    TextView temp;
    GridLayout layout;
    int currentTwitterId;
    int currentMeteoId;
    int currentHoraId;
    ListView currentTwitterLayout;
    Boolean meteoExists;
    Boolean horaExists;
    Boolean twitterExists;
    LinearLayout currentMeteo;
    LinearLayout meteo;
    ImageView imageView;
    SearchTimeline searchTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        layout= (GridLayout) findViewById(R.id.gridLayoutInMirror);
        currentTwitterLayout = new ListView(this);
        currentMeteo= new LinearLayout(this);
        currentHora= new TextView(this);
        twitterExists=false;
        meteoExists=false;
        horaExists=false;

        fillLayout();
    }

    void fillLayout(){

        setGetMirrorListener();
        System.out.println("ESPEJOOOO" + User.mirrors.get(Configurator.espejoActual).toString());

        hora = new TextView(MirrorActivity.this);

        meteo = new LinearLayout(MirrorActivity.this);
        meteo.setOrientation(LinearLayout.VERTICAL);

        city =new TextView(MirrorActivity.this);
        temp = new TextView(MirrorActivity.this);
        imageView = new ImageView(MirrorActivity.this);

        defaultTimeLine();

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //WIDGET METEO

                String resourceName=null;

                if (User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen()!=null) {
                    resourceName = User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPathImagen();
                }

                if (resourceName!=null) {


                    city.setTextColor(Color.WHITE);
                    city.setTextSize(35);
                    city.setText(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getCity());

                    temp.setTextColor(Color.WHITE);
                    temp.setTextSize(35);
                    temp.setText(User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getTemp().toString()+"ºC");
                    temp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    imageView.setImageResource(getResources().getIdentifier(resourceName, "drawable", getPackageName()));
                    imageView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    if (city.getParent() != null) {
                        ((ViewGroup) city.getParent()).removeView(city);
                    }

                    if (temp.getParent() != null) {
                        ((ViewGroup) temp.getParent()).removeView(temp);
                    }

                    if (imageView.getParent() != null) {
                        ((ViewGroup) imageView.getParent()).removeView(imageView);
                    }

                    meteo.addView(imageView);
                    meteo.addView(temp);
                    meteo.addView(city);

                    int id = getResources().getIdentifier("rl"+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosYinMirror()+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetWeather().getPosXinMirror(), "id", getPackageName() );
                    if (meteoExists) ((RelativeLayout) findViewById(currentMeteoId)).removeView(currentMeteo);
                    ((RelativeLayout) findViewById(id)).addView(meteo);

                    currentMeteoId = id;
                    currentMeteo= meteo;
                    meteoExists=true;
                }

                //WIDGET TIME

                hora.setTextColor(Color.WHITE);
                hora.setTextSize(50);

                WidgetTime wt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime();

                hora.setText(WidgetTime.timeNow(wt.getHoraActual()));

                if (hora.getParent()!=null){
                    ((ViewGroup)hora.getParent()).removeView(hora);
                }

                int id = getResources().getIdentifier("rl"+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosYinMirror()+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime().getPosXinMirror(), "id", getPackageName() );
                if (horaExists){ ((RelativeLayout) findViewById(currentHoraId)).removeView(currentHora);}
                ((RelativeLayout) findViewById(id)).addView(hora);

                currentHora= hora;
                currentHoraId= id;
                horaExists=true;

                someHandler.postDelayed(this, 50);
            }
        }, 10);
    }

    public void refreshListView(){

        if (twitterExists) {

            ((RelativeLayout) findViewById(currentTwitterId)).removeView(currentTwitterLayout);
        }

        WidgetTwitter wtt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter();

        if (wtt.getActive()) {

            if (wtt.getUserName().isEmpty() ) {
                searchTimeline = new SearchTimeline.Builder().query(wtt.getHashtag()).build();
            }else if(wtt.getHashtag().isEmpty()) {
                searchTimeline = new SearchTimeline.Builder().query(wtt.getUserName()).build();
            }

            TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(MirrorActivity.this, searchTimeline);
            ListView lv = new ListView(MirrorActivity.this);
            lv.setAdapter(timelineAdapter);

            if (lv.getParent() != null) {
                ((ViewGroup) lv.getParent()).removeView(lv);
            }

            int id = getResources().getIdentifier("rl"+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror()+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror(), "id", getPackageName() );
            RelativeLayout twitter= (RelativeLayout) findViewById(id);
            twitter.getLayoutParams().height=750;
            twitter.getLayoutParams().width=800;
            twitter.addView(lv);

            currentTwitterId= id;
            currentTwitterLayout = lv;
            twitterExists=true;
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
                if (!m.getConfigurator().getWidgetTwitter().getUserName().isEmpty() || !m.getConfigurator().getWidgetTwitter().getHashtag().isEmpty()){
                    refreshListView();
                }
                else defaultTimeLine();
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

    //TESTING
    void fillLayout2(){
        for(int i=0; i<3; i++){
            for(int j=0; j<6; j++){
                TextView tv = new TextView(this);
                tv.setText(i + "," + j);
                int id = getResources().getIdentifier("rl"+i+j, "id", getPackageName() );
                ((RelativeLayout) findViewById(id)).addView(tv);
            }
        }
    }

    public void defaultTimeLine(){

        if (twitterExists) ((RelativeLayout) findViewById(currentTwitterId)).removeView(currentTwitterLayout);
        WidgetTwitter wtt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter();

        if (wtt.getActive()) {

            if(!wtt.getCurrentUserName().isEmpty() && wtt.getHashtag().isEmpty() && wtt.getUserName().isEmpty() ){

                System.out.println("TUTIMELINE!!");
                System.out.println("DENTROCURRENTUSERNAME "+wtt.getCurrentUserName());
                searchTimeline = new SearchTimeline.Builder().query(wtt.getCurrentUserName()).build();

                TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(MirrorActivity.this, searchTimeline);
                ListView lv = new ListView(MirrorActivity.this);
                lv.setAdapter(timelineAdapter);

                if (lv.getParent() != null) {
                    ((ViewGroup) lv.getParent()).removeView(lv);
                }

                int id = getResources().getIdentifier("rl"+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosYinMirror()+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getPosXinMirror(), "id", getPackageName() );
                RelativeLayout twitter= (RelativeLayout) findViewById(id);
                twitter.getLayoutParams().height=750;
                twitter.getLayoutParams().width=800;

                twitter.addView(lv);

                currentTwitterId= id;
                currentTwitterLayout = lv;
                twitterExists=true;
            }

        }
    }
}
