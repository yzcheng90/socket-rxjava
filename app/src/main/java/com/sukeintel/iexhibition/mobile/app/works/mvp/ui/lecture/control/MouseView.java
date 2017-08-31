package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control;

import cn.droidlover.xdroidmvp.mvp.IPresent;
import cn.droidlover.xdroidmvp.mvp.IView;


/**
 * Created by czx on 2017/2/14.
 */

public interface MouseView<P extends IPresent> extends IView<P> {
    void showSuccess(String msg);
    void showError(String error);
}
