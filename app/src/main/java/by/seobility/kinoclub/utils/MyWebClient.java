package by.seobility.kinoclub.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;

public class MyWebClient extends WebChromeClient {

    private View customView;
    private CustomViewCallback customViewCallback;
    private int originalSystemUiVisibility;
    private Activity activity;

    public MyWebClient(Activity activity){
        this.activity = activity;
    }

    public Bitmap getDefaultVideoPoster(){
        if (customView == null){
            return null;
        }
        return BitmapFactory.decodeResource(activity.getResources(), 2130837573);
    }

    public void onHideCustomView(){
        ((FrameLayout) activity.getWindow().getDecorView()).removeView(this.customView);
        this.customView = null;
        activity.getWindow().getDecorView().setSystemUiVisibility(this.originalSystemUiVisibility);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.customViewCallback.onCustomViewHidden();
        this.customViewCallback = null;
    }

    public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback){
        if (this.customView != null){
            onHideCustomView();
            return;
        }
        this.customView = paramView;
        this.originalSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.customViewCallback = paramCustomViewCallback;
        ((FrameLayout) activity.getWindow().getDecorView()).addView(this.customView, new FrameLayout.LayoutParams(-1, -1));
        activity.getWindow().getDecorView().setSystemUiVisibility(3846);
    }
}
