package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by czx on 2016/12/31.
 */

public class StateView extends LinearLayout {

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.icon)
    ImageView icon;

    //初始化接口变量
    StateOnClick clickEvent = null;

    public StateView(Context context) {
        super(context);
        setupView(context);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public StateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.view_state, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }
    }

    public void setIconDisplay(Boolean b){
        if(b){
            icon.setVisibility(View.VISIBLE);
        }else{
            icon.setVisibility(View.GONE);
        }
    }

    public void setIcon(int resource) {
        icon.setImageResource(resource);
    }

    @OnClick({ R.id.layout_id})
    public void layoutClick(View view){
        clickEvent.onStateOnClick(view);
    }

    //自定义控件的自定义事件
    public void setClickEvent(StateOnClick iBack){
        clickEvent = iBack;
    }

    //定义一个接口
    public interface StateOnClick{
        public void onStateOnClick(View v);
    }

}
