package by.seobility.kinoclub.ui.filter.rowtypes;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import by.seobility.kinoclub.R;
import by.seobility.kinoclub.ui.filter.FilterViewModel;
import by.seobility.kinoclub.utils.OnClickListener;

public class ButtonRowType implements RowType {
    private String type;

    public ButtonRowType(String type){
        this.type = type;
    }

    public int getTextId(){
        switch (type){
            case "category":
                return R.string.choose_category;
            case "quality":
                return R.string.choose_quality;
            case "genre":
                return R.string.choose_genre;
            case "country":
                return R.string.choose_country;
            default:
                return 0;
        }
    }

    public View.OnClickListener getOnClickListener(FilterViewModel viewModel, LifecycleOwner lifecycleOwner, OnClickListener onBtnClick){
        switch (type){
            case "category":
                return v -> {
                    viewModel.getCategories().observe(lifecycleOwner,
                            onBtnClick::onCategoriesClick);
                    viewModel.fetchCategories();
                };
            case "quality":
                return v -> {
                    viewModel.getQualities().observe(lifecycleOwner,
                            onBtnClick::onQualitiesClick);
                    viewModel.fetchQualities();
                };
            case "genre":
                return v -> {
                    viewModel.getGenres().observe(lifecycleOwner,
                            onBtnClick::onGenresClick);
                    viewModel.fetchGenres();
                };
            case "country":
                return v -> {
                    viewModel.getCountries().observe(lifecycleOwner,
                            onBtnClick::onCountriesClick);
                    viewModel.fetchCountries();
                };
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType() {
        return RowType.BUTTON_ROW_TYPE;
    }
}
