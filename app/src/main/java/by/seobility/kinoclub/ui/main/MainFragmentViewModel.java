package by.seobility.kinoclub.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.seobility.kinoclub.repo.Repository;
import by.seobility.kinoclub.repo.models.FilmsList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<FilmsList> topSliderLiveData = new MutableLiveData<>();
    private MutableLiveData<FilmsList> seriesUpdateLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchTopSlider() {
        repository.getTopSlider()
                .thenAccept(topSliderCall -> {
                    topSliderCall.enqueue(new Callback<FilmsList>() {
                        @Override
                        public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                            topSliderLiveData.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<FilmsList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<FilmsList> getTopSlider() {
        return topSliderLiveData;
    }

    public void fetchSeriesUpdate() {
        repository.getSeriesUpdate()
                .thenAccept(filmsListCall -> {
                    filmsListCall.enqueue(new Callback<FilmsList>() {
                        @Override
                        public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                            seriesUpdateLiveData.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<FilmsList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<FilmsList> getSeriesUpdate() {
        return seriesUpdateLiveData;
    }
}