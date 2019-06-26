package login.social.sample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import login.social.sample.facebookSignIn.FacebookHelper;
import login.social.sample.facebookSignIn.FacebookResponse;
import login.social.sample.facebookSignIn.FacebookUser;
import login.social.sample.twitterSignIn.TwitterHelper;
import login.social.sample.twitterSignIn.TwitterResponse;
import login.social.sample.twitterSignIn.TwitterUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
         FacebookResponse, TwitterResponse {

    private FacebookHelper mFbHelper;
    private TwitterHelper mTwitterHelper;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //fb api initialization
        mFbHelper = new FacebookHelper(this,
                "id,name,email,gender,birthday,picture,cover",
                this);

        //twitter initialization
        mTwitterHelper = new TwitterHelper(R.string.twitter_api_key,
                R.string.twitter_secret_key,
                this,
                this);


        //set sign in button
        findViewById(R.id.twitter_login_button).setOnClickListener(this);
        findViewById(R.id.bt_act_login_fb).setOnClickListener(this);
        findViewById(R.id.bt_act_logout_fb).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_act_login_fb:
                mFbHelper.performSignIn(this);
                break;
            case R.id.bt_act_logout_fb:
                mFbHelper.performSignOut();
                break;
            case R.id.twitter_login_button:
                mTwitterHelper.performSignIn();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        //handle permissions
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //handle results
        mFbHelper.onActivityResult(requestCode, resultCode, data);
        mTwitterHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFbSignInFail() {
        Toast.makeText(this, "Facebook sign in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbSignInSuccess() {
        Toast.makeText(this, "Facebook sign in success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFbProfileReceived(FacebookUser facebookUser) {
        //Toast.makeText(this, "Facebook user data: name= " + facebookUser.name + " email= " + facebookUser.email, Toast.LENGTH_SHORT).show();
        Log.d("Person name: ", facebookUser.name + "");
        Log.d("Person gender: ", facebookUser.gender + "");
        Log.d("Person email: ", facebookUser.email + "");
        Log.d("Person image: ", facebookUser.facebookID + "");

        SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
        editor.putString("name", facebookUser.name);
        editor.putString("gender", facebookUser.gender);
        editor.putString("email", facebookUser.email);
        editor.putString("image", facebookUser.facebookID);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, facebookActivity.class);
        startActivity(intent);

    }

    @Override
    public void onFBSignOut() {
        Toast.makeText(this, "Facebook sign out success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTwitterError() {
        Toast.makeText(this, "Twitter sign in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTwitterSignIn(@NonNull String userId, @NonNull String userName) {
        //Toast.makeText(this, " User id: " + userId + "\n user name" + userName, Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences("mypref2", MODE_PRIVATE).edit();
        editor.putString("userid", userId);
        editor.apply();
    }

    @Override
    public void onTwitterProfileReceived(TwitterUser user) {
        //Toast.makeText(this, "Twitter user data: name= " + user.name + " email= " + user.email, Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences("mypref2", MODE_PRIVATE).edit();
        editor.putString("name", user.name);
        editor.putString("email", user.email);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, twitterActivity.class);
        startActivity(intent);


    }

}
