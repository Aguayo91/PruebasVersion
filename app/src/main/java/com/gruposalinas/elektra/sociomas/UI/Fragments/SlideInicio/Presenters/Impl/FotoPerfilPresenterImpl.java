package com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.Impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.gruposalinas.elektra.sociomas.UI.Fragments.SlideInicio.Presenters.FotoPerfilPresenter;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.gruposalinas.elektra.sociomas.Utils.Utils;
import com.sociomas.core.MVP.BasePresenterImpl;
import com.sociomas.core.MVP.BaseView;

/**
 * Created by oemy9 on 13/11/2017.
 */

public class FotoPerfilPresenterImpl extends BasePresenterImpl implements FotoPerfilPresenter {

    private FotoPerfilView view;

    @Override
    public void register(BaseView view) {
        super.register(view);
        this.view=(FotoPerfilView)view;
    }

    @Override
    public void guardarImagenPerfil(Bitmap bmp) {
        Context ctx=ApplicationBase.getIntance().getApplicationContext();
        Utils.saveImagenPerfilWallpaper(ctx,bmp,false);
        view.mostrarFotoPerfil(Utils.getImagenPerfilWallpaper(ctx,false));
    }

    public  interface FotoPerfilView extends BaseView{
        void mostrarFotoPerfil(Bitmap bmp);
    }
}
