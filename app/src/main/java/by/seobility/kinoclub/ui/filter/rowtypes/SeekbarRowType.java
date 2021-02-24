package by.seobility.kinoclub.ui.filter.rowtypes;

import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.slider.RangeSlider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        switch (type) {
            case "min":
                return (v, actionId, event) -> {
                    float from = seekbar.getValueFrom();
                    float to = seekbar.getValueTo();
                    float current = Float.parseFloat(year.getText().toString());
                    List<Float> newValues = new ArrayList<>(Arrays.asList(from, to));
                    if (current > to) {
                        newValues.set(0, to);
                    } else if (current > from && current < to) {
                        newValues.set(0, current);
                    }
                    seekbar.setValues(newValues);
                    String newFrom = newValues.get(0).toString();
                    newFrom = newFrom.substring(0, newFrom.length() - 2);
                    year.setText(newFrom);
                    year.clearFocus();
                    return false;
                };
            case "max":
                return (v, actionId, event) -> {
                    float from = seekbar.getValueFrom();
                    float to = seekbar.getValueTo();
                    float current = Float.parseFloat(year.getText().toString());
                    List<Float> newValues = new ArrayList<>(Arrays.asList(from, to));
                    if (current < from){
                        newValues.set(1, from);
                    } else if (current > from && current < to){
                        newValues.set(1, current);
                    }
                    seekbar.setValues(newValues);
                    String newTo = newValues.get(1).toString();
                    newTo = newTo.substring(0, newTo.length() - 2);
                    year.setText(newTo);
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
