package com.ccy.chuchaiyi.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ccy.chuchaiyi.R;


public class EditTextWithDel extends EditText {

    private Drawable mDelAbleUnPress;
    private Drawable mDelAblePress;
    private Drawable mRightDrawable;
    private Drawable mLeftDrawable;
    private Drawable mTopDrawable;
    private Drawable mBottomDrawable;
    private Rect mRect;

    public EditTextWithDel(Context context) {
        super(context);
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRect = new Rect();
        Resources res = getResources();
        mDelAbleUnPress = res.getDrawable(R.mipmap.login_img_del_un);
        mDelAblePress = res.getDrawable(R.mipmap.login_img_del_pr);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!hasFocus() || TextUtils.isEmpty(getText().toString())) {
                    setDrawable(null);
                } else {
                    setDrawable(mDelAbleUnPress);
                }
            }
        });
        setDrawable(null);

        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !TextUtils.isEmpty(getText().toString())) {
                    setDrawable(mDelAbleUnPress);
                } else {
                    setDrawable(null);
                }
            }
        });
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top,
            Drawable right, Drawable bottom) {
        mLeftDrawable = left;
        mTopDrawable = top;
        mBottomDrawable = bottom;
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 设置删除图片
     */
    private void setDrawable(Drawable delDrawable) {
        if (mRightDrawable == delDrawable) {
            return;
        }
        mRightDrawable = delDrawable;
        setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable, mTopDrawable, mRightDrawable,
                mBottomDrawable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDelAblePress != null) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = mRect;
            getGlobalVisibleRect(rect);
            rect.left = rect.right - getPaddingRight() - getTotalPaddingRight();
            if (rect.contains(eventX, eventY)) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                } else {
                    setDrawable(mDelAblePress);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
