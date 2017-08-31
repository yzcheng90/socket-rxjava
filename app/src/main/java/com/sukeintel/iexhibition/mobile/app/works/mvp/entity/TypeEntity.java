package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czx on 2017/8/24.
 */

public class TypeEntity {

    public int add_time;
    private int count;
    public String creater;
    public String describe;
    public String file_ext;
    public String file_name;
    private String id;
    private List<TypeEntity> list = new ArrayList();
    public String md5;
    public int modify_time;
    private String path;
    public String resource_id;
    public int state;
    private String type;
    public String type_mime;

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFile_ext() {
        return file_ext;
    }

    public void setFile_ext(String file_ext) {
        this.file_ext = file_ext;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TypeEntity> getList() {
        return list;
    }

    public void setList(List<TypeEntity> list) {
        this.list = list;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getModify_time() {
        return modify_time;
    }

    public void setModify_time(int modify_time) {
        this.modify_time = modify_time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_mime() {
        return type_mime;
    }

    public void setType_mime(String type_mime) {
        this.type_mime = type_mime;
    }
}
