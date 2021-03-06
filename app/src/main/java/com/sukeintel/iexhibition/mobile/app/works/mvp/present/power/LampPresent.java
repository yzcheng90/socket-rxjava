package com.sukeintel.iexhibition.mobile.app.works.mvp.present.power;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastPacket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.Serializer;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.CameraEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.ResourceEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.FastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.monitoring.CurrentMonitoringActivity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.power.LampListActivity;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by czx on 2017/1/12.
 */

public class LampPresent extends XPresent<LampListActivity> {

    public void getResourceListByType(String type) {
        long id = FastApi.getResourceListByType(type);
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
                            if(json != null && json.trim().length()!=0 && !json.equals("[]") && !json.equals("null")){
                                List<ResourceEntity> appBaseResult = new Gson().fromJson(json, new TypeToken<List<ResourceEntity>>(){}.getType());
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

}
