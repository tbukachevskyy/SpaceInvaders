package com.android.cis195.taras.spaceinvaders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderboardActivity extends AppCompatActivity {
@BindView(R.id.list) RecyclerView list;
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        players = new ArrayList<Player>();
        SharedPreferences sharedPrefs = getSharedPreferences(GameEndActivity.PREFS_NAME, 0);
        Set<String> strings = sharedPrefs.getStringSet("players", new HashSet<String>());
        for (String s: strings) {
            players.add(new Player(s));
        }
        PlayerAdapter adapter = new PlayerAdapter(players);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.newGame) {
            startActivity(new Intent(LeaderboardActivity.this, PlayerActivity.class));
            return true;
        } else if (item.getItemId() == R.id.mainMenu) {
            startActivity(new Intent(LeaderboardActivity.this, TitleActivity.class));
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
