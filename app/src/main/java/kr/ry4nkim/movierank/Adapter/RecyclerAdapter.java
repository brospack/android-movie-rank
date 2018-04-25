package kr.ry4nkim.movierank.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ry4nkim.movierank.Adapter.Model.RecyclerDataModel;
import kr.ry4nkim.movierank.Adapter.View.RecyclerAdapterView;
import kr.ry4nkim.movierank.Model.Movie;
import kr.ry4nkim.movierank.MovieDetailActivity;
import kr.ry4nkim.movierank.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements RecyclerDataModel, RecyclerAdapterView {
    private Context context;
    private List<Movie> items;

    public RecyclerAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<Movie>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie item = getMovie(position);

        holder.movie_poster.setImageBitmap(item.getMoviePoster());
        holder.movie_rank.setText(String.valueOf(position + 1));
        holder.movie_title.setText(item.getMovieTitle());
        holder.movie_reservation_rate.setText(item.getMovieReservationRate() + "%");
        holder.movie_outline.setText((!item.getMovieGenre().equals("") ? item.getMovieGenre() + " | " : "")
                                        + (!item.getMovieReleaseDate().equals("") ? item.getMovieReleaseDate() + " 개봉 | " : "")
                                        + (item.getMovieRunningTime() != 0 ? item.getMovieRunningTime() + "분 | " : "")
                                        + (!item.getMovieAgeLimit().equals("") ? item.getMovieAgeLimit() : ""));
        holder.movie_director.setText(!item.getMovieDirector().equals("") ? item.getMovieDirector() : "정보없음");
        holder.movie_the_casts.setText(!item.getMovieTheCasts().equals("") ? item.getMovieTheCasts() : "정보없음");

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("movie", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public void add(Movie movie) {
        this.items.add(movie);
    }

    @Override
    public void remove(int position) {
        this.items.remove(position);
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public Movie getMovie(int position) {
        return this.items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card_view;
        private ImageView movie_poster;
        private TextView movie_rank;
        private TextView movie_title;
        private TextView movie_reservation_rate;
        private TextView movie_outline;
        private TextView movie_director;
        private TextView movie_the_casts;

        public ViewHolder(View itemView) {
            super(itemView);

            card_view = (CardView) itemView.findViewById(R.id.card_view);
            movie_poster = (ImageView) itemView.findViewById(R.id.movie_poster);
            movie_rank = (TextView) itemView.findViewById(R.id.movie_rank);
            movie_title = (TextView) itemView.findViewById(R.id.movie_title);
            movie_reservation_rate = (TextView) itemView.findViewById(R.id.movie_reservation_rate);
            movie_outline = (TextView) itemView.findViewById(R.id.movie_outline);
            movie_director = (TextView) itemView.findViewById(R.id.movie_director);
            movie_the_casts = (TextView) itemView.findViewById(R.id.movie_the_casts);

        }
    }
}
