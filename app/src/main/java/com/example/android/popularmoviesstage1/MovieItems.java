package com.example.android.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItems implements Parcelable {

    String title, releaseDate, overview, posterImage;
    Double userRating;

    public MovieItems(String title, Double userRating, String releaseDate, String overview, String posterImage) {

        this.title = title;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.posterImage = posterImage;
    }

    private MovieItems(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        posterImage = in.readString();
        if (in.readByte() == 0) {
            userRating = null;
        } else {
            userRating = in.readDouble();
        }
    }

    public static final Creator<MovieItems> CREATOR = new Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[0];
        }
    };

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

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(posterImage);
        if (userRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(userRating);
        }
    }
}
