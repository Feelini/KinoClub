package by.seobility.kinoclub.repo;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import retrofit2.Call;

public interface Repository {
    CompletableFuture<Call<FilmsList>> getTopSlider();
    CompletableFuture<Call<FilmsList>> getSeriesUpdate();
    CompletableFuture<Call<FilmsList>> getSimilarFilms(SimilarFilms id);
}
