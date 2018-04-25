package kr.ry4nkim.movierank.Presenter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.ry4nkim.movierank.Model.Movie;
import kr.ry4nkim.movierank.R;
import kr.ry4nkim.movierank.Utils.Utils;
import kr.ry4nkim.movierank.View.MovieDetailView;

public class MovieDetailPresenterImpl implements MovieDetailPresenter {
    private MovieDetailView view;
    private Movie movie;
    private SharedPreferences preferences;

    private boolean is_wish_list;
    private int pref_wish_list;

    public MovieDetailPresenterImpl(MovieDetailView view, Serializable movie, SharedPreferences preferences) {
        this.view = view;
        this.movie = (Movie) movie;
        this.preferences = preferences;
    }

    @Override
    public void getMovieTitle() {
        view.setTitle(movie.getMovieTitle());
    }

    @Override
    public void getMovieDetail() {
        final ArrayList<Drawable> moviePoster = new ArrayList<Drawable>();
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(movie.getMoviePosterUrl());
                    Bitmap bmp = Utils.loadImage(url);
                    moviePoster.add(new BitmapDrawable(bmp));
                    bmp = Utils.cropCenterBitmap(bmp, bmp.getWidth(), bmp.getHeight() / 2);
                    moviePoster.add(new BitmapDrawable(bmp));
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Drawable background = moviePoster.get(1);
                        Drawable poster = moviePoster.get(0);
                        String score = String.valueOf(movie.getMovieScore());
                        String scoreParticipant = movie.getMovieScoreParticipant() + "명 참여";
                        String age_limit = movie.getMovieAgeLimit();
                        String release_date = movie.getMovieReleaseDate();
                        String release_after = "";
                        try {
                            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
                            Date dateToday = new Date();
                            Date dateRelease = transFormat.parse(release_date);

                            long diff = (dateToday.getTime() - dateRelease.getTime()) / (24 * 60 * 60 * 1000);
                            if(diff < 0) {
                                release_after = "개봉 D" + String.valueOf(diff - 1);
                            } else {
                                release_after = "개봉 " + String.valueOf(diff + 1) + "일차";
                            }

                        } catch (ParseException e) {

                        }
                        String genre = !movie.getMovieGenre().equals("") ?  movie.getMovieGenre() : "정보없음";
                        String running_time = movie.getMovieRunningTime() + "분";
                        String rank = movie.getMovieReservationRank() + "위";
                        String reservation_rate = String.valueOf(movie.getMovieReservationRate()) + "%";
                        String total_attendance = "";
                        if (movie.getMovieTotalAttendance() >= 100000) {
                            total_attendance = String.format("%d", movie.getMovieTotalAttendance() / 10000) + "만명";
                        } else if (movie.getMovieTotalAttendance() >= 10000) {
                            total_attendance = String.format("%.1f", movie.getMovieTotalAttendance() / 10000.0) + "만명";
                        } else {
                            total_attendance = String.format("%d", movie.getMovieTotalAttendance()) + "명";
                        }
                        String story = movie.getMovieStory();
                        String director = !movie.getMovieDirector().equals("") ?  movie.getMovieDirector() : "정보없음";
                        String the_casts = !movie.getMovieTheCasts().equals("") ?  movie.getMovieTheCasts() : "정보없음";

                        view.setMovieDetail(background, poster, score, scoreParticipant, age_limit, release_date, genre, running_time, rank, reservation_rate, total_attendance, story, director, the_casts, release_after);
                    }
                });
            }
        };
        mThread.start();
    }

    @Override
    public void getIsWished() {
        pref_wish_list = preferences.getInt(movie.getMovieCode(), -1);
        if (pref_wish_list != -1) {
            is_wish_list = true;
        } else {
            is_wish_list = false;
        }

        if (is_wish_list == true) {
            view.setWishToggleImage(R.drawable.ic_favorite_black_24dp);
        } else {
            view.setWishToggleImage(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public void updateWishList() {
        SharedPreferences.Editor editor = preferences.edit();

        if (is_wish_list == true) {
            view.showSnackBar("내 위시리스트에서 삭제되었습니다.");
            view.setWishToggleImage(R.drawable.ic_favorite_border_black_24dp);

            editor.remove(movie.getMovieCode());
            editor.commit();

            is_wish_list = false;

        } else {
            view.showSnackBar("내 위시리스트에 저장되었습니다.");
            view.setWishToggleImage(R.drawable.ic_favorite_black_24dp);

            editor.putInt(movie.getMovieCode(), preferences.getInt("idx", 0));
            editor.putInt("idx", preferences.getInt("idx", 0) + 1);
            editor.commit();

            is_wish_list = true;
        }
    }
}
