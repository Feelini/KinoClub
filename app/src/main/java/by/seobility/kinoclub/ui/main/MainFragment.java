package by.seobility.kinoclub.ui.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.FilmsListQuery;
import by.seobility.kinoclub.utils.FragmentsParent;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class MainFragment extends FragmentsParent {

    @BindView(R.id.top_slider)
    RecyclerView viewTopSlider;
    @BindView(R.id.series_update_list)
    RecyclerView viewSeriesUpdate;
    @BindView(R.id.films_list)
    RecyclerView filmsListView;
    @BindView(R.id.series_update)
    ConstraintLayout seriesUpdate;
    @BindView(R.id.series_update_icon)
    ImageView seriesUpdateIcon;
    @BindView(R.id.series_update_expandable)
    ExpandableLayout seriesUpdateExpandable;
    @BindView(R.id.filter_frame_layout)
    FrameLayout filterFrameLayout;
    @BindView(R.id.order_by_spinner)
    Spinner orderBySpinner;
    @BindView(R.id.order_desc)
    ImageView orderDesc;
    @BindView(R.id.order_asc)
    ImageView orderAsc;
    @BindView(R.id.order)
    FrameLayout orderBy;

    private static MainFragment instance;
    private MainFragmentViewModel viewModel;
    private Unbinder unbinder;
    private TopSliderAdapter topSliderAdapter;
    private SeriesUpdateAdapter seriesUpdateAdapter;
    private FilmsListAdapter filmsListAdapter;
    private OnClickListener onClickListener;
    private static FilmsListQuery query = new FilmsListQuery(null, 1, "updated", "desc", null, null, null, null, null);

    public static MainFragment getInstance(OnClickListener onClickListener) {
        if (instance == null) {
            instance = new MainFragment(onClickListener);
        }
        return instance;
    }

    public static MainFragment getInstance(OnClickListener onClickListener, FilmsListQuery newQuery) {
        if (instance == null) {
            instance = new MainFragment(onClickListener);
        }
        query = newQuery;
        return instance;
    }

    private MainFragment(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getTopSlider().observe(
                getViewLifecycleOwner(),
                this::showTopSlider
        );
        viewModel.getSeriesUpdate().observe(
                getViewLifecycleOwner(),
                this::showSeriesUpdate
        );
        viewModel.getFilmsList().observe(
                getViewLifecycleOwner(),
                this::showFilmsList
        );
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        viewModel.fetchTopSlider();
        viewModel.fetchSeriesUpdate();
//        viewModel.fetchFilmsList(query);
        seriesUpdate.setOnClickListener(v -> {
            seriesUpdateExpandable.toggle();
            SeriesUpdateAdapter adapter = (SeriesUpdateAdapter) viewSeriesUpdate.getAdapter();
            Boolean isExpanded = adapter.isExpanded();
            seriesUpdateIcon.setImageResource(isExpanded ? R.drawable.add : R.drawable.remove);
            adapter.setExpanded(!isExpanded);
        });
        orderBy.setOnClickListener(v -> {
            boolean orderByDesc = orderDesc.getVisibility() == View.VISIBLE;
            if (orderByDesc){
                orderDesc.setVisibility(View.GONE);
                orderAsc.setVisibility(View.VISIBLE);
                query.setOrder("asc");
            } else {
                orderDesc.setVisibility(View.VISIBLE);
                orderAsc.setVisibility(View.GONE);
                query.setOrder("desc");
            }
            viewModel.fetchFilmsList(query);
        });
        filterFrameLayout.setOnClickListener(v -> onClickListener.onFilterClick());
        showOrderSpinner();
    }


    private void showOrderSpinner() {
        String[] order_by = getContext().getResources().getStringArray(R.array.order_by);
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(order_by));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_order_by, arrayList);
        orderBySpinner.setAdapter(adapter);

        orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId) {
                switch (selectedItemPosition){
                    case 0:
                        query.setOrderby("updated");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 1:
                        query.setOrderby("year");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 2:
                        query.setOrderby("rating");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 3:
                        query.setOrderby("view");
                        viewModel.fetchFilmsList(query);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showTopSlider(FilmsList filmsList) {
        topSliderAdapter = new TopSliderAdapter(filmsList.getData(), (OnClickListener) getContext(), getBaseUrl());
        viewTopSlider.setAdapter(topSliderAdapter);
        viewTopSlider.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    private void showSeriesUpdate(FilmsList filmsList) {
        seriesUpdateAdapter = SeriesUpdateAdapter.getInstance(filmsList.getData(), (OnClickListener) getContext(), getBaseUrl());
        seriesUpdateIcon.setImageResource(seriesUpdateAdapter.isExpanded() ? R.drawable.remove : R.drawable.add);
        viewSeriesUpdate.setAdapter(seriesUpdateAdapter);
        viewSeriesUpdate.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewSeriesUpdate.setNestedScrollingEnabled(false);
        viewSeriesUpdate.addItemDecoration(new SpacesItemDecoration(0, 2));
    }

    private void showFilmsList(FilmsList filmsList) {
        filmsListAdapter = new FilmsListAdapter(getContext(), filmsList.getData(), (OnClickListener) getContext(), getBaseUrl());
        filmsListView.setAdapter(filmsListAdapter);
        filmsListView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        filmsListView.setNestedScrollingEnabled(false);
    }
}