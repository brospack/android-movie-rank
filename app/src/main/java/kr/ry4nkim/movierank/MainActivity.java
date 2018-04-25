package kr.ry4nkim.movierank;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ry4nkim.movierank.Adapter.RecyclerAdapter;
import kr.ry4nkim.movierank.Model.Movie;
import kr.ry4nkim.movierank.Presenter.MainPresenterImpl;
import kr.ry4nkim.movierank.View.MainView;

public class MainActivity extends AppCompatActivity implements MainView, BottomNavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    public static final int BOX_OFFICE = 0;
    public static final int MY_WISH_LIST = 1;

    private MainPresenterImpl presenter;
    private BottomNavigationView navigation;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ProgressDialog progressDialog;
    private Toast toast;

    private int menu = BOX_OFFICE;
    private int page = 0;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (menu == BOX_OFFICE && !recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        isLoading = true;
                        presenter.getMovieList(page++);
                    }
                }
            }
        });

        recyclerAdapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("로딩 중 ...");
        progressDialog.setCancelable(false);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        presenter = new MainPresenterImpl(this, PreferenceManager.getDefaultSharedPreferences(this));
        presenter.getMovieList(page++);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                showSnackBar(getString(R.string.about_message));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_boxoffice:
                page = 0;
                recyclerAdapter.clear();
                presenter.getMovieList(page++);
                swipeRefreshLayout.setRefreshing(false);
                scrollToPosition(0);
                setMenu(BOX_OFFICE);
                return true;
            case R.id.navigation_wishlist:
                recyclerAdapter.clear();
                presenter.getWishList();
                scrollToPosition(0);
                setMenu(MY_WISH_LIST);
                return true;
        }
        return false;
    }

    public void onRestart() {
        super.onRestart();
        if (menu == MY_WISH_LIST) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        recyclerAdapter.clear();
        if (menu == BOX_OFFICE) {
            presenter.getMovieList(page++);
        } else if (menu == MY_WISH_LIST) {
            presenter.getWishList();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setMovieList(ArrayList<Movie> movieList) {
        for (Movie movie : movieList)
            recyclerAdapter.add(movie);
        recyclerAdapter.refresh();
        isLoading = false;
    }

    @Override
    public void setMenu(int position) {
        this.menu = position;
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
    }

}