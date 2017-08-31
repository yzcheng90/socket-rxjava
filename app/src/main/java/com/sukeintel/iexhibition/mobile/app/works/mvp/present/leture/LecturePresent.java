package com.sukeintel.iexhibition.mobile.app.works.mvp.present.leture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.base.AppBaseResult;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastPacket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.Serializer;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.TypeEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.UserInfo;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.FastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.LectureActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.login.LoginActivity;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by czx on 2017/1/12.
 */

public class LecturePresent extends XPresent<LectureActivity> {

    public void getPlayResourceType(String param) {
        long id = FastApi.getPlayResourceType(param);
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
                                List<TypeEntity> appBaseResult = new Gson().fromJson(json, new TypeToken<List<TypeEntity>>(){}.getType());
                                getV().showData(appBaseResult);
                            }else{
                                getV().showData(null);
                            }
                        }
                    }
                }
            });
        }
    }


    public void setMediaBoxPlayResource(String boxid,String resid,String resourceName) {
        long id = FastApi.setMediaBoxPlayResource(boxid,resid);
        if(id == -1){
            getV().playError(AppConstant.SERVICE_ERROR);
        }else{
            FastApiHelper.getInstance().read().subscribe((FastApiHelper.ApiInvokeCallBack back)->{

                if(back == null){
                    getV().playError(AppConstant.SERVICE_ERROR);
                }else{
                    FastPacket fastPacket = back.getResult();
                    if(fastPacket.Id == id){
                        if(fastPacket.IsException){
                            getV().playError(Serializer.DeserializeToJson(fastPacket.Body));
                        }else{
                            String json = Serializer.DeserializeToJson(fastPacket.Body);
                            if(json != null && !json.equals("") && !json.equals("null")){
                                getV().playSuccess(json,resourceName);
                            }else{
                                getV().showData(null);
                            }
                        }
                    }
                }
            });
        }
    }

}
