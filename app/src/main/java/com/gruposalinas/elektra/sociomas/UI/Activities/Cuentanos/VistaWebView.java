package com.gruposalinas.elektra.sociomas.UI.Activities.Cuentanos;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.gruposalinas.elektra.sociomas.UI.Activities.Base.BaseAppCompactActivity;
import com.gruposalinas.elektra.sociomas.R;
import com.sociomas.core.Utils.Constants;

public class VistaWebView extends BaseAppCompactActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_web_view);
        WebView webView=(WebView)findViewById(R.id.webViewCuentanos);
        webView.getSettings().setJavaScriptEnabled(true);
        boolean isPie=getIntent().getBooleanExtra(Constants.PIE_PAGINA,true);
        webView.loadUrl(isPie?Constants.CUENTANOS_INICIO: Constants.CUENTANOS_LOGIN);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new cliente());

    }

    class cliente extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}
