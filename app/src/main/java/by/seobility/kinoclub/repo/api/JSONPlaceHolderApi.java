package by.seobility.kinoclub.repo.api;

import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import by.seobility.kinoclub.repo.models.YearsList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @GET("top-slider")
    Call<FilmsList> getTopSlider();
    @GET("series-update")
    Call<FilmsList> getSeriesUpdate();
    @POST("similar")
    Call<FilmsList> getSimilarFilms(@Body SimilarFilms id);
    @POST("get-films")
    Call<FilmsList> getFilmsList(@Body FilmsListQuery query);
    @GET("cats")
    Call<RowForChooseList> getCategories();
    @GET("qualities")
    Call<RowForChooseList> getQualities();
    @GET("genres")
    Call<RowForChooseList> getGenres();
    @GET("countries")
    Call<RowForChooseList> getCountries();
    @GET("get-years")
    Call<YearsList> getYears();
}
