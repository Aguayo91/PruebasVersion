package com.gruposalinas.elektra.sociomas.IO.FireBase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.gruposalinas.elektra.sociomas.Utils.ApplicationBase;
import com.sociomas.core.WebService.Model.Request.TokenFireBase.TokenRequest;
import com.sociomas.core.WebService.Model.Response.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oemy on 18/10/2017.
 */

public class TokenID extends FirebaseInstanceIdService
{
    private static final String TAG = "TokenID";
    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setTokenDispositivo(token);
        ApplicationBase.getIntance().getControllerAventon().enviarTokenFireBase(tokenRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "TOKEN FIREBASE SINCRONIZADO CORRECTMENTE");
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
            }
        });

    }
}
