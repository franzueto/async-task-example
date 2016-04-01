package com.android.studyjam.asynctask.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    private static final String PARSE_APP_ID = "mmIcA9C2TmIgUZlsCRgEvIbPiH9jZsRfDEqKxqiJ";
    private static final String PARSE_REST_API_KEY = "DWMSs6y6DLTwOqgZQnwgI6sPbuARCM5ToUVdS32J";

    public static Bitmap loadImageFromNetwork(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        return bmp;
    }

    public static InputStream getRequest(String httpUrl){
        // Deprecated - To show is shouldn't be used anymore
        //HttpClient httpclient = new DefaultHttpClient();

        InputStream inputStream = null;
        //String result = "";
        try {
            URL url = new URL(httpUrl);

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("X-Parse-Application-Id", PARSE_APP_ID);
            urlConnection.setRequestProperty("X-Parse-REST-API-Key", PARSE_REST_API_KEY);

            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            //result = convertInputStreamToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static String getRequestOkHttp(String httpUrl) {
        String result = "";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url(httpUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Parse-Application-Id", PARSE_APP_ID)
            .addHeader("X-Parse-REST-API-Key", PARSE_REST_API_KEY)
            .build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
