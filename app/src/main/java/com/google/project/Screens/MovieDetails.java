package com.google.project.Screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.project.R;
import com.google.project.Service.Model.Movie;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            if( getIntent().getExtras().getParcelable(MovieDetailsFragment.DETAIL_URI) == null){
                arguments.putParcelable(MovieDetailsFragment.DETAIL_URI, getIntent().getData());
            }else{
                Movie movie = getIntent().getExtras().getParcelable(MovieDetailsFragment.DETAIL_URI);
                arguments.putParcelable(MovieDetailsFragment.DETAIL_URI, movie);
            }
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                            .add(R.id.movie_details_container, fragment)
                            .commit();
                }
        }
}
