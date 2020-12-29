package by.seobility.kinoclub.ui.filmviewer;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;
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


    private static FilmViewerFragment instance;
    private FilmViewerViewModel viewModel;
    private Unbinder unbinder;
    private Film film;

    public static FilmViewerFragment getInstance(Film film) {
        if (instance == null) {
            instance = new FilmViewerFragment(film);
        }
        return instance;
    }

    private FilmViewerFragment(Film film){
        this.film = film;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(FilmViewerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.films_viewer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        filmTitle.setText(film.getTitle());
        Picasso.get().load(film.getPoster()).into(filmPoster);
        filmRateText.setText(film.getRating());
        filmCategory.setText(HtmlCompat.fromHtml(getResources().getString(R.string.category,
                "<font color='#FFFFFF'>" + film.getCat() + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmYear.setText(HtmlCompat.fromHtml(getResources().getString(R.string.year,
                "<font color='#FFFFFF'>" + film.getYear() + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmCountries.setText(HtmlCompat.fromHtml(getResources().getString(R.string.countries,
                "<font color='#FFFFFF'>" + listToString(film.getCountries()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmGenres.setText(HtmlCompat.fromHtml(getResources().getString(R.string.genres,
                "<font color='#FFFFFF'>" + listToString(film.getGenres()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmQualities.setText(HtmlCompat.fromHtml(getResources().getString(R.string.qualites,
                "<font color='#FFFFFF'>" + listToString(film.getQualites()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmTranslations.setText(HtmlCompat.fromHtml(getResources().getString(R.string.translations,
                "<font color='#FFFFFF'>" + listToString(film.getTranslations()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmDuration.setText(HtmlCompat.fromHtml(getResources().getString(R.string.duration,
                "<font color='#FFFFFF'>" + film.getDuration() + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmComposers.setText(HtmlCompat.fromHtml(getResources().getString(R.string.composers,
                "<font color='#FFFFFF'>" + listToString(film.getComposers()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmActors.setText(HtmlCompat.fromHtml(getResources().getString(R.string.actors,
                "<font color='#FFFFFF'>" + listToString(film.getActors()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmDirectors.setText(HtmlCompat.fromHtml(getResources().getString(R.string.directors,
                "<font color='#FFFFFF'>" + listToString(film.getDirectors()) + "</font>"),
                HtmlCompat.FROM_HTML_MODE_LEGACY));
        filmDescriptionText.setText(film.getDescription());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private String listToString(List<String> list){
        StringBuilder return_string = new StringBuilder();
        for (String s : list){
            return_string.append(s).append(", ");
        }
        return return_string.toString().substring(0, return_string.length() - 2);
    }
}
