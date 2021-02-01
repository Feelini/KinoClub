package by.seobility.kinoclub.ui.filter;

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

    public static ChooseListFragment getInstance(RowForChooseList rowForChooseList){
        return new ChooseListFragment(rowForChooseList);
    }

    public ChooseListFragment(RowForChooseList rowForChooseList){
        this.rowForChooseList = rowForChooseList;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showChooseList(RowForChooseList rowForChooseList) {
        adapter = new ChooseListAdapter(rowForChooseList.getCategories());
        chooseList.setAdapter(adapter);
        chooseList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
