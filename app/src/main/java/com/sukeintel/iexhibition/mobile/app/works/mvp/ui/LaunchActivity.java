package com.sukeintel.iexhibition.mobile.app.works.mvp.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.FastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.RxSocketService;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.login.LoginActivity;

import butterknife.BindView;

public class LaunchActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    public void initData(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(tvName, alpha, scaleX, scaleY);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);

        FastApiHelper.getInstance().init(AppConstant.PORT_IP,AppConstant.PORT_PORT, new FastApi());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(2000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
               /* if(getUserInfo() == null){
                    Intent intent =new Intent(LaunchActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent =new Intent(LaunchActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }*/

                Intent intent =new Intent(LaunchActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch;
    }
}
