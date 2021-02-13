package by.seobility.kinoclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.ui.filmviewer.FilmViewerFragment;
import by.seobility.kinoclub.ui.filter.ChooseListFragment;
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

    private void addMainFragment(FilmsListQuery query) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.getInstance(this, query))
                .commit();
    }

    private void addFilmViewerFragment(Film film) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FilmViewerFragment.getInstance(film))
                .addToBackStack(null)
                .commit();
    }

    private void addFilterFragment(RowForChooseList data, String type) {
        if (data != null && type != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FilterFragment.getInstance(data, type))
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FilterFragment.getInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void addChooseListFragment(RowForChooseList rowForChooseList, String type) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ChooseListFragment.getInstance(rowForChooseList, type))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFilmClick(Film film) {
        addFilmViewerFragment(film);
    }

    @Override
    public void onFilterClick() {
        addFilterFragment(null, null);
    }

    @Override
    public void onCategoriesClick(RowForChooseList rowForChooseList) {
        addChooseListFragment(rowForChooseList, "category");
    }

    @Override
    public void onQualitiesClick(RowForChooseList rowForChooseList) {
        addChooseListFragment(rowForChooseList, "quality");
    }

    @Override
    public void onGenresClick(RowForChooseList rowForChooseList) {
        addChooseListFragment(rowForChooseList, "genre");
    }

    @Override
    public void onCountriesClick(RowForChooseList rowForChooseList) {
        addChooseListFragment(rowForChooseList, "country");
    }

    @Override
    public void onCategoryAdd(RowForChooseList categories) {
        addFilterFragment(categories, "category");
    }

    @Override
    public void onQualityAdd(RowForChooseList qualities) {
        addFilterFragment(qualities, "quality");
    }

    @Override
    public void onGenreAdd(RowForChooseList genres) {
        addFilterFragment(genres, "genre");
    }

    @Override
    public void onCountriesAdd(RowForChooseList countries) {
        addFilterFragment(countries, "country");
    }

    @Override
    public void onFilterConfirm(FilmsListQuery query) {
        addMainFragment(query);
    }
}