package com.google.project.Service.API;

import com.google.project.Service.Model.Movie;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by OmarAli on 15/10/2015.
 */
public interface API {

    public interface Movies{
        @GET("/discover/movie")
        void getMovies(@Query("api_key") String API_KEY,@Query("sort_by") String Sort_Type, Callback <Movie> movies);
    }
    public interface Trailers{
        @GET("/movie/{movieId}/videos")
        void getTrailers(@Query("api_key") String API_KEY,@Path("movieId") String movieId, Callback <Movie> Trailers);
    }
    public interface Reviews{
        @GET("/movie/{movieId}/reviews")
        void getReviews(@Query("api_key") String API_KEY,@Path("movieId") String movieId, Callback <Movie> Reviews);
    }

    public interface OnGetFavourites{
        public void Loaded_Favourites(List<Movie> movies);
    }

}
