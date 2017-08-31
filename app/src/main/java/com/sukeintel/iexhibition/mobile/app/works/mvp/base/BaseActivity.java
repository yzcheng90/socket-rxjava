package com.sukeintel.iexhibition.mobile.app.works.mvp.base;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.UserInfo;

import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by czx on 2017/5/13.
 */

public abstract class BaseActivity<P> extends XActivity {

    public UserInfo result = null;
    private P mPresenter;

    @Override
    public P newP() {
        return mPresenter;
    }

    public P getmPresenter(){
        return (P) getP();
    }

    public UserInfo getUserInfo(){
        String user_info = AppSetting.Initial(context).getStringPreferences(AppConstant.USER_INFO);
        if(user_info!=null && !user_info.equals("")){
            Gson json = new Gson();
            result = json.fromJson(user_info,UserInfo.class);
        }
        return result;
    }
}
