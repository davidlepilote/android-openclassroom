package com.monirapps.androidopenclassrooms;

import android.support.v7.util.SortedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public class RSSDownloader extends Thread {

    // Callback called at the end of the thread if it has completed
    public interface ThreadCompletedListener {
        public void onCompleted();
    }

    private ThreadCompletedListener listener;

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");

    private final String link;

    private final String channel;

    private final List<RSSAdapter.RSS> rssList;

    public RSSDownloader(RSSChannel channel, List<RSSAdapter.RSS> rssList) {
        this.link = channel.link;
        this.channel = channel.channel;
        this.rssList = rssList;
    }

    // Returns this to use a Builder pattern when instantiated
    public RSSDownloader onComplete(ThreadCompletedListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(link);
            final HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) url.openConnection();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream stream = null;
                        try {
                            stream = connection.getInputStream();
                            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
                            NodeList rssLists = doc.getElementsByTagName("item");
                            for (int rssIndex = 0; rssIndex < rssLists.getLength(); rssIndex++) {
                                Element rssItem = (Element) rssLists.item(rssIndex);
                                rssList.add(new RSSAdapter.RSS(channel, rssItem.getElementsByTagName("title").item(0).getTextContent(),
                                        rssItem.getElementsByTagName("link").item(0).getTextContent(),
                                        dateFormat.parse(rssItem.getElementsByTagName("pubDate").item(0).getTextContent()),
                                        rssItem.getElementsByTagName("enclosure").item(0).getAttributes().item(0).getTextContent()));
                            }
                            Collections.sort(rssList);
                            if (listener != null) {
                                listener.onCompleted();
                            }
                        } catch (IOException | ParserConfigurationException | SAXException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
