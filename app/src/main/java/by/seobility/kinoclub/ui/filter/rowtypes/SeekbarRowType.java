package by.seobility.kinoclub.ui.filter.rowtypes;

import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

public class SeekbarRowType implements RowType {

    private int yearMin;
    private int yearMax;

    public SeekbarRowType(int yearMin, int yearMax) {
        this.yearMin = yearMin;
        this.yearMax = yearMax;
    }

    public int getYearMin() {
        return yearMin;
    }

    public int getYearMax() {
        return yearMax;
    }

    public TextView.OnEditorActionListener getOnEditorActionListener(String type, RangeSlider seekbar, EditText year) {
        float from = seekbar.getValueFrom();
        float to = seekbar.getValueTo();
        float current = Float.parseFloat(year.getText().toString());
        List<Float> oldValues = seekbar.getValues();
        List<Float> newValues = new ArrayList<>();
        switch (type) {
            case "min":
                return (v, actionId, event) -> {
                    if (current < from) {
                        newValues.add(from);
                        newValues.add(oldValues.get(1));
                    } else if (current > oldValues.get(1)) {
                        newValues.add(oldValues.get(1));
                        newValues.add(oldValues.get(1));
                    } else {
                        newValues.add(current);
                        newValues.add(oldValues.get(1));
                    }
                    seekbar.setValues(newValues);
                    year.clearFocus();
                    return false;
                };
            case "max":
                return (v, actionId, event) -> {
                    if (current < oldValues.get(0)){
                        newValues.add(oldValues.get(0));
                        newValues.add(oldValues.get(0));
                    } else if (current > to){
                        newValues.add(oldValues.get(0));
                        newValues.add(to);
                    } else {
                        newValues.add(oldValues.get(0));
                        newValues.add(current);
                    }
                    seekbar.setValues(newValues);
                    year.clearFocus();
                    return false;
                };
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType() {
        return RowType.SEEKBAR_ROW_TYPE;
    }
}
