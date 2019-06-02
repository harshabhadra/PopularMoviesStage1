package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    ArrayList<MovieItems>movieItemsArrayList = new ArrayList<>();
    TextView errorText;
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movie_recycler);
        loadingIndicator = findViewById(R.id.loading_indicator);
        errorText = findViewById(R.id.error_text);
        loadingIndicator.setVisibility(View.VISIBLE);

        movieAdapter = new MovieAdapter(this,movieItemsArrayList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerView.setAdapter(movieAdapter);
        fetchJson();
    }

    private void fetchJson(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieInterface.JsonUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        String path = "popular";
        String apiKey = "5e1c576128055caa4e6c32b19525b6bc";

        MovieInterface movieInterface = retrofit.create(MovieInterface.class);
        Call<String> call = movieInterface.getString(path, apiKey);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        loadingIndicator.setVisibility(View.GONE);
                        String jsonResponse = response.body();
                        writeRecycler(jsonResponse);
                    }else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                loadingIndicator.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void writeRecycler(String json){
        try{
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("results");

            for (int i = 0; i<jsonArray.length(); i++){

                JSONObject movieObj = jsonArray.getJSONObject(i);
                String posterImage = movieObj.getString("poster_path");
                String image_url = "http://image.tmdb.org/t/p/w185/" + posterImage;
                String title = movieObj.getString("title");
                String release_date = movieObj.getString("release_date");
                String overview = movieObj.getString("overview");
                Double user_rating = movieObj.getDouble("popularity");

                MovieItems movieItems = new MovieItems(title, user_rating, release_date, overview, image_url);
                movieItemsArrayList.add(movieItems);
            }
            movieAdapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(int itemClicked) {

        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(detailIntent);

    }
}
