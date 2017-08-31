package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.monitoring;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.App;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.CameraEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.BarUtils;
import butterknife.BindView;
import cn.droidlover.xdroidmvp.router.Router;

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    LinearLayout backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.video_webView)
    WebView videoWebView;

    public void initToolbar(String mTitle) {
        title.setText(mTitle);
        backBtn.setOnClickListener((v) -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        String info = getIntent().getExtras().getString("info");
        CameraEntity cameraEntity = new Gson().fromJson(info,CameraEntity.class);
        initToolbar(cameraEntity.getName());
        initWebView(cameraEntity.getResource_id());
    }

    private void initWebView(String res_id) {

        String ip = AppSetting.Initial(context).getStringPreferences(AppConstant.SERVICE_IP);
        String port = AppSetting.Initial(context).getStringPreferences(AppConstant.SERVICE_PORT);

        String webUrl = "http://"+ip+":"+port+ "/Mobile/WebPlayer?resid=" + res_id;
        videoWebView.loadUrl(webUrl);
        WebSettings webSettings = videoWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);// 支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true); // 为图片添加放大缩小功能
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //不使用缓存，只从网络获取数据.
        videoWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_play_video;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoWebView != null) {
            ViewGroup parent = (ViewGroup) videoWebView.getParent();
            if (parent != null) {
                parent.removeView(videoWebView);
            }
            videoWebView.removeAllViews();
            videoWebView.destroy();
        }
    }

    public static void launch(Activity activity, Bundle bundle) {
        Router.newIntent(activity)
                .to(PlayVideoActivity.class)
                .data(bundle)
                .launch();
    }

}
