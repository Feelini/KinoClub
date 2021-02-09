package by.seobility.kinoclub.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.utils.OnClickListener;

public class FilterAdapter extends RecyclerView.Adapter {

    private List<RowType> dataSet;
    private Context context;
    private FilterViewModel viewModel;
    private LifecycleOwner lifecycleOwner;
    private OnClickListener onBtnClick;

    public FilterAdapter(List<RowType> dataSet, Context context, FilterViewModel viewModel,
                         LifecycleOwner lifecycleOwner, OnClickListener onBtnClick) {
        this.dataSet = dataSet;
        this.context = context;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.onBtnClick = onBtnClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position) instanceof ButtonRowType) {
            return RowType.BUTTON_ROW_TYPE;
        } else if (dataSet.get(position) instanceof ListRowType) {
            return RowType.LIST_ROW_TYPE;
        } else {
            return -1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RowType.BUTTON_ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_type_btn, parent, false);
            return new ButtonViewHolder(view);
        } else if (viewType == RowType.LIST_ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_type_list, parent, false);
            return new ListViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof ButtonViewHolder) {
            ((ButtonViewHolder) holder).bindData(dataSet.get(position));
        } else if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).bindData(dataSet.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public class ButtonViewHolder extends ViewHolder {

        @BindView(R.id.type_btn)
        Button typeBtn;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RowType row) {
            typeBtn.setText(
                    context.getResources().getString(
                            ((ButtonRowType) row).getTextId()
                    )
            );
            typeBtn.setOnClickListener(
                    ((ButtonRowType) row).getOnClickListener(viewModel, lifecycleOwner, onBtnClick)
            );
        }
    }

    public class ListViewHolder extends ViewHolder {

        @BindView(R.id.filter_list_item_text)
        TextView filterListItemText;
        @BindView(R.id.filter_list_item_delete)
        ImageView filterListItemDelete;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RowType row) {
            filterListItemText.setText(((ListRowType) row).getText());
            filterListItemDelete.setOnClickListener(v -> {
                dataSet.remove(row);
                notifyDataSetChanged();
            });
        }
    }
}
