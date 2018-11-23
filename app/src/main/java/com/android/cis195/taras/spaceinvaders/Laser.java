package com.android.cis195.taras.spaceinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by Taras on 12/4/17.
 */

public class Laser extends GameObj implements Comparable<Laser> {

    private LaserType type;
    private boolean inverted;

    public static double WIDTH_SCALE = 9.0 / 700;
    public static double HEIGHT_SCALE = 15.0 / 700;
    public static int VEL_X = 0;
    public static Bitmap[] LASERS = new Bitmap[3];

    // basic constructor for laser. All lasers start right side up
    public Laser(int px, int py, LaserType type,int courtWidth, int courtHeight) {
        super(VEL_X, type.getVel(), px, py,(int)(WIDTH_SCALE * courtWidth),
                (int)(HEIGHT_SCALE * courtHeight), courtWidth, courtWidth);
        this.type = type;
        this.inverted = false;
    }

    public LaserType getType() {
        return this.type;
    }

    public boolean isInverted() {
        return this.inverted;
    }

    public void invert() {
        this.inverted = !this.inverted;
    }

    public int compareTo(Laser laser) {
        return (this.getPx() - laser.getPx()) + (this.getPy() - laser.getPy());
    }


    //paints Laser
    @Override
    public void paint(Canvas canvas) {
        Bitmap bitmapToDraw = null;
        switch (type) {
            case PLAYER: bitmapToDraw = LASERS[0]; break;
            case SLOW: bitmapToDraw = LASERS[1]; break;
            case FAST: bitmapToDraw = LASERS[2];
        }
        if (!inverted || type == LaserType.PLAYER) {
            canvas.drawBitmap(bitmapToDraw, getPx(), getPy(), null);
        } else {
            Matrix matrix = new Matrix();
            matrix.setScale(1, -1);
            matrix.postTranslate(getPx(), getPy() + this.getHeight());
            canvas.drawBitmap(bitmapToDraw, matrix, null);
        }
    }
}
