package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by czx on 2017/8/25.
 */

public class XLinearLayout extends LinearLayout {

    private InputWindowListener listener;

    public XLinearLayout(Context context) {
        super(context);
    }

    public XLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(listener != null){
            if (oldh > h) {
                listener.show();
            } else{
                listener.hidden();
            }
        }

    }
    public void setListener(InputWindowListener listener) {
        this.listener = listener;
    }

    public interface InputWindowListener {
        void show();

        void hidden();
    }
}
