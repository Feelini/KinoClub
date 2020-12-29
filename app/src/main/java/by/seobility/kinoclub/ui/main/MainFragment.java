package by.seobility.kinoclub.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;
import by.seobility.kinoclub.repo.models.Film;
import by.seobility.kinoclub.repo.models.TopSlider;
import by.seobility.kinoclub.utils.ViewModelFactory;

public class MainFragment extends Fragment {

    @BindView(R.id.top_slider)
    RecyclerView viewTopSlider;

    private static MainFragment instance;
    private MainFragmentViewModel viewModel;
    private Unbinder unbinder;
    private TopSliderAdapter adapter;

    public static MainFragment getInstance() {
        if (instance == null) {
            instance = new MainFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getTopSlider().observe(
                getViewLifecycleOwner(),
                this::showTopSlider
        );
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        viewModel.fetchTopSlider();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showTopSlider(TopSlider topSlider) {
        adapter = new TopSliderAdapter(topSlider.getData());
        viewTopSlider.setAdapter(adapter);
        viewTopSlider.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
    }

    public class TopSliderAdapter extends RecyclerView.Adapter<TopSliderAdapter.TopSliderViewHolder> {

        private List<Film> films;

        public TopSliderAdapter(List<Film> films) {
            this.films = films;
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
                Picasso.get().load(film.getPoster()).into(poster);
            }
        }
    }
}