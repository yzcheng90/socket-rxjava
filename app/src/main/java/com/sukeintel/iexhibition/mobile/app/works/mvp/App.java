package com.sukeintel.iexhibition.mobile.app.works.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.sukeintel.iexhibition.mobile.app.works.mvp.net.RxSocketService;
import com.sukeintel.iexhibition.mobile.app.works.mvp.utils.AppException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by czx on 2017/8/24.
 */

public class App extends Application {

    private static List<Activity> activityList = new ArrayList();
    public static Context context;


    @Override
    public void onCreate(){
        super.onCreate();
        context = this;

        System.out.println("---------------RxSocketService初始化-------");
        Intent rxServiceSocket = new Intent(context, RxSocketService.class);
        context.startService(rxServiceSocket);

        AppException.getInstance().init(context);


        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return null;
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }
        });
    }


    public static void addActivity(Activity paramActivity){
        activityList.add(paramActivity);
    }

    public static void closeAllActivity(){
        Iterator localIterator = activityList.iterator();
        while (localIterator.hasNext())
            ((Activity)localIterator.next()).finish();
    }

    public static void removeActivity(Activity paramActivity){
        activityList.remove(paramActivity);
    }


}
