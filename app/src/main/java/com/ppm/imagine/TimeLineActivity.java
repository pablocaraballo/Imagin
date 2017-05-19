package com.ppm.imagine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class TimeLineActivity extends AppCompatActivity {


    private static final String SEARCH_QUERY = "ManelAguayo"; //the user name

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ListView lv = (ListView) findViewById(R.id.timeline_list);
        //lv.setEmptyView(findViewById(R.id.loading)); d'ont forget to add loading pic

        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(SEARCH_QUERY).build();

        final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(this, searchTimeline);

        lv.setAdapter(timelineAdapter);
    }
}
