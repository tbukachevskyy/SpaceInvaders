package com.android.cis195.taras.spaceinvaders;

/**
 * Created by Taras on 12/4/17.
 */

public abstract class Alien extends GameObj {
    public static int INIT_VEL_Y = 0; //all Aliens start with an initial y velocity of 0
    private int pointVal; //point value of alien


    public Alien(int vx, int px, int py, int width, int height, int courtWidth,
                 int courtHeight, int pointVal) {
        super(vx, INIT_VEL_Y, px, py, width, height, courtWidth, courtHeight);
        this.pointVal = pointVal;
    }

    //getter for point value
    public int getPointVal() {
        return this.pointVal;
    }
}
