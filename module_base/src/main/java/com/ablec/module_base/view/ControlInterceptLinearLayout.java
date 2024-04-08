package com.ablec.module_base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/11/16 10:25
 */
public class ControlInterceptLinearLayout extends LinearLayoutCompat {

    private String TAG = "ControlInterceptLinearLayout";

    /**
     * 是否拦截子View
     */
    private boolean interceptClick;
    private GestureDetector gestureDetector;

    public ControlInterceptLinearLayout(Context context) {
        super(context);
        init();

    }

    public ControlInterceptLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ControlInterceptLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setClickable(true);
        //只消费点击事件的识别器
        GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                Log.e(TAG,"双击");
                return true;
            }
        };
        gestureDetector = new GestureDetector(getContext(), onGestureListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (interceptClick ) {
            LogUtils.d("拦截事件 by flag");
            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean isInterceptClick() {
        return interceptClick;
    }

    public void setInterceptClick(boolean intercept) {
        this.interceptClick = intercept;
    }




}
