package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.RxSocket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.RxSocketService;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.FunSwitch;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.user_icon)
    ImageView userIcon;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.connect_status)
    TextView connectStatus;
    @BindView(R.id.config_connect)
    LinearLayout configConnect;
    @BindView(R.id.btn_islocal)
    FunSwitch btnIslocal;
    @BindView(R.id.cut_user)
    LinearLayout cutUser;
    @BindView(R.id.exit_system)
    Button exitSystem;
    @BindView(R.id.status_bar)
    LinearLayout statusBar;

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

        RxSocketService.getSocketStatus().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketStatus -> {
                    if(socketStatus == RxSocket.SocketStatus.DIS_CONNECT){
                        connectStatus.setText("断开连接");
                        connectStatus.setTextColor(getResources().getColor(R.color.text_disabled));
                    }else if(socketStatus == RxSocket.SocketStatus.CONNECTING){
                        connectStatus.setText("正在连接...");
                    }else if(socketStatus == RxSocket.SocketStatus.CONNECTED){
                        connectStatus.setText("连接成功");
                        connectStatus.setTextColor(getResources().getColor(R.color.colorSuccess));
                    }
                });
    }


    @OnClick({R.id.cut_user,R.id.exit_system,R.id.config_connect})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.config_connect:
                ConfigServiceActivity.launch(context);
                break;
            case R.id.exit_system:
                break;
            case R.id.cut_user:
                AlertView.cutUser(context);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

}
