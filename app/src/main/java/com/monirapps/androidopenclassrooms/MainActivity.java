package com.monirapps.androidopenclassrooms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

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

    final List<RSSAdapter.RSS> rssList = new ArrayList<>();

    final RSSAdapter rssAdapter = new RSSAdapter(rssList);

    final List<RSSDownloader> rssDownloaders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(rssAdapter);

        downloadData(rssList, rssAdapter);
    }

    // Fetch data from RSS channels
    // Launch threads in parallel and join them to wait until they are finished
    private void downloadData(List<RSSAdapter.RSS> rssList, RSSAdapter rssAdapter) {
        rssList.clear();
        for (RSSChannel rssChannel : RSSChannel.values()) {
            RSSDownloader rssDownloader = new RSSDownloader(rssChannel, rssList);
            rssDownloaders.add(rssDownloader);
            rssDownloader.start();
            try {
                rssDownloader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // When threads finished their tasks, call data set change
        rssAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                downloadData(rssList, rssAdapter);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // Cancel downloads when destroying
        for (RSSDownloader rssDownloader : rssDownloaders) {
            try {
                rssDownloader.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
