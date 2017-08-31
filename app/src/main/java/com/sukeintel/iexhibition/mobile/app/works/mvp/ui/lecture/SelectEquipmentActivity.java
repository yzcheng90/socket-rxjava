package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.lecture.SelectEquipmentAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.leture.SelectEquipmentPresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class SelectEquipmentActivity extends BaseActivity<SelectEquipmentPresent> {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.box)
    LinearLayout box;

    SelectEquipmentAdapter adapter;
    StateView errorView;

    EquipmentEntity saveEntity = null;
    EquipmentEntity entity = null; //传过来的
    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String info = getIntent().getExtras().getString(AppConstant.INFO);
        initToolbar("选择设备");
        initAdapter();
        reLoad();
        if(info != null && !info.equals("") && !info.equals("null")){
            entity = new Gson().fromJson(info,EquipmentEntity.class);
            adapter.setEntity(entity);
        }
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
        contentLayout.getRecyclerView().removeAllFootView();
        contentLayout.showLoading();
    }


    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new SelectEquipmentAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<EquipmentEntity, SelectEquipmentAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, EquipmentEntity model, int tag, SelectEquipmentAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    for (int i=0;i<adapter.viewHolders.size();i++){
                        adapter.viewHolders.get(i).setSelected(false);
                    }
                    holder.setSelected(true);
                    saveEntity = model;
                }
            });
        }
        return adapter;
    }

    @OnClick({R.id.btn_submit})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_submit:
                if(saveEntity == null ){
                    AlertView.showAlert(context,"请选择一台设备！");
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(AppConstant.INFO,new Gson().toJson(saveEntity));
                    //通过Intent对象返回结果，调用setResult方法
                    setResult(RESULT_OK,intent);
                    finish();//结束当前的activity的生命周期
                }
                break;
        }
    }


    public void showData(List<EquipmentEntity> model) {
        if(model == null || model.size() == 0){
            errorView.setMsg(AppConstant.SHOW_EMPTY);
            contentLayout.showError();
        }else{
            getAdapter().setData(model);
            contentLayout.getRecyclerView().setPage(1,1);
            if (getAdapter().getItemCount() < 1) {
                errorView.setMsg(AppConstant.SHOW_EMPTY);
                contentLayout.showError();
                return;
            }
        }
    }


    public void showError(String msg){
        errorView.setMsg(msg);
        contentLayout.showError();
    }


    public void reLoad(){
        getmPresenter().getMediaBoxList();
    }

    @Override
    public SelectEquipmentPresent newP() {
        return new SelectEquipmentPresent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_equipment;
    }

    public static void launch(Activity activity, Bundle bundle,int requestCode) {
        Router.newIntent(activity)
                .to(SelectEquipmentActivity.class)
                .data(bundle)
                .requestCode(requestCode)
                .launch();
    }


}
