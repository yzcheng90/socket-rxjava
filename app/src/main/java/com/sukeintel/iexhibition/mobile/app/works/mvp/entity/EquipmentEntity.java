package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

/**
 * Created by czx on 2017/8/24.
 */

public class EquipmentEntity {

    private String ip;
    private String mediabox_id;
    private String name;
    private String secret_signs;
    public boolean selected = false;
    private String sessions;
    private int status;
    private String user_id;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMediabox_id() {
        return mediabox_id;
    }

    public void setMediabox_id(String mediabox_id) {
        this.mediabox_id = mediabox_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret_signs() {
        return secret_signs;
    }

    public void setSecret_signs(String secret_signs) {
        this.secret_signs = secret_signs;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
