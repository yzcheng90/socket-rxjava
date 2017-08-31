package com.sukeintel.iexhibition.mobile.app.works.mvp.ui;

import android.os.Bundle;
import android.widget.FrameLayout;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import butterknife.BindView;

public class SplashActivity extends BaseActivity {


    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

}
