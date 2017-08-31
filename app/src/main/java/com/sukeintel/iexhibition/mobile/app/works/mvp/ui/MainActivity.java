package com.sukeintel.iexhibition.mobile.app.works.mvp.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.MainMenuAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.WaveView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.mywave)
    WaveView mywave;
    @BindView(R.id.head_layout)
    LinearLayout headLayout;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.body_layout)
    LinearLayout bodyLayout;
    MainMenuAdapter adapter;
    StateView errorView;

    @Override
    public void initData(Bundle savedInstanceState) {
        initAdapter();
        reLoad();
        mywave.startAnim();
    }


    private void initAdapter() {
        contentLayout.getRecyclerView().gridLayoutManager(context,3);
        contentLayout.getRecyclerView().setAdapter(getAdapter());
        contentLayout.getRecyclerView().setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                reLoad();
            }

            @Override
            public void onLoadMore(int page) {

            }
        });

        if (errorView == null) {
            errorView = new StateView(context);
        }

        errorView.setClickEvent((v)->{reLoad();}); //设置了点击事件
        contentLayout.errorView(errorView);
        contentLayout.loadingView(View.inflate(context, R.layout.view_loading, null));
        contentLayout.showLoading();
        contentLayout.getRecyclerView().removeAllFootView();
    }


    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new MainMenuAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<MenuEntity, MainMenuAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, MenuEntity model, int tag, MainMenuAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    try{
                        Intent intentActivity = new Intent();
                        ComponentName componentName = new ComponentName( context.getPackageName(),model.getUrl());
                        intentActivity.setComponent(componentName);
                        intentActivity.putExtra("model_menu",new Gson().toJson(model));
                        context.startActivity(intentActivity);
                    }catch (ActivityNotFoundException e){
                        AlertView.showAlert(context,"正在开发中!");
                    }
                }
            });
        }
        return adapter;
    }

    public void showData(List<MenuEntity> model) {
        getAdapter().setData(model);
        contentLayout.getRecyclerView().setPage(1,1);
        if (getAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
    }


    private List<MenuEntity> createData() {
        List<MenuEntity> list = new ArrayList<>();
        list.add(new MenuEntity(R.mipmap.power_icon,"","1","电源控制","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power.PowerControlActivity"));
        list.add(new MenuEntity(R.mipmap.current_icon,"","2","实时监控","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.monitoring.CurrentMonitoringActivity"));
        list.add(new MenuEntity(R.mipmap.screen_icon,"","3","展屏管理","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.screen.ScreenLEDListActivity"));
        list.add(new MenuEntity(R.mipmap.lecture_icon,"","4","演讲者模式","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.LectureActivity"));
        list.add(new MenuEntity(R.mipmap.scene_icon,"","5","情景模式","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.scene.SceneListActivity"));
        list.add(new MenuEntity(R.mipmap.setting_icon,"","6","系统设置","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.setting.SettingActivity"));

        return list;
    }


    public void reLoad(){
        showData(createData());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


}
