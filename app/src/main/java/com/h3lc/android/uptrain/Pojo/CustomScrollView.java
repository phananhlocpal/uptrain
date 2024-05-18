package com.h3lc.android.uptrain.Pojo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getPointerCount() > 1) {
            // Nếu phát hiện đa điểm chạm, không chặn sự kiện để Google Map xử lý
            return false;
        } else {
            // Với các sự kiện khác, để ScrollView xử lý
            return super.onInterceptTouchEvent(ev);
        }
    }
}

