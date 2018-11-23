package com.android.cis195.taras.spaceinvaders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameEndActivity extends AppCompatActivity {
    @BindView(R.id.mainMenuButton) Button menuButton;
    @BindView(R.id.score) TextView playerScore;
    @BindView(R.id.result) TextView result;
    private Player player;
    public static final String PREFS_NAME = "preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        player = new Player(intent.getStringExtra("name"), intent.getIntExtra("score", 0));
        playerScore.setText("Score: " + String.valueOf(player.getScore()));
        result.setText(intent.getStringExtra("result"));
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameEndActivity.this, TitleActivity.class));
            }
        });
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, 0);
        Set<String> players = sharedPrefs.getStringSet("players", new HashSet<String>());
        String toBeRemoved = "";
        for (String s: players) {
            Player p = new Player(s);
            if (p.getName().equals(this.player.getName())) {
                if (player.getScore() > p.getScore()) {
                    toBeRemoved = s;
                } else {
                    player = p;
                }
            }
        }
        players.remove(toBeRemoved);
        players.add(player.toString());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.putStringSet("players", players);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.leaderboard) {
            startActivity(new Intent(GameEndActivity.this, LeaderboardActivity.class));
            return true;
        } else if (item.getItemId() == R.id.newGame) {
            startActivity(new Intent(GameEndActivity.this, PlayerActivity.class));
            return true;
        } else if (item.getItemId() == R.id.mainMenu) {
            startActivity(new Intent(GameEndActivity.this, TitleActivity.class));
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
