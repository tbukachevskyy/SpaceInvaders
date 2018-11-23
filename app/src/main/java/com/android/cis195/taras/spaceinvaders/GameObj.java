package com.android.cis195.taras.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
* Created by Taras on 12/4/17.
*/


public abstract class GameObj {
    //position of object
    private int px;
    private int py;

    private int courtWidth;
    private int courtHeight;

    //size of object
    private int width;
    private int height;

    // velocity of object
    private int vx;
    private int vy;

    private Rect rect;


    public GameObj(int vx, int vy, int px, int py, int width, int height, int courtWidth,
                   int courtHeight) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width  = width;
        this.height = height;
        this.courtHeight = courtHeight;
        this.courtWidth = courtWidth;
        this.rect = new Rect(px, py, width + px, height = py);
    }

    public int getCourtWidth() {
        return courtWidth;
    }

    public int getCourtHeight() {
        return courtHeight;
    }

    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Rect getRect() {
        return this.rect;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }


    public void move() {
        this.px += this.vx;
        this.py += this.vy;
    }

    //checks for intersection
    public boolean intersects(GameObj that) {
        return (this.px + this.width >= that.px
                && this.py + this.height >= that.py
                && that.px + that.width >= this.px
                && that.py + that.height >= this.py);
    }

    /**
     * Determine whether the game object will hit a wall in the next time step. If so, return the
     * direction of the wall in relation to this game object.
     *
     * @return Direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (this.px + this.vx < 0) {
            return Direction.LEFT;
        } else if (this.px + this.vx + this.getWidth() > this.courtWidth) {
            return Direction.RIGHT;
        }

        if (this.py + this.vy < 0) {
            return Direction.UP;
        } else if (this.py + this.vy > this.courtHeight) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    // Checks whether an object is outside the borders of the game
    public boolean outsideBorders() {
        if (this.px + this.vx + this.width < 0) return true;
        if (this.px + this.vx > this.courtWidth) return true;
        if (this.py + this.vy + this.height < 0) return true;
        if (this.py + this.vy > this.courtHeight) return true;
        return false;
    }

    /* Returns the px needed for object2 in order for it to be
     * be vertically aligned with object1 */
    public static int centered(int px1, int width1, int width2) {
        return px1 + (width1 / 2) - (width2 / 2);
    }


    //paints the object
    public abstract void paint(Canvas canvas);
}
