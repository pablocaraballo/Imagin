package com.ppm.imagine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.Exclude;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import org.w3c.dom.Text;

/**
 * Created by tarda on 16/05/17.
 */

public class WidgetTwitter extends Widget {

    String currentUserName=" ";
    String userName=" ";
    String hashtag=" ";
    SearchTimeline searchTimeline;
    ListView lv;

    int icon = R.drawable.twitter_icon_scaled;

    public WidgetTwitter(){}

    public void init(Context context){
        createView(context);
    }

    @Exclude
    @Override
    public View getView() {
        return lv;
    }

    @Exclude
    public int getIcon() { return icon; }

    public WidgetTwitter(String name){
        super(name);
        this.setActive(false);
        setPosition(1);
    }

    public void createView(Context context){
        lv = new ListView(context);

        if (this.getActive()) {

            System.out.println("WIDGETW ISACTIVE TRUE");

            if (getUserName().isEmpty() ) {
                searchTimeline = new SearchTimeline.Builder().query(getHashtag()).build();
                System.out.println("DENTROHASTAG "+getHashtag());
            }else if(getHashtag().isEmpty()) {
                searchTimeline = new SearchTimeline.Builder().query(getUserName()).build();
                System.out.println("DENTROUSERNAME "+getUserName());
            }else{
                searchTimeline = new SearchTimeline.Builder().query(getCurrentUserName()).build();
            }

            TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(context, searchTimeline);

            lv.setAdapter(timelineAdapter);
        }
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
