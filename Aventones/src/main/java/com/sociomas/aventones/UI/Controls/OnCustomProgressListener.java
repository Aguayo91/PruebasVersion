package com.sociomas.aventones.UI.Controls;

import android.content.Context;
import android.widget.TextView;

import com.sociomas.aventones.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by oemy9 on 06/10/2017.
 */

public class OnCustomProgressListener implements DiscreteSeekBar.OnProgressChangeListener {


    private TextView tvChanged;
    private Context context;
    public OnCustomProgressListener(Context context, TextView tvChanged) {
        this.tvChanged = tvChanged;
        this.context=context;
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
        this.tvChanged.setText(String.valueOf(value)+" "+context.getString(R.string.MinTolerancia));

    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

    }
}
