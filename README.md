# PopularMoviesStage1

This project is build by me for Android Developer Nanodegree in Udacity.
This Application will fetch data from MovieDB API and populate the UI with Movies poster and if you click on any Movie poster then a new 
activity will open, in which you can see the Details about the movie(Name, Release Date, User Rating, Overview).
The app contains two Activiy

#MainActivity
This is the activity in which movies poster will be populated. I'm using Retrofit to fetch data from MovieDB API and Recycle View with 
GridLayoutManager to populate the UI.

#DetailsActivity
This activity will open when you click on any image in MainActivity.

In order to test the App you need get your own API key by creating an account on https://www.themoviedb.org/?_dc=1559554767.
You can create your own API key after you create an account.
