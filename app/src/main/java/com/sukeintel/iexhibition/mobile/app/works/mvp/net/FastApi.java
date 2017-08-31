package com.sukeintel.iexhibition.mobile.app.works.mvp.net;

import com.sukeintel.iexhibition.mobile.app.works.mvp.App;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastApiHelper;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.IFastApi;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.UserInfo;

/**
 * Created by czx on 2017/8/25.
 */

public class FastApi implements IFastApi {

    public static long login(UserInfo userInfo){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("login",userInfo);
        }

        return id;
    }


    public static long setMediaBoxCursorPos(String box_id,int x,int y){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("SetMediaBoxCursorPos",box_id,x,y);
        }
        return id;
    }

    public static long setMediaBoxMouseEvent(String boxid,String button,String status){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("SetMediaBoxMouseEvent",boxid,button,status);
        }
        return id;
    }

    public static long setMediaBoxKeyBoardEvent(String boxid,String textvalue){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("SetMediaBoxKeyBoardEvent",boxid,textvalue);
        }
        return id;
    }


    public static long getPlayResourceType(String paramString){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("GetPlayResourceListByBoxId",paramString);
        }

        return id;
    }

    public static long setMediaBoxPlayResource(String boxid,String resid){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("SetMediaBoxPlayResource",boxid,resid);
        }

        return id;
    }


    public static long getMediaBoxList(){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("GetMediaBoxList");
        }

        return id;
    }

    public static long getCameraResourceListOnPage(int pageIndex,int pageSize){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("GetCameraResourceListOnPage",pageIndex,pageSize);
        }

        return id;
    }


    public static long getResourceListByType(String type){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("GetResourceListByType",type);
        }

        return id;
    }


    public static long getCurrentPlanList(){
        long id = -1l;
        if(isSukeService()){

        }else{
            id = FastApiHelper.getInstance().callServiceApi("GetPlans");
        }

        return id;
    }


    private static boolean isSukeService(){
        return AppSetting.Initial(App.context).getBooleanPreferences("is_suke_service");
    }

}
