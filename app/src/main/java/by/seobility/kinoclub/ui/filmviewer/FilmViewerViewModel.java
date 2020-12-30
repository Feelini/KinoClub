package by.seobility.kinoclub.ui.filmviewer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import by.seobility.kinoclub.repo.Repository;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmViewerViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<FilmsList> similarFilmsLiveData = new MutableLiveData<>();

    public FilmViewerViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchSimilarFilms(SimilarFilms id) {
        repository.getSimilarFilms(id)
                .thenAccept(filmsListCall -> {
                    filmsListCall.enqueue(new Callback<FilmsList>() {
                        @Override
                        public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                            similarFilmsLiveData.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<FilmsList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<FilmsList> getSimilarFilms() {
        return similarFilmsLiveData;
    }
}
