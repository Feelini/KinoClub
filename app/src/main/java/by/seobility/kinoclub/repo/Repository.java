package by.seobility.kinoclub.repo;

import java.util.concurrent.CompletableFuture;

import by.seobility.kinoclub.repo.models.TopSlider;
import retrofit2.Call;

public interface Repository {
    CompletableFuture<Call<TopSlider>> getTopSlider();
}
