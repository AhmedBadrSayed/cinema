package com.google.project.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.project.Service.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarAli on 02/10/2015.
 */
public class ImageAdapter extends BaseAdapter{

    private Context mContext;
    private List<Movie>movies=new ArrayList<Movie>();

    public ImageAdapter(Context c,List<Movie>movies){
        mContext = c;
        this.movies=movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(mContext);
        imageView.setMaxHeight(278);
        Picasso.with(mContext).load(movies.get(i).getPoser_Path()).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
