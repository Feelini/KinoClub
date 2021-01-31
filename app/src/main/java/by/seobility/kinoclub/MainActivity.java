package by.seobility.kinoclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.ui.filmviewer.FilmViewerFragment;
import by.seobility.kinoclub.ui.filter.FilterFragment;
import by.seobility.kinoclub.ui.main.MainFragment;
import by.seobility.kinoclub.utils.OnClickListener;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            addMainFragment();
        }
    }

    private void addMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.getInstance(this))
                .commit();
    }

    private void addFilmViewerFragment(Film film) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FilmViewerFragment.getInstance(film))
                .addToBackStack(null)
                .commit();
    }

    private void addFilterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FilterFragment.getInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFilmClick(Film film) {
        addFilmViewerFragment(film);
    }

    @Override
    public void onFilterClick() {
        addFilterFragment();
    }
}