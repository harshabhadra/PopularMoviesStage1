package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<MovieItems> movieItems;
    public final MovieAdapterClickHandler clickListener;

    public interface MovieAdapterClickHandler{
        void  onClick(int itemClicked);
    }

    public MovieAdapter(Context context, ArrayList<MovieItems> movieItems, MovieAdapterClickHandler clickHandler) {
        this.movieItems = movieItems;
        inflater = LayoutInflater.from(context);
        clickListener = clickHandler;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, user_rating, release_date, overview;
        ImageView poster_image;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title);
            title.setVisibility(View.GONE);
            user_rating = itemView.findViewById(R.id.user_ratings);
            user_rating.setVisibility(View.GONE);
            release_date = itemView.findViewById(R.id.movie_release_date);
            release_date.setVisibility(View.GONE);
            overview = itemView.findViewById(R.id.overview);
            overview.setVisibility(View.GONE);
            poster_image = itemView.findViewById(R.id.movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            clickListner.onClick
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

        Picasso.get().load(movieItems.get(i).getPosterImage()).into(movieViewHolder.poster_image);
        movieViewHolder.title.setText(movieItems.get(i).getTitle());
        movieViewHolder.release_date.setText(movieItems.get(i).getReleaseDate());
        movieViewHolder.user_rating.setText(String.valueOf(movieItems.get(i).getUserRating()));
        movieViewHolder.overview.setText(movieItems.get(i).overview);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }
}
