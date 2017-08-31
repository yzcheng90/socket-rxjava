package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.adapter.lecture.LectureTypeAdapter;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.TypeEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.leture.LecturePresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control.ControlActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.TypeForObjectUtil;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.DialogAlert;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.StateView;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

public class LectureActivity extends BaseActivity<LecturePresent> {

    @BindView(R.id.status_bar)
    LinearLayout statusBar;
    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dart_status)
    TextView dartStatus;
    @BindView(R.id.dart_name)
    TextView dartName;
    @BindView(R.id.equipment_icon)
    ImageView equipmentIcon;
    @BindView(R.id.equipment_name)
    TextView equipmentName;
    @BindView(R.id.up)
    LinearLayout up;
    @BindView(R.id.path)
    TextView path;
    @BindView(R.id.back_level_btn)
    TextView backLevelBtn;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;

    LectureTypeAdapter adapter;
    StateView errorView;

    private long firstClickTime = 0;
    private long secondClickTime = 0;
    private boolean isDoubleClick;

    EquipmentEntity selectEquiment = null;
    List<TypeEntity> resourceData;

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
        backLevelBtn.setClickable(false);
        backLevelBtn.setTextColor(getResources().getColor(R.color.colorDisabled));
    }


    @OnClick({R.id.equipment_icon,R.id.back_level_btn})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.back_level_btn:
                path.setText("所有类型 > ");
                backLevelBtn.setClickable(false);
                backLevelBtn.setTextColor(getResources().getColor(R.color.colorDisabled));
                reLoad();
                break;
            case R.id.equipment_icon:
                if (firstClickTime > 0) {
                    secondClickTime = System.currentTimeMillis();
                    if (secondClickTime - firstClickTime < 500) {
                        if(selectEquiment == null){
                            AlertView.showAlert(context,"请先选择一台设备！");
                        }else{
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstant.INFO,new Gson().toJson(selectEquiment));
                            ControlActivity.launch(context,bundle);
                        }
                        firstClickTime = 0;
                        isDoubleClick = true;
                        return;
                    }
                }
                firstClickTime = System.currentTimeMillis();
                isDoubleClick = false;
                new Thread(() ->{
                        try {
                        Thread.sleep(500);
                        firstClickTime = 0;
                        if (!isDoubleClick) {
                            Bundle bundle = new Bundle();
                            if(selectEquiment == null){
                                bundle.putString(AppConstant.INFO,null);
                            }else{
                                bundle.putString(AppConstant.INFO,new Gson().toJson(selectEquiment));
                            }
                            SelectEquipmentActivity.launch(context,bundle, AppConstant.SELECT_EQUIPMENT_CODE);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
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
        contentLayout.showLoading();
        contentLayout.getRecyclerView().removeAllFootView();
    }


    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new LectureTypeAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<TypeEntity, LectureTypeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, TypeEntity model, int tag, LectureTypeAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    if(model.getFile_ext() != null && !model.getFile_ext().equals("")){
                        DialogAlert dialogAlert = new DialogAlert(context,DialogAlert.ALERT_WARNING);
                        dialogAlert.setMsg("确定投放『"+model.getFile_name()+"."+model.getFile_ext()+"』");
                        dialogAlert.setSubmitText("确定");
                        dialogAlert.setCancelText("取消");
                        dialogAlert.show();
                        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
                            @Override
                            public void onSubmitClick(DialogAlert d) {
                                d.dismiss();
                                getmPresenter().setMediaBoxPlayResource(selectEquiment.getMediabox_id(),model.getResource_id(),model.getFile_name()+"."+model.getFile_ext());
                            }

                            @Override
                            public void onCancelClick(DialogAlert d) {
                                d.dismiss();
                            }
                        });
                    }else{
                        path.setText("所有类型 > "+model.getFile_name());
                        getAdapter().setData(model.getList());
                        backLevelBtn.setClickable(true);
                        backLevelBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                }
            });
        }
        return adapter;
    }

    public void showData(List<TypeEntity> model) {
        if(model == null || model.size() == 0){
            errorView.setMsg(AppConstant.SHOW_EMPTY);
            contentLayout.showError();
        }else{
            resourceData = TypeForObjectUtil.getTypeList(model);
            getAdapter().setData(resourceData);
            contentLayout.getRecyclerView().setPage(1,1);
            if (getAdapter().getItemCount() < 1) {
                errorView.setMsg(AppConstant.SHOW_EMPTY);
                contentLayout.showEmpty();
            }
        }
    }

    public void showError(String msg){
        errorView.setMsg(msg);
        contentLayout.showError();
    }

    public void playError(String msg){
        AlertView.showAlert(context,msg);
    }

    public void playSuccess(String status,String res_name){
        if(status.equals("true")){
            dartStatus.setText("投屏中...");
            dartStatus.setTextColor(getResources().getColor(R.color.colorSuccess));
            dartName.setText(res_name);
            dartName.setTextColor(getResources().getColor(R.color.colorSuccess));
        }else{
            AlertView.showAlert(context,"投屏失败！");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            String info = data.getExtras().getString(AppConstant.INFO);
            if(requestCode == AppConstant.SELECT_EQUIPMENT_CODE){
                selectEquiment = new Gson().fromJson(info,EquipmentEntity.class);
                equipmentName.setText(selectEquiment.getName());
                reLoad();
            }
        }
    }


    public void reLoad(){
        contentLayout.showLoading();
        if(selectEquiment != null){
            getmPresenter().getPlayResourceType(selectEquiment.getMediabox_id());
        }else{
            getmPresenter().getPlayResourceType("");
        }
    }

    @Override
    public LecturePresent newP() {
        return new LecturePresent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lecture;
    }

}
