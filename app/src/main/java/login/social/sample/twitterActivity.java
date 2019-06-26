package login.social.sample;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class twitterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        SharedPreferences prefs = getSharedPreferences("mypref2", MODE_PRIVATE);
        String name = prefs.getString("name", null);

        String userid = prefs.getString("userid", null);


        ImageView img = (ImageView) findViewById(R.id.imageView);
        TextView tv10 = (TextView) findViewById(R.id.textView10);

        TextView tv11 = (TextView) findViewById(R.id.textView11);
        TextView tv14 = (TextView) findViewById(R.id.textView14);

        tv10.setText(name);
        tv11.setText(userid);
        tv14.setText("User ID:");

    }


}


