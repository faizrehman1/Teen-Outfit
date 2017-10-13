package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.faiz.vividways.UI.Activities.MainActivity;

/**
 * Created by AST on 10/12/2017.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
    private int min_distance = 100;
    private float downX, downY, upX, upY;
    private GestureDetector gestureDetector;
    private RecyclerViewClickListener clickListener;
    public RecyclerView rv;
    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        this.rv = rv;
        switch(e.getAction()) { // Check vertical and horizontal touches
            case MotionEvent.ACTION_DOWN: {
                downX = e.getX();
                downY = e.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = e.getX();
                upY = e.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                //HORIZONTAL SCROLL
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    if (Math.abs(deltaX) > min_distance) {
                        // left or right
                        if (deltaX < 0) {
                            this.onLeftToRightSwipe();
                            return true;
                        }
                        if (deltaX > 0) {
                            this.onRightToLeftSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                //VERTICAL SCROLL
                else {
                    if (Math.abs(deltaY) > min_distance) {
                        // top or down
                        if (deltaY < 0) {
                            this.onTopToBottomSwipe();
                            return true;
                        }
                        if (deltaY > 0) {
                            this.onBottomToTopSwipe();
                            return true;
                        }
                    } else {
                        //not long enough swipe...
                        return false;
                    }
                }
                return false;
            }
        }
        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public void onLeftToRightSwipe(){
        Toast.makeText(rv.getContext(),"left to right",
                Toast.LENGTH_SHORT).show();
    }

    public void onRightToLeftSwipe() {
        Toast.makeText(rv.getContext(),"right to left",
                Toast.LENGTH_SHORT).show();
    }

    public void onTopToBottomSwipe() {
        Toast.makeText(rv.getContext(),"top to bottom",
                Toast.LENGTH_SHORT).show();
    }

    public void onBottomToTopSwipe() {
        Toast.makeText(rv.getContext(),"bottom to top",
                Toast.LENGTH_SHORT).show();
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}