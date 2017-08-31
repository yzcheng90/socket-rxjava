package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;

import com.sukeintel.iexhibition.mobile.app.works.mvp.common.RxSocket;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Neil on 2017/7/23.
 */

public class FastApiHelper {
    public String TAG = "FastApiHelper";
    private static volatile FastApiHelper defaultInstance;
    private RxSocket mFastSocketClient;
    private IFastApi mApi;
    private long mPacketId = 0L;
    private static Subject<ApiInvokeCallBack> read;

    public synchronized long NewPacketId() {
        return mPacketId++;
    }

    public boolean initStatus=false;

    public FastApiHelper(){
        read = PublishSubject.create();
    }

    //异步互斥的实例获取
    public static FastApiHelper getInstance() {
        FastApiHelper instance = defaultInstance;
        if (defaultInstance == null) {
            synchronized (FastApiHelper.class) {
                instance = defaultInstance;
                if (defaultInstance == null) {
                    instance = new FastApiHelper();
                    defaultInstance = instance;
                }
            }
        }
        return instance;
    }

    public void init(String serverIp, int serverPort,IFastApi apiObj){
        mFastSocketClient = RxSocket.getInstance();
        mFastSocketClient.connectRx(serverIp,serverPort)
                .subscribe(aBoolean -> {
                            System.out.println("--------------socket连接：>>"+aBoolean);
                        },
                        throwable -> {
                            System.out.println("--------------socket连接错误：>>"+throwable.getMessage());
                        },
                        () -> {});

        mFastSocketClient.read()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fastPacket -> {
                            if(fastPacket!=null){
                                remoteInvokeApi(fastPacket);
                            }else{
                                //服务器挂了
                                mFastSocketClient.initiativeDisconnect();
                                read.onNext(new ApiInvokeCallBack(null));
                            }

                        },
                        throwable -> {
                            System.out.println("--------------read throwable：>>");
                            throwable.printStackTrace();
                        },
                        ()->{
                            System.out.println("--------------read：>>");
                        }
                );

        mApi =apiObj;
        initStatus=true;
    }

    /**
     * 获取接口参数类型列表
     * **/
    public Class<?>[] getApiParamsClassArray(String apiname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class apiClass = mApi.getClass();
        Method[] methods = apiClass.getDeclaredMethods();
        for (Method method:methods) {
            ApiAnno annos=method.getAnnotation(ApiAnno.class);
            String api_name=annos.name();
            if(api_name.equals(apiname)){
                return method.getParameterTypes();
            }
        }
        return null;
    }
    /**
     * 据接口名称获取方法对象
     * **/
    public Method getMethodByApiName(String apiname) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class apiClass = mApi.getClass();
        Method[] methods = apiClass.getDeclaredMethods();
        for (Method method:methods) {
            ApiAnno annos=method.getAnnotation(ApiAnno.class);
            String api_name=annos.name();
            if(api_name.equals(apiname)){
                return method;
            }
        }
        return null;
    }
    /**
     * 据接口名称执行方法
     * **/
    public Object invokeApi(String apiname,Object[] params) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class apiClass = mApi.getClass();
        Method[] methods = apiClass.getDeclaredMethods();
        Method api_method=null;
        Class<?>[] api_method_params_class=null;
        for (Method method:methods) {
            ApiAnno annos=method.getAnnotation(ApiAnno.class);
            String api_name=annos.name();
            //查找与接口名称匹配的方法
            if(api_name.equals(apiname)){
                api_method=method;
                api_method_params_class=method.getParameterTypes();
                break;
            }
        }
        if(api_method!=null){
            if(api_method_params_class!=null&&api_method_params_class.length>0){
                Object[] api_method_param_values=new Object[api_method_params_class.length];
                for(int i=0;i<api_method_params_class.length;i++){
                    api_method_param_values[i]=Serializer.Deserialize((byte[]) params[i],String.class);
                }
                return api_method.invoke(mApi,api_method_param_values);
            }else{
                return api_method.invoke(mApi);
            }
        }
        return null;
    }

    /**
     * 远程调用本地接口
     */
    public void remoteInvokeApi(FastPacket rev_packet) throws IOException {
        //调用回复
        if (rev_packet.IsFromClient) {
            read.onNext(new ApiInvokeCallBack(rev_packet));
        } else {
            //远程服务主动调用
            Object[] params = rev_packet.GetBodyParameters();
            try {
                FastPacket return_packet=new FastPacket(rev_packet.ApiName,rev_packet.Id,rev_packet.IsFromClient);
                return_packet.SetBodyParameter(this.invokeApi(rev_packet.ApiName, params),true);
                //返回请求数据
                callServiceApi(return_packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * 调用服务接口
     */
    public long callServiceApi(String api, Object... parameters) {
        FastPacket packet = new FastPacket(api, this.NewPacketId(), true);
        packet.SetBodyParameters(parameters);
        return callServiceApi(packet);
    }
    /**
     * 调用服务接口
     */
    public long callServiceApi(FastPacket packet){
        ByteBuffer rst = packet.ToByteBuffer();
        final long[] packet_id = {-1l};
        final CountDownLatch latch = new CountDownLatch(1);
        mFastSocketClient.write(rst).subscribe((aBoolean)->{
            latch.countDown();
            if(aBoolean){
                //发送成功
                packet_id[0] = packet.Id;
            }else{
                //发送失败
                packet_id[0] = -1;
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return packet_id[0];
    }

    public Observable<ApiInvokeCallBack> read(){
        return read;
    }

    public Observable<RxSocket.SocketStatus> socketStatusListener(){
        return mFastSocketClient.socketStatusListener();
    }

    /**
     * 接口调用回调
     */
    public class ApiInvokeCallBack{
        private FastPacket result;
        ApiInvokeCallBack(FastPacket fastPacket){
            this.result = fastPacket;
        }

        public FastPacket getResult(){
            return this.result;
        }
    }


}
