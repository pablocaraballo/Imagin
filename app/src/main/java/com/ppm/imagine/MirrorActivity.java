package com.ppm.imagine;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
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

public class MirrorActivity extends GoogleApiActivity {

    //LISTADO DE WIDGETS
    String widgetTime= "Widget_Time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        System.out.println("ESPEJOOOO" + User.mirrors.get(Configurator.espejoActual).toString());

        final TextView hora= new TextView(this);
        final WidgetTime wt= (WidgetTime) User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime();
        final WidgetTwitter wtt=(WidgetTwitter) User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter();

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hora.setText(WidgetTime.timeNow(wt.getHoraActual()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);

        //ListView TimeLine Twitter
        SearchTimeline searchTimeline;
        if(wtt.getUserName()==""){
            searchTimeline = new SearchTimeline.Builder().query(wtt.getHashtag()).build();
        }
        else{
            searchTimeline = new SearchTimeline.Builder().query(wtt.getUserName()).build();
        }

        final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(this, searchTimeline);

        hora.setText(wt.getHoraActual());
        wtt.getTimeLine().setAdapter(timelineAdapter);

        RelativeLayout layout= (RelativeLayout) findViewById(R.id.activity_mirror);

        if(hora.getParent()!=null){
            ((ViewGroup)hora.getParent()).removeView(hora);
        }

        if(wtt.getTimeLine().getParent()!=null){
            ((ViewGroup)wtt.getTimeLine().getParent()).removeView(wtt.getTimeLine());
        }

        layout.addView(wtt.getTimeLine());
        layout.addView(hora);
    }
}
