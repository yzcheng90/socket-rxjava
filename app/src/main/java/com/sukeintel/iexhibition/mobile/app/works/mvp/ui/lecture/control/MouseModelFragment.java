package com.sukeintel.iexhibition.mobile.app.works.mvp.ui.lecture.control;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sukeintel.iexhibition.mobile.app.works.mvp.R;
import com.sukeintel.iexhibition.mobile.app.works.mvp.entity.EquipmentEntity;
import com.sukeintel.iexhibition.mobile.app.works.mvp.present.leture.MouseModelPresent;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.FunSwitch;
import com.sukeintel.iexhibition.mobile.app.works.mvp.widget.XLinearLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * Created by czx on 2017/8/25.
 */

public class MouseModelFragment extends XFragment<MouseModelPresent> implements MouseView<MouseModelPresent>,GestureDetector.OnGestureListener {

    @BindView(R.id.switchButton)
    FunSwitch switchButton;
    @BindView(R.id.shou_text)
    TextView shouText;
    @BindView(R.id.touch)
    LinearLayout touch;
    @BindView(R.id.btn_keyword)
    Button btnKeyword;
    @BindView(R.id.btn_hand)
    Button btnHand;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.show_edit)
    EditText showEdit;
    @BindView(R.id.mouse_root_layout)
    XLinearLayout mouseRootLayout;
    boolean isSendNull = true;

    public static EquipmentEntity equipmentEntity = null;
    private boolean isDrag = false;
    private GestureDetectorCompat touchDetector;
    private SharedPreferences prefs;
    private final String PREF_SENSITIVITY = "pref_sensitivity";
    private final String PREF_ACCELERATION = "pref_acceleration";
    private float sensitivity;
    private float acceleration;
    private float movePreviousX;
    private float movePreviousY;
    private float moveResultX;
    private float moveResultY;

    @Override
    public void initData(Bundle savedInstanceState) {

        //添加layout大小发生改变监听器
        mouseRootLayout.setListener(new XLinearLayout.InputWindowListener() {
            @Override
            public void show() {
                isSendNull = true;
            }

            @Override
            public void hidden() {
                isSendNull = false;
                showEdit.setText("");
            }
        });
        showEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s!= null && s.length()==0){
                    btnSend.setVisibility(View.GONE);
                    btnHand.setVisibility(View.VISIBLE);
                }else{
                    btnSend.setVisibility(View.VISIBLE);
                    btnHand.setVisibility(View.GONE);
                }
            }
        });

        touchDetector = new GestureDetectorCompat(context, this);
        touch.setOnTouchListener((View v, MotionEvent event) ->{
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    handleTouchMove(event);
                    break;
                case MotionEvent.ACTION_DOWN:
                    handleTouchDown(event);
                    break;
                default:
                    break;
            }
            touchDetector.onTouchEvent(event);
            return true;
        });
        setPrefs();

    }

    @OnClick({R.id.btn_submit,R.id.btn_keyword,R.id.btn_send,R.id.btn_hand})
    public void EventClick(View view){
        switch (view.getId()){
            case R.id.btn_submit:
                isDrag = !isDrag;
                if(isDrag){
                    btnSubmit.setText("点击放下");
                    getP().setMediaBoxMouseEvent(equipmentEntity.getMediabox_id(),"LEFT","0");
                }else{
                    btnSubmit.setText("点击拖动");
                    getP().setMediaBoxMouseEvent(equipmentEntity.getMediabox_id(),"LEFT","1");
                }
                break;
            case R.id.btn_keyword:
                btnKeyword.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                btnHand.setVisibility(View.VISIBLE);
                showEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_send:
                getP().setMediaBoxKeyBoardEvent(equipmentEntity.getMediabox_id(),showEdit.getText().toString());
                showEdit.setText("");
                break;
            case R.id.btn_hand:
                btnKeyword.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                btnHand.setVisibility(View.GONE);
                showEdit.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_control_mouse_model_fragment;
    }

    @Override
    public MouseModelPresent newP() {
        return new MouseModelPresent();
    }

    public static MouseModelFragment newInstance(EquipmentEntity entity) {
        equipmentEntity = entity;
        return new MouseModelFragment();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == 67){
            getP().setMediaBoxKeyBoardEvent(equipmentEntity.getMediabox_id(),"{BACKSPACE}");
        }
        return true;
    }

    /*
	 * Handle touch input of type ACTION_MOVE
	 */
    private void handleTouchMove(MotionEvent e) {
        float moveDistanceRawX = e.getRawX() - movePreviousX;
        float moveDistanceRawY = e.getRawY() - movePreviousY;
        moveDistanceRawX *= sensitivity;
        moveDistanceRawY *= sensitivity;
        float accelerationX = (float) ((Math.pow(Math.abs(moveDistanceRawX), acceleration) * Math.signum(moveDistanceRawX)));
        float accelerationY = (float) ((Math.pow(Math.abs(moveDistanceRawY), acceleration) * Math.signum(moveDistanceRawY)));
        moveDistanceRawX = accelerationX;
        moveDistanceRawY = accelerationY;
        moveDistanceRawX += moveResultX;
        moveDistanceRawY += moveResultY;
        int moveDistanceXFinal = Math.round(moveDistanceRawX);
        int moveDistanceYFinal = Math.round(moveDistanceRawY);
        if (moveDistanceXFinal != 0 || moveDistanceYFinal != 0){
            getP().setMediaBoxCursorPos(equipmentEntity.getMediabox_id(),moveDistanceXFinal,moveDistanceYFinal);
        }
        moveResultX = moveDistanceRawX - moveDistanceXFinal;
        moveResultY = moveDistanceRawY - moveDistanceYFinal;
        movePreviousX = e.getRawX();
        movePreviousY = e.getRawY();
    }

    /*
     * Handle touch input of type ACTION_DOWN
     */
    private void handleTouchDown(MotionEvent e) {
        moveResultX = 0;
        moveResultY = 0;
        movePreviousX = e.getRawX();
        movePreviousY = e.getRawY();
    }

    private void setPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener((SharedPreferences sharedPreferences, String key) ->{
            if(key.equals(PREF_SENSITIVITY)) {
                sensitivity = Float.parseFloat(sharedPreferences.getString(key, "0.7"));
            } else if (key.equals(PREF_ACCELERATION)) {
                acceleration = Float.parseFloat(sharedPreferences.getString(key, "1.5"));
            }

        });
        sensitivity = Float.parseFloat(prefs.getString("pref_sensitivity", "0.7"));
        acceleration = Float.parseFloat(prefs.getString("pref_acceleration", "1.5"));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(isDrag){
            getP().setMediaBoxMouseEvent(equipmentEntity.getMediabox_id(),"LEFT","0");
        }else{
            getP().setMediaBoxMouseEvent(equipmentEntity.getMediabox_id(),"LEFT","2");
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        getP().setMediaBoxMouseEvent(equipmentEntity.getMediabox_id(),"RIGHT","2");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void showSuccess(String msg) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onResume() {
        super.onResume();
        btnHand.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        btnHand.setVisibility(View.GONE);
    }
}
