package com.monirapps.androidopenclassrooms;

import android.content.Intent;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.rss.fake.FakeNews;

import java.util.List;

/**
 * Created by David et Monireh on 11/11/2016.
 */

public class FakeNewsAdapter extends RecyclerView.Adapter<FakeNewsAdapter.FakeNewsViewHolder> {

    static final class FakeNewsViewHolder extends RecyclerView.ViewHolder{

        public FakeNewsViewHolder(View itemView) {
            super(itemView);
        }
    }

    private List<FakeNews> fakeNews;

    public FakeNewsAdapter(List<FakeNews> fakeNews) {
        this.fakeNews = fakeNews;
    }

    @Override
    public FakeNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FakeNewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fakenewscell, parent, false));
    }

    @Override
    public void onBindViewHolder(final FakeNewsViewHolder holder, int position) {
        final FakeNews fakeNews = this.fakeNews.get(position);
        ((TextView) holder.itemView.findViewById(R.id.fakeNewsTitle)).setText(fakeNews.title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), FakeNewsActivity.class);
                intent.putExtra(FakeNewsActivity.TITLE, fakeNews.title);
                intent.putExtra(FakeNewsActivity.NEWS, fakeNews.htmlContent);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fakeNews.size();
    }
}
