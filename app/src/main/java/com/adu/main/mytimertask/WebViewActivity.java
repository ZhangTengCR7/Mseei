package com.adu.main.mytimertask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.adu.main.mytimertask.base.BaseActivity;

/**
 * Created by dell on 2016/6/3.
 */
public class WebViewActivity extends BaseActivity
{

    private ProgressBar pb;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    private void initView()
    {
        Intent intent = getIntent();
        String title=intent.getStringExtra("title");
        String url = intent.getStringExtra("url");

        getSupportActionBar().setTitle(title);

        pb= (ProgressBar) findViewById(R.id.pb);
        pb.setMax(100);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebViewClient());
        webView.loadUrl(url);

        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    class WebViewClient extends WebChromeClient{
        @Override public void onProgressChanged(WebView view, int newProgress) {
            pb.setProgress(newProgress);
            if (newProgress == 100){
                pb.setVisibility(View.GONE);
            }

            super.onProgressChanged(view, newProgress);
        }
    }

}
