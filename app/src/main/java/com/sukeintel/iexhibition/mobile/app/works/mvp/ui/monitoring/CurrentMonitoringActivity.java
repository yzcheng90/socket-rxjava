package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.monitoring;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.monitoring.MonitoringAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.CameraEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.monitoring.MonitoringPresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class CurrentMonitoringActivity extends BaseActivity<MonitoringPresent> {

    @BindView(R.id.status_bar)
    LinearLayout statusBar;
    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    MonitoringAdapter adapter;
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
        contentLayout.getRecyclerView().useDefLoadMoreView();
    }


    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new MonitoringAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<CameraEntity, MonitoringAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, CameraEntity model, int tag, MonitoringAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    Bundle bundle = new Bundle();
                    bundle.putString("info",new Gson().toJson(model));
                    PlayVideoActivity.launch(context,bundle);
                }
            });
        }
        return adapter;
    }

    public void showData(List<CameraEntity> model,int pageIndex) {

        if(model == null || model.size() == 0){
            errorView.setMsg(AppConstant.SHOW_EMPTY);
            contentLayout.showError();
        }else {
            getAdapter().setData(model);
            contentLayout.getRecyclerView().setPage(pageIndex,1);
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

    @Override
    public MonitoringPresent newP() {
        return new MonitoringPresent();
    }

    public void reLoad(){
        getmPresenter().getCameraResourceListOnPage(1, AppConstant.PAGESIZE);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_current_monitoring;
    }

}
