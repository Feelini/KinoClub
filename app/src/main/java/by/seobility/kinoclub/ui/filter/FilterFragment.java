package by.seobility.kinoclub.ui.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.repo.models.RowForChoose;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.repo.models.Years;
import by.seobility.kinoclub.repo.models.YearsList;
import by.seobility.kinoclub.ui.filter.rowtypes.ButtonRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.ListRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.RowType;
import by.seobility.kinoclub.ui.filter.rowtypes.SeekbarRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.SubmitButtonRowType;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class FilterFragment extends Fragment {

    @BindView(R.id.filter_close)
    ImageView filterClose;
    @BindView(R.id.filer_list_view)
    RecyclerView filterListView;


    private static FilterFragment instance;
    private Unbinder unbinder;
    private FilterViewModel viewModel;
    private OnClickListener onBtnClick;
    private RowForChooseList categoryUserList;
    private RowForChooseList qualityUserList;
    private RowForChooseList genreUserList;
    private RowForChooseList countryUserList;
    private Years years;
    private FilmsListQuery query = new FilmsListQuery(null, 1, "updated", "desc", null, null, null, null, null);

    public static FilterFragment getInstance() {
        if (instance == null) {
            instance = new FilterFragment();
        }
        return instance;
    }

    public static FilterFragment getInstance(RowForChooseList data, String type) {
        instance.addData(data, type);
        return instance;
    }

    private FilterFragment() {
    }

    private void addData(RowForChooseList data, String type) {
        switch (type) {
            case "category":
                categoryUserList = data;
                break;
            case "quality":
                this.qualityUserList = data;
                break;
            case "genre":
                this.genreUserList = data;
                break;
            case "country":
                this.countryUserList = data;
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
        if (onBtnClick != null) {
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
        viewModel.getYears().observe(getViewLifecycleOwner(), this::showFilter);
        return inflater.inflate(R.layout.filter_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        viewModel.fetchYears();
        filterClose.setOnClickListener(v -> onBtnClick.onFilterClose());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showFilter(YearsList years) {
        this.years = years.getData();
        ButtonRowType categoryBtn = new ButtonRowType("category");
        ButtonRowType qualityBtn = new ButtonRowType("quality");
        ButtonRowType genreBtn = new ButtonRowType("genre");
        ButtonRowType countryBtn = new ButtonRowType("country");
        List<RowType> filterList = new ArrayList<>();
        filterList.add(categoryBtn);
        if (categoryUserList != null) {
            for (RowForChoose row : categoryUserList.getData()) {
                filterList.add(new ListRowType(row.getId(), row.getName(), "category"));
            }
        }
        filterList.add(qualityBtn);
        if (qualityUserList != null) {
            for (RowForChoose row : qualityUserList.getData()) {
                filterList.add(new ListRowType(row.getId(), row.getName(), "quality"));
            }
        }
        filterList.add(genreBtn);
        if (genreUserList != null) {
            for (RowForChoose row : genreUserList.getData()) {
                filterList.add(new ListRowType(row.getId(), row.getName(), "genre"));
            }
        }
        filterList.add(countryBtn);
        if (countryUserList != null) {
            for (RowForChoose row : countryUserList.getData()) {
                filterList.add(new ListRowType(row.getId(), row.getName(), "country"));
            }
        }
        SeekbarRowType seekbarRowType = new SeekbarRowType(years.getData().getMin(), years.getData().getMax());
        filterList.add(seekbarRowType);
        SubmitButtonRowType submitButtonRowType = new SubmitButtonRowType();
        filterList.add(submitButtonRowType);
        FilterAdapter adapter = new FilterAdapter(filterList, getContext(), viewModel, getViewLifecycleOwner(), onBtnClick, years.getData());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                FilterAdapter filterAdapter = (FilterAdapter) filterListView.getAdapter();
                ListRowType row = (ListRowType) filterAdapter.getDataSet().get(positionStart);
                switch (row.getType()){
                    case "category":
                        for (RowForChoose category: categoryUserList.getData()){
                            if (category.getId() == row.getId()){
                                categoryUserList.getData().remove(category);
                                break;
                            }
                        }
                        break;
                    case "quality":
                        for (RowForChoose quality: qualityUserList.getData()){
                            if (quality.getId() == row.getId()){
                                qualityUserList.getData().remove(quality);
                                break;
                            }
                        }
                        break;
                    case "genre":
                        for (RowForChoose genre: genreUserList.getData()){
                            if (genre.getId() == row.getId()){
                                genreUserList.getData().remove(genre);
                                break;
                            }
                        }
                        break;
                    case "country":
                        for (RowForChoose country: countryUserList.getData()){
                            if (country.getId() == row.getId()){
                                countryUserList.getData().remove(country);
                                break;
                            }
                        }
                        break;
                }
            }
        });
        filterListView.setAdapter(adapter);
        filterListView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

}
