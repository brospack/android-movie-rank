package kr.ry4nkim.movierank.View;

import java.util.ArrayList;

import kr.ry4nkim.movierank.Model.Movie;

public interface MainView {
    public void setMovieList(ArrayList<Movie> movieList);
    public void setMenu(int position);
    public void scrollToPosition(int position);
    public void showProgress();
    public void hideProgress();
    public void showSnackBar(String message);
}