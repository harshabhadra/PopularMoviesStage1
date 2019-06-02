package com.example.android.popularmoviesstage1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {

    String JsonUrl = "http://api.themoviedb.org/";

    @GET("/3/movie/{Path}/")
    Call<String> getString(@Path("Path") String path, @Query("api_key") String apiKey);
}
