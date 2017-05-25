package com.ppm.imagine;

import android.widget.ListView;

/**
 * Created by tarda on 16/05/17.
 */

public class WidgetTwitter extends Widget {

    String userName=" ";
    String hashtag=" ";



    public WidgetTwitter(){}
    public WidgetTwitter(String name){
        super(name);
        this.posYinMirror = 0f;
        this.posXinMirror = 0f;
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
