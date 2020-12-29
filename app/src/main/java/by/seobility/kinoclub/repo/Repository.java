package by.seobility.kinoclub.repo;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.models.FilmsList;
import retrofit2.Call;

public interface Repository {
    CompletableFuture<Call<FilmsList>> getTopSlider();
    CompletableFuture<Call<FilmsList>> getSeriesUpdate();
}
