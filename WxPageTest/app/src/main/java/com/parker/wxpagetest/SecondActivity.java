package com.parker.wxpagetest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class SecondActivity extends AppCompatActivity {
    private View parent;
    private float downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        parent = findViewById(android.R.id.content);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (downX < 100) {
                    float moveX = event.getX() - downX;
                    if (moveX > 0) {
                        parent.setX(moveX);
                    }
                }
                break;
            default:
                float upX = event.getX();
                finishOrGoBack(upX);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void finishOrGoBack(float upX) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        float x = parent.getX();
        if (upX < widthPixels / 4) {
            float time = upX / (widthPixels / 4) * 200;
            ObjectAnimator.ofFloat(parent, "x", x, 0).setDuration((long) time).start();
        } else {
            float time = (widthPixels - upX) / (widthPixels * 3 / 4) * 200;
            ObjectAnimator end = ObjectAnimator.ofFloat(parent, "x", x, widthPixels).setDuration((long) time);
            end.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    overridePendingTransition(0, 0);
                    finish();
                }
            });
            end.start();
        }
    }
}
