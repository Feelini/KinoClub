package by.seobility.kinoclub.ui.filmviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.seobility.kinoclub.R;

public class FilmTrailerFragment extends Fragment {
    @BindView(R.id.film_trailer_view)
    WebView filmTrailerView;


    private static FilmTrailerFragment instance;
    private String trailer_link;
    private Unbinder unbinder;

    public static FilmTrailerFragment getInstance(){
        return instance;
    }

    public static FilmTrailerFragment getInstance(String iframe_link) {
        return new FilmTrailerFragment(iframe_link);
    }

    private FilmTrailerFragment(String trailer_link){
        this.trailer_link = "<iframe style=\"transform: translate(-10px, -10px); width: calc(100% + 20px); height: calc(100% + 20px)\" width=\"100%\" height=\"100%\" src=\""+trailer_link+"\" frameborder=\"0\" allowfullscreen=\"\"></iframe>";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.film_trailer_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        filmTrailerView.getSettings().setJavaScriptEnabled(true);
        filmTrailerView.getSettings().setAllowFileAccess(true);
        filmTrailerView.setWebChromeClient(new WebChromeClient());
        filmTrailerView.setWebViewClient(new WebViewClient());
        filmTrailerView.loadDataWithBaseURL("https://vkino.fun/", trailer_link , "text/html",  "UTF-8", "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
