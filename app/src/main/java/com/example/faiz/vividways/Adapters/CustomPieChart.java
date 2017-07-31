package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

/**
 * Created by Faiz on 7/26/2017.
 */

public class CustomPieChart extends View {
    float start = 0;
    int width;
    int[] data;
    int cx, cy;
    int numberOfparts;//it tells many data or item will be placed in chart
    private int[] color;
    private int item_count;

    public CustomPieChart(Context context, int numOfItems, int[] data, int[] color,int item_count) {
        super(context);
        setFocusable(true);
        this.numberOfparts = numOfItems;
        this.data = data;
        this.color = color;
        this.item_count = item_count;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(0);
        p.setStyle(Paint.Style.FILL);
        float[] scaledValues = scale();

        RectF rectF = new RectF(0, 0, getWidth(), getWidth());

        p.setColor(Color.BLACK);
        for (int i = 0; i < numberOfparts; i++) {
            p.setColor(color[i]);
            p.setStyle(Paint.Style.FILL);

            canvas.drawArc(rectF, start, scaledValues[i], true, p);
            start = start + scaledValues[i];
        }

        Paint cenPaint = new Paint();
        int radius = getWidth() / 2 - 100;
        cenPaint.setStyle(Paint.Style.FILL);
        cenPaint.setColor(Color.WHITE);
        cx = cy = getWidth() / 2;
        canvas.drawCircle(cx, cy, radius, cenPaint);
        cenPaint.setColor(Color.parseColor("#da59a8"));
        cenPaint.setTextSize(80);
        cenPaint.setTextAlign(Paint.Align.CENTER);
        cenPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(String.valueOf(item_count), cx, cy+15, cenPaint);
    }

    private float[] scale() {
        float[] scaledValues = new float[this.data.length];
        float total = getTotal(); //Total all values supplied to the chart
        for (int i = 0; i < this.data.length; i++) {
            scaledValues[i] = (this.data[i] / total) * 360; //Scale each value
        }
        return scaledValues;
    }

    private float getTotal() {
        float total = 0;
        for (float val : this.data)
            total += val;
        return total;
    }
}
