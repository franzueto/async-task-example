package com.android.studyjam.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.studyjam.asynctask.model.CartoonCharacter;
import com.android.studyjam.asynctask.model.RequestResult;
import com.android.studyjam.asynctask.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private static final String CHARACTERS_URL = "https://api.parse.com/1/classes/Character";

    private ArrayList<CartoonCharacter> cartoonCharacters;

    private ImageView imageView;

    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        cartoonCharacters = new ArrayList<>();
    }

    public void initTask(View v) {
        new FetchCharactersTask().execute(CHARACTERS_URL);
    }

    public void nextCharacter(View v) {
        changeImage(getNextIndex());
    }

    public int getNextIndex() {
        return (selectedIndex + 1) < cartoonCharacters.size() ? selectedIndex + 1 : 0;
    }

    public void changeImage(int index) {
        if(!cartoonCharacters.isEmpty() && index < cartoonCharacters.size()) {
            selectedIndex = index;
            new DownloadImageTask().execute(cartoonCharacters.get(index).getImage().getUrl());
        }
    }

    private class FetchCharactersTask extends AsyncTask<String, Void, RequestResult> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity2.this, "", "Loading DATA. Please wait...", true);
        }

        @Override
        protected RequestResult doInBackground(String... params) {

            String x = Utils.getRequestOkHttp(params[0]);
            //InputStream inputStream = Utils.getRequest(params[0]);

            //Reader reader = new InputStreamReader(inputStream);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            RequestResult requestResult = gson.fromJson(x, RequestResult.class);

            return requestResult;
        }

        @Override
        protected void onPostExecute(RequestResult requestResult) {
            cartoonCharacters.clear();
            cartoonCharacters.addAll(requestResult.getResults());

            if(progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            changeImage(0);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity2.this, "", "Loading IMAGE. Please wait...", true);
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
