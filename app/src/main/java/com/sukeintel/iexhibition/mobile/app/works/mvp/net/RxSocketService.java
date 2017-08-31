package com.sukeintel.iexhibition.mobile.app.works.mvp.net;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sukeintel.iexhibition.mobile.app.works.mvp.App;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.common.RxSocket;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by czx on 2017/1/20.
 */

public class RxSocketService extends Service{

    public static RxSocketService rxSocketService;
    private static final String TAG = "RxSocketService";
    private static boolean isDis = false;

    private static Subject<RxSocket.SocketStatus> connectStatus;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        rxSocketService = this;
        connectStatus = PublishSubject.create();
        //监听Socket的连接状态
        RxSocket.getInstance().socketStatusListener()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socketStatus -> {
                    connectStatus.onNext(socketStatus);
                    if (socketStatus == RxSocket.SocketStatus.DIS_CONNECT){
                        if(!isDis){
                            System.out.println("------------------服务器断开！正在重连..--------------");
                            isDis = true;
                            TimerTask timerTask = new TimerTask(){
                                public void run(){
                                    socket_connect();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(timerTask, 1000);
                        }
                    }
                });

        return super.onStartCommand(intent, flags, startId);
    }





    public static void socket_connect(){
        isDis = false;
        //进行连接Socket：这里使用的是，在本地上开启了一个服务器。
        String temp_ip = AppSetting.Initial(App.context).getStringPreferences(AppConstant.SERVICE_IP);
        String temp_port = AppSetting.Initial(App.context).getStringPreferences(AppConstant.SERVICE_PORT);

        String ip = temp_ip.equals("")?AppConstant.PORT_IP:temp_ip;
        int port = temp_port.equals("")?AppConstant.PORT_PORT:Integer.parseInt(temp_port);

        RxSocket.getInstance().connectRx(ip,port)
                .subscribe(aBoolean -> {
                            System.out.println("--------------连接：>>"+aBoolean);
                        },
                        throwable -> {
                            System.out.println("--------------连接错误：>>"+throwable.getMessage());
                        },
                        () -> {
                            System.out.println("--------------socket_connect：>>");
                        }
                );

    }

    public static void socket_disConnect(){
        //断开连接
        isDis = true;
        RxSocket.getInstance().disConnect().subscribe(aBoolean -> {
                    System.out.println("--------------客户端主动断开：>>"+aBoolean);
                },
                throwable -> {
                    System.out.println("--------------客户端主动断开错误：>>"+throwable.getMessage());
                },
                () -> {
                    System.out.println("--------------socket_disConnect：>>");
                }
        );
    }

    public static  Observable<RxSocket.SocketStatus> getSocketStatus(){
        return connectStatus;
    }

}
