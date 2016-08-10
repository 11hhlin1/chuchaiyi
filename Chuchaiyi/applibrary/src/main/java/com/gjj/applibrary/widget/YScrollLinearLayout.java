package com.gjj.applibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class YScrollLinearLayout extends LinearLayout {

    private Scroller mScroller;

    public YScrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        Scroller scroller = mScroller;
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public void yScrollTo(int sy, int duration) {
        Scroller scroller = mScroller;
        scroller.startScroll(scroller.getCurrX(), scroller.getCurrY(), 0,
                sy - scroller.getCurrY(), duration);
        invalidate();
    }

    public int getCurrY() {
        return mScroller.getCurrY();
    }

}
