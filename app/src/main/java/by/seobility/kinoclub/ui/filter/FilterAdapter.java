package by.seobility.kinoclub.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.RowForChoose;
import by.seobility.kinoclub.repo.models.Years;
import by.seobility.kinoclub.ui.filter.rowtypes.ButtonRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.ListRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.RowType;
import by.seobility.kinoclub.ui.filter.rowtypes.SeekbarRowType;
import by.seobility.kinoclub.ui.filter.rowtypes.SubmitButtonRowType;
import by.seobility.kinoclub.utils.OnClickListener;

public class FilterAdapter extends RecyclerView.Adapter {

    private List<RowType> dataSet;
    private Context context;
    private FilterViewModel viewModel;
    private LifecycleOwner lifecycleOwner;
    private OnClickListener onBtnClick;
    private Years years;

    public FilterAdapter(List<RowType> dataSet, Context context, FilterViewModel viewModel,
                         LifecycleOwner lifecycleOwner, OnClickListener onBtnClick,
                         Years years) {
        this.dataSet = dataSet;
        this.context = context;
        this.viewModel = viewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.onBtnClick = onBtnClick;
        this.years = years;
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position).getItemViewType();
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
        } else if (viewType == RowType.SEEKBAR_ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_type_seekbar, parent, false);
            return new SeekbarViewHolder(view);
        } else if (viewType == RowType.SUBMIT_BUTTON_ROW_TYPE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_type_submit_btn, parent, false);
            return new SubmitBtnViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof ButtonViewHolder) {
            ((ButtonViewHolder) holder).bindData((ButtonRowType) dataSet.get(position));
        } else if (holder instanceof ListViewHolder) {
            ((ListViewHolder) holder).bindData((ListRowType) dataSet.get(position));
        } else if (holder instanceof SeekbarViewHolder){
            ((SeekbarViewHolder) holder).bindData((SeekbarRowType) dataSet.get(position));
        } else if (holder instanceof SubmitBtnViewHolder){
            ((SubmitBtnViewHolder) holder).bindData((SubmitButtonRowType) dataSet.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.type_btn)
        Button typeBtn;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ButtonRowType data) {
            typeBtn.setText(context.getResources().getString(data.getTextId()));
            typeBtn.setOnClickListener(data.getOnClickListener(viewModel, lifecycleOwner, onBtnClick));
        }
    }

    public List<RowType> getDataSet(){
        return dataSet;
    }

    private List<RowForChoose> getParamList(String type){
        List<RowForChoose> categoryList = new ArrayList<>();
        for (RowType row: dataSet){
            if (row instanceof ListRowType){
                if (((ListRowType) row).getType().equals(type)){
                    categoryList.add(new RowForChoose(((ListRowType) row).getId(), ((ListRowType) row).getText(), null));
                }
            }
        }
        return categoryList;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

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
                int position = dataSet.indexOf(row);
                notifyItemRangeRemoved(position, 1);
                dataSet.remove(row);
//                notifyDataSetChanged();
            });
        }
    }



    public class SeekbarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.year_min)
        EditText yearMin;
        @BindView(R.id.year_max)
        EditText yearMax;
        @BindView(R.id.seekbar)
        RangeSlider seekbar;

        public SeekbarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(SeekbarRowType row) {
            int min = row.getYearMin();
            int max = row.getYearMax();
            int currentMax = row.getCurrentYearMax();
            int currentMin = row.getCurrentYearMin();
            yearMin.setText(String.valueOf(currentMin));
            yearMax.setText(String.valueOf(currentMax));
            seekbar.setValueFrom(min);
            seekbar.setValueTo(max);
            List<Float> values = new ArrayList<>();
            values.add((float) currentMin);
            values.add((float) currentMax);
            seekbar.setValues(values);
            seekbar.addOnChangeListener((rangeSlider, v, b) -> {
                String min1 = rangeSlider.getValues().get(0).toString();
                min1 = min1.substring(0, min1.length() - 2);
                String max1 = rangeSlider.getValues().get(1).toString();
                max1 = max1.substring(0, max1.length() - 2);
                yearMin.setText(min1);
                yearMax.setText(max1);
                years.setMin(Integer.parseInt(min1));
                years.setMax(Integer.parseInt(max1));
                SeekbarRowType.getInstance().setCurrentYearMin(Integer.parseInt(min1));
                SeekbarRowType.getInstance().setCurrentYearMax(Integer.parseInt(max1));
            });
            yearMin.setOnEditorActionListener(row.getOnEditorActionListener("min", seekbar, yearMin));
            yearMax.setOnEditorActionListener(row.getOnEditorActionListener("max", seekbar, yearMax));
        }
    }

    public class SubmitBtnViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.submitBtn)
        TextView submitBtn;

        public SubmitBtnViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(SubmitButtonRowType row) {
            submitBtn.setOnClickListener(row.getOnClickListener(getParamList("category"),
                    getParamList("quality"), getParamList("genre"),
                    getParamList("country"), years, onBtnClick));
        }
    }
}
