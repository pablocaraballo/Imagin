package com.ppm.imagine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    RelativeLayout layout;
    ListView currentListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);

        layout= (RelativeLayout) findViewById(R.id.activity_mirror);
        setGetMirrorListener();
        System.out.println("ESPEJOOOO" + User.mirrors.get(Configurator.espejoActual).toString());

        hora = new TextView(MirrorActivity.this);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hora.setTextColor(Color.WHITE);
                hora.setTextSize(100);
                //System.out.println("hooooooooooooooooora" + hora.getText());

                WidgetTime wt= User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTime();

                hora.setText(WidgetTime.timeNow(wt.getHoraActual()));

                hora.setX(wt.getPosXinMirror());
                hora.setY(wt.getPosYinMirror());

                if (hora.getParent()!=null){
                    ((ViewGroup)hora.getParent()).removeView(hora);
                }
                layout.addView(hora);

                someHandler.postDelayed(this, 50);
            }
        }, 10);

    }

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

            final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(MirrorActivity.this, searchTimeline);
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
