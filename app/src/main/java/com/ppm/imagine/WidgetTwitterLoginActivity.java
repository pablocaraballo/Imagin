package com.ppm.imagine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import io.fabric.sdk.android.Fabric;


public class WidgetTwitterLoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "jKVRlJTTTGNO9svtBaBx021uA";
    private static final String TWITTER_SECRET = "AYGwObrgO2iWKlT4pjzInDCVRoYwciokLmMpouAlx8AjjtbmyR";
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getApplicationContext(), new Twitter(authConfig));

        setContentView(R.layout.activity_widget_twitter_login);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                //Twitter.getInstance().core.getSessionManager().getActiveSession();
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model

                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();


                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setUserName(session.getUserName()); //Escrito en memoria, falta en Firebase

                System.out.println("USERNAME "+User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getUserName());
                //ESTO DEBERA IR EN LA ACTIVITY MIRROR
                /*Intent timeLine = new Intent(getApplicationContext(), TimeLineActivity.class);
                startActivity(timeLine);*/

                Intent twitterFilters=new Intent(getApplicationContext(), WidgetTwitterConfigurator.class);
                startActivity(twitterFilters);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
