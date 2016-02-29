package com.android.studyjam.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.studyjam.asynctask.util.Utils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "http://www.tecnologia.net/wp-content/uploads/2015/05/Los-mejores-trucos-para-Android.png";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void initTask(View v) {
        new DownloadImageTask().execute(IMAGE_URL);
    }

    public void nextCharacter(View v) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final Bitmap bmp = Utils.loadImageFromNetwork(IMAGE_URL);
                    imageView.post(new Runnable() {

                        @Override
                        public void run() {
                            imageView.setImageBitmap(bmp);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading. Please wait...", true);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bmp = null;

            try {
                bmp = Utils.loadImageFromNetwork(params[0]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getBaseContext(), "Couldn't load image!", Toast.LENGTH_LONG).show();
            }

            if(progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
