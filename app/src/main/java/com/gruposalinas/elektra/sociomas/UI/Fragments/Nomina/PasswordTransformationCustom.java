package com.gruposalinas.elektra.sociomas.UI.Fragments.Nomina;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 23/10/2017.
 */

public class PasswordTransformationCustom extends PasswordTransformationMethod {

    private int lenghtCharsToShow = 4;

    public PasswordTransformationCustom() {
    }

    public PasswordTransformationCustom(int visibleChars) {
        this.lenghtCharsToShow = visibleChars;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }



    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;
        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }
        public char charAt(int index) {
            if(index < mSource.length()- lenghtCharsToShow){
                return '•';
            } else {
                return mSource.charAt(index);
            }
        }
        public int length() {
            return mSource.length(); // Return default
        }
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
};