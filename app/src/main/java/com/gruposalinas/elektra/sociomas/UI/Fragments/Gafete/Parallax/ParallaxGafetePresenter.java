package com.gruposalinas.elektra.sociomas.UI.Fragments.Gafete.Parallax;

import com.sociomas.core.MVP.BasePresenter;
import com.sociomas.core.Utils.Enums.EnumDensidad;

/**
 * Created by oemy9 on 22/11/2017.
 */

public interface ParallaxGafetePresenter extends BasePresenter {
    void requestGafete(Double lat, Double lng);
    EnumDensidad getCurrentDensidad();
}
