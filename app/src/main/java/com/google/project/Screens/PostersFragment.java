package com.google.project.Screens;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.project.Adapters.ImageAdapter;
import com.google.project.R;
import com.google.project.Service.LoadFavourites;
import com.google.project.Service.API.API;
import com.google.project.Utilites.Constants;
import com.google.project.Service.Model.Movie;
import com.google.project.Service.API.ServiceBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostersFragment extends Fragment implements GridView.OnItemClickListener,API.OnGetFavourites {

    @Bind(R.id.grid_view)GridView gridView;
    List<Movie> global_movies =new ArrayList<Movie>();
    String sortType;

    public interface Callback {
        /*
         * DetailFragmentCallback for when an item has been selected.
         */
         void onItemSelected(Movie object);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posters, container, false);
        ButterKnife.bind(this, rootView);

        sortType = getSetting();
        if(!sortType.equals("favourite")) {
            PerformMoviesCall(sortType); // load movies from internet based on sort type
        }else {
            new LoadFavourites(getActivity(), this).execute();  // load favourite movies from shared preference
        }

        gridView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSetting();
        if((sortBy!=sortType)&& !sortBy.equals("favourite")){
            PerformMoviesCall(sortBy);
        }else if((sortBy!=sortType)&& sortBy.equals("favourite" )){
            new LoadFavourites(getActivity(), this).execute();
        }
        sortType=sortBy;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ((Callback) getActivity())
                .onItemSelected(global_movies.get(position));
    }

    @Override
    public void Loaded_Favourites(List<Movie> movies) {
        global_movies=movies;
        gridView.setAdapter(new ImageAdapter(getActivity(), movies));
    }

    public void PerformMoviesCall(String sortType){

        ServiceBuilder Builder = new ServiceBuilder();
        API.Movies service =Builder.BuildMovies();
        service.getMovies(Constants.API_KRY, sortType, new retrofit.Callback<Movie>() {
            @Override
            public void success(Movie result, Response response) {
                global_movies=result.movies;
                gridView.setAdapter(new ImageAdapter(getActivity(), result.movies));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT);
            }
        });

    }

    public String getSetting(){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        String sortBy = prefs.getString("listpref", null);
        if(sortBy==null){
            sortBy="popularity.desc";
        }
        return sortBy;
    }
}
