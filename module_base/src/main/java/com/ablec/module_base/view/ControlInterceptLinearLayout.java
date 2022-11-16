package com.ablec.module_base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/11/16 10:25
 */
public class ControlInterceptLinearLayout extends LinearLayoutCompat {

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
        //只消费点击事件的识别器
        GestureDetector.OnGestureListener onGestureListener = new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtils.d("消费点击事件》》》》");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };
        gestureDetector = new GestureDetector(getContext(), onGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (interceptClick && gestureDetector.onTouchEvent(ev)) {
            LogUtils.d("拦截事件点击");
            return true;
        }
        LogUtils.d("拦截事件滑动");
        return super.onInterceptTouchEvent(ev);
    }


    public boolean isInterceptClick() {
        return interceptClick;
    }

    public void setInterceptClick(boolean intercept) {
        this.interceptClick = intercept;
    }


}
