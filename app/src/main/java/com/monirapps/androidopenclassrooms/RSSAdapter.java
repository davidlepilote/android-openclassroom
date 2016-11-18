package com.monirapps.androidopenclassrooms;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public class RSSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
    public static final int PROGRESS_CELL = 1;
    public static final int RSS_CELL = 2;

    public static class ProgressViewHolder extends RecyclerView.ViewHolder{

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RSSViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView channel;

        private TextView date;

        private ImageView image;

        public RSSViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            channel = (TextView) itemView.findViewById(R.id.channel);
            date = (TextView) itemView.findViewById(R.id.date);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public static class RSS implements Serializable, Comparable<RSS> {
        public final String channel;
        public final String title;
        public final String link;
        public final Date date;
        public final String image;

        public RSS(String channel, String title, String link, Date date, String image) {
            this.channel = channel;
            this.title = title;
            this.link = link;
            this.date = date;
            this.image = image;
        }

        // Compares date in an anti chronological way
        @Override
        public int compareTo(RSS other) {
            return other.date.compareTo(this.date);
        }
    }

    private List<RSS> rssList;

    public RSSAdapter(List<RSS> rssList) {
        this.rssList = rssList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PROGRESS_CELL:
                return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progresscell, parent, false));
            case RSS_CELL:
            default:
                return new RSSViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rsscell, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RSSViewHolder) {
            final RSSViewHolder holder = (RSSViewHolder) viewHolder;
            final RSS rss = rssList.get(position);
            holder.title.setText(rss.title);
            holder.channel.setText(rss.channel);
            holder.date.setText(dateFormat.format(rss.date));
            Picasso.with(holder.itemView.getContext()).load(rss.image).into(holder.image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), RSSActivity.class);
                    intent.putExtra(RSSActivity.RSS, rss);
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    // If the RSS list is empty, the the view type is the progress bar cell
    // Otherwise it is always a RSS cell
    @Override
    public int getItemViewType(int position) {
        return rssList.size() == 0 ? PROGRESS_CELL : RSS_CELL;
    }

    // If the list is empty, just get 1 cell, representing the progress cell
    // Otherwise, return the RSS list size
    @Override
    public int getItemCount() {
        return rssList.size() == 0 ? 1 : rssList.size();
    }
}
