package by.seobility.kinoclub.ui.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.RowForChoose;

public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.ChooseListViewHolder> implements Filterable {

    private List<RowForChoose> rowForChooses;
    private List<RowForChoose> rowForChoosesFull;
    private ArrayList<RowForChoose> checkedRowForChooses = new ArrayList<>();

    public ChooseListAdapter(List<RowForChoose> rowForChooses) {
        this.rowForChooses = rowForChooses;
        if (rowForChooses != null) {
            this.rowForChoosesFull = new ArrayList<>(rowForChooses);
        }
    }

    public List<RowForChoose> getCheckedRowForChooses() {
        return checkedRowForChooses;
    }

    @NonNull
    @Override
    public ChooseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_for_choose, parent, false);
        return new ChooseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseListViewHolder holder, int position) {
        holder.bindData(rowForChooses.get(position));
    }

    @Override
    public int getItemCount() {
        return rowForChooses != null ? rowForChooses.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RowForChoose> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rowForChoosesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (RowForChoose rowForChoose : rowForChoosesFull) {
                    if (rowForChoose.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(rowForChoose);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (rowForChooses != null) {
                rowForChooses.clear();
                rowForChooses.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public class ChooseListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rowName)
        TextView rowName;
        @BindView(R.id.checkRow)
        CheckBox checkRow;

        public ChooseListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RowForChoose rowForChoose) {
            rowName.setText(rowForChoose.getName());
            checkRow.setChecked(checkedRowForChooses.contains(rowForChoose));
            itemView.setOnClickListener(v -> {
                if (checkedRowForChooses.contains(rowForChoose)) {
                    checkedRowForChooses.remove(rowForChoose);
                    checkRow.setChecked(false);
                } else {
                    checkedRowForChooses.add(rowForChoose);
                    checkRow.setChecked(true);
                }
            });
            checkRow.setOnClickListener(v -> {
                if (checkedRowForChooses.contains(rowForChoose)) {
                    checkedRowForChooses.remove(rowForChoose);
                    checkRow.setChecked(false);
                } else {
                    checkedRowForChooses.add(rowForChoose);
                    checkRow.setChecked(true);
                }
            });
        }
    }
}