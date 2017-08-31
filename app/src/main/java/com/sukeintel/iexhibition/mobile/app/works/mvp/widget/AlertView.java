package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.app.Activity;
import android.content.Intent;

import com.sukeintel.iexhibition.mobile.app.works.mvp.App;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppConstant;
import com.sukeintel.iexhibition.mobile.app.works.mvp.AppSetting;
import com.sukeintel.iexhibition.mobile.app.works.mvp.net.RxSocketService;
import com.sukeintel.iexhibition.mobile.app.works.mvp.ui.login.LoginActivity;

/**
 * Created by Administrator on 2017/1/12.
 */

public class AlertView {
    public static void showMessage(String msg) {
        showAlert((Activity) App.context,msg);
    }

    public static DialogAlert showDelete(Activity activity, String msg){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_ERROR);
        dialogAlert.setMsg(msg);
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        return dialogAlert;
    }

    public static void showExit(Activity activity, String msg){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_WARNING);
        dialogAlert.setMsg(msg);
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
            @Override
            public void onSubmitClick(DialogAlert d) {
                d.dismiss();
                activity.finish();
            }

            @Override
            public void onCancelClick(DialogAlert d) {
                d.dismiss();
            }
        });
    }

    public static void showAlert(Activity activity, String msg){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_WARNING);
        dialogAlert.setMsg(msg);
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
            @Override
            public void onSubmitClick(DialogAlert d) {
                d.dismiss();
            }

            @Override
            public void onCancelClick(DialogAlert d) {
                d.dismiss();
            }
        });
    }

    public static void showAlert(Activity activity, String msg,String title){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_WARNING);
        dialogAlert.setMsg(msg);
        dialogAlert.setTitle(title);
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
            @Override
            public void onSubmitClick(DialogAlert d) {
                d.dismiss();
            }

            @Override
            public void onCancelClick(DialogAlert d) {
                d.dismiss();
            }
        });
    }




    public static void exitSystem(final Activity activity){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_WARNING);
        dialogAlert.setMsg("你确定要退出系统吗？");
        dialogAlert.setTitle("系统提示！");
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
            @Override
            public void onSubmitClick(DialogAlert d) {
                d.dismiss();
                activity.finish();
            }

            @Override
            public void onCancelClick(DialogAlert d) {
                d.dismiss();
            }
        });
    }

    public static void cutUser(final Activity activity){
        DialogAlert dialogAlert = new DialogAlert(activity,DialogAlert.ALERT_WARNING);
        dialogAlert.setMsg("你确定要退出当前用户吗？");
        dialogAlert.setTitle("系统提示！");
        dialogAlert.setSubmitText("确定");
        dialogAlert.setCancelText("取消");
        dialogAlert.show();
        dialogAlert.setOnDialogAlertClickListener(new DialogAlert.OnDialogAlertClickListener() {
            @Override
            public void onSubmitClick(DialogAlert d) {
                d.dismiss();
                AppSetting.Initial(activity).removePreferences(AppConstant.USER_INFO);
                App.closeAllActivity();
                Intent intent =new Intent(activity,LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onCancelClick(DialogAlert d) {
                d.dismiss();
            }
        });

    }
}
