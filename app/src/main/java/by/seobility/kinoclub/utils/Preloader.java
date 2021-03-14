package by.seobility.kinoclub.utils;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

public class Preloader {
    private final FrameLayout container;

    public Preloader(Activity activity, int container) {
        this.container = activity.findViewById(container);
    }

    public void showPreloader(){
        container.setVisibility(View.VISIBLE);
    }

    public void hidePreloader(){
        container.setVisibility(View.GONE);
    }
}
