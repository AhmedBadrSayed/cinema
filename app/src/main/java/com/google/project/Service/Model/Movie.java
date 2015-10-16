package com.google.project.Service.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.project.Utilites.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarAli on 02/10/2015.
 */
public class Movie implements Parcelable{

    @SerializedName("results")
    public List<Movie> movies=new ArrayList<Movie>();

    public String id;
    public String key; // movie trailer key
    public String content; // movie review content
    public String author; // movie review content author
    public String poster_path;
    public String overview;
    public String original_title;
    public String release_date;
    public float vote_average;
    public ArrayList<String> reviewContent;
    public ArrayList<String>trailers;
    public boolean IsFavourite=false;

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            Movie movie=new Movie();
            movie.id = in.readString();
            movie.poster_path = in.readString();
            movie.overview = in.readString();
            movie.original_title = in.readString();
            movie.release_date=in.readString();
            movie.vote_average=in.readFloat();
            movie.reviewContent = (ArrayList<String>)in.readSerializable();
            movie.trailers = (ArrayList<String>)in.readSerializable();
            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(original_title);
        parcel.writeString(release_date);
        parcel.writeFloat(vote_average);
        parcel.writeSerializable(reviewContent);
        parcel.writeSerializable(trailers);

    }

    public ArrayList<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<String> trailers) {this.trailers = trailers;}

    public ArrayList<String>  getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(ArrayList<String> reviewContent) {this.reviewContent = reviewContent;}

    public String getId() {
        return id;
    }

    public void setId(String id) {this.id = id;}

    public String getPoser_Path() {
        return Constants.BASE_URL+poster_path;
    }

    public void setPoser_Path(String PoserPath) {
        this.poster_path = PoserPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public float getVote_average() {return vote_average;}

    public void setVote_average(float vote_average) {this.vote_average = vote_average;}

    public String getRelease_date() {return release_date;}

    public void setRelease_date(String release_date) {this.release_date = release_date;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isFavourite() {
        return IsFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        IsFavourite = isFavourite;
    }

    public String encode(){
        Gson gson = new Gson();
        return ( gson.toJson(this) );
    }

    public Movie decode(String encodedMovie){
        Gson gson = new Gson();
        Movie movie = gson.fromJson(encodedMovie, Movie.class);
        return movie;
    }

}
