package by.seobility.kinoclub.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.utils.OnClickListener;

public class FilmsListAdapter extends RecyclerView.Adapter<FilmsListAdapter.FilmsListViewHolder> {

    private List<Film> films;
    private String baseUrl;
    private Context context;
    OnClickListener onClickListener;

    public FilmsListAdapter(Context context, List<Film> films, OnClickListener onClickListener, String baseUrl) {
        this.films = films;
        this.context = context;
        this.onClickListener = onClickListener;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public FilmsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_films_list, parent, false);
        return new FilmsListAdapter.FilmsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsListAdapter.FilmsListViewHolder holder, int position) {
        holder.bindData(films.get(position));
    }

    @Override
    public int getItemCount() {
        return films != null ? films.size() : 0;
    }

    public class FilmsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.films_list_title)
        TextView title;
        @BindView(R.id.films_list_poster)
        ImageView poster;
        @BindView(R.id.films_list_rate_text)
        TextView rateText;
        @BindView(R.id.films_list_category)
        TextView category;
        @BindView(R.id.films_list_year)
        TextView year;
        @BindView(R.id.films_list_qualities)
        TextView qualities;
        @BindView(R.id.films_list_translations)
        TextView translations;

        public FilmsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Film film) {
            itemView.setOnClickListener(v -> {
                if (onClickListener != null){
                    onClickListener.onFilmClick(film);
                }
            });

            title.setText(film.getTitle());
            String posterUrl = baseUrl + film.getPoster();
            Picasso.get().load(posterUrl).into(poster);
            rateText.setText(film.getRating());
            category.setText(HtmlCompat.fromHtml(context.getResources().getString(R.string.category,
                    "<font color='#FFFFFF'>" + film.getCat() + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
            year.setText(HtmlCompat.fromHtml(context.getResources().getString(R.string.year,
                    "<font color='#FFFFFF'>" + film.getYear() + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
            String qualitiesString = listToString(film.getQualites());
            qualities.setText(HtmlCompat.fromHtml(context.getResources().getString(R.string.qualites,
                    "<font color='#FFFFFF'>" + qualitiesString + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
            String translationsString = listToString(film.getTranslations());
            translations.setText(HtmlCompat.fromHtml(context.getResources().getString(R.string.translations,
                    "<font color='#FFFFFF'>" + translationsString + "</font>"),
                    HtmlCompat.FROM_HTML_MODE_LEGACY));
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
}
