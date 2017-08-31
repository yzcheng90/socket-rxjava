package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.App;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.RxSocket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.MenuEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.RxSocketService;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ConfigServiceActivity extends BaseActivity {


    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.connect_status)
    TextView connectStatus;
    @BindView(R.id.ip)
    EditText ip;
    @BindView(R.id.port)
    EditText port;
    @BindView(R.id.text_connect)
    Button textConnect;
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
        initToolbar("配置连接");
        RxSocketService.getSocketStatus().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketStatus -> {
                    if(socketStatus == RxSocket.SocketStatus.DIS_CONNECT){
                        connectStatus.setText("断开连接");
                        textConnect.setText("断开连接");
                        connectStatus.setTextColor(getResources().getColor(R.color.text_disabled));
                    }else if(socketStatus == RxSocket.SocketStatus.CONNECTING){
                        connectStatus.setText("正在连接...");
                        textConnect.setText("正在连接...");
                    }else if(socketStatus == RxSocket.SocketStatus.CONNECTED){
                        textConnect.setText("连接成功");
                        connectStatus.setText("连接成功");
                        connectStatus.setTextColor(getResources().getColor(R.color.colorSuccess));
                    }
                });
    }

    @OnClick({R.id.text_connect})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.text_connect:
                String ips = ip.getText().toString();
                String ports = port.getText().toString();
                if(ips == null || ips.equals("")){
                    AlertView.showAlert(context,"IP不能为空！");
                    return;
                }

                if(ports == null || ports.equals("端口不能为空！")){
                    AlertView.showAlert(context,"IP不能为空！");
                    return;
                }
                textConnect.setText("正在连接");
                AppSetting.Initial(App.context).setStringPreferences(AppConstant.SERVICE_IP,ips);
                AppSetting.Initial(App.context).setStringPreferences(AppConstant.SERVICE_PORT,ports);
                RxSocketService.socket_disConnect();
                RxSocketService.socket_connect();
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_config_service;
    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(ConfigServiceActivity.class)
                .data(new Bundle())
                .launch();
    }

}
