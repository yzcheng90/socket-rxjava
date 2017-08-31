package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control;

import android.os.Bundle;
import android.view.KeyEvent;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;

import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * Created by czx on 2017/8/25.
 */

public class PPTModelFragment extends XFragment {

    public static EquipmentEntity equipmentEntity = null;
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_control_ppt_model_fragment;
    }

    @Override
    public Object newP() {
        return null;
    }

    public static PPTModelFragment newInstance(EquipmentEntity entity) {
        equipmentEntity = entity;
        return new PPTModelFragment();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return true;
    }
}
