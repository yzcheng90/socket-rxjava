package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.scene;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.scene.SceneAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.PlanEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.scene.ScenePresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class SceneListActivity extends BaseActivity<ScenePresent> {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;

    SceneAdapter adapter;
    StateView errorView;

    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
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
        contentLayout.getRecyclerView().gridLayoutManager(context,1);
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
            adapter = new SceneAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<PlanEntity, SceneAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, PlanEntity model, int tag, SceneAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                   /* Bundle bundle = new Bundle();
                    bundle.putString(AppConstant.INFO,new Gson().toJson(model));
                    DeviceSwitchActivity.launch(context,bundle);*/
                }
            });
        }
        return adapter;
    }

    public void showData(List<PlanEntity> model) {

        if(model == null || model.size() == 0){
            errorView.setMsg(AppConstant.SHOW_EMPTY);
            contentLayout.showError();
        }else {
            getAdapter().setData(model);
            contentLayout.getRecyclerView().setPage(1,1);
            if (getAdapter().getItemCount() < 1) {
                contentLayout.showEmpty();
                return;
            }
        }

    }

    public void showError(String msg){
        errorView.setMsg(msg);
        contentLayout.showError();
    }

    public void reLoad(){
        getmPresenter().getCurrentPlanList();
    }

    @Override
    public ScenePresent newP() {
        return new ScenePresent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scene_list;
    }

}
