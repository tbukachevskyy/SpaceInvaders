package com.android.cis195.taras.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Collection;

/**
 * Created by Taras on 12/4/17.
 */

public abstract class Invader extends Alien {

    public static final double INIT_VEL_X_SCALE = 10.0 / 700;
    private boolean open; // records whether the invader is in open position or not
    private boolean alive; // records whether the invader is alive or not


    public Invader(int vx, int px, int py, int width, int height, int courtWidth,
                   int courtHeight,int pointVal) {
        super(vx, px, py, width, height, courtWidth, courtHeight, pointVal);
        this.alive = true;
        this.open = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isOpen() {
        return open;
    }

    public void changeState() {
        open = !open;
    }

    public void die() {
        alive = false;
    }

    //All invaders are able to shoot
    public abstract Laser shoot();
}

//======================
//INVADER SUBCLASS
//======================
class Invader0 extends Invader {

    public static double WIDTH_SCALE = 36.0 / 700.0;
    public static double HEIGHT_SCALE = 24.0 / 700.0;
    public static int POINT_VAL = 10;
    public static Bitmap OPEN_FORM;
    public static Bitmap CLOSED_FORM;

    public Invader0(int px, int py, int courtWidth, int courtHeight) {
        super((int)(INIT_VEL_X_SCALE * courtWidth), px, py, (int)(WIDTH_SCALE * courtWidth),
                (int)(HEIGHT_SCALE * courtHeight), courtWidth, courtHeight, POINT_VAL);
    }

    //Creates a new laser below invader which can be either fast or slow
    public Laser shoot() {
        LaserType laserType;
        if (Math.random() < 0.3) {
            laserType = LaserType.FAST;
        }
        else {
            laserType = LaserType.SLOW;
        }
        if (this.isAlive())  {
            return new Laser(Laser.centered(this.getPx(), getWidth(), (int)(Laser.WIDTH_SCALE * getCourtWidth())),
                    this.getPy() + getHeight() + 1, laserType, this.getCourtWidth(), this.getCourtHeight());
        }
        return null;
    }

    //Draws invader based on the canvas
    public void paint(Canvas canvas) {
        if (isAlive()) {
            if (isOpen()) canvas.drawBitmap(OPEN_FORM, this.getPx(), this.getPy(), null);
            else canvas.drawBitmap(CLOSED_FORM, this.getPx(), this.getPy(), null);
        }
    }
}

// ======================
// INVADER SUBCLASS
// ======================

class Invader1 extends Invader {

    public static double WIDTH_SCALE = 33.0 / 700;
    public static double HEIGHT_SCALE = 24.0 / 700;
    public static int POINT_VAL = 20;
    public static Bitmap OPEN_FORM;
    public static Bitmap CLOSED_FORM;

    public Invader1(int px, int py, int courtWidth, int courtHeight) {
        super((int)(INIT_VEL_X_SCALE * courtWidth), px, py, (int)(WIDTH_SCALE * courtWidth),
                (int)(HEIGHT_SCALE * courtHeight), courtWidth, courtHeight, POINT_VAL);
    }

    //Creates a new laser below invader which can be either fast or slow
    public Laser shoot() {
        LaserType laserType;
        if (Math.random() < 0.4) {
            laserType = LaserType.FAST;
        }
        else {
            laserType = LaserType.SLOW;
        }
        if (this.isAlive())  {
            return new Laser(Laser.centered(this.getPx(), getWidth(), (int)(Laser.WIDTH_SCALE * getCourtWidth())),
                    this.getPy() + getHeight() + 1, laserType, this.getCourtWidth(), this.getCourtHeight());
        }
        return null;
    }

    //Draws invader based on the canvas
    public void paint(Canvas canvas) {
        if (isAlive()) {
            if (isOpen()) canvas.drawBitmap(OPEN_FORM, this.getPx(), this.getPy(), null);
            else canvas.drawBitmap(CLOSED_FORM, this.getPx(), this.getPy(), null);
        }
    }
}

// ======================
// INVADER SUBCLASS
// ======================

class Invader2 extends Invader {

    public static double WIDTH_SCALE = 24.0 / 700;
    public static double HEIGHT_SCALE = 24.0 / 700;
    public static int POINT_VAL = 30;
    public static Bitmap OPEN_FORM;
    public static Bitmap CLOSED_FORM;

    public Invader2(int px, int py, int courtWidth, int courtHeight) {
        super((int)(INIT_VEL_X_SCALE * courtWidth), px, py, (int)(WIDTH_SCALE * courtWidth),
                (int)(HEIGHT_SCALE * courtHeight), courtWidth, courtHeight, POINT_VAL);
    }

    //Creates a new laser below invader which can be either fast or slow
    public Laser shoot() {
        LaserType laserType;
        if (Math.random() < 0.5) {
            laserType = LaserType.FAST;
        }
        else {
            laserType = LaserType.SLOW;
        }
        if (this.isAlive())  {
            return new Laser(Laser.centered(this.getPx(), getWidth(), (int)(Laser.WIDTH_SCALE * getCourtWidth())),
                    this.getPy() + getHeight() + 1, laserType, this.getCourtWidth(), this.getCourtHeight());
        }
        return null;
    }

    //Draws invader based on the canvas
    public void paint(Canvas canvas) {
        if (isAlive()) {
            if (isOpen()) canvas.drawBitmap(OPEN_FORM, this.getPx(), this.getPy(), null);
            else canvas.drawBitmap(CLOSED_FORM, this.getPx(), this.getPy(), null);
        }
    }
}
