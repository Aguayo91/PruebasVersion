package com.gruposalinas.elektra.sociomas.UI.Controls.EditTexts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gruposalinas.elektra.sociomas.R;

/**
 * Created by oemy9 on 05/06/2017.
 */

@SuppressWarnings("unused")
public class CustomNumberPicker extends LinearLayout implements View.OnClickListener {

    public interface NumberPickerCallBack{
        void onValueChanged(int value);
    }


    private Context context;
    //CONTROLES DE NUMBERPICKER
    private Button btnDecrement,btnIncrement;
    private EditText txtDisplay;
    private NumberPickerCallBack callBack;

    //PROPIEDADES DEL CONTROL
    private int selectedValue=1;
    private int maxValue=3;
    private int minValue=1;

    public void setCallBack(NumberPickerCallBack callBack) {
        this.callBack = callBack;
    }
    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public CustomNumberPicker(Context context) {
        super(context);
        inflateLayouts(context);
    }

    public CustomNumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflateLayouts(context);
    }

    public CustomNumberPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateLayouts(context);
    }

    private void  inflateLayouts(Context context)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.numer_picker,this);
        this.context=context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.txtDisplay=(EditText)findViewById(R.id.txtDisplay);
        this.btnDecrement=(Button)findViewById(R.id.btnDecrement);
        this.btnIncrement=(Button)findViewById(R.id.btnIncrement);

        this.btnDecrement.setOnClickListener(this);
        this.btnIncrement.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnIncrement:
                    if(selectedValue<maxValue){
                        selectedValue++;
                        String textDisplay=selectedValue==maxValue? "+".concat(String.valueOf(selectedValue)):
                                                                    String.valueOf(selectedValue);
                        txtDisplay.setText(textDisplay);
                        if(callBack!=null){
                            callBack.onValueChanged(selectedValue);
                        }
                    }
                    break;
                case R.id.btnDecrement:
                    if(selectedValue>minValue){
                        selectedValue--;
                        txtDisplay.setText(String.valueOf(selectedValue));
                        if(callBack!=null){
                            callBack.onValueChanged(selectedValue);
                        }
                    }
                    break;
            }
    }

    public void disabled(){
        this.btnIncrement.setEnabled(false);
        this.btnDecrement.setEnabled(false);
    }
    public void enabled(){
        this.btnIncrement.setEnabled(true);
        this.btnDecrement.setEnabled(true);
    }
}
