package com.fandexian.tongxue.activitys;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fandexian.tongxue.R;
import com.fandexian.tongxue.Utils.MToast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by xia.yang on 2016/4/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    EditText phoneNum,code,userName,passWord,rePassWord;
    Button btnGainCode,btnSubmit;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initView();
        context=this;
        //初始化短信验证sdk
        SMSSDK.initSDK(this, "10e49ebab1f3c", "49b99208bfc47f794ed344177e21a3a0");
        SMSSDK.registerEventHandler(eh); //注册短信回调



    }

    /**
     * 初始化view
     */
    private void initView() {
        phoneNum=(EditText)findViewById(R.id.edt_register_phoneNum);
        code=(EditText)findViewById(R.id.edt_register_code);
        userName=(EditText)findViewById(R.id.edt_register_userName);
        passWord=(EditText)findViewById(R.id.edt_register_passWord);
        rePassWord=(EditText)findViewById(R.id.edt_register_rePassWord);
        btnGainCode=(Button)findViewById(R.id.btn_register_gainCode);
        btnSubmit=(Button)findViewById(R.id.btn_register_submit);

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
            case R.id.btn_register_gainCode://获取验证码
               TimeCount timeCount = new TimeCount(60 * 1000, 1000);
                timeCount.start();
                gainCode();
            break;

            case  R.id.btn_register_submit://提交
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
    private void register() {


        if (TextUtils.isEmpty(phoneNum.getText().toString())) {
            MToast.mToast(context,"手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())){
            MToast.mToast(context, "验证码不能为空");
        }
         if (TextUtils.isEmpty(userName.getText().toString())) {
             MToast.mToast(context,"昵称不能为空");
         }
         if (TextUtils.isEmpty(passWord.getText().toString())){
             MToast.mToast(context,"密码不能为空");
             return;
          }
        if (!(passWord.getText().toString()).equals((rePassWord.getText().toString()))){
            MToast.mToast(context,"两次输入密码不一致");
         }

        MToast.mToast(context,"下面可以进行网路请求了");

    }

    /**
     * 获取验证码
     */
    private void gainCode() {

        if (!TextUtils.isEmpty(phoneNum.getText().toString())) {
            String phone = phoneNum.getText().toString();
            SMSSDK.getVerificationCode("86", phone);
        }else {
            Toast.makeText(RegisterActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();

        }
    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {//回调完成

                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    register();//注册

                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){ //获取验证码成功
                    Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_LONG).show();

                }
            }else{
                ((Throwable)data).printStackTrace();
                Toast.makeText(RegisterActivity.this,"验证码输入错误",Toast.LENGTH_LONG).show();
            }
        }
    };

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
        SMSSDK.unregisterEventHandler(eh);
    }
}
