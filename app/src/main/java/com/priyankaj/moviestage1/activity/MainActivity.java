package com.priyankaj.moviestage1.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.priyankaj.moviestage1.R;
import com.priyankaj.moviestage1.adapter.MovieAdapter;
import com.priyankaj.moviestage1.model.movieResponse;
import com.priyankaj.moviestage1.model.movie_List;
import com.priyankaj.moviestage1.rest.ApiClient;
import com.priyankaj.moviestage1.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView release,rating;
    private static final String TAG=MainActivity.class.getSimpleName();
    private static String API_KEY="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API_KEY = getResources().getString(R.string.apiKey);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your API Key first", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar = findViewById(R.id.progress);

        data();
    }
    public void data(){
        data(1);
    }
    public void data(int id){
        if (isOnline()) {
            release = findViewById(R.id.release_year);
            rating = findViewById(R.id.rating);
            final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<movieResponse> call = apiInterface.getTopRatedMovies(API_KEY);

            switch (id) {
                case 1:
                    call = apiInterface.getPopularMovies(API_KEY);
                    //textViewMoviesType.setText(this.getResources().getString(R.string.p_movies));
                    break;
                case 2:
                    call = apiInterface.getTopRatedMovies(API_KEY);
                    //textViewMoviesType.setText(this.getResources().getString(R.string.tr_movies));
                    break;
                default:
                    data();

            }
            call.enqueue(new Callback<movieResponse>() {
                @Override
                public void onResponse(retrofit2.Call<movieResponse> call, Response<movieResponse> response) {

                    List<movie_List> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(movies, R.layout.cardview_item_book, getApplicationContext()));
                    Log.d(TAG, "Number of movies Recieved " + movies.size());
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onFailure(retrofit2.Call<movieResponse> call, Throwable t) {

                    Log.e(TAG, t.toString());
                }
            });
        }
        else
            {
                Toast.makeText(this, "Please Check your Internet connection!", Toast.LENGTH_LONG).show();
            }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {

            case R.id.top_rated:
                //perform any action;
                data(2);
                return true;

            case R.id.most_popular:
                //perform any action;
                data();
                return true;
            default:
                data();
                return true;
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
