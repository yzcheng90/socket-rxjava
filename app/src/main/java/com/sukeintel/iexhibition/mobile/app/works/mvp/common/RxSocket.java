package com.sukeintel.iexhibition.mobile.app.works.mvp.common;

import com.sukeintel.iexhibition.mobile.app.works.mvp.common.core.FastPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import cn.droidlover.xdroidmvp.log.XLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class RxSocket {

    /*  常量
    * */
    private String TAG = "RxSocket";
    private long WRITE_TIME_OUT = 1000;
    private long CONNECT_TIME_OUT = 1000;
    /*  单例
    * */
    private Subject<FastPacket> readSubject;
    private Subject<SocketStatus> connectStatus;
    private static volatile RxSocket defaultInstance;
    private RxSocket() {
        readSubject = PublishSubject.create();
        connectStatus = PublishSubject.create();
    }
    public static RxSocket getInstance() {
        RxSocket rxSocket = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxSocket.class) {
                rxSocket = defaultInstance;
                if (defaultInstance == null) {
                    rxSocket = new RxSocket();
                    defaultInstance = rxSocket;
                }
            }
        }
        return rxSocket;
    }

    /*  变量
    * */
    private SocketStatus socketStatus = SocketStatus.DIS_CONNECT;
    private Selector selector = null;
    private SocketChannel socketChannel = null;
    private SelectionKey selectionKey = null;
    private ReadThread readThread = null;
    private boolean isReadThreadAlive = true;
    private SocketReconnectCallback socketReconnectCallback = null;

    /*  方法
    * */
    /**
     * 监听Socket的状态
     * @return Rx SocketStatus 状态
     */
    public Observable<SocketStatus> socketStatusListener () {
        System.out.println("监听socketStatusListener");
        return connectStatus;
    }

    /**
     * 建立Socket连接，只是尝试建立一次
     * @param ip    IP or 域名
     * @param port  端口
     * @return  Rx true or false
     */
    public Observable<Boolean> connectRx(String ip, int port) {
        return Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) {

                        XLog.d(TAG,"connectRx:"+"status:"+socketStatus.name());

                        //正在连接
                        if (socketStatus == SocketStatus.CONNECTING) {
                            emitter.onNext(false);
                            emitter.onComplete();
                            return;
                        }

                        //未连接 | 已经连接，关闭Socket
                        socketStatus = SocketStatus.DIS_CONNECT;
                        isReadThreadAlive = false;
                        readThread = null;
                        if (selector!=null)
                            try {
                                selector.close();
                            } catch (Exception e) {
                                XLog.d(TAG,"selector.close");
                            }
                        if (selectionKey!=null)
                            try {
                                selectionKey.cancel();
                            } catch (Exception e) {
                                XLog.d(TAG,"selectionKey.cancel");
                            }
                        if (socketChannel!=null)
                            try {
                                socketChannel.close();
                            } catch (Exception e) {
                                XLog.d(TAG,"socketChannel.close");
                            }

                        //重启Socket
                        isReadThreadAlive = true;
                        readThread = new ReadThread(ip,port);
                        readThread.start();
                        socketReconnectCallback = new SocketReconnectCallback() {
                            @Override
                            public void onSuccess() {
                                XLog.d(TAG,"connectRx:"+"CONNECTED");
                                socketStatus = SocketStatus.CONNECTED;
                                emitter.onNext(true);
                                emitter.onComplete();
                            }

                            @Override
                            public void onFail(String msg) {
                                XLog.d(TAG,"connectRx:"+msg);
                                emitter.onNext(false);
                                emitter.onComplete();
                            }
                        };
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(aBoolean -> {
                    socketReconnectCallback = null;
                    return aBoolean;
                })
                .timeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS, Observable.just(false));
    }

    /**
     * 断开当前的Socket
     *  还能再继续连接
     * @return Rx true or false
     */
    public Observable<Boolean> disConnect() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter subscriber) {
                try {
                    if (socketStatus == SocketStatus.DIS_CONNECT) {
                        subscriber.onNext(true);
                        subscriber.onComplete();
                    }else {
                        socketStatus = SocketStatus.DIS_CONNECT;
                        isReadThreadAlive = false;
                        readThread = null;
                        if (selector!=null)
                            try {
                                selector.close();
                            } catch (Exception e) {
                                XLog.d(TAG,"selector.close");
                            }
                        if (selectionKey!=null)
                            try {
                                selectionKey.cancel();
                            } catch (Exception e) {
                                XLog.d(TAG,"selectionKey.cancel");
                            }
                        if (socketChannel!=null)
                            try {
                                socketChannel.close();
                            } catch (Exception e) {
                                XLog.d(TAG,"socketChannel.close");
                            }

                        connectStatus.onNext(SocketStatus.DIS_CONNECT);

                        subscriber.onNext(true);
                        subscriber.onComplete();
                    }
                } catch (Exception e) {
                    subscriber.onNext(false);
                    subscriber.onComplete();
                }
            }
        });
    }

    /**
     * 读取Socket的消息
     * @return  Rx error 或者 有数据
     */
    public Observable<FastPacket> read() {
        return readSubject;
    }

    /**
     * 向Socket写消息
     * @param buffer    数据包
     * @return  Rx true or false
     */
    public Observable<Boolean> write(ByteBuffer buffer) {
        return Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter subscriber) {
                        if (socketStatus != SocketStatus.CONNECTED) {
                            XLog.d( TAG, "write." + "SocketStatus.DISCONNECTED");
                            subscriber.onNext(false);
                            subscriber.onComplete();
                        }else {
                            if (socketChannel!=null && socketChannel.isConnected()) {
                                try {
                                    int result = socketChannel.write(buffer);
                                    if (result<0) {
                                        XLog.d(TAG, "write." + "发送出错");
                                        subscriber.onNext(false);
                                        subscriber.onComplete();
                                    }else {
                                        XLog.d(TAG, "write." + "success!");
                                        subscriber.onNext(true);
                                        subscriber.onComplete();
                                    }
                                } catch (Exception e) {
                                    XLog.d(TAG,"write."+e.getMessage());
                                    subscriber.onNext(false);
                                    subscriber.onComplete();
                                }
                            }else {
                                XLog.d(TAG,"write."+"close");
                                subscriber.onNext(false);
                                subscriber.onComplete();
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .timeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS, Observable.just(false));
    }

    /**
     * 获取Socket的链接状态
     * @return  状态
     */
    public SocketStatus getSocketStatus() {
        return socketStatus;
    }

    /*  类 && 枚举 && 接口
        * */
    private class ReadThread extends Thread {
        private String ip;
        private int port;
        ReadThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
        @Override
        public void run() {
            XLog.d(TAG,"ReadThread:"+"start");
            while (isReadThreadAlive) {
                //连接
                if (socketStatus == SocketStatus.DIS_CONNECT) {
                    try {
                        if (selectionKey != null) selectionKey.cancel();
                        socketChannel = SocketChannel.open();
                        socketChannel.configureBlocking(false);
                        selector = Selector.open();
                        socketChannel.connect(new InetSocketAddress(ip, port));
                        selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
                        socketStatus = SocketStatus.CONNECTING;
                        connectStatus.onNext(socketStatus);
                    } catch (Exception e) {
                        isReadThreadAlive = false;
                        socketStatus = SocketStatus.DIS_CONNECT;
                        connectStatus.onNext(socketStatus);
                        XLog.e(TAG, "ReadThread:init:" + e.getMessage());
                        if (socketReconnectCallback!=null)
                            socketReconnectCallback.onFail("SocketConnectFail1");
                    }
                }
                //读取
                else if (socketStatus == SocketStatus.CONNECTING || socketStatus  == SocketStatus.CONNECTED) {
                    try {
                        selector.select();
                        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            if (key.isConnectable()) {
                                if (socketChannel.isConnectionPending()) {
                                    try {
                                        socketChannel.finishConnect();
                                        socketStatus = SocketStatus.CONNECTED;
                                        connectStatus.onNext(socketStatus);
                                        socketChannel.configureBlocking(false);
                                        socketChannel.register(selector, SelectionKey.OP_READ);
                                        if (socketReconnectCallback!=null)
                                            socketReconnectCallback.onSuccess();
                                    } catch (Exception e) {
                                        isReadThreadAlive = false;
                                        socketStatus = SocketStatus.DIS_CONNECT;
                                        connectStatus.onNext(socketStatus);
                                        XLog.e(TAG, "ReadThread:finish:" + e.getMessage());
                                        if (socketReconnectCallback!=null)
                                            socketReconnectCallback.onFail("SocketConnectFail2");
                                    }
                                }
                            } else if (key.isReadable()) {
                                Thread.sleep(1000);
                                socketStatus = SocketStatus.CONNECTED;
                                connectStatus.onNext(socketStatus);
                                FastPacket rev_packet = FastPacket.Parse(socketChannel);
                                readSubject.onNext(rev_packet);
                            }
                        }
                        it.remove();
                    } catch (Exception e) {
                        isReadThreadAlive = false;
                        socketStatus = SocketStatus.DIS_CONNECT;
                        connectStatus.onNext(socketStatus);
                        XLog.e(TAG, "ReadThread:read:" + e.getMessage());
                        if (socketReconnectCallback!=null)
                            socketReconnectCallback.onFail("SocketConnectFail4");
                    }
                }
            }
        }
    }

    public void initiativeDisconnect(){
        XLog.e(TAG, "服务器主动断开链接！");
        isReadThreadAlive = false;
        socketStatus = SocketStatus.DIS_CONNECT;
        connectStatus.onNext(socketStatus);
        if (socketReconnectCallback!=null)
            socketReconnectCallback.onFail("SocketConnectFail3");
    }


    public enum SocketStatus {
        DIS_CONNECT,
        CONNECTING,
        CONNECTED,
    }

    private interface SocketReconnectCallback {
        void onSuccess();
        void onFail(String msg);
    }
}

