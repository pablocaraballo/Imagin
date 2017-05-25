package com.ppm.imagine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import io.fabric.sdk.android.Fabric;

public class TimeLineActivity extends AppCompatActivity {

    public  static String SEARCH_QUERY="ManelAguayo"; //the user name

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

       /* Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String hashtag = intent.getStringExtra("hashtag");

        if(username==null)
            SEARCH_QUERY=hashtag;
        else
            SEARCH_QUERY=username;*/

        ListView lv = (ListView) findViewById(R.id.timeline_list);
        //lv.setEmptyView(findViewById(R.id.loading)); d'ont forget to add loading pic
        User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setTimeLine(lv); //LINEA DE PRUEBA
       // SearchTimeline searchTimeline = new SearchTimeline.Builder().query(SEARCH_QUERY).build();

       // final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(this, searchTimeline);

        //lv.setAdapter(timelineAdapter);*/
    }
}
