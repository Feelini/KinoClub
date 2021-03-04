package by.seobility.kinoclub.ui.filter.rowtypes;

import android.view.View;

import java.util.List;

import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChoose;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.Years;
import by.seobility.kinoclub.ui.filter.FilterViewModel;
import by.seobility.kinoclub.utils.OnClickListener;

public class SubmitButtonRowType implements RowType{

    private FilmsListQuery query = new FilmsListQuery(null, 1, "updated", "desc", null, null, null, null, null);

    public View.OnClickListener getOnClickListener(List<RowForChoose> categoryUserList, List<RowForChoose> qualityUserList,
                                                   List<RowForChoose> genreUserList, List<RowForChoose> countryUserList, Years years, OnClickListener onBtnClick){
        return v -> {
            String categoryId = (categoryUserList != null) ? getIdsString(categoryUserList) : null;
            String qualityId = (qualityUserList != null) ? getIdsString(qualityUserList) : null;
            String genreId = (genreUserList != null) ? getIdsString(genreUserList) : null;
            String countryId = (countryUserList != null) ? getIdsString(countryUserList) : null;

            query.setCat(categoryId);
            query.setQuality(qualityId);
            query.setGenre(genreId);
            query.setCountry(countryId);
            query.setYear(getYearsString(years));

            onBtnClick.onFilterConfirm(query);
        };
    }

    private String getIdsString(List<RowForChoose> data){
        if (!data.isEmpty()) {
            StringBuilder dataId = new StringBuilder();
            for (RowForChoose item : data) {
                dataId.append(item.getId()).append(",");
            }
            return dataId.deleteCharAt(dataId.length() - 1).toString();
        } else {
            return null;

        }
    }

    private String getYearsString(Years years){
        if (years != null){
            StringBuilder dataYears = new StringBuilder();
            dataYears.append(years.getMin()).append(",").append(years.getMax());
            return dataYears.toString();
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType() {
        return RowType.SUBMIT_BUTTON_ROW_TYPE;
    }
}
