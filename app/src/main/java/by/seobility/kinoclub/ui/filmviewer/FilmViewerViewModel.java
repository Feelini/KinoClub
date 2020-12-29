package by.seobility.kinoclub.ui.filmviewer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import by.seobility.kinoclub.repo.Repository;

public class FilmViewerViewModel extends AndroidViewModel {

    private Repository repository;

    public FilmViewerViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }
}
