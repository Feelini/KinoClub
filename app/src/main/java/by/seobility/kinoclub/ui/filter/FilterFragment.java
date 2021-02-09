package by.seobility.kinoclub.ui.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChoose;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class FilterFragment extends Fragment {

    @BindView(R.id.filter_close)
    ImageView filterClose;
//    @BindView(R.id.filter_year_start)
//    EditText filterYearStart;
//    @BindView(R.id.filter_year_end)
//    EditText filterYearEnd;
//    @BindView(R.id.filter_year_slider)
//    RangeSlider filterYearSlider;
    @BindView(R.id.filer_list_view)
    RecyclerView filerListView;
//    @BindView(R.id.confirm_filter_btn)
//    FrameLayout confirmFilterBtn;

    private static FilterFragment instance;
    private Unbinder unbinder;
    private FilterViewModel viewModel;
    private OnClickListener onBtnClick;
    private RowForChooseList categoryUserList;
    private RowForChooseList qualityUserList;
    private RowForChooseList genreUserList;
    private String type;
    private CategoryAdapter categoryAdapter;
    private QualitiesAdapter qualitiesAdapter;
    private GenresAdapter genresAdapter;
    private FilterAdapter filterAdapter;
    private FilmsListQuery query = new FilmsListQuery(null, 1, "updated", "desc", null, null, null, null, null);

    public static FilterFragment getInstance(){
        if (instance == null){
            instance = new FilterFragment();
        }
        return instance;
    }

    public static FilterFragment getInstance(RowForChooseList data, String type){
        instance.addData(data, type);
        return instance;
    }

    private FilterFragment(){}

    private void addData(RowForChooseList data, String type){
        switch (type){
            case "category":
                categoryUserList = data;
                break;
            case "quality":
                this.qualityUserList = data;
                break;
            case "genre":
                this.genreUserList = data;
                break;
        }
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
//        viewModel.getFilmsList().observe(
//                getViewLifecycleOwner(), filmsList -> onBtnClick.onFilterConfirm(filmsList)
//        );
        return inflater.inflate(R.layout.filter_fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        showFilter();
//        confirmFilterBtn.setOnClickListener(v -> {
//            categoryAdapter = (CategoryAdapter) categoryList.getAdapter();
//            qualitiesAdapter = (QualitiesAdapter) qualityList.getAdapter();
//            genresAdapter = (GenresAdapter) genresList.getAdapter();
//
//            String categoryId = getIdsString(categoryAdapter.getCategories());
//            String qualityId = getIdsString(qualitiesAdapter.getQualities());
//            String genreId = getIdsString(genresAdapter.getGenres());
//
//            query.setCat(categoryId);
//            query.setQuality(qualityId);
//            query.setGenre(genreId);
//
//            onBtnClick.onFilterConfirm(query);

//            viewModel.fetchFilmsList(query);
//        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showFilter(){
        ButtonRowType categoryBtn = new ButtonRowType("category");
        ButtonRowType qualityBtn = new ButtonRowType("quality");
        ButtonRowType genreBtn = new ButtonRowType("genre");
        List<RowType> filterList = new ArrayList<>();
        filterList.add(categoryBtn);
        if (categoryUserList != null ){
            for (RowForChoose row: categoryUserList.getData()){
                filterList.add(new ListRowType(row.getName()));
            }
        }
        filterList.add(qualityBtn);
        if (qualityUserList != null ){
            for (RowForChoose row: qualityUserList.getData()){
                filterList.add(new ListRowType(row.getName()));
            }
        }
        filterList.add(genreBtn);
        if (genreUserList != null ){
            for (RowForChoose row: genreUserList.getData()){
                filterList.add(new ListRowType(row.getName()));
            }
        }
        filterAdapter = new FilterAdapter(filterList, getContext(), viewModel, getViewLifecycleOwner(), onBtnClick);
        filerListView.setAdapter(filterAdapter);
        filerListView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
