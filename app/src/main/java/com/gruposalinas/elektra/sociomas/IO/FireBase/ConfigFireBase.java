package com.gruposalinas.elektra.sociomas.IO.FireBase;

import com.sociomas.core.Utils.Constants;

import java.util.HashMap;

/**
 * Created by oemy9 on 24/11/2017.
 */

public class ConfigFireBase {
    public static final String URL_BASE_MOVILIDAD="url_base_movilidad";
    public static final String URL_BASE_AVENTON="url_base_aventon";
    public static final String URL_BASE_PROMOCION="url_base_promocion";
    /**
     *
     * @return Regresa los valores defaults de firebase
     */
    public static HashMap<String,Object>getDefaults(){
        HashMap<String,Object> defuaultsValues=new HashMap<>();
        defuaultsValues.put(URL_BASE_MOVILIDAD, Constants.DOMAIN_URL);
        defuaultsValues.put(URL_BASE_AVENTON,Constants.DOMAIN_URL_STAGING_AVENTONES);
        return defuaultsValues;
    }
}
