package com.gruposalinas.elektra.sociomas.UI.Controls.TextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by oemy9 on 23/11/2017.
 */

public class TextViewOswald extends AppCompatTextView {
    public TextViewOswald(Context context) {
        super(context);
        init();
    }

    public TextViewOswald(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewOswald(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"fonts/OswaldRegular.ttf");
        setTypeface(typeFace);

    }
}
