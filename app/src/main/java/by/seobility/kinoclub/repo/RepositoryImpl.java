package by.seobility.kinoclub.repo;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.api.NetworkService;
import by.seobility.kinoclub.repo.models.FilmsList;
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
}
