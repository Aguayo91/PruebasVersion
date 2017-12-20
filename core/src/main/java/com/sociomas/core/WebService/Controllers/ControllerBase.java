package com.sociomas.core.WebService.Controllers;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sociomas.core.BuildConfig;
import com.sociomas.core.Utils.Constants;
import com.sociomas.core.Utils.Security.SecurityItems;
import com.sociomas.core.Utils.Utils;

import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oemy9 on 13/09/2017.
 */

public class ControllerBase {
    protected Retrofit retrofit;
    protected Context context;
    private static final String TAG = "ControllerBase";


    private OkHttpClient.Builder getClientHttp(HostnameVerifier hostnameVerifier){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if(hostnameVerifier!=null) {
            httpClient.hostnameVerifier(hostnameVerifier);
        }
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.readTimeout(60,TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        if (MockInterceptor.inHardCode) {
            httpClient.addInterceptor(new MockInterceptor(context));
        }
        return httpClient;
    }

    public ControllerBase(Context context, String urlBase) {
            this.context = context;
            retrofit = getClientRetrofit(urlBase,Utils.hostnameVerifier(),false);
    }

    public  ControllerBase(Context context,String urlBase,HostnameVerifier hostnameVerifier, boolean acceptNulls){
        this.context=context;
        retrofit=getClientRetrofit(urlBase,hostnameVerifier,acceptNulls);
    }

    public ControllerBase(Context context, String urlBase, HostnameVerifier hostnameVerifier){
        this.context = context;
        retrofit = getClientRetrofit(urlBase,hostnameVerifier,false);
    }

    private Retrofit getClientRetrofit(String urlBase, HostnameVerifier hostnameVerifier, boolean acceptNulls){

        Log.d(TAG, "getClientRetrofit:"+urlBase);

       return   new Retrofit.Builder()
                .addConverterFactory(acceptNulls?GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()) : GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getUrlBaseFormat(urlBase))
                .client(getClientHttp(urlBase.startsWith(Constants.IS_HTTPS)?hostnameVerifier:null).build())
                .build();
    }



    public String getUrlBaseFormat(String urlBase){
        return urlBase.endsWith("/")? urlBase: urlBase.concat("/");
    }

    /*OBTIENE RETROFIT CLIENTE*/
    protected Retrofit getRetrofitClient() {
        return retrofit;
    }
    /*OBTIENE EL NÚMERO DE EMPLEADO PARA HACER PETICIONES*/
    protected String getNumeroEmpleado(){
      return   Utils.getNumeroEmpleado(this.context);
    }

    /*OBTIENE EL TOKEN PARA HACER PETICIONES*/
    protected SecurityItems getInstanceToken(String numeroEmpleado){
        SecurityItems securityItems=new SecurityItems(numeroEmpleado);
        return securityItems;
    }
    /*OBTIENE EL TOKEN PARA HCER PETICIONES DEL ACTUAL NÚMERO DE EMPLEADO*/
    protected SecurityItems getInstanceToken(){
        SecurityItems securityItems=new SecurityItems(getNumeroEmpleado());
        return securityItems;
    }
    protected Retrofit generateBaseUrl(String url){
        //VERIFICA SI ES HTTPS O NO
        if(url.startsWith(Constants.IS_HTTPS)) {
            retrofit = getClientRetrofit(url,Utils.hostnameVerifier(),false);
        }
        //GENERA UN CLIENTE SIN AUTENTIFICACIÓN
        else{
            retrofit = getClientRetrofit(url,null,false);
        }
        return retrofit;
    }
}
