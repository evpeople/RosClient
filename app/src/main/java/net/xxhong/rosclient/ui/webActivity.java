package net.xxhong.rosclient.ui;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.xxhong.rosclient.R;

public class webActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData("", "text/html", "UTF-8");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://192.168.43.96:2345/");

    }
}