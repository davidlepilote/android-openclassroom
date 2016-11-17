package com.monirapps.androidopenclassrooms;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public enum RSSChannel {
    IDEES("http://www.lemonde.fr/idees/rss_full.xml", "Idées"),
    UNE("http://www.lemonde.fr/rss/une.xml", "A la une"),
    ACTU("http://www.lemonde.fr/m-actu/rss_full.xml", "Actus");

    public final String link;

    public final String channel;

    RSSChannel(String link, String channel) {
        this.link = link;
        this.channel = channel;
    }
}
