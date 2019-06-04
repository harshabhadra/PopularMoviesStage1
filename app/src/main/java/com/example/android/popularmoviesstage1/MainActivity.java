package com.example.android.popularmoviesstage1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    MovieItems movieItems;
    ArrayList<MovieItems>movieItemsArrayList = new ArrayList<>();
    TextView errorText;
    ProgressBar loadingIndicator;
    SharedPreferences preferences;
    String sort_type;
    String path = "popular";
    String apiKey = "";

    String title;
    String image_url;
    String release_date;
    String overview;
    Double user_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movie_recycler);
        loadingIndicator = findViewById(R.id.loading_indicator);
        errorText = findViewById(R.id.error_text);
        loadingIndicator.setVisibility(View.VISIBLE);

        preferences = getSharedPreferences("popular_movies",MODE_PRIVATE);
        sort_type = preferences.getString("sort_type","popular");

        if (sort_type.equals("popular")){
            getSupportActionBar().setTitle("Popular Movies");
        }else if (sort_type.equals("top_rated")){
            getSupportActionBar().setTitle("Top Rated Movies");
            path = "top_rated";
        }

        int numberOfColumns = Utility.calculateNoOfColumns(getApplicationContext(), 140);
        movieAdapter = new MovieAdapter(this,movieItemsArrayList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),numberOfColumns));
        recyclerView.setAdapter(movieAdapter);
        fetchJson();
    }

    private void fetchJson(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieInterface.JsonUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();



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
                image_url = "http://image.tmdb.org/t/p/w185/" + posterImage;
                title = movieObj.getString("title");
                release_date = movieObj.getString("release_date");
                overview = movieObj.getString("overview");
                user_rating = movieObj.getDouble("vote_average");

                 movieItems = new MovieItems(title, user_rating, release_date, overview, image_url);
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
        detailIntent.putExtra("MovieItems", movieItemsArrayList.get(itemClicked));
        startActivity(detailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.preference_sort){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final SharedPreferences.Editor editor = preferences.edit();
            int selected = 0;
            sort_type = preferences.getString("sort_type","popular");
            if (sort_type.equals("popular")){
                selected = 0;
            }else if (sort_type.equals("top_rated")){
                selected = 1;
            }
            builder.setTitle("Select Option");
            builder.setSingleChoiceItems(R.array.sort_types, selected,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0)
                                editor.putString("sort_type", "popular");
                            else if (which == 1)
                                editor.putString("sort_type", "top_rated");
                        }
                    });
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.apply();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }


}
