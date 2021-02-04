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

public class QualitiesAdapter extends RecyclerView.Adapter<QualitiesAdapter.QualityViewHolder>{

    private List<RowForChoose> qualities;

    public QualitiesAdapter(List<RowForChoose> qualities) {
        this.qualities = qualities;
    }

    @NonNull
    @Override
    public QualitiesAdapter.QualityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiter_list, parent, false);
        return new QualitiesAdapter.QualityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QualitiesAdapter.QualityViewHolder holder, int position) {
        holder.bindData(qualities.get(position));
    }

    @Override
    public int getItemCount() {
        return qualities != null ? qualities.size() : 0;
    }

    public class QualityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.filter_list_item_text)
        TextView filterListItemText;
        @BindView(R.id.filter_list_item_delete)
        ImageView filterListItemDelete;

        public QualityViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RowForChoose quality) {
            filterListItemText.setText(quality.getName());
            filterListItemDelete.setOnClickListener(v -> {
                qualities.remove(quality);
                notifyDataSetChanged();
            });
        }
    }
}
