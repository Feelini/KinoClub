package by.seobility.kinoclub.ui.filter;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.RangeSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

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
    @BindView(R.id.category_list)
    RecyclerView categoryList;
    @BindView(R.id.qualities_list)
    RecyclerView qualityList;

    private static FilterFragment instance;
    private Unbinder unbinder;
    private FilterViewModel viewModel;
    private OnClickListener onBtnClick;
    private static RowForChooseList categoryUserList;
    private RowForChooseList qualityUserList;
    private String type;
    private CategoryAdapter categoryAdapter;
    private QualitiesAdapter qualitiesAdapter;

    public static FilterFragment getInstance(){
        if (instance == null){
            instance = new FilterFragment();
        }
        return instance;
    }

    public static FilterFragment getInstance(RowForChooseList data, String type){
        instance = new FilterFragment(data, type);
        return instance;
    }

    private FilterFragment(){}

    private FilterFragment(RowForChooseList data, String type){
        switch (type){
            case "category":
                categoryUserList = data;
                break;
            case "quality":
                this.qualityUserList = data;
                break;
        }
        this.type = type;
    }

    public static void setCategory(RowForChooseList category){
        categoryUserList = category;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickListener) {
            onBtnClick = (OnClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onBtnClick != null){
            onBtnClick = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FilterViewModel.class);
    }

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
        category.setOnClickListener(v -> {
            viewModel.getCategories().observe(getViewLifecycleOwner(),
                    categoriesList -> onBtnClick.onCategoriesClick(categoriesList));
            viewModel.fetchCategories();
        });
        quality.setOnClickListener(v -> {
            viewModel.getQualities().observe(getViewLifecycleOwner(),
                    qualitiesList -> onBtnClick.onQualitiesClick(qualitiesList));
            viewModel.fetchQualities();
        });
        if (categoryUserList != null ){
            showCategory(categoryUserList);
        }
        if (qualityUserList != null ){
            showQualities(qualityUserList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showCategory(RowForChooseList categories){
        categoryAdapter = new CategoryAdapter(categories.getData());
        categoryList.setAdapter(categoryAdapter);
        categoryList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    private void showQualities(RowForChooseList qualities){
        qualitiesAdapter = new QualitiesAdapter(qualities.getData());
        qualityList.setAdapter(qualitiesAdapter);
        qualityList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
