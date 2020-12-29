package by.seobility.kinoclub.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.seobility.kinoclub.repo.Repository;
import by.seobility.kinoclub.repo.models.TopSlider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentViewModel extends AndroidViewModel {
    private Repository repository;
    private MutableLiveData<TopSlider> topSliderLiveData = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchTopSlider() {
        repository.getTopSlider()
                .thenAccept(topSliderCall -> {
                    topSliderCall.enqueue(new Callback<TopSlider>() {
                        @Override
                        public void onResponse(Call<TopSlider> call, Response<TopSlider> response) {
                            topSliderLiveData.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<TopSlider> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<TopSlider> getTopSlider() {
        return topSliderLiveData;
    }
}