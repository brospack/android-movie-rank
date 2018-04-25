package kr.ry4nkim.movierank.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Movie implements Serializable {
    private String movie_code;
    private String movie_title;
    private double movie_score;
    private int movie_score_participant;
    private String movie_age_limit;
    transient private Bitmap movie_poster;
    private String movie_poster_url;
    private String movie_release_date;
    private String movie_genre;
    private int movie_running_time;
    private int movie_reservation_rank;
    private double movie_reservation_rate;
    private int movie_total_attendance;
    private String movie_story;
    private String movie_director;
    private String movie_the_casts;

    public Movie() {

    }

    public Movie(String movie_code) {
        this.movie_code = movie_code;
    }

    public void setMovie(String movie_title, double movie_score, int movie_score_participant, String movie_age_limit, Bitmap movie_poster, String movie_poster_url,
                         String movie_release_date, String movie_genre, int movie_running_time, int movie_reservation_rank,
                         double movie_reservation_rate, int movie_total_attendance, String movie_story, String movie_director, String movie_the_casts) {

        setMovieTitle(movie_title);
        setMovieScore(movie_score);
        setMovieScoreParticipant(movie_score_participant);
        setMovieAgeLimit(movie_age_limit);
        setMoviePoster(movie_poster);
        setMoviePosterUrl(movie_poster_url);
        setMovieReleaseDate(movie_release_date);
        setMovieGenre(movie_genre);
        setMovieRunningTime(movie_running_time);
        setMovieReservationRank(movie_reservation_rank);
        setMovieReservationRate(movie_reservation_rate);
        setMovieTotalAttendance(movie_total_attendance);
        setMovieStory(movie_story);
        setMovieDirector(movie_director);
        setMovieTheCasts(movie_the_casts);
    }

    public String getMovieCode() {
        return movie_code;
    }

    public void setMovieCode(String movie_code) {
        this.movie_code = movie_code;
    }

    public String getMovieTitle() {
        return movie_title;
    }

    public void setMovieTitle(String movie_title) {
        this.movie_title = movie_title;
    }

    public double getMovieScore() {
        return movie_score;
    }

    public void setMovieScore(double movie_score) {
        this.movie_score = movie_score;
    }

    public String getMovieAgeLimit() {
        return movie_age_limit;
    }

    public void setMovieAgeLimit(String movie_age_limit) {
        this.movie_age_limit = movie_age_limit;
    }

    public Bitmap getMoviePoster() {
        return movie_poster;
    }

    public void setMoviePoster(Bitmap movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMoviePosterUrl() {
        return movie_poster_url;
    }

    public void setMoviePosterUrl(String movie_poster_url) {
        this.movie_poster_url = movie_poster_url;
    }

    public String getMovieReleaseDate() {
        return movie_release_date;
    }

    public void setMovieReleaseDate(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public String getMovieGenre() {
        return movie_genre;
    }

    public void setMovieGenre(String movie_genre) {
        this.movie_genre = movie_genre;
    }

    public int getMovieRunningTime() {
        return movie_running_time;
    }

    public void setMovieRunningTime(int movie_running_time) {
        this.movie_running_time = movie_running_time;
    }

    public int getMovieReservationRank() {
        return movie_reservation_rank;
    }

    public void setMovieReservationRank(int movie_reservation_rank) {
        this.movie_reservation_rank = movie_reservation_rank;
    }

    public double getMovieReservationRate() {
        return movie_reservation_rate;
    }

    public void setMovieReservationRate(double movie_reservation_rate) {
        this.movie_reservation_rate = movie_reservation_rate;
    }

    public int getMovieTotalAttendance() {
        return movie_total_attendance;
    }

    public void setMovieTotalAttendance(int movie_total_attendance) {
        this.movie_total_attendance = movie_total_attendance;
    }

    public String getMovieStory() {
        return movie_story;
    }

    public void setMovieStory(String movie_story) {
        this.movie_story = movie_story;
    }

    public String getMovieDirector() {
        return movie_director;
    }

    public void setMovieDirector(String movie_director) {
        this.movie_director = movie_director;
    }

    public String getMovieTheCasts() {
        return movie_the_casts;
    }

    public void setMovieTheCasts(String movie_the_casts) {
        this.movie_the_casts = movie_the_casts;
    }

    public int getMovieScoreParticipant() {
        return movie_score_participant;
    }

    public void setMovieScoreParticipant(int movie_score_participant) {
        this.movie_score_participant = movie_score_participant;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movie_code='" + movie_code + '\'' +
                ", movie_title='" + movie_title + '\'' +
                ", movie_score=" + movie_score +
                ", movie_score_participant=" + movie_score_participant +
                ", movie_age_limit='" + movie_age_limit + '\'' +
                ", movie_poster_url='" + movie_poster_url + '\'' +
                ", movie_release_date='" + movie_release_date + '\'' +
                ", movie_genre='" + movie_genre + '\'' +
                ", movie_running_time=" + movie_running_time +
                ", movie_reservation_rank=" + movie_reservation_rank +
                ", movie_reservation_rate=" + movie_reservation_rate +
                ", movie_total_attendance=" + movie_total_attendance +
                ", movie_story='" + movie_story + '\'' +
                ", movie_director='" + movie_director + '\'' +
                ", movie_the_casts='" + movie_the_casts + '\'' +
                '}';
    }

}
