package com.monirapps.androidopenclassrooms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public class RSSDownloader extends Thread {

    private final String link;

    private final List<RSSAdapter.RSS> rssList;

    private final RSSAdapter rssAdapter;

    public RSSDownloader(String link, List<RSSAdapter.RSS> rssList, RSSAdapter rssAdapter) {
        this.link = link;
        this.rssList = rssList;
        this.rssAdapter = rssAdapter;
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
                                rssList.add(new RSSAdapter.RSS(rssItem.getElementsByTagName("title").item(0).getTextContent(),
                                        rssItem.getElementsByTagName("description").item(0).getTextContent(),
                                        rssItem.getElementsByTagName("enclosure").item(0).getAttributes().item(0).getTextContent()));
                            }
                            rssAdapter.notifyDataSetChanged();
                        } catch (IOException | ParserConfigurationException | SAXException e) {
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
