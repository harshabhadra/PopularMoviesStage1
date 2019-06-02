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

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }
}
