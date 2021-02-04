package by.seobility.kinoclub.utils;

import by.seobility.kinoclub.repo.models.RowForChoose;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.Film;

public interface OnClickListener {
    void onFilmClick(Film film);
    void onFilterClick();
    void onCategoriesClick(RowForChooseList rowForChooseList);
    void onQualitiesClick(RowForChooseList rowForChooseList);
    void onCategoryAdd(RowForChooseList categories);
    void onQualityAdd(RowForChooseList qualities);
}
