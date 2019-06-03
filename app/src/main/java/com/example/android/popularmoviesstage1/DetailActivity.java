package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView userRating, releaseDate, overview;
    String title,  date, detail;
    String rating, image_url;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        userRating = findViewById(R.id.rating_tv);
        releaseDate = findViewById(R.id.release_date_tv);
        overview = findViewById(R.id.overview_tv);
        poster = findViewById(R.id.image_tv);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bundle = intent.getExtras();

        try {
            if (bundle.containsKey("title")) {
                title = bundle.getString("title");
                getSupportActionBar().setTitle(title);
            }
            if (bundle.containsKey("rating")) {
                rating = String.valueOf(bundle.getDouble("rating"));
            }
            if (bundle.containsKey("image")){
                image_url = bundle.getString("image");
            }
            if (bundle.containsKey("date")){
                date = bundle.getString("date");
            }
            if (bundle.containsKey("detail")){
                detail = bundle.getString("detail");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        Picasso.get().load(image_url).error(R.drawable.ic_launcher_background).into(poster);
        userRating.setText(rating);
        releaseDate.setText(date);
        overview.setText(detail);
    }
}
