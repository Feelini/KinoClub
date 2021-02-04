package by.seobility.kinoclub.repo;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import retrofit2.Call;

public interface Repository {
    CompletableFuture<Call<FilmsList>> getTopSlider();
    CompletableFuture<Call<FilmsList>> getSeriesUpdate();
    CompletableFuture<Call<FilmsList>> getSimilarFilms(SimilarFilms id);
    CompletableFuture<Call<FilmsList>> getFilmsList(FilmsListQuery query);
    CompletableFuture<Call<RowForChooseList>> getCategories();
    CompletableFuture<Call<RowForChooseList>> getQualities();
}
