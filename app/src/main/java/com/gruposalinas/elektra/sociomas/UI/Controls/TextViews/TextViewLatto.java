package com.gruposalinas.elektra.sociomas.UI.Controls.TextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by oemy9 on 23/11/2017.
 */

public class TextViewLatto extends AppCompatTextView {
    public TextViewLatto(Context context) {
        super(context);
        init();
    }

    public TextViewLatto(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewLatto(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"fonts/LatoRegular.ttf");
        setTypeface(typeFace);

    }
}
