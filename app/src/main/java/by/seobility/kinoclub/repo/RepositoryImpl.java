package by.seobility.kinoclub.repo;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.api.NetworkService;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import retrofit2.Call;

public class RepositoryImpl implements Repository{
    @Override
    public CompletableFuture<Call<FilmsList>> getTopSlider() {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getTopSlider());
    }

    @Override
    public CompletableFuture<Call<FilmsList>> getSeriesUpdate() {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getSeriesUpdate());
    }

    @Override
    public CompletableFuture<Call<FilmsList>> getSimilarFilms(SimilarFilms id) {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getSimilarFilms(id));
    }

    @Override
    public CompletableFuture<Call<FilmsList>> getFilmsList(FilmsListQuery query) {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getFilmsList(query));
    }

    @Override
    public CompletableFuture<Call<RowForChooseList>> getCategories() {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getCategories());
    }

    @Override
    public CompletableFuture<Call<RowForChooseList>> getQualities() {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getQualities());
    }

    @Override
    public CompletableFuture<Call<RowForChooseList>> getGenres() {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getGenres());
    }
}
