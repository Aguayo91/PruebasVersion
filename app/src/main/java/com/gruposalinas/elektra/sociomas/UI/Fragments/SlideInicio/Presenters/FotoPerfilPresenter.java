package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters;

import android.graphics.Bitmap;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by oemy9 on 13/11/2017.
 */

public interface FotoPerfilPresenter extends BasePresenter{
        void guardarImagenPerfil(Bitmap bmp);
}
