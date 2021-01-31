package by.seobility.kinoclub.ui.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.utils.FragmentsParent;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class MainFragment extends FragmentsParent {

    @BindView(R.id.top_slider)
    RecyclerView viewTopSlider;
    @BindView(R.id.series_update_list)
    RecyclerView viewSeriesUpdate;
    @BindView(R.id.series_update)
    ConstraintLayout seriesUpdate;
    @BindView(R.id.series_update_icon)
    ImageView seriesUpdateIcon;
    @BindView(R.id.series_update_expandable)
    ExpandableLayout seriesUpdateExpandable;
    @BindView(R.id.filter_frame_layout)
    FrameLayout filterFrameLayout;

    private static MainFragment instance;
    private MainFragmentViewModel viewModel;
    private Unbinder unbinder;
    private TopSliderAdapter topSliderAdapter;
    private SeriesUpdateAdapter seriesUpdateAdapter;
    private OnClickListener onClickListener;

    public static MainFragment getInstance(OnClickListener onClickListener) {
        if (instance == null) {
            instance = new MainFragment(onClickListener);
        }
        return instance;
    }

    private MainFragment(OnClickListener onClickListener){
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
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        viewModel.fetchTopSlider();
        viewModel.fetchSeriesUpdate();
        seriesUpdate.setOnClickListener(v -> {
            seriesUpdateExpandable.toggle();
            SeriesUpdateAdapter adapter = (SeriesUpdateAdapter) viewSeriesUpdate.getAdapter();
            Boolean isExpanded = adapter.isExpanded();
            seriesUpdateIcon.setImageResource(isExpanded ? R.drawable.add : R.drawable.remove);
            adapter.setExpanded(!isExpanded);
        });
        filterFrameLayout.setOnClickListener(v -> onClickListener.onFilterClick());
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
}