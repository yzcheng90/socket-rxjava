package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * Created by Neil on 2017/7/21.
 */
public class FastPacket {

    /**
    * 获取fast协议封包标记
    * 字符表示为$
    **/
    public static byte Mark = 36;

    /**
    * 获取封包的字节长度
    **/
    public int TotalBytes;

    /**
    * 获取api名称长度
    **/
    public byte ApiNameLength;

    /**
    * 获取api名称
    **/
    public String ApiName;

    /**
    * 获取封包的唯一标识
    **/
    public long Id;

    /**
    * 获取是否为客户端的封包
    **/
    public boolean IsFromClient;

    /**
    * 获取或设置是否异常数据
    **/
    public boolean IsException;

    /**
    * 获取或设置数据体的数据
    **/
    public byte[] Body;

    /**
    * 通讯协议的封包
    **/
    public FastPacket(String api, long id, boolean fromClient)
    {
        if (api=="")
        {
          //  throw new ArgumentNullException("api");
        }
        this.ApiName = api;
        this.Id = id;
        this.IsFromClient = fromClient;
    }
    /**
     * 将参数序列化并写入为Body
     **/
    public void SetBodyParameters(Object[] parameters)
    {
        if (parameters == null || parameters.length == 0)
        {
            return;
        }
        ByteBuilder builder = new ByteBuilder(ByteOrder.BIG_ENDIAN);
        for (Object item : parameters)
        {
            // 序列化参数为二进制内容
            byte[] paramBytes = Serializer.Serialize(item);
            // 添加参数内容长度
            builder.Add(paramBytes == null ? 0 : paramBytes.length);
            // 添加参数内容
            builder.Add(paramBytes);
        }
        this.Body = builder.ToArray();
    }

    /**
     * 设置包内容
     **/
    public void SetBodyParameter(Object parameter){
        SetBodyParameter(parameter,false);
    }
    /**
     * 设置包内容
     **/
    public void SetBodyParameter(Object parameter,boolean isReturn)
    {
        if (parameter == null)
        {
            return;
        }
        ByteBuilder builder = new ByteBuilder(ByteOrder.BIG_ENDIAN);
        // 序列化参数为二进制内容
        byte[] paramBytes = Serializer.Serialize(parameter);

        //回复时不添加内容长度
        if(!isReturn){
            // 添加参数内容长度
            builder.Add(paramBytes == null ? 0 : paramBytes.length);
        }

        // 添加参数内容
        builder.Add(paramBytes);
        this.Body = builder.ToArray();
    }

    /**
     * 获取参数列表
     **/
    public Object[] GetBodyParameters()
    {
        ArrayList parameterList = new ArrayList();

        if (this.Body == null || this.Body.length < 4)
        {
            return null;
        }

        int index = 0;
        while (index < this.Body.length)
        {
            // 参数长度
            int length = ByteConverter.ToInt(this.Body, index, ByteOrder.BIG_ENDIAN);
            index = index + 4;
            byte[] paramBytes = new byte[length];
            // 复制出参数的数据
            System.arraycopy(this.Body, index, paramBytes, 0, length);

            index = index + length;
            if(length>0&&paramBytes.length>0){
                parameterList.add(paramBytes);
            }
        }
        return parameterList.toArray();
    }

    /**
     * 转换为ByteBuffer
     **/
    public ByteBuffer ToByteBuffer()
    {
        byte[] apiNameBytes = ByteConverter.ToBytes(this.ApiName);
        int headLength = apiNameBytes.length + 16;
        this.TotalBytes = this.Body == null ? headLength : headLength + this.Body.length;

        this.ApiNameLength = (byte)apiNameBytes.length;
        ByteBuilder builder = new ByteBuilder(ByteOrder.BIG_ENDIAN);
        builder.Add(FastPacket.Mark);
        builder.Add(this.TotalBytes);
        builder.Add(this.ApiNameLength);
        builder.Add(apiNameBytes);
        builder.Add(this.Id);
        builder.Add(this.IsFromClient);
        builder.Add(this.IsException);
        builder.Add(this.Body);
        return ByteBuffer.wrap(builder.ToArray());
    }

    /**
     * 解析一个数据包
     * 不足一个封包时返回null
     **/
    public static FastPacket Parse(SocketChannel socketChannel)throws IOException
    {
        FastPacket packet = null;
        int packetMinSize=16;
        int packetHandSize = 5;
        //获取包长
        ByteBuffer read_packetHandBuf = ByteBuffer.allocate(packetHandSize);
        int readlenght=socketChannel.read(read_packetHandBuf);
        read_packetHandBuf.flip();
        if(readlenght<packetHandSize){
            return null;
        }

        byte[] make = new byte[1];
        read_packetHandBuf.get(make);
        byte[] packet_len = new byte[4];
        read_packetHandBuf.get(packet_len);
        int packet_length= ByteConverter.ToInt(packet_len) ;

        if (packet_length < packetMinSize || make[0] != FastPacket.Mark)
        {
            return null;
        }

        int body_lenght=packet_length-packetHandSize;
        //获取接口名
        ByteBuffer read_packetBodyBuf = ByteBuffer.allocate(body_lenght);
        readlenght=socketChannel.read(read_packetBodyBuf);
        read_packetBodyBuf.flip();

        if (readlenght < body_lenght)
        {
            return null;
        }

        int read_position=1;
        byte[] api_len_bt = new byte[1];
        read_packetBodyBuf.get(api_len_bt);
        int api_name_len=ByteConverter.ToInt(api_len_bt[0]);
        read_position+=api_name_len;
        byte[] api_name_bt = new byte[api_name_len];
        read_packetBodyBuf.get(api_name_bt);

        byte[] id_bt = new byte[8];
        read_packetBodyBuf.get(id_bt);
        read_position+=8;
        byte[] isfromclient_bt = new byte[1];
        read_packetBodyBuf.get(isfromclient_bt);
        read_position+=1;
        byte[] isexception_bt = new byte[1];
        read_packetBodyBuf.get(isexception_bt);
        read_position+=1;
        byte[] body_bt=new byte[body_lenght-read_position];
        read_packetBodyBuf.get(body_bt);


        String apiName =ByteConverter.ToString(api_name_bt);

        long id=ByteConverter.ToLong(id_bt);
        boolean isFromClient=ByteConverter.ToBoolean(isfromclient_bt);
        boolean isException=ByteConverter.ToBoolean(isexception_bt);
        packet = new FastPacket(apiName, id, isFromClient);
        packet.TotalBytes= packetHandSize+body_lenght;
        packet.ApiNameLength=api_name_bt[0];
        packet.IsException=isException;
        packet.Body=body_bt;
        return packet;
    }
}
