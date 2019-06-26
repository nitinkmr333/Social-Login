package login.social.sample;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class facebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String name = prefs.getString("name", null);
        String gender = prefs.getString("gender", null);
        String email = prefs.getString("email", null);
        String image = prefs.getString("image", null);

        ImageView img = (ImageView) findViewById(R.id.imageView);
        TextView tv5 = (TextView) findViewById(R.id.textView10);
        TextView tv6 = (TextView) findViewById(R.id.textView11);

        tv5.setText(name);
        tv6.setText(email);

        // Get the widgets reference from XML layout



        InputStream is = null;
        try {
            is = new URL(image).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        img.setImageBitmap(bitmap);

        new DownLoadImageTask(img).execute(image);


    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


}


