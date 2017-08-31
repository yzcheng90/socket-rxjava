package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

/**
 * Created by czx on 2017/8/24.
 */

public class ResourceEntity {

    public String address;
    public String harbor;
    public String initial_id;
    public String lat;
    public String lon;
    public String modeltype;
    public String name;
    public String parent_id;
    public String production_time;
    public String register_time;
    public String rejection_time;
    public String resource_id;
    public String resource_prototype;
    public String resource_relay;
    public String resource_type;
    public String status;
    public String type;
    public String unit;
    public String user_id;
    public String vender;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHarbor() {
        return harbor;
    }

    public void setHarbor(String harbor) {
        this.harbor = harbor;
    }

    public String getInitial_id() {
        return initial_id;
    }

    public void setInitial_id(String initial_id) {
        this.initial_id = initial_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getModeltype() {
        return modeltype;
    }

    public void setModeltype(String modeltype) {
        this.modeltype = modeltype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getProduction_time() {
        return production_time;
    }

    public void setProduction_time(String production_time) {
        this.production_time = production_time;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getRejection_time() {
        return rejection_time;
    }

    public void setRejection_time(String rejection_time) {
        this.rejection_time = rejection_time;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_prototype() {
        return resource_prototype;
    }

    public void setResource_prototype(String resource_prototype) {
        this.resource_prototype = resource_prototype;
    }

    public String getResource_relay() {
        return resource_relay;
    }

    public void setResource_relay(String resource_relay) {
        this.resource_relay = resource_relay;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }


    public static class ResourceType{
        public static String Camera = "Camera";
        public static String Mediabox = "Mediabox";
        public static String LedDisplay = "LedDisplay";
        public static String SwitchResource = "SwitchResource";
    }
}
