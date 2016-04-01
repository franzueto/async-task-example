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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.GsonConverterFactory;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity3 extends AppCompatActivity {

    private static final String CHARACTERS_URL = "https://api.parse.com/1/classes/";

    private ArrayList<CartoonCharacter> cartoonCharacters;

    private ImageView imageView;

    private int selectedIndex;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        cartoonCharacters = new ArrayList<>();
    }

    public void initTask(View v) {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

        Retrofit retrofitClient = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(CHARACTERS_URL)
            .build();

        CharactersService service = retrofitClient.create(CharactersService.class);

        Call<RequestResult> requestResultCall = service.listCharacters();

        progressDialog = ProgressDialog.show(MainActivity3.this, "", "Loading DATA. Please wait...", true);

        requestResultCall.enqueue(new Callback<RequestResult>() {

            @Override
            public void onResponse(Response<RequestResult> response, Retrofit retrofit) {
                //alFinalizar(response.body());
                if (response.isSuccess()) {
                    Toast.makeText(getBaseContext(), "PASO", Toast.LENGTH_LONG).show();
                    alFinalizar(response.body());
                } else {
                    Toast.makeText(getBaseContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getBaseContext(), "FALLO", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });


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
            Picasso.with(this)
                .load(cartoonCharacters.get(index).getImage().getUrl())
                .placeholder(R.drawable.avatar)
                .into(imageView);
        }
    }

    private void alFinalizar(RequestResult requestResult) {
        cartoonCharacters.clear();
        cartoonCharacters.addAll(requestResult.getResults());

        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        changeImage(0);
    }

}
