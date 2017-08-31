package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.ResourceEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.ToggleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.router.Router;

public class DeviceSwitchActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toggleView)
    ToggleView toggleView;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
            String info = getIntent().getExtras().getString(AppConstant.INFO);
            ResourceEntity menuEntity = new Gson().fromJson(info,ResourceEntity.class);
            initToolbar(menuEntity.getName());

        toggleView.setSwitchBackgroundResouce(R.mipmap.switch_background);
        toggleView.setSlideButtonResouce(R.mipmap.slide_button);
        toggleView.setSwitchState(true);

        //状态监听
        toggleView.setOnSwitchStateUpdateListener(new ToggleView.OnSwitchStateUpdateListener() {
            @Override
            public void onStateUpdate(boolean staet) {
                if (staet){
                    //Toast.makeText(MainActivity.this, "开关打开", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(MainActivity.this, "开关关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_switch;
    }

    public static void launch(Activity activity,Bundle bundle) {
        Router.newIntent(activity)
                .to(DeviceSwitchActivity.class)
                .data(bundle)
                .launch();
    }

}
