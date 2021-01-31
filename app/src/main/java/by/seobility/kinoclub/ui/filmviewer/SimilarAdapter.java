package by.seobility.kinoclub.ui.filmviewer;

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

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>{

    private List<Film> films;
    private String baseUrl;
    OnClickListener onClickListener;
    private static SimilarAdapter instance;

    public static SimilarAdapter getInstance(){
        return instance;
    }

    public static SimilarAdapter getInstance(List<Film> films, OnClickListener onClickListener, String baseUrl){
        return new SimilarAdapter(films, onClickListener, baseUrl);
    }

    private SimilarAdapter(List<Film> films, OnClickListener onClickListener, String baseUrl) {
        this.films = films;
        this.onClickListener = onClickListener;
        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public SimilarAdapter.SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_similar, parent, false);
        return new SimilarAdapter.SimilarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarAdapter.SimilarViewHolder holder, int position) {
        holder.bindData(films.get(position));
    }

    @Override
    public int getItemCount() {
        return films != null ? films.size() : 0;
    }

    public class SimilarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.similar_poster)
        ImageView poster;
        @BindView(R.id.similar_title)
        TextView title;

        public SimilarViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Film film) {
            itemView.setOnClickListener(v -> {
                if (onClickListener != null){
                    onClickListener.onFilmClick(film);
                }
            });
            Picasso.get().load(baseUrl + film.getPoster()).into(poster);
            title.setText(film.getTitle());
        }
    }
}
