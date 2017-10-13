package com.example.faiz.vividways.Models;

/**
 * Created by AST on 10/12/2017.
 */

public class Axis {
    public long x_axis;
    public long y_axis;

    public Axis(long x_axis, long y_axis) {
        this.x_axis = x_axis;
        this.y_axis = y_axis;
    }

    public long getX_axis() {
        return x_axis;
    }

    public void setX_axis(long x_axis) {
        this.x_axis = x_axis;
    }

    public long getY_axis() {
        return y_axis;
    }

    public void setY_axis(long y_axis) {
        this.y_axis = y_axis;
    }
}
