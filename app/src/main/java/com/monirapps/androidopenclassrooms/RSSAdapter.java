package com.monirapps.androidopenclassrooms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by David et Monireh on 17/11/2016.
 */

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.RSSViewHolder> {

    public static class RSSViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        private ImageView image;

        public RSSViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public static class RSS{
        public final String title;
        public final String text;
        public final String image;

        public RSS(String title, String text, String image) {
            this.title = title;
            this.text = text;
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
    public void onBindViewHolder(RSSViewHolder holder, int position) {
        RSS rss = rssList.get(position);
        holder.title.setText(rss.title);
        Picasso.with(holder.itemView.getContext()).load(rss.image).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Send to news activity
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssList.size();
    }
}
