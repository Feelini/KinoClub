package by.seobility.kinoclub.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;

public class TopSliderAdapter extends RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder> {

    private List<Film> films;
    OnFilmClickListener onFilmClickListener;

    public TopSliderAdapter(List<Film> films, OnFilmClickListener onFilmClickListener) {
        this.films = films;
        this.onFilmClickListener = onFilmClickListener;
    }

    public interface OnFilmClickListener{
        void onFilmClick(Film film);
    }

    @NonNull
    @Override
    public TopSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_slider, parent, false);
        return new TopSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSliderViewHolder holder, int position) {
        holder.bindData(films.get(position));
    }

    @Override
    public int getItemCount() {
        return films != null ? films.size() : 0;
    }

    public class TopSliderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster)
        RoundedImageView poster;

        public TopSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Film film) {
            itemView.setOnClickListener(v -> {
                if (onFilmClickListener != null){
                    onFilmClickListener.onFilmClick(film);
                }
            });
            Picasso.get().load(film.getPoster()).into(poster);
        }
    }
}
