package by.seobility.kinoclub.repo.api;

import by.seobility.kinoclub.repo.models.TopSlider;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {
    @GET("top-slider")
    Call<TopSlider> getTopSlider();
}
