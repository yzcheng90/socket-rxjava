package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

/**
 * Created by czx on 2017/8/24.
 */

public class MenuEntity {

    private int icon;
    private String iconUrl;
    private String id;
    private String name;
    private String url;

    public MenuEntity() {
    }

    public MenuEntity(int icon, String iconUrl, String id, String name, String url) {
        this.icon = icon;
        this.iconUrl = iconUrl;
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
