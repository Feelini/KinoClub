package by.seobility.kinoclub.ui.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.RowForChooseList;
import by.seobility.kinoclub.utils.OnClickListener;

public class ChooseListFragment extends Fragment {
    @BindView(R.id.chooseList)
    RecyclerView chooseList;
    @BindView(R.id.search)
    SearchView search;
    @BindView(R.id.addChoose)
    TextView addChoose;

    private Unbinder unbinder;
    private ChooseListAdapter adapter;
    private static ChooseListFragment instance;
    private RowForChooseList rowForChooseList;
    private String type;
    private OnClickListener onClickListener;

    public static ChooseListFragment getInstance(RowForChooseList rowForChooseList, String type){
        return new ChooseListFragment(rowForChooseList, type);
    }

    public ChooseListFragment(RowForChooseList rowForChooseList, String type){
        this.rowForChooseList = rowForChooseList;
        this.type = type;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnClickListener) {
            onClickListener = (OnClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onClickListener != null){
            onClickListener = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        showChooseList(rowForChooseList);
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = (ChooseListAdapter) chooseList.getAdapter();
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        addChoose.setOnClickListener(v -> {
            switch (type){
                case "category":
                    adapter = (ChooseListAdapter) chooseList.getAdapter();
                    onClickListener.onCategoryAdd(new RowForChooseList(null, adapter.getCheckedRowForChooses(), null));
                    break;
                case "quality":
                    adapter = (ChooseListAdapter) chooseList.getAdapter();
                    onClickListener.onQualityAdd(new RowForChooseList(null, adapter.getCheckedRowForChooses(), null));
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showChooseList(RowForChooseList rowForChooseList) {
        adapter = new ChooseListAdapter(rowForChooseList.getData());
        chooseList.setAdapter(adapter);
        chooseList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
