package com.google.project.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.project.Adapters.ExpandReviewsAdapter;
import com.google.project.Adapters.ExpandTrailersAdapter;
import com.google.project.R;
import com.google.project.Service.API.API;
import com.google.project.Service.API.ServiceBuilder;
import com.google.project.Service.Model.Movie;
import com.google.project.Utilites.Child;
import com.google.project.Utilites.Constants;
import com.google.project.Utilites.Group;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    @Bind(R.id.movies_trailers) ExpandableListView movieTrailers;
    @Bind(R.id.movies_reviews)ExpandableListView movieReviews;
    @Bind(R.id.detailMovieImage)ImageView movieImage;
    @Bind(R.id.detailMovieReleaseDate)TextView releaseDate;
    @Bind(R.id.detailMovieName)TextView movieName;
    @Bind(R.id.detailMovieRate)RatingBar movieRate;
    @Bind(R.id.detailMovieOverview)TextView overView;
    @Bind(R.id.favorite)Button addFavourite;
    Movie ReciviedMovie = null;
    String favouriteMovies;
    boolean isFavourite=false;
    String SPLITTER="#";
    SharedPreferences.Editor editor;

    static final String DETAIL_URI = "URI";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            ReciviedMovie = arguments.getParcelable(DETAIL_URI);
        }
        View rootView = null;
        if(ReciviedMovie != null){
            rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
            ButterKnife.bind(this, rootView);
            releaseDate.setText(ReciviedMovie.getRelease_date());
            movieRate.setRating(ReciviedMovie.getVote_average());
            overView.setText(ReciviedMovie.getOverview());
            movieName.setText(ReciviedMovie.getOriginal_title());
            favouriteMovies = getFavouriteMovies(); // get favourite movies from shared preference

            if(favouriteMovies!=null){
                isFavourite = favouriteMovies.contains(ReciviedMovie.getId()); // check if clicked movie is already a favourite movie
            }

            if(isFavourite==true){
                addFavourite.setBackgroundResource(R.drawable.star_on);
            }

            // add favourite on click listener
            addFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(isFavourite == false){

                        addFavourite.setBackgroundResource(R.drawable.star_on);
                        editor = getActivity().getSharedPreferences("Favourites",0).edit(); // adding movie to shared preference
                        if(favouriteMovies!=null){
                            editor.putString("movies", favouriteMovies + ReciviedMovie.encode() + SPLITTER);
                        }
                        else{
                            editor.putString("movies", ReciviedMovie.encode() + SPLITTER);
                        }
                        editor.commit();
                        Toast.makeText(getActivity(),"movie added to favourites",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // movieTrailers on child click listener
            movieTrailers.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(ReciviedMovie.getTrailers().get(childPosition)));
                    startActivity(intent);
                    return false;
                }
            });

            Picasso.with(getActivity()).load(ReciviedMovie.getPoser_Path()).resize(100,150).into(movieImage); // load movie image

            ServiceBuilder Builder = new ServiceBuilder();

            // fetching trailers and load it to expandable list
            API.Trailers TrailersService =Builder.BuildTrailers();
            TrailersService.getTrailers(Constants.API_KRY, ReciviedMovie.getId(),new retrofit.Callback<Movie>() {
                @Override
                public void success(Movie result, Response response) {
                    ArrayList<String>Trailers=new ArrayList<String>();
                    for(int i=0;i<result.movies.size();i++){
                        Trailers.add(Constants.YOUTUBE_URL + result.movies.get(i).getKey());
                    }
                    FetchList_Trailers(Trailers);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT);
                }
            });

            // fetching reviews and load it to expandable list
            API.Reviews ReviewsService =Builder.BuildReviews();
            ReviewsService.getReviews(Constants.API_KRY, ReciviedMovie.getId(), new retrofit.Callback<Movie>() {
                @Override
                public void success(Movie result, Response response) {
                    FetchList_Reviews(result.movies);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(getActivity(),Setting.class);
            startActivity(intent);
            return true;
        }else if(id==R.id.menu_item_share){
            if( ReciviedMovie!=null){
                if(ReciviedMovie.getTrailers().size()>0){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ReciviedMovie.getTrailers().get(0));
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }else{
                    Toast.makeText(getActivity(),"noting to share",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getActivity(),"noting to share",Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void FetchList_Reviews(List<Movie> movies) {

        ArrayList<Child> ch_list;
        ArrayList<Group> ExpListItems=new ArrayList<Group>();
        ExpandReviewsAdapter ExpAdapter;

        Group gru = new Group();
        gru.setName("reviews");

        ch_list = new ArrayList<Child>();
        for (int i=0; i < movies.size(); i++) {
            Child ch = new Child();
            ch.setReviewerName(movies.get(i).getAuthor());
            ch.setReviewerContent(movies.get(i).getContent());
            ch_list.add(ch);
        }
        gru.setItems(ch_list);
        ExpListItems.add(gru);
        ExpAdapter = new ExpandReviewsAdapter(getActivity(), ExpListItems);
        movieReviews.setAdapter(ExpAdapter);
    }


    public void FetchList_Trailers(ArrayList<String>Trailers) {

        ReciviedMovie.setTrailers(Trailers);
        ArrayList<Child> ch_list;
        ArrayList<Group> ExpListItems=new ArrayList<Group>();
        ExpandTrailersAdapter ExpAdapter;

        Group gru = new Group();
        gru.setName("trailers");

        ch_list = new ArrayList<Child>();
        for (int i=0; i < Trailers.size(); i++) {
            Child ch = new Child();
            ch.setTrailerName("official trailer " + (i + 1));
            ch.setTrailerImage(R.drawable.youtube);
            ch_list.add(ch);
        }
        gru.setItems(ch_list);
        ExpListItems.add(gru);
        ExpAdapter = new ExpandTrailersAdapter(getActivity(), ExpListItems);
        movieTrailers.setAdapter(ExpAdapter);
    }

    public String getFavouriteMovies(){
        SharedPreferences favourites=getActivity().getSharedPreferences("Favourites", 0);
        String movies = favourites.getString("movies", null);
        return movies;
    }

}
