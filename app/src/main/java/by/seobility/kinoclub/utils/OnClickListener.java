package by.seobility.kinoclub.utils;

import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.Film;

public interface OnClickListener {
    void onFilmClick(Film film);
    void onFilterClick();
    void onCategoriesClick(RowForChooseList rowForChooseList);
    void onQualitiesClick(RowForChooseList rowForChooseList);
    void onGenresClick(RowForChooseList rowForChooseList);
    void onCountriesClick(RowForChooseList rowForChooseList);
    void onCategoryAdd(RowForChooseList categories);
    void onQualityAdd(RowForChooseList qualities);
    void onGenreAdd(RowForChooseList genres);
    void onCountriesAdd(RowForChooseList countries);
    void onFilterConfirm(FilmsListQuery query);
}
