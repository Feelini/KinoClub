package by.seobility.kinoclub.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.utils.OnClickListener;

public class SeriesUpdateAdapter extends RecyclerView.Adapter<SeriesUpdateAdapter.SeriesUpdateViewHolder>{
    private List<Film> films;
    private Boolean expanded;
    private static SeriesUpdateAdapter instance;
    private OnClickListener onClickListener;
    private String baseUrl;

    public static SeriesUpdateAdapter getInstance(){
        return instance;
    }

    public static SeriesUpdateAdapter getInstance(List<Film> films, OnClickListener context, String baseUrl){
        return new SeriesUpdateAdapter(films, context, baseUrl);
    }

    private SeriesUpdateAdapter(List<Film> films, OnClickListener context, String baseUrl) {
        this.films = films;
        this.onClickListener = context;
        this.expanded = false;
        this.baseUrl = baseUrl;
    }

    public Boolean isExpanded(){
        return expanded;
    }

    public void setExpanded(Boolean expanded){
        this.expanded = expanded;
    }

    @NonNull
    @Override
    public SeriesUpdateAdapter.SeriesUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_series_update, parent, false);
        return new SeriesUpdateAdapter.SeriesUpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesUpdateAdapter.SeriesUpdateViewHolder holder, int position) {
        holder.bindData(films.get(position));
    }

    @Override
    public int getItemCount() {
        return films != null ? films.size() : 0;
    }

    public class SeriesUpdateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.series_update_list_image)
        ImageView seriesUpdateListImage;
        @BindView(R.id.series_update_list_text)
        TextView seriesUpdateListText;
        @BindView(R.id.series_update_list_title)
        TextView seriesUpdateListTitle;

        public SeriesUpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Film film) {
            String posterUrl = baseUrl + film.getPoster();
            Picasso.get().load(posterUrl).into(seriesUpdateListImage);
            String seriesUpdateText = itemView.getResources().getString(
                    R.string.series_update_list_text,
                    film.getLast_episode().getSeason(),
                    film.getLast_episode().getEpisode());
            seriesUpdateListText.setText(seriesUpdateText);
            seriesUpdateListTitle.setText(film.getTitle());
            itemView.setOnClickListener(v -> {
                if (onClickListener != null){
                    onClickListener.onFilmClick(film);
                }
            });
        }
    }
}
