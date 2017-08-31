package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;

/**
 * Created by czx on 2017/8/24.
 */

public class WaveView extends View {
    int controlLength;
    int dx;
    int itemLength;
    Paint paint;
    Paint paintback;
    Paint paintback1;
    Path path;
    Path pathback;
    Path pathback1;
    int screen_height;
    int screen_width;

    public WaveView(Context paramContext)
    {
        this(paramContext, null);
    }

    public WaveView(Context paramContext, AttributeSet paramAttributeSet)
    {
        this(paramContext, paramAttributeSet, 0);
    }

    public WaveView(Context paramContext, AttributeSet paramAttributeSet, int paramInt){
        super(paramContext, paramAttributeSet, paramInt);
        WindowManager localWindowManager = (WindowManager)paramContext.getSystemService(Context.WINDOW_SERVICE);
        this.screen_width = localWindowManager.getDefaultDisplay().getWidth();
        this.screen_height = localWindowManager.getDefaultDisplay().getHeight();
        this.itemLength = this.screen_height;
        this.pathback1 = new Path();
        this.paintback1 = new Paint();
        this.paintback1.setColor(Color.parseColor("#BDE9FA"));
        this.paintback1.setStyle(Paint.Style.FILL_AND_STROKE);
        this.path = new Path();
        this.paint = new Paint();
        this.paint.setColor(Color.parseColor("#98DEF9"));
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.pathback = new Path();
        this.paintback = new Paint();
        this.paintback.setColor(getResources().getColor(R.color.colorPrimary));
        this.paintback.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        pathback.reset();
        controlLength = itemLength / 3;
        path.moveTo(-itemLength + dx, 500);
        pathback.moveTo((float) (-itemLength * 1.25+ dx), 500);

        drawWave(canvas, path, paint);
        drawWave(canvas, pathback, paintback);
    }

    public void drawWave(Canvas canvas, Path drawpath, Paint drawpaint) {
        for (int i = 0; i <= screen_width / itemLength + 2; i++) {
            drawpath.rQuadTo(controlLength, 30, itemLength / 2, 0);
            drawpath.rQuadTo(controlLength, -30, itemLength / 2, 0);
        }
        drawpath.lineTo(screen_width, 0);
        drawpath.lineTo(0, 0);
        drawpath.close();
        canvas.drawPath(drawpath, drawpaint);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, itemLength);
        animator.setDuration(2000);
        //设置动画无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
