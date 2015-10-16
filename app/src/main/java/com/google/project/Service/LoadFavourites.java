package com.google.project.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.project.Service.API.API;
import com.google.project.Service.Model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarAli on 21/09/2015.
 *
 * this AsyncTask used to perform url connection and sending request to the API to get Movies posters
 */
public class LoadFavourites extends AsyncTask<Void, List<Movie>, List<Movie>> {
    API.OnGetFavourites onGetFavourites; // interface object to notify the main Thread when this thread is finished
    Context ctx;
    ProgressDialog progressBar;

    public LoadFavourites(Context ctx,API.OnGetFavourites onGetFavourites){
        this.onGetFavourites=onGetFavourites;
        this.ctx=ctx;
    }

    // start the progress bar
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(ctx);
        progressBar.setIndeterminate(true);
        progressBar.setMessage("Loading favourites");
        progressBar.show();
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        List<Movie>movies=new ArrayList<Movie>();
        SharedPreferences favourites=ctx.getSharedPreferences("Favourites", 0);
        String allMovies=favourites.getString("movies",null);
        if(allMovies!=null){
            for (String encodedMovie: allMovies.split("#")){

                Movie movieObject = new Movie();
                movieObject=movieObject.decode(encodedMovie);
                movies.add(movieObject);
            }
        }

        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie>movies) {
        super.onPostExecute(movies);
        onGetFavourites.Loaded_Favourites(movies);
        progressBar.dismiss(); // dismiss progressBar

    }
}
