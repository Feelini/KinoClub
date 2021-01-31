package by.seobility.kinoclub.ui.filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.RangeSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.ui.main.SeriesUpdateAdapter;

public class FilterFragment extends Fragment {

    @BindView(R.id.filter_category)
    Button category;
    @BindView(R.id.filter_quality)
    Button quality;
    @BindView(R.id.filter_genre)
    Button genre;
    @BindView(R.id.filter_country)
    Button country;
    @BindView(R.id.filter_close)
    ImageView filterClose;
    @BindView(R.id.filter_year_start)
    EditText filterYearStart;
    @BindView(R.id.filter_year_end)
    EditText filterYearEnd;
    @BindView(R.id.filter_year_slider)
    RangeSlider filterYearSlider;

    private static FilterFragment instance;
    private Unbinder unbinder;

    public static FilterFragment getInstance(){
        if (instance == null){
            instance = new FilterFragment();
        }
        return instance;
    }

    private FilterFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
