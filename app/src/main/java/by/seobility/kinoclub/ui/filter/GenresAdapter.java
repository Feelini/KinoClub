package by.seobility.kinoclub.ui.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.RowForChoose;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenresViewHolder>{

    private List<RowForChoose> genres;

    public GenresAdapter(List<RowForChoose> genres) {
        this.genres = genres;
    }

    public List<RowForChoose> getGenres(){
        return genres;
    }

    @NonNull
    @Override
    public GenresAdapter.GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiter_list, parent, false);
        return new GenresAdapter.GenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.GenresViewHolder holder, int position) {
        holder.bindData(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres != null ? genres.size() : 0;
    }

    public class GenresViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.filter_list_item_text)
        TextView filterListItemText;
        @BindView(R.id.filter_list_item_delete)
        ImageView filterListItemDelete;

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RowForChoose genre) {
            filterListItemText.setText(genre.getName());
            filterListItemDelete.setOnClickListener(v -> {
                genres.remove(genre);
                notifyDataSetChanged();
            });
        }
    }
}
