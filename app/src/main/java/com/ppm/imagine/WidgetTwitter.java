package com.ppm.imagine;

import android.widget.ListView;

/**
 * Created by tarda on 16/05/17.
 */

public class WidgetTwitter extends Widget {

    String currentUserName=" ";
    String userName=" ";
    String hashtag=" ";


    public WidgetTwitter(){}
    public WidgetTwitter(String name){
        super(name);
        this.setActive(false);
        setPosXinMirror(0);
        setPosYinMirror(1);
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
