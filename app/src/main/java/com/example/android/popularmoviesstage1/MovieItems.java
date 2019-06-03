package com.example.android.popularmoviesstage1;

public class MovieItems {

    String title, releaseDate, overview, posterImage;
    Double userRating;

    public MovieItems(String title, Double userRating, String releaseDate, String overview, String posterImage) {

        this.title = title;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterImage = posterImage;
    }

    public String getTitle() {
        return title;
    }

    public Double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterImage() {
        return posterImage;
    }
}
