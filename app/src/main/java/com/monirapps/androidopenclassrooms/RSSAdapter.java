package com.monirapps.androidopenclassrooms;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.RSSViewHolder> {

    public static class RSSViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        private TextView channel;

        private ImageView image;

        public RSSViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            channel = (TextView) itemView.findViewById(R.id.channel);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public static class RSS implements Serializable{
        public final String channel;
        public final String title;
        public final String link;
        public final String image;

        public RSS(String channel, String title, String link, String image) {
            this.channel = channel;
            this.title = title;
            this.link = link;
            this.image = image;
        }
    }

    private List<RSS> rssList;

    public RSSAdapter(List<RSS> rssList) {
        this.rssList = rssList;
    }

    @Override
    public RSSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RSSViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rsscell, parent, false));
    }

    @Override
    public void onBindViewHolder(final RSSViewHolder holder, int position) {
        final RSS rss = rssList.get(position);
        holder.title.setText(rss.title);
        holder.channel.setText(rss.channel);
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

    @Override
    public int getItemCount() {
        return rssList.size();
    }
}
