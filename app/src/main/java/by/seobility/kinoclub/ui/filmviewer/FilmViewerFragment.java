package by.seobility.kinoclub.ui.filmviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.repo.models.FilmsList;
import by.seobility.kinoclub.repo.models.SimilarFilms;
import by.seobility.kinoclub.ui.main.MainFragmentViewModel;
import by.seobility.kinoclub.ui.main.SeriesUpdateAdapter;
import by.seobility.kinoclub.ui.main.SpacesItemDecoration;
import by.seobility.kinoclub.ui.main.TopSliderAdapter;
import by.seobility.kinoclub.utils.OnClickListener;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class FilmViewerFragment extends Fragment {

    @BindView(R.id.film_title)
    TextView filmTitle;
    @BindView(R.id.film_poster)
    ImageView filmPoster;
    @BindView(R.id.film_rate_text)
    TextView filmRateText;
    @BindView(R.id.film_category)
    TextView filmCategory;
    @BindView(R.id.film_year)
    TextView filmYear;
    @BindView(R.id.film_countries)
    TextView filmCountries;
    @BindView(R.id.film_genres)
    TextView filmGenres;
    @BindView(R.id.film_qualities)
    TextView filmQualities;
    @BindView(R.id.film_translations)
    TextView filmTranslations;
    @BindView(R.id.film_duration)
    TextView filmDuration;
    @BindView(R.id.film_composers)
    TextView filmComposers;
    @BindView(R.id.film_actors)
    TextView filmActors;
    @BindView(R.id.film_directors)
    TextView filmDirectors;
    @BindView(R.id.film_description_text)
    TextView filmDescriptionText;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
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
    @BindView(R.id.similar_list)
    RecyclerView similarList;


    private static FilmViewerFragment instance;
    private MainFragmentViewModel viewModel;
    private FilmViewerViewModel filmViewerViewModel;
    private Unbinder unbinder;
    private Film film;
    private TopSliderAdapter topSliderAdapter;
    private SeriesUpdateAdapter seriesUpdateAdapter;
    private SimilarAdapter similarAdapter;

    public static FilmViewerFragment getInstance(){
        return instance;
    }

    public static FilmViewerFragment getInstance(Film film) {
        return new FilmViewerFragment(film);
    }

    public FilmViewerFragment(Film film){
        this.film = film;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel.class);
        filmViewerViewModel = new ViewModelProvider(this, viewModelFactory).get(FilmViewerViewModel.class);
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
        filmViewerViewModel.getSimilarFilms().observe(
                getViewLifecycleOwner(),
                this::showSimilarFilms
        );
        return inflater.inflate(R.layout.film_viewer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        viewModel.fetchTopSlider();
        viewModel.fetchSeriesUpdate();
        filmViewerViewModel.fetchSimilarFilms(new SimilarFilms(film.getId()));
        seriesUpdate.setOnClickListener(v -> {
            seriesUpdateExpandable.toggle();
            SeriesUpdateAdapter adapter = (SeriesUpdateAdapter) viewSeriesUpdate.getAdapter();
            Boolean isExpanded = adapter.isExpanded();
            seriesUpdateIcon.setImageResource(isExpanded ? R.drawable.add : R.drawable.remove);
            adapter.setExpanded(!isExpanded);
        });

        filmTitle.setText(film.getTitle());
        Picasso.get().load(film.getPoster()).into(filmPoster);
        filmRateText.setText(film.getRating());
        if (film.getCat().equals("false")){
            filmCategory.setVisibility(View.GONE);
        } else {
            filmCategory.setText(HtmlCompat.fromHtml(getResources().getString(R.string.category,
                    "<font color='#FFFFFF'>" + film.getCat() + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        if (film.getYear().equals("false")){
            filmYear.setVisibility(View.GONE);
        } else {
            filmYear.setText(HtmlCompat.fromHtml(getResources().getString(R.string.year,
                    "<font color='#FFFFFF'>" + film.getYear() + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String countries = listToString(film.getCountries());
        if (countries.equals("false")){
            filmCountries.setVisibility(View.GONE);
        } else {
            filmCountries.setText(HtmlCompat.fromHtml(getResources().getString(R.string.countries,
                    "<font color='#FFFFFF'>" + countries + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String genres = listToString(film.getGenres());
        if (genres.equals("false")){
            filmGenres.setVisibility(View.GONE);
        } else {
            filmGenres.setText(HtmlCompat.fromHtml(getResources().getString(R.string.genres,
                    "<font color='#FFFFFF'>" + genres + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String qualities = listToString(film.getQualites());
        if (qualities.equals("false")){
            filmQualities.setVisibility(View.GONE);
        } else {
            filmQualities.setText(HtmlCompat.fromHtml(getResources().getString(R.string.qualites,
                    "<font color='#FFFFFF'>" + qualities + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String translations = listToString(film.getTranslations());
        if (translations.equals("false")){
            filmTranslations.setVisibility(View.GONE);
        } else {
            filmTranslations.setText(HtmlCompat.fromHtml(getResources().getString(R.string.translations,
                    "<font color='#FFFFFF'>" + translations + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        if (film.getDuration().equals("false")){
            filmDuration.setVisibility(View.GONE);
        } else {
            filmDuration.setText(HtmlCompat.fromHtml(getResources().getString(R.string.duration,
                    "<font color='#FFFFFF'>" + film.getDuration() + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String composers = listToString(film.getComposers());
        if (composers.equals("false")){
            filmComposers.setVisibility(View.GONE);
        } else {
            filmComposers.setText(HtmlCompat.fromHtml(getResources().getString(R.string.composers,
                    "<font color='#FFFFFF'>" + composers + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String actors = listToString(film.getActors());
        if (actors.equals("false")){
            filmActors.setVisibility(View.GONE);
        } else {
            filmActors.setText(HtmlCompat.fromHtml(getResources().getString(R.string.actors,
                    "<font color='#FFFFFF'>" + actors + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        String directors = listToString(film.getDirectors());
        if (directors.equals("false")){
            filmDirectors.setVisibility(View.GONE);
        } else {
            filmDirectors.setText(HtmlCompat.fromHtml(getResources().getString(R.string.directors,
                    "<font color='#FFFFFF'>" + directors + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        if (film.getDescription().equals("false")){
            filmDescriptionText.setVisibility(View.GONE);
        } else {
            filmDescriptionText.setText(film.getDescription());
        }

        showTabs();
    }

    private void showTabs(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0);
        viewPagerAdapter.addFragment(FilmOnlineFragment.getInstance(film.getIframe()), "Онлайн");
        viewPagerAdapter.addFragment(FilmTrailerFragment.getInstance(film.getTrailer()), "Трейлер");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private String listToString(List<String> list){
        if (list.isEmpty()) return "false";
        StringBuilder return_string = new StringBuilder();
        for (String s : list){
            return_string.append(s).append(", ");
        }
        return return_string.toString().substring(0, return_string.length() - 2);
    }

    private void showTopSlider(FilmsList filmsList) {
        topSliderAdapter = new TopSliderAdapter(filmsList.getData(), (OnClickListener) getContext());
        viewTopSlider.setAdapter(topSliderAdapter);
        viewTopSlider.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    private void showSeriesUpdate(FilmsList filmsList) {
        seriesUpdateAdapter = SeriesUpdateAdapter.getInstance(filmsList.getData(), (OnClickListener) getContext());
        seriesUpdateIcon.setImageResource(seriesUpdateAdapter.isExpanded() ? R.drawable.remove : R.drawable.add);
        viewSeriesUpdate.setAdapter(seriesUpdateAdapter);
        viewSeriesUpdate.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewSeriesUpdate.setNestedScrollingEnabled(false);
        viewSeriesUpdate.addItemDecoration(new SpacesItemDecoration(0, 2));
    }

    private void showSimilarFilms(FilmsList filmsList){
        similarAdapter = SimilarAdapter.getInstance(filmsList.getData(), (OnClickListener) getContext());
        similarList.setAdapter(similarAdapter);
        similarList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }
}
