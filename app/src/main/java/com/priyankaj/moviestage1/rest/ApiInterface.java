package com.priyankaj.moviestage1.rest;

import com.priyankaj.moviestage1.model.movieResponse;
import com.priyankaj.moviestage1.model.movie_List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<movieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<movieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<movie_List> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
