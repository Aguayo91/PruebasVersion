package com.gruposalinas.elektra.sociomas.UI.Controls.Notification;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.gruposalinas.elektra.sociomas.R;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;

/**
 * Created by oemy9 on 06/06/2017.
 */

public class TipsBuilder {
    public static ShowTipsView show(Activity activity, View view, String title, String description){
        ShowTipsView showtips = new ShowTipsBuilder(activity)
                .setTarget(view).setTitle(title)
                .setDescription(description)
                .setTitleColor(Color.YELLOW)
                .setBackgroundAlpha(128).setDelay(800)
                .setCloseButtonColor(Color.YELLOW)
                .setCloseButtonTextColor(Color.BLACK)
                .setButtonText(activity.getString(R.string.aceptar))
                .build();
        showtips.show(activity);

        return  showtips;
    }
}
