package kr.ry4nkim.movierank.View;

import android.graphics.drawable.Drawable;

public interface MovieDetailView {
    public void setTitle(String title);
    public void setMovieDetail(Drawable background, Drawable poster, String score, String score_participant, String age_limit,
                                String release_date, String genre, String running_time, String rank, String reservation_rate,
                                String total_attendance, String story, String director, String the_casts, String release_after);
    public void setWishToggleImage(int imageResId);
    public void showSnackBar(String message);
}