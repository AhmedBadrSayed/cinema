package com.google.project.Service.API;

import com.google.project.Utilites.Constants;

import retrofit.RestAdapter;

/**
 * Created by OmarAli on 16/10/2015.
 */
public class ServiceBuilder {
    private RestAdapter restAdapter;
    public ServiceBuilder(){
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(Constants.API).build();
    }
    public API.Movies BuildMovies(){
        return restAdapter.create(API.Movies.class);
    }
    public API.Trailers BuildTrailers(){
        return restAdapter.create(API.Trailers.class);
    }
    public API.Reviews BuildReviews(){
        return restAdapter.create(API.Reviews.class);
    }
}
