package com.sukeintel.iexhibition.mobile.app.works.mvp.common.core;



import com.google.gson.Gson;

/**
 * Created by Neil on 2017/7/22.
 */

public class Serializer {
    private static volatile Gson mGson = new Gson();


    /***
     * 序列化
     * */
    public static byte[] Serialize(Object model)
    {
        String json = mGson.toJson(model);
        return json == null ? null : ByteConverter.ToBytes(json);
    }
    /***
     * 反序列化
     * */
    public static Object Deserialize(byte[] bytes, Class type)
    {
        if (bytes == null || bytes.length == 0)
        {
            return null;
        }

        String json = ByteConverter.ToString(bytes);
        return mGson.fromJson(json, type);
    }

    /***
     * 反序列化
     * */
    public static String DeserializeToJson(byte[] bytes)
    {
        if (bytes == null || bytes.length == 0)
        {
            return null;
        }

        String json = ByteConverter.ToString(bytes);
        return json;
    }
}
