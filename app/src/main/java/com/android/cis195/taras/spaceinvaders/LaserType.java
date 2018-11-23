package com.android.cis195.taras.spaceinvaders;

/**
 * Created by Taras on 12/4/17.
 */

public enum LaserType {
    PLAYER(-20), FAST(12), SLOW(7);

    private final int velocity;
    LaserType(int velocity) {
        this.velocity = velocity;
    }

    public int getVel() {
        return this.velocity;
    }
}
