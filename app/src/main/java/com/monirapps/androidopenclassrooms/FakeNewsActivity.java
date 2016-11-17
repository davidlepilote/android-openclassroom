package com.monirapps.androidopenclassrooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by David et Monireh on 11/11/2016.
 */
public class FakeNewsActivity extends AppCompatActivity {

    public static final String TITLE = "title";
    public static final String NEWS = "news";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakenews);
        Bundle extras = getIntent().getExtras();
        final String title = extras.getString(TITLE);
        final String htmlContent = extras.getString(NEWS);
        WebView webView = (WebView) findViewById(R.id.webView);
        getSupportActionBar().setTitle(title);
        webView.loadData(htmlContent, "text/html; charset=UTF-8", null);
    }
}
