package com.monirapps.androidopenclassrooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oc.rss.fake.FakeNews;
import com.oc.rss.fake.FakeNewsList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<RSSAdapter.RSS> rssList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final RSSAdapter rssAdapter = new RSSAdapter(rssList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(rssAdapter);

        // Fetch data from RSS channels
        for (RSSChannel rssChannel : RSSChannel.values()) {
            new RSSDownloader(rssChannel.link, rssList, rssAdapter).start();
        }
    }
}
