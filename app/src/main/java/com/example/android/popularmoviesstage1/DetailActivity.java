package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TextView userRating, releaseDate, overview;
    String title,  date, detail;
    String rating, image_url, mRating;
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
        MovieItems movieItems = intent.getParcelableExtra("MovieItems");
        title = movieItems.getTitle();
        getSupportActionBar().setTitle(title);
        date = movieItems.getReleaseDate();
        detail = movieItems.getOverview();
        image_url = movieItems.getPosterImage();
        rating = String.valueOf(movieItems.getUserRating());
        mRating = "Rating " + "(" + rating + ")";


        Picasso.get().load(image_url).error(R.drawable.ic_launcher_background).into(poster);
        userRating.setText(mRating);
        releaseDate.setText(date);
        overview.setText(detail);
    }
}
