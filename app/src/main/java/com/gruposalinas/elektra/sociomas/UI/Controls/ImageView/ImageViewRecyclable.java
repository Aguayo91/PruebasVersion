package com.gruposalinas.elektra.sociomas.UI.Controls.ImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

/**
 * Created by oemy9 on 12/06/2017.
 */

@SuppressWarnings("unused")
public class ImageViewRecyclable extends android.support.v7.widget.AppCompatImageView {
    private Bitmap bitmap;

    public ImageViewRecyclable(Context context) {
        super(context);
    }

    public ImageViewRecyclable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewRecyclable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageBitmap(Bitmap bm)
    {
        super.setImageBitmap(bm);
        if (bitmap != null) bitmap.recycle();
        this.bitmap = bm;
    }

}
