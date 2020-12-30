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

public class FilmOnlineFragment extends Fragment {

    @BindView(R.id.film_online_view)
    WebView filmOnlineView;

    private static FilmOnlineFragment instance;
    private String iframe_link;
    private Unbinder unbinder;

    public static FilmOnlineFragment getInstance(){
        return instance;
    }

    public static FilmOnlineFragment getInstance(String iframe_link) {
        return new FilmOnlineFragment(iframe_link);
    }

    private FilmOnlineFragment(String iframe_link) {
        this.iframe_link = "<iframe style=\"transform: translate(-10px, -10px); width: calc(100% + 20px); height: calc(100% + 20px)\" src=\""+iframe_link+"\" frameborder=\"0\" id=\"onik-player\" allowfullscreen=\"\"></iframe>";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.film_online_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        filmOnlineView.getSettings().setJavaScriptEnabled(true);
        filmOnlineView.getSettings().setAllowFileAccess(true);
        filmOnlineView.setWebChromeClient(new WebChromeClient());
        filmOnlineView.setWebViewClient(new WebViewClient());
        filmOnlineView.loadDataWithBaseURL("https://vkino.fun/", iframe_link , "text/html",  "UTF-8", "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
