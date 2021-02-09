package by.seobility.kinoclub.ui.filter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import by.seobility.kinoclub.repo.Repository;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<RowForChooseList> categories = new MutableLiveData<>();
    private MutableLiveData<RowForChooseList> qualities = new MutableLiveData<>();
    private MutableLiveData<RowForChooseList> genres = new MutableLiveData<>();
    private MutableLiveData<FilmsList> filmsList = new MutableLiveData<>();

    public FilterViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchCategories() {
        repository.getCategories()
                .thenAccept(categoriesListCall -> {
                    categoriesListCall.enqueue(new Callback<RowForChooseList>() {
                        @Override
                        public void onResponse(Call<RowForChooseList> call, Response<RowForChooseList> response) {
                            categories.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<RowForChooseList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<RowForChooseList> getCategories() {
        return categories;
    }

    public void fetchQualities() {
        repository.getQualities()
                .thenAccept(qualitiesListCall -> {
                    qualitiesListCall.enqueue(new Callback<RowForChooseList>() {
                        @Override
                        public void onResponse(Call<RowForChooseList> call, Response<RowForChooseList> response) {
                            qualities.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<RowForChooseList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<RowForChooseList> getQualities() {
        return qualities;
    }

    public void fetchGenres() {
        repository.getGenres()
                .thenAccept(genresListCall -> {
                    genresListCall.enqueue(new Callback<RowForChooseList>() {
                        @Override
                        public void onResponse(Call<RowForChooseList> call, Response<RowForChooseList> response) {
                            genres.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<RowForChooseList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<RowForChooseList> getGenres() {
        return genres;
    }

    public void fetchFilmsList(FilmsListQuery query) {
        repository.getFilmsList(query)
                .thenAccept(filmsListCall -> {
                    filmsListCall.enqueue(new Callback<FilmsList>() {
                        @Override
                        public void onResponse(Call<FilmsList> call, Response<FilmsList> response) {
                            filmsList.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<FilmsList> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                });
    }

    public LiveData<FilmsList> getFilmsList() {
        return filmsList;
    }
}
