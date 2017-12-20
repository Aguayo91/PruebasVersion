package com.gruposalinas.elektra.sociomas.UI.Activities.SOS;
import com.sociomas.core.MVP.BasePresenter;
/**
 * Created by oemy9 on 07/09/2017.
 */
public interface SosPresenter extends BasePresenter {
    void loadContactsList();
    void checkIfStartTimer();
}
