package com.sociomas.aventones.UI.Controls.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sociomas.aventones.R;

/**
 * Created by oemy9 on 05/09/2017.
 */

public class DialogFrecuencia extends Dialog {

    public DialogFrecuencia(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picker_frequencia);
    }
}
