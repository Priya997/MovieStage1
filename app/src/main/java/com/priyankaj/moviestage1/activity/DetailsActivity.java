package com.priyankaj.moviestage1.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.priyankaj.moviestage1.R;
import com.priyankaj.moviestage1.model.movie_List;
import com.priyankaj.moviestage1.rest.ApiClient;
import com.priyankaj.moviestage1.rest.ApiInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import jp.wasabeef.blurry.Blurry;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity  extends AppCompatActivity {

    private String Api_Key="";
    private int id;
    private movie_List movie;
    ImageView background_image,small_image;

    TextView titletv,rating,overview,language,release_date;
    Context context=this;
    ProgressBar progressBar;
    private static final String TAG = DetailsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        background_image=(ImageView)findViewById(R.id.background_img);
        small_image=(ImageView)findViewById(R.id.small_img);

        titletv=(TextView)findViewById(R.id.titletv);
        rating=(TextView)findViewById(R.id.rating);
        overview=(TextView)findViewById(R.id.overview);
        language=(TextView)findViewById(R.id.language);
        release_date=(TextView)findViewById(R.id.release);

        progressBar = findViewById(R.id.progress);
        Api_Key = getResources().getString(R.string.apiKey);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<movie_List> call = apiService.getMovieDetails(id, Api_Key);
        call.enqueue(new Callback<movie_List>() {

            @Override
            public void onResponse(Call<movie_List> call, Response<movie_List> response) {
                movie = response.body();
                PopulateUI(movie);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                Log.v(TAG, id + " " + movie.getTitle());
            }

            @Override
            public void onFailure(Call<movie_List> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public void PopulateUI(movie_List movie)
    {

        titletv.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        release_date.setText(movie.getReleaseDate());
        rating.setText(movie.getVoteAverage().toString());
        language.setText(movie.getOriginalLanguage());
        getSupportActionBar().setTitle(movie.getTitle());
        Picasso.with(context).load(getImageUrl(movie.getPosterPath(), null)).into(background_image);
        Picasso.with(context).load(getImageUrl(movie.getPosterPath(), null)).into(small_image);

    }
    public String getImageUrl(String imageName, @Nullable String width) {
        if (width == null) {
            width = "w342/";
        } else {
            width = "w" + width + "/";
        }
        return getResources().getString(R.string.img) + width + imageName;
    }
}
