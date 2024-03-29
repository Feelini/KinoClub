package by.seobility.kinoclub.ui.main;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
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
import by.seobility.kinoclub.utils.Preloader;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class MainFragment extends FragmentsParent {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search)
    SearchView search;
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
    @BindView(R.id.parent)
    NestedScrollView parent;

    private static MainFragment instance;
    private MainFragmentViewModel viewModel;
    private Unbinder unbinder;
    private TopSliderAdapter topSliderAdapter;
    private SeriesUpdateAdapter seriesUpdateAdapter;
    private FilmsListAdapter filmsListAdapter;
    private OnClickListener onClickListener;
    private Preloader preloader;
    private boolean addMore = true;
    private static boolean newFilter = false;
    private static String type;
    private static FilmsListQuery query = new FilmsListQuery(null, 1, "updated", "desc", null, null, null, null, null);

    public static MainFragment getInstance() {
        return instance;
    }

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
        newFilter = true;
        query = newQuery;
        return instance;
    }

    private MainFragment(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public MainFragment() {
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
                filmsList -> {
                    addMore = filmsList.getData().size() != 0;
                    if (!addMore) preloader.hidePreloader(); else preloader.showPreloader();
                    showFilmsList(filmsList);
                }
        );
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    private void setSearchViewStyle(){
        SearchView.SearchAutoComplete searchAutoComplete = search.findViewById(R.id.search_src_text);
        ImageView searchIcon = search.findViewById(R.id.search_button);
        ImageView closeIcon = search.findViewById(R.id.search_close_btn);
        searchIcon.setColorFilter(getResources().getColor(R.color.orange));
        closeIcon.setColorFilter(getResources().getColor(R.color.orange));
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.white));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        preloader = new Preloader(getActivity(), R.id.preloader_container);
        setSearchViewStyle();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                filmsListAdapter.clearList();
                query.setSearch(queryText);
                query.setPage(1);
                viewModel.fetchFilmsList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    filmsListAdapter.clearList();
                    query.setPage(1);
                    query.setSearch(null);
                    viewModel.fetchFilmsList(query);
                }
                return false;
            }
        });
        viewModel.fetchTopSlider();
        viewModel.fetchSeriesUpdate();
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
                        query.setPage(1);
                        query.setOrderby("updated");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 1:
                        query.setPage(1);
                        query.setOrderby("year");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 2:
                        query.setPage(1);
                        query.setOrderby("rating");
                        viewModel.fetchFilmsList(query);
                        break;
                    case 3:
                        query.setPage(1);
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
        if (!search.getQuery().toString().equals("")){
            query.setSearch(search.getQuery().toString());
        }
        if (type == null) {
            filmsListAdapter = FilmsListAdapter.getInstance(getContext(), (OnClickListener) getContext(), getBaseUrl(), filmsList.getData());
        } else {
            filmsListAdapter = FilmsListAdapter.getInstance(getContext(), (OnClickListener) getContext(), getBaseUrl());
            filmsListAdapter.addFilms(filmsList.getData());
            type = null;
        }
        filmsListView.setAdapter(filmsListAdapter);
        filmsListView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        parent.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    if (addMore){
                        query.nextPage();
                        type = "add";
                        viewModel.fetchFilmsList(query);
                    }
                }
            }
        });
        if (newFilter) {
            filmsListAdapter.clearList();
            newFilter = false;
        }
    }

    
}