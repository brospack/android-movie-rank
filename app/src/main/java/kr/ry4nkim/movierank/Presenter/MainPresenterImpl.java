package kr.ry4nkim.movierank.Presenter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.ry4nkim.movierank.Model.Movie;
import kr.ry4nkim.movierank.Utils.Utils;
import kr.ry4nkim.movierank.View.MainView;

public class MainPresenterImpl implements MainPresenter {
    private MainView view;
    private SharedPreferences preferences;

    private int movieCount = 0;
    private boolean isLastPage = false;

    public MainPresenterImpl(MainView view, SharedPreferences preferences) {
        this.view = view;
        this.preferences = preferences;
    }

    @Override
    public void getMovieList(int page) {
        if (page == 0) {
            isLastPage = false;
        }
        if (!isLastPage) {
            view.showProgress();
            final ArrayList<Movie> movieList = new ArrayList<Movie>();
            final int startNo = page * 10;
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        Connection.Response movieListResponse = Jsoup.connect("http://www.megabox.co.kr/pages/movie/Movie_List.jsp?menuId=movie-boxoffice&startNo=" + startNo + "&count=10&sort=releaseDate")
                                .method(Connection.Method.GET)
                                .execute();
                        Document movieListDocument = movieListResponse.parse();

                        if (movieListDocument.select("[name^=\"btnInterestingMovie\"]").size() < 10) {
                            isLastPage = true;
                        }

                        for (int i = 0; i < movieListDocument.select("[name^=\"btnInterestingMovie\"]").size(); i++) {
                            Element div = movieListDocument.select("[name^=\"btnInterestingMovie\"]").get(i);
                            String movieCode = div.attr("data-code");
                            movieList.add(new Movie(movieCode));
                            movieCount++;
                        }

                        for (Movie movie : movieList) {
                            getMovieDetail(movie);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setMovieList(movieList);
                            view.scrollToPosition(movieCount - 10);
                            view.hideProgress();
                        }
                    });
                }
            };
            mThread.start();
        } else {
            view.showSnackBar("마지막 페이지입니다.");
        }
    }

    @Override
    public void getWishList() {
        view.showProgress();
        final ArrayList<Movie> wishList = new ArrayList<Movie>();
        HashMap<String, Integer> movieCodeMap = new HashMap<String, Integer>((Map<? extends String, ? extends Integer>) preferences.getAll());

        Iterator it = Utils.sortByValue(movieCodeMap).iterator();

        while (it.hasNext()) {
            String movieCode = (String) it.next();
            if (!movieCode.equals("idx")) {
                wishList.add(new Movie(movieCode));
            }
        }

        Thread mThread = new Thread() {
            @Override
            public void run() {
                for (Movie movie : wishList) {
                    getMovieDetail(movie);
                }
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setMovieList(wishList);
                        view.hideProgress();
                    }
                });
            }
        };
        mThread.start();
    }

    public void getMovieDetail(Movie movie) {
        Connection.Response movieDetailResponse = null;
        Document movieDetailDocument = null;
        try {
            movieDetailResponse = Jsoup.connect("http://www.megabox.co.kr/pages/movie/Movie_Detail.jsp?code=" + movie.getMovieCode())
                    .method(Connection.Method.GET)
                    .execute();
            movieDetailDocument = movieDetailResponse.parse();
        } catch (IOException e) {

        }

        String movieTitle = movieDetailDocument.select("span").first().text();
        double movieScore = 0.0;
        int movieScoreParticipant = 0;
        try {
            movieScore = Double.parseDouble(movieDetailDocument.select("[id^=\"averageScoreDetail\"]").first().text());
            movieScoreParticipant = Integer.parseInt(movieDetailDocument.select("[id^=\"starMemberCount\"]").first().text());
        } catch (NullPointerException e) {

        }
        String movieAgeLimit = movieDetailDocument.select("div h2 i").first().text();
        String moviePosterUrl = movieDetailDocument.select("img[alt=\"" + movieTitle + "\"]").first().attr("src");
        String movieReleaseDate = movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(1).text().substring(5).trim();
        String movieGenre = movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(4).text().substring(4).split("/")[0].trim();
        int movieRunningTime = 0;
        int movieReservationRank = 0;
        try {
            movieRunningTime = Integer.parseInt(movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(4).text().split("/")[1].replaceAll("[^0-9]", ""));
            movieReservationRank = Integer.parseInt(movieDetailDocument.select("p[class=\"right_p\"] strong").first().text());
        } catch (NumberFormatException e) {

        }
        double movieReservationRate = 0.0;
        try {
            movieReservationRate = Double.parseDouble(movieDetailDocument.select("p[class=\"right_p\"] span").first().text().split("%")[0]);
        } catch (NullPointerException e) {

        }
        int movieTotalAttendance = 0;
        try {
            movieTotalAttendance = Integer.parseInt(movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(5).text().split("명")[0].replaceAll("[^0-9]", ""));
        } catch (IndexOutOfBoundsException e) {

        }
        String movieStory = movieDetailDocument.select("div div[class=\"text\"]").get(1).text();
        String movieDirector = movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(2).text().substring(4).trim();
        String movieTheCast = movieDetailDocument.select("ul[class=\"info_wrap\"] li").get(3).text().substring(5).trim();

        URL url = null;
        try {
            url = new URL(moviePosterUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap moviePoster = Utils.loadImage(url);

        movie.setMovie(movieTitle, movieScore, movieScoreParticipant, movieAgeLimit, moviePoster, moviePosterUrl, movieReleaseDate, movieGenre, movieRunningTime, movieReservationRank, movieReservationRate, movieTotalAttendance, movieStory, movieDirector, movieTheCast);
    }

}
