package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Neil on 2017/7/21.
 */
public class ByteBuilder {
    /**
     * 容量
     **/
    private int _capacity;

    /**
     * 当前数据
     **/
    private byte[] _buffer;

    /**
     * 获取数据长度
     **/
    public int Length;

    /**
     * 获取字节存储次序
     **/
    public ByteOrder Endian;


    /**
     * 提供二进制数据读取和操作支持
     **/
    public ByteBuilder(ByteOrder endian) {
        this.Endian = endian;
    }


    /**
     * 添加一个bool类型
     **/
    public void Add(Boolean value) {
        this.Add(ByteConverter.ToBytes(value));
    }
    /**
     * 添加一个bool类型
     **/
    public void Add(String value) {
        this.Add(ByteConverter.ToBytes(value));
    }

    /**
     * 添加一个字节
     **/
    public void Add(byte value) {
        this.Add(new byte[]{value});
    }

    /**
     * 将32位整数转换为byte数组再添加
     **/
    public void Add(int value) {
        byte[] bytes = ByteConverter.ToBytes(value, this.Endian);
        this.Add(bytes);
    }

    /**
     * 将64位整数转换为byte数组再添加
     **/
    public void Add(long value) {
        byte[] bytes = ByteConverter.ToBytes(value, this.Endian);
        this.Add(bytes);
    }

    /**
     * 添加指定数据数组
     **/
    public void Add(byte[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        this.Add(array, 0, array.length);
    }

    /**
     * 添加指定数据数组
     **/
    public void Add(byte[] array, int offset, int count) {
        if (array == null || array.length == 0) {
            return;
        }
        if (offset < 0 || offset > array.length) {
            throw new IllegalArgumentException("offset"+"offset值无效");
        }

        if (count < 0 || (offset + count) > array.length) {
            throw new IllegalArgumentException("offset"+ "offset值无效");
        }
        int newLength = this.Length + count;
        this.ExpandCapacity(newLength);
        System.arraycopy(array, offset, this._buffer,this.Length, count);
        this.Length = newLength;
    }

    /**
     * 扩容
     **/
    private void ExpandCapacity(int newLength) {
        if (newLength <= this._capacity) {
            return;
        }

        if (this._capacity == 0) {
            this._capacity = 64;
        }

        while (newLength > this._capacity) {
            this._capacity = this._capacity * 2;
        }

        byte[] newBuffer = new byte[this._capacity];
        if (this.Length > 0) {
            System.arraycopy(this._buffer, 0, newBuffer, 0, this.Length);
        }
        this._buffer = newBuffer;
    }

    /**
     * 转换为byte数组
     **/
    public byte[] ToArray() {
        byte[] array = new byte[this.Length];
        System.arraycopy(this._buffer, 0, array, 0, this.Length);
        return array;
    }

    /**
     * 转换为ToByteBuffer类型
     **/
    public ByteBuffer ToByteBuffer() {
        return  ByteBuffer.wrap(this._buffer,0, this.Length);
    }


}
