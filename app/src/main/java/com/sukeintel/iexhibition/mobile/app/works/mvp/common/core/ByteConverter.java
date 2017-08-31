package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;


import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Neil on 2017/7/21.
 */
public class ByteConverter {
    public static final String TAG="ByteConverter";
    public static ByteOrder Endian = ByteConverter.GetSystemEndian();

    /**
     * 系统字节存储次序
     **/
    private static ByteOrder GetSystemEndian() {
        return ByteOrder.BIG_ENDIAN;
    }

    public static byte[] ToBytes(String iValue) {
        try {
            return iValue.getBytes("utf-8");
        }catch(Exception e){
            Log.e(TAG,iValue);
            return new byte[0];
        }
    }


    public static byte[] ToBytes(boolean iValue) {
        byte[] result = new byte[1];
        result[0] = (byte) (iValue ? 0x01 : 0x00);
        return result;
    }

    public static byte[] ToBytes(Boolean iValue) {
        byte[] result = new byte[1];
        result[0] = (byte) (iValue ? 0x01 : 0x00);
        return result;
    }


    public static byte[] ToBytes(int iValue) {
        return ToBytes(iValue, GetSystemEndian());
    }

    public static byte[] ToBytes(int iValue, ByteOrder endian) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(endian);
        byteBuffer.putInt(iValue);
        return byteBuffer.array();
    }
    public static byte[] ToBytes(long iValue, ByteOrder endian) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(endian);
        byteBuffer.putLong(iValue);
        return byteBuffer.array();
    }


    public static int ToInt(byte iValue) {

        return iValue & 0xFF;
    }
    public static int ToInt(byte[] iValue) {
        return ToInt(iValue,GetSystemEndian());
    }

    public static int ToInt(byte[] iValue, ByteOrder endian) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(iValue);
        byteBuffer.order(endian);
        return byteBuffer.getInt();
    }
    public static int ToInt(byte[] iValue,int index, ByteOrder endian) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(iValue,index,4);
        byteBuffer.order(endian);
        return byteBuffer.getInt();
    }


    public static long ToLong(byte[] iValue) {
        return ToLong(iValue,GetSystemEndian());
    }
    public static long ToLong(byte[] iValue, ByteOrder endian) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(iValue);
        byteBuffer.order(endian);
        return byteBuffer.getLong();
    }

    public static String ToString(byte[] iValue) {
        try {
            return new String(iValue, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    public static boolean ToBoolean(byte[] iValue) {
        return iValue[0]>0;
    }




}
