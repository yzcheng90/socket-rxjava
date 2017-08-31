package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.MainMenuAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.FunSwitch;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class PowerControlActivity extends BaseActivity {


    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.switchButton)
    FunSwitch switchButton;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.status_bar)
    LinearLayout statusBar;

    MainMenuAdapter adapter;
    StateView errorView;

    public void initToolbar(String mTitle) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
        statusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BarUtils.getStatusBarHeight(context)));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String info = getIntent().getExtras().getString("model_menu");
        MenuEntity menuEntity = new Gson().fromJson(info,MenuEntity.class);
        initToolbar(menuEntity.getName());
        initAdapter();
        reLoad();
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
        list.add(new MenuEntity(R.mipmap.illumination_icon,"","1","照明设备","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power.LampListActivity"));
        list.add(new MenuEntity(R.mipmap.show_screen_icon,"","2","展屏设备","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power.ScreenListActivity"));
        list.add(new MenuEntity(R.mipmap.computer_icon,"","2","主机设备","com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power.ComputerListActivity"));

        return list;
    }


    public void reLoad(){
        showData(createData());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_power;
    }

}
