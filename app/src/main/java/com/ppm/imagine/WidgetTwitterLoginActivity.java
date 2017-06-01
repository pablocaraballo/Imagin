package com.ppm.imagine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;


public class WidgetTwitterLoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "jKVRlJTTTGNO9svtBaBx021uA";
    public static final String TWITTER_SECRET = "AYGwObrgO2iWKlT4pjzInDCVRoYwciokLmMpouAlx8AjjtbmyR";
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

                String msg = "Has iniciado sesion con: @" + session.getUserName();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setCurrentUserName(session.getUserName());

                //Active Widget Twitter
                User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().setActive(true);
                Map<String, Object> newTw = new HashMap<String, Object>();
                newTw.put("active", true);
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);
                newTw.put("currentUserName", session.getUserName());
                FirebaseDatabase.getInstance().getReference("/users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" +User.mirrors.get(Configurator.espejoActual).id +"/configurator/"+ User.mirrors.get(Configurator.espejoActual).getConfigurator().getWidgetTwitter().getName()).updateChildren(newTw);


                //Go to configure Twitter Timeline
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
