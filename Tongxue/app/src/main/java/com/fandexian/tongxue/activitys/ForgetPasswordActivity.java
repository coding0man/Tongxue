package com.fandexian.tongxue.activitys;

/**
 * Created by fandexian on 16/4/9.
 */


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fandexian.tongxue.R;
import com.fandexian.tongxue.Utils.MToast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener{

    //=======view
    private EditText phoneNum,code,passWord,rePassWord;
    private Button btnGainCode,btnSubmit;

    //========variable
    private Context _this;
    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            handler.sendMessage(msg);

        }
    };

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {//回调完成

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    forget();//注册

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功
                    Toast.makeText(_this,"验证码已发送",Toast.LENGTH_LONG).show();

                }
            }else{
                ((Throwable)data).printStackTrace();
                Toast.makeText(_this,"验证码输入错误",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_foget_password);

        initView();
        _this = this;
        //初始化短信验证sdk
        SMSSDK.initSDK(this, "10e49ebab1f3c", "49b99208bfc47f794ed344177e21a3a0");
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }

    /**
     * 初始化view
     */
    private void initView() {

        //titleBar
        ((TextView)findViewById(R.id.id_title_title_text)).setText("忘记密码");
        findViewById(R.id.id_title_right_text).setVisibility(View.GONE);


        //linear
        phoneNum=(EditText)findViewById(R.id.edt_forget_phoneNum);
        code=(EditText)findViewById(R.id.edt_forget_code);
        passWord=(EditText)findViewById(R.id.edt_forget_passWord);
        rePassWord=(EditText)findViewById(R.id.edt_forget_rePassWord);
        btnGainCode=(Button)findViewById(R.id.btn_forget_gainCode);
        btnSubmit=(Button)findViewById(R.id.btn_forget_submit);

        //setListener
        findViewById(R.id.id_title_back_img).setOnClickListener(this);
        btnGainCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    /**
     * 实现单击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_title_back_img:
                finish();
                break;
            case R.id.btn_forget_gainCode://获取验证码
                TimeCount timeCount = new TimeCount(60 * 1000, 1000);
                timeCount.start();
                gainCode();
                break;

            case R.id.btn_forget_submit://提交
                checkCode();
                break;
        }
    }

    /**
     * 验证验证码是否正确
     */
    private void checkCode() {

        SMSSDK.submitVerificationCode("86", phoneNum.getText().toString(), code.getText().toString());

    }

    /**
     * 注册提交
     */
    private void forget() {


        if (TextUtils.isEmpty(phoneNum.getText().toString())) {
            MToast.mToast(_this,"手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())){
            MToast.mToast(_this, "验证码不能为空");
        }
        if (TextUtils.isEmpty(passWord.getText().toString())){
            MToast.mToast(_this,"密码不能为空");
            return;
        }
        if (!(passWord.getText().toString()).equals((rePassWord.getText().toString()))){
            MToast.mToast(_this,"两次输入密码不一致");
        }

        //MToast.mToast(_this,"下面可以进行网路请求了");

    }

    /**
     * 获取验证码
     */
    private void gainCode() {

        if (!TextUtils.isEmpty(phoneNum.getText().toString())) {
            String phone = phoneNum.getText().toString();
            SMSSDK.getVerificationCode("86", phone);
        }else {
            Toast.makeText(_this, "电话不能为空", Toast.LENGTH_SHORT).show();

        }
    }



    /**
     * 倒计时
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onFinish() {
            btnGainCode.setText("重新发送");
            btnGainCode.setClickable(true);
            btnGainCode.setBackgroundResource(R.drawable.shape_btn);
        }

        public void onTick(long millisUntilFinished) {
            btnGainCode.setText(millisUntilFinished / 1000 + "秒");
            btnGainCode.setClickable(false);
            btnGainCode.setBackgroundResource(R.drawable.shape_btn_unclick);
        }
    }

    /**
     * 解除监听
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}


