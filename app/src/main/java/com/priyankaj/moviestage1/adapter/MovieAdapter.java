package com.priyankaj.moviestage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.priyankaj.moviestage1.R;
import com.priyankaj.moviestage1.activity.DetailsActivity;
import com.priyankaj.moviestage1.model.movie_List;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    static List<movie_List> movies;
    private int rowLayout;
    private Context context;



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //LayoutInflater mLayoutInflater= LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        LayoutInflater mLayoutInflater= LayoutInflater.from(context);
        view=mLayoutInflater.inflate(R.layout.cardview_item_book,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Date yourDate = null;

        String url=context.getResources().getString(R.string.img)+"w342/" + movies.get(position).getPosterPath();
        //holder.movieTitle.setText(movies.get(position).getTitle());

        Picasso.with(context).load(url).into(holder.movie_poster, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if(holder.progressBar != null){
                    holder.progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onError() {

            }
        });

        String dateFromDB = movies.get(position).getReleaseDate();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
             yourDate = parser.parse(dateFromDB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(yourDate);
        int abc =calendar.get(Calendar.YEAR); //Day of the month :)


        holder.movieTitle.setText(abc+"");

        //holder.movieDescription.setText(movies.get(position).getOverview());
        //holder.rating.setText(abc+"");
        holder.rating.setText(movies.get(position).getVoteAverage().toString());    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView release;

        ImageView movie_poster;
        TextView movieDescription;
        TextView rating;
        ProgressBar progressBar;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movie_poster=(ImageView)itemView.findViewById(R.id.movie_wallpaper);
            movieTitle = (TextView) itemView.findViewById(R.id.release_year);
            //release = (TextView) itemView.findViewById(R.id.release_year);
            //movieDescription = (TextView) itemView.findViewById(R.id.description);
            rating = (TextView) itemView.findViewById(R.id.rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    intent.putExtra("id", movies.get(getAdapterPosition()).getId());
                    intent.putExtra("position", getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    public MovieAdapter(List<movie_List> movies, int rowLayout, Context context)
    {

        this.movies=movies;
        this.rowLayout=rowLayout;
        this.context=context;

    }

}
