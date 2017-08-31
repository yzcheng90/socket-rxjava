package com.sukeintel.iexhibition.mobile.app.works.mvp.present.login;

import com.google.gson.Gson;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.AppBaseResult;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastPacket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.Serializer;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.UserInfo;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.FastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.login.LoginActivity;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by czx on 2017/1/12.
 */

public class LoginPresent extends XPresent<LoginActivity> {

    public void login(UserInfo userInfo) {
        long id = FastApi.login(userInfo);
        if(id == -1){
            getV().showError(AppConstant.SERVICE_ERROR);
        }else{
            FastApiHelper.getInstance().read().subscribe((FastApiHelper.ApiInvokeCallBack back)->{

                if(back == null){
                    getV().showError(AppConstant.SERVICE_ERROR);
                }else{
                    FastPacket fastPacket = back.getResult();
                    if(fastPacket.Id == id){
                        if(fastPacket.IsException){
                            getV().showError(Serializer.DeserializeToJson(fastPacket.Body));
                        }else{
                            String json = Serializer.DeserializeToJson(fastPacket.Body);
                            if(json != null && !json.equals("") && !json.equals("null")){
                                AppBaseResult appBaseResult = new Gson().fromJson(json, AppBaseResult.class);
                                if(appBaseResult.getStatus()){
                                    getV().goMain(userInfo);
                                }else{
                                    getV().showError(appBaseResult.getMessage());
                                }
                            }else {
                                getV().showError("登录失败！");
                            }
                        }
                    }
                }
            });
        }

    }

}
