package kr.ry4nkim.movierank;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.ry4nkim.movierank.Presenter.MovieDetailPresenterImpl;
import kr.ry4nkim.movierank.View.MovieDetailView;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView, View.OnClickListener {
    private MovieDetailPresenterImpl presenter;

    Toolbar toolbar;
    CollapsingToolbarLayout toolbarLayout;

    private ImageView movie_poster;
    private TextView movie_score;
    private TextView movie_score_participant;
    private TextView movie_age_limit;
    private TextView movie_release_date;
    private TextView movie_genre;
    private TextView movie_running_time;
    private TextView movie_rank;
    private TextView movie_reservation_rate;
    private TextView movie_total_attendance;
    private TextView movie_story;
    private TextView movie_director;
    private TextView movie_the_casts;
    private TextView movie_release_after;

    private FloatingActionButton wish_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        movie_poster = findViewById(R.id.movie_poster);
        movie_score = findViewById(R.id.movie_score);
        movie_score_participant = findViewById(R.id.movie_score_participant);
        movie_age_limit = findViewById(R.id.movie_age_limit);
        movie_release_date = findViewById(R.id.movie_release_date);
        movie_genre = findViewById(R.id.movie_genre);
        movie_running_time = findViewById(R.id.movie_running_time);
        movie_rank = findViewById(R.id.movie_rank);
        movie_reservation_rate = findViewById(R.id.movie_reservation_rate);
        movie_total_attendance = findViewById(R.id.movie_total_attendance);
        movie_story = findViewById(R.id.movie_story);
        movie_director = findViewById(R.id.movie_director);
        movie_the_casts = findViewById(R.id.movie_the_casts);
        movie_release_after = findViewById(R.id.movie_release_after);

        wish_list = (FloatingActionButton) findViewById(R.id.wish_list);
        wish_list.setOnClickListener(this);

        presenter = new MovieDetailPresenterImpl(this, getIntent().getSerializableExtra("movie"), PreferenceManager.getDefaultSharedPreferences(this));
        presenter.getMovieTitle();
        presenter.getMovieDetail();
        presenter.getIsWished();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_detail, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.about:
                showSnackBar(getString(R.string.about_message));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        presenter.updateWishList();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void setMovieDetail(Drawable background, Drawable poster, String score, String score_participant, String age_limit,
                               String release_date, String genre, String running_time, String rank, String reservation_rate,
                               String total_attendance, String story, String director, String the_casts, String release_after) {

        toolbarLayout.setBackground(background);
        toolbarLayout.getBackground().setAlpha(100);
        movie_poster.setImageDrawable(poster);
        movie_score.setText(score);
        movie_score_participant.setText(score_participant);
        movie_age_limit.setText(age_limit);
        movie_release_date.setText(release_date);
        movie_genre.setText(genre);
        movie_running_time.setText(running_time);
        movie_rank.setText(rank);
        movie_reservation_rate.setText(reservation_rate);
        movie_total_attendance.setText(total_attendance);
        movie_story.setText(story);
        movie_director.setText(director);
        movie_the_casts.setText(the_casts);
        movie_release_after.setText(release_after);
    }

    @Override
    public void setWishToggleImage(int imageResId) {
        wish_list.setImageResource(imageResId);
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(toolbarLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
