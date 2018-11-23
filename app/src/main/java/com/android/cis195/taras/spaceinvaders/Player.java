package com.android.cis195.taras.spaceinvaders;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Taras on 12/4/17.
 */

public class Player implements Comparable<Player>{
    private int score;
    private String name;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Player(String s) {
        String[] components = s.split(String.valueOf('\0'));
        this.name = components[0];
        this.score = Integer.parseInt(components[1]);
    }


    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + String.valueOf('\0') + String.valueOf(score);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Player))  return false;
        Player p = (Player) o;
        if (p.getName().equals(this.name)) return true;
        return false;
    }

    @Override
    public int compareTo(@NonNull Player player) {
        return player.getScore() - this.getScore();
    }
}
