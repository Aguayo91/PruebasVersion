package com.sociomas.aventones.UI.Activities.QrScan;

import com.sociomas.core.MVP.BasePresenter;

/**
 * Created by Giovanni Toledo Toledo<mail: giio.toledo@gmail.com>
 * on 13/10/2017.
 */

public interface QRScanPresenter extends BasePresenter {
    void setResultadoCoordenadas(ResultadoScan r);
    void enviarQrDatos();
}
