package com.android.studyjam.asynctask;

import com.android.studyjam.asynctask.model.RequestResult;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;

public interface CharactersService {

    @Headers({
        "Content-Type: application/json",
        "X-Parse-Application-Id: mmIcA9C2TmIgUZlsCRgEvIbPiH9jZsRfDEqKxqiJ",
        "X-Parse-REST-API-Key: DWMSs6y6DLTwOqgZQnwgI6sPbuARCM5ToUVdS32J"
    })
    @GET("Character")
    Call<RequestResult> listCharacters();

}
