package com.prmproject.clothesstoremobileandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentWebViewActivity extends AppCompatActivity {
    private static final String PAYMENT_RETURN_URL = "com.prmproject.clothesstoremobileandroid://payment-return";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith(PAYMENT_RETURN_URL)) {
                    handlePaymentReturn(url);
                    return true;
                }
                return false;
            }
        });

        String paymentUrl = getIntent().getStringExtra("paymentUrl");
        if (paymentUrl != null) {
            webView.loadUrl(paymentUrl);
        }
    }

    private void handlePaymentReturn(String url) {
        Uri uri = Uri.parse(url);
        String responseCode = uri.getQueryParameter("vnp_ResponseCode");
        String transactionStatus = uri.getQueryParameter("vnp_TransactionStatus");

        Intent resultIntent = new Intent();

        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
            resultIntent.putExtra("payment_status", "success");
        } else {
            resultIntent.putExtra("payment_status", "failure");
        }

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
