package by.seobility.kinoclub.repo.api;

import by.seobility.kinoclub.repo.models.FilmsList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONPlaceHolderApi {
    @GET("top-slider")
    Call<FilmsList> getTopSlider();
    @GET("series-update")
    Call<FilmsList> getSeriesUpdate();
}
