package com.sukeintel.iexhibition.mobile.app.works.mvp.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sukeintel.iexhibition.mobile.app.works.mvp.R;

/**
 * Created by czx on 2017/4/10.
 */

public class DialogAlert extends Dialog implements View.OnClickListener{

    public static final int ALERT_DEFAULT = 0;
    public static final int ALERT_WARNING = 1;
    public static final int ALERT_ERROR = 2;
    public static final int ALERT_SUCCESS = 3;
    private LinearLayout top;
    private String msg;
    private String title_text;
    private String cancel_text;
    private String submit_text;
    private int type;
    private Context context;
    private OnDialogAlertClickListener listener;

    public void setOnDialogAlertClickListener(OnDialogAlertClickListener listener){
        this.listener = listener;
    }

    public interface OnDialogAlertClickListener {
        void onSubmitClick(DialogAlert dialogAlert);
        void onCancelClick(DialogAlert dialogAlert);
    }

    public DialogAlert(Context context, int type){
        super(context,R.style.record_voice_dialog);
        this.type = type;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        top = (LinearLayout) view.findViewById(R.id.top);
        top.setBackgroundResource(R.color.colorPrimary);
        TextView context_tv = (TextView) view.findViewById(R.id.context);
        TextView title = (TextView) view.findViewById(R.id.title);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.linear);
        LinearLayout title_layout = (LinearLayout) view.findViewById(R.id.title_layout);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView submit = (TextView) view.findViewById(R.id.submit);

        if(title_text == null || title_text.equals("")){
            title_layout.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);
        }else{
            title_layout.setVisibility(View.VISIBLE);
            linear.setVisibility(View.VISIBLE);
        }

        title.setText(title_text);
        context_tv.setText(msg);
        submit.setText(submit_text);
        cancel.setText(cancel_text);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);

        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9);
        getWindow().setAttributes(p);
    }

    public void setMsg(String msg){
       this.msg = msg;
    }

    public void setSubmitText(String text){
        this.submit_text = text;
    }

    public void setCancelText(String text){
        this.cancel_text = text;
    }

    public void setTitle(String text){
        this.title_text = text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                listener.onCancelClick(this);
                break;
            case R.id.submit:
                listener.onSubmitClick(this);
                break;
        }
    }

}
