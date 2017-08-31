package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;



import android.util.Log;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Neil on 2017/7/21.
 */
public class FastSocketClient {
    private String TAG = "FastSocketClient";
    private SocketChannel mSocketChannel = null;
    private Selector mSelector = null;
    private IReceiveCallBack mCallBack;

    public void init(String ip, int port,IReceiveCallBack callBack) throws IOException {
        mSocketChannel = SocketChannel.open();
        InetAddress ia = InetAddress.getByName(ip);
        InetSocketAddress isa = new InetSocketAddress(ia, port);
        mSocketChannel.connect(isa);
        mSocketChannel.configureBlocking(false);
        Log.d(TAG, "Connect Success!");
        mSelector = Selector.open();
        mCallBack=callBack;
        //mSocketChannel.finishConnect();//才能完成连接
    }

    /**
     * 需要在子线程处理的等待数据接收
     */
    public void start() throws IOException {
        mSocketChannel.register(mSelector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (mSelector.select() > 0) {
            Set<SelectionKey> readyKeys = mSelector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isReadable()&&mCallBack!=null) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    mCallBack.run(socketChannel);
                }
            }
        }
    }


    /**
     * 调用服务接口
     */
    public int send(ByteBuffer byteBuffer) throws IOException {
        if(mSocketChannel.finishConnect()){
            return mSocketChannel.write(byteBuffer);
        }else{
            return 0;
        }
    }

    /**
     * 接收回调
     */
    public interface IReceiveCallBack {
        void run(SocketChannel socketChannel) throws IOException;
    }

}


