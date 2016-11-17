package com.monirapps.androidopenclassrooms;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public enum RSSChannel {
    IDEES("http://www.lemonde.fr/idees/rss_full.xml"),
    UNE("http://www.lemonde.fr/rss/une.xml"),
    ACTU("http://www.lemonde.fr/m-actu/rss_full.xml");

    public final String link;

    RSSChannel(String link) {
        this.link = link;
    }
}
