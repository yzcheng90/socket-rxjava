package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.screen;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.ResourceEntity;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class ScreenSetContextActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.more_icon)
    ImageView moreIcon;
    @BindView(R.id.more_btn)
    LinearLayout moreBtn;
    @BindView(R.id.content)
    EditText content;


    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        moreIcon.setImageResource(R.mipmap.submit_btn_y);
        String info = getIntent().getExtras().getString(AppConstant.INFO);
        ResourceEntity menuEntity = new Gson().fromJson(info,ResourceEntity.class);
        initToolbar("设置显示内容");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_set_context;
    }

    public static void launch(Activity activity, Bundle bundle) {
        Router.newIntent(activity)
                .to(ScreenSetContextActivity.class)
                .data(bundle)
                .launch();
    }

}
