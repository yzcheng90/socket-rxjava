package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by czx on 2017/8/25.
 * 自定义开关
 * Android的界面流程
 * 测量 摆放 绘制
 * measure -> layout -> draw
 * onMeasure -> onLayout -> onDraw重写这些方法，实现自定义控件
 * View
 * onMeasure(在这个方法里面指定自己的宽高) -> onDraw(绘制自己的内容)
 * ViewGroup
 * onMeasure(指定自己的宽高,所有子View的宽高) ->onLayout(摆放所有的子View) -> onDraw(绘制自己的内容)
 */

public class ToggleView extends View {
    private Bitmap switchBitmap;
    private Bitmap decodeResource;
    private  Paint paint;
    private boolean mSwitchState = false; // 开关状态, 默认false
    private float currentX;
    /**
     * 用于代码创建控件
     * @param context
     */
    public ToggleView(Context context) {
        super(context);
        init();
    }

    /**
     * 用于XML里使用，可指定自定义属性
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 用于XML里使用，可指定自定义属性，如果指定了样式，则走此函数
     * @param context
     * @param attrs
     */
    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        //自定义，为了在xml中直接使用图片资源
       /* String namespace = "http://schemas.android.com/apk/res/cn.buildworld.toggleview";
        int switch_background = attrs.getAttributeResourceValue(namespace, "switch_background", -1);
        int slide_background = attrs.getAttributeResourceValue(namespace, "slide_background", -1);
        mSwitchState = attrs.getAttributeBooleanValue(namespace,"switch_background",false);

        System.out.println("slide_background"+slide_background);
        setSwitchBackgroundResouce(switch_background);
        setSlideButtonResouce(slide_background);*/
    }


    private void init() {
        paint  = new Paint();
    }



    /**
     * 设置背景图片
     * @param switch_background
     */
    public void setSwitchBackgroundResouce(int switch_background) {

        switchBitmap = BitmapFactory.decodeResource(getResources(), switch_background);

    }

    /**
     * 设置滑块图片资源
     * @param slide_button
     */
    public void setSlideButtonResouce(int slide_button) {
        decodeResource = BitmapFactory.decodeResource(getResources(), slide_button);
    }


    /**
     * 设置开关状态
     * @param b
     */
    public void setSwitchState(boolean b) {
        this.mSwitchState = b;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(switchBitmap.getWidth(),switchBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1、绘制背景

        canvas.drawBitmap(switchBitmap,0,0,paint);

        //2、滑块
        //根据当前用户触摸到的位置滑块

        if (isTouchMode){
            float newLeft = currentX -decodeResource.getWidth() /2.0f;

            int maxLeft = switchBitmap.getWidth() - decodeResource.getWidth();

            if (newLeft <0){
                newLeft = 0;//左边范围
            }else if (newLeft >maxLeft){
                newLeft = maxLeft;//右边范围
            }
            canvas.drawBitmap(decodeResource,newLeft,0,paint);
        }
        else {
            //根据开关状态，直接设置图片位置
            if (mSwitchState) {//开
                int newLeft = switchBitmap.getWidth() - decodeResource.getWidth();
                canvas.drawBitmap(decodeResource, newLeft, 0, paint);
            } else {//关
                canvas.drawBitmap(decodeResource, 0, 0, paint);
            }
        }
    }

    boolean isTouchMode = false;
    //重写触摸事件，响应用户的触摸

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouchMode = true;
                System.out.println("event: ACTION_DOWN: " + event.getX());
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("event: ACTION_MOVE: " + event.getX());
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchMode = false;
                System.out.println("event: ACTION_UP: " + event.getX());
                currentX = event.getX();

                float center = switchBitmap.getWidth() /2.0f;

                //根据当前按下的位置，和控件中心的位置进行比较
                boolean state = currentX >center;

                //当状态发生改变，就会通知主界面
                if (state != mSwitchState && onSwitchStateUpdateListener != null){
                    onSwitchStateUpdateListener.onStateUpdate(state);
                }

                mSwitchState = state;

                break;
            default:
                break;
        }

        invalidate();//会引发onDraw被调用，里边的变量会重新生效.界面会更新
        return true;
    }


    //设置监听状态

    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;
    public interface OnSwitchStateUpdateListener {
        //状态回调，把当前状态传送出去
        void onStateUpdate(boolean staet);
    }

    public void setOnSwitchStateUpdateListener(OnSwitchStateUpdateListener onSwitchStateUpdateListener){
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;

    }
}
