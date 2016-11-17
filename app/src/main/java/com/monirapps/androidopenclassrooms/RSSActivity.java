package com.monirapps.androidopenclassrooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.io.Serializable;

/**
 * Created by David et Monireh on 11/11/2016.
 */
public class RSSActivity extends AppCompatActivity {

    public static final String RSS = "rss";
    public static final String LINK = "link";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakenews);
        Bundle extras = getIntent().getExtras();
        RSSAdapter.RSS rss = (RSSAdapter.RSS) extras.getSerializable(RSS);
        WebView webView = (WebView) findViewById(R.id.webView);
        getSupportActionBar().setTitle(rss.title);
        webView.loadUrl(rss.link);
    }
}
