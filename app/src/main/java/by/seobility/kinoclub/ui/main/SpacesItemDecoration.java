package by.seobility.kinoclub.ui.main;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int spanCount;

    public SpacesItemDecoration(int space, int spanCount) {
        this.space = space;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.left = space - column * space / spanCount;
        outRect.right = (column + 1) * space / spanCount;

        if (position < spanCount) {
            outRect.top = space;
        }
        outRect.bottom = space;
    }
}
