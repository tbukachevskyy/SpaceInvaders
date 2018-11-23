package com.android.cis195.taras.spaceinvaders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Taras on 12/4/17.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>{
    private ArrayList<Player> scores;

    public PlayerAdapter(ArrayList<Player> scores) {
        this.scores = scores;
        Collections.sort(this.scores);
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.score_item_layout, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        Player score = scores.get(position);
        holder.name.setText(score.getName());
        holder.score.setText(String.valueOf(score.getScore()));
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    protected static class PlayerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.playerName) TextView name;
        @BindView(R.id.playerPoints) TextView score;

        public PlayerViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
