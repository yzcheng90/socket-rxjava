package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

/**
 * Created by czx on 2017/8/24.
 */

public class CameraEntity {

    /**
     * resource_id : 03624458544a41029e414506adae609a
     * parent_id :
     * initial_id :
     * name : 苏科会议室摄像头
     * address : 湖南省长沙市岳麓区麓谷街道尖山路
     * lon : 112.88209
     * lat : 28.226577
     * unit : 372eeb3a-a17a-457e-b31f-09f5e6cf54cd
     * type : Camera
     * user_id : 372eeb3a-a17a-457e-b31f-09f5e6cf54cd
     * resource_prototype : {"IP":"192.168.0.209","USERNAME":"admin","PASSWORD":"admin172839","CLOUD":2,"NAME":"苏科会议室摄像头","TMPL_RTSP_URL":"RTSP://{USERNAME}:{PASSWORD}@{IP}RTSP://{USERNAME}:{PASSWORD}@{IP}:1024/Streaming/Channels/701?transportmode=unicast","ACCESSMODE":2,"streaming_media_id":"401958cf140c44c3bcdcb0bec48a09a2"}
     * harbor : eb2201a7892e4589807f8b55900290b3
     */

    private String resource_id;
    private String parent_id;
    private String initial_id;
    private String name;
    private String address;
    private String lon;
    private String lat;
    private String unit;
    private String type;
    private String user_id;
    private String resource_prototype;
    private String harbor;

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getInitial_id() {
        return initial_id;
    }

    public void setInitial_id(String initial_id) {
        this.initial_id = initial_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getResource_prototype() {
        return resource_prototype;
    }

    public void setResource_prototype(String resource_prototype) {
        this.resource_prototype = resource_prototype;
    }

    public String getHarbor() {
        return harbor;
    }

    public void setHarbor(String harbor) {
        this.harbor = harbor;
    }
}
