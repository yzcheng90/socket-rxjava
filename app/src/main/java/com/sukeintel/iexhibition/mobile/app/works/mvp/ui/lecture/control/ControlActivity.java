package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.flyco.tablayout.SegmentTabLayout;
import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class ControlActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabLayout)
    SegmentTabLayout tabLayout;
    @BindView(R.id.page_frame)
    FrameLayout pageFrame;

    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    String[] titles = {"鼠标模式", "PPT模式"};

    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String info = getIntent().getExtras().getString(AppConstant.INFO);
        EquipmentEntity entity = new Gson().fromJson(info,EquipmentEntity.class);
        initToolbar("控制模式");
        fragmentList.clear();
        fragmentList.add(MouseModelFragment.newInstance(entity));
        fragmentList.add(PPTModelFragment.newInstance(entity));
        tabLayout.setTabData(titles, (FragmentActivity) context,R.id.page_frame,fragmentList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_control;
    }

    public static void launch(Activity activity, Bundle bundle) {
        Router.newIntent(activity)
                .to(ControlActivity.class)
                .data(bundle)
                .launch();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        for (int i=0;i<fragmentList.size();i++){
            if(fragmentList.get(i) instanceof MouseModelFragment){
                ((MouseModelFragment) fragmentList.get(i)).onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
