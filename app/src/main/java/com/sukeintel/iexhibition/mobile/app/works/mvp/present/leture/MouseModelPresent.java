package com.sukeintel.iexhibition.mobile.app.works.mvp.present.leture;

import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastPacket;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.Serializer;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.FastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control.MouseView;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by czx on 2017/1/12.
 */

public class MouseModelPresent extends XPresent<MouseView> {

    public void setMediaBoxCursorPos(String box_id,int x,int y) {
        long id = FastApi.setMediaBoxCursorPos(box_id,x,y);
        if(id == -1){
            getV().showError(AppConstant.SERVICE_ERROR);
        }else{
            FastApiHelper.getInstance().read().subscribe((FastApiHelper.ApiInvokeCallBack back)->{

                if(getV() != null){
                    if(back == null){
                        getV().showError(AppConstant.SERVICE_ERROR);
                    }else{
                        FastPacket fastPacket = back.getResult();
                        if(fastPacket.Id == id){
                            if(fastPacket.IsException){
                                getV().showError(Serializer.DeserializeToJson(fastPacket.Body));
                            }else{
                                String json = Serializer.DeserializeToJson(fastPacket.Body);
                                getV().showSuccess(json);
                            }
                        }
                    }
                }

            });
        }
    }

    public void setMediaBoxMouseEvent(String box_id,String button,String status) {
        long id = FastApi.setMediaBoxMouseEvent(box_id,button,status);
        if(id == -1){
            getV().showError(AppConstant.SERVICE_ERROR);
        }else{
            FastApiHelper.getInstance().read().subscribe((FastApiHelper.ApiInvokeCallBack back)->{

                if(getV() != null){
                    if(back == null){
                        getV().showError(AppConstant.SERVICE_ERROR);
                    }else{
                        FastPacket fastPacket = back.getResult();
                        if(fastPacket.Id == id){
                            if(fastPacket.IsException){
                                getV().showError(Serializer.DeserializeToJson(fastPacket.Body));
                            }else{
                                String json = Serializer.DeserializeToJson(fastPacket.Body);
                                getV().showSuccess(json);
                            }
                        }
                    }
                }
            });
        }
    }

    public void setMediaBoxKeyBoardEvent(String box_id,String textvalue) {
        long id = FastApi.setMediaBoxKeyBoardEvent(box_id,textvalue);
        if(id == -1){
            getV().showError(AppConstant.SERVICE_ERROR);
        }else{
            FastApiHelper.getInstance().read().subscribe((FastApiHelper.ApiInvokeCallBack back)->{

                if(getV() != null){
                    if(back == null){
                        getV().showError(AppConstant.SERVICE_ERROR);
                    }else{
                        FastPacket fastPacket = back.getResult();
                        if(fastPacket.Id == id){
                            if(fastPacket.IsException){
                                getV().showError(Serializer.DeserializeToJson(fastPacket.Body));
                            }else{
                                String json = Serializer.DeserializeToJson(fastPacket.Body);
                                getV().showSuccess(json);
                            }
                        }
                    }
                }
            });
        }
    }

}
