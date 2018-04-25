package kr.ry4nkim.movierank.Adapter.Model;

import kr.ry4nkim.movierank.Model.Movie;

public interface RecyclerDataModel {
    public void add(Movie movie);
    public void remove(int position);
    public void clear();
    public Movie getMovie(int position);
    public int getItemCount();
}
