package com.sociomas.aventones.UI.Controls.ItemDecorationRecyclers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sociomas.aventones.R;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 05/10/2017.
 */

public class TransparentItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;


    public TransparentItemDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.divider_recyclerview_transparent);
    }


    @Override

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);


            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();


            int top = child.getBottom() + params.bottomMargin;

            int bottom = top + mDivider.getIntrinsicHeight();


            mDivider.setBounds(left, top, right, bottom);

            mDivider.draw(c);

        }

    }
}
