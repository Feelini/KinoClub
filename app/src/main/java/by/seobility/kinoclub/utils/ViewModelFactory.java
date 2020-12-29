package by.seobility.kinoclub.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import by.seobility.kinoclub.repo.RepositoryImpl;
import by.seobility.kinoclub.ui.filmviewer.FilmViewerViewModel;
import by.seobility.kinoclub.ui.main.MainFragmentViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory{

    private Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel(application, new RepositoryImpl());
        }
        if (modelClass.isAssignableFrom(FilmViewerViewModel.class)) {
            return (T) new FilmViewerViewModel(application, new RepositoryImpl());
        }
        return null;
    }
}
