package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.BaseActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.UserInfo;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.login.LoginPresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.MainActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.setting.ConfigServiceActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.AlertView;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresent> {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    public void initData(Bundle savedInstanceState) {
        initListener();
        getRxPermissions().request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe((b)->{
            if(!b){
                // 未获取权限
                Toast.makeText(context, "获取权限失败！",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initListener() {
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    AlertView.showAlert(context,"请输入数字或字母!");
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @OnClick({R.id.iv_clean_phone,R.id.clean_password,R.id.iv_show_pwd,R.id.regist,R.id.btn_login,R.id.forget_password,R.id.setting_btn})
    public void eventClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_phone:
                etMobile.setText("");
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.pass_visuable);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.pass_gone);
                }
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    etPassword.setSelection(pwd.length());
                break;
            case R.id.btn_login:
                submit();
                break;
            case R.id.regist:
                break;
            case R.id.forget_password:
                break;
            case R.id.setting_btn:
                ConfigServiceActivity.launch(context);
                break;

        }
    }

    public void submit(){
        String user = etMobile.getText().toString();
        String psw = etPassword.getText().toString();
        if(user == null || user.equals("")){
            AlertView.showAlert(context,"用户名不能为空！");
            return;
        }

        if(psw == null || psw.equals("")){
            AlertView.showAlert(context,"密码不能为空！");
            return;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(user);
        userInfo.setPassword(psw);
        userInfo.setClient_type(AppConstant.CLIENTTYPE);
        userInfo.setAccount_type(AppConstant.ACCOUNTTYPE);
        btnLogin.setText("正在登录中...");
        btnLogin.setClickable(false);
        getmPresenter().login(userInfo);
    }

    public void showError(String msg){
        AlertView.showAlert(context,msg);
        btnLogin.setText("登录");
        btnLogin.setClickable(true);
    }

    public void goMain(UserInfo userInfo){
        AppSetting.Initial(context).setStringPreferences(AppConstant.USER_INFO,new Gson().toJson(userInfo));
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

}
