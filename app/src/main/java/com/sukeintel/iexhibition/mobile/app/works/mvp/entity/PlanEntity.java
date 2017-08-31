package com.sukeintel.iexhibition.mobile.app.works.mvp.entity;

/**
 * Created by czx on 2017/8/29.
 */

public class PlanEntity {
    /**
     * plan_id : 201708290204184759
     * title : dianyuan
     * led_display : first blood
     * multi_screen :
     * resource_list : ccdc15803fe84c74ae476041a2de5a0c
     * mediabox_plans : [["716e6eed923b4e0b810801638491b9c5","201708290153109548"]]
     * modi_time : 1503986658
     * add_time : 1503986658
     * memo : 添加
     * status : 1
     */

    private String plan_id;
    private String title;
    private String led_display;
    private String multi_screen;
    private String resource_list;
    private String mediabox_plans;
    private String modi_time;
    private String add_time;
    private String memo;
    private int status;

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLed_display() {
        return led_display;
    }

    public void setLed_display(String led_display) {
        this.led_display = led_display;
    }

    public String getMulti_screen() {
        return multi_screen;
    }

    public void setMulti_screen(String multi_screen) {
        this.multi_screen = multi_screen;
    }

    public String getResource_list() {
        return resource_list;
    }

    public void setResource_list(String resource_list) {
        this.resource_list = resource_list;
    }

    public String getMediabox_plans() {
        return mediabox_plans;
    }

    public void setMediabox_plans(String mediabox_plans) {
        this.mediabox_plans = mediabox_plans;
    }

    public String getModi_time() {
        return modi_time;
    }

    public void setModi_time(String modi_time) {
        this.modi_time = modi_time;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



  /*  public enum EPlanStatus{
        stop  = 0,
        ready = 1,
        running = 2,
    }*/
}
