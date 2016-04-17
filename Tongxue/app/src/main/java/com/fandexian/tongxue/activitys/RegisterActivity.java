package com.fandexian.tongxue.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fandexian.tongxue.MainActivity;
import com.fandexian.tongxue.R;
import com.fandexian.tongxue.Utils.Api;
import com.fandexian.tongxue.Utils.Constants;
import com.fandexian.tongxue.Utils.JsonHelper;
import com.fandexian.tongxue.Utils.MyApplication;
import com.fandexian.tongxue.Utils.ToastHelper;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by xia.yang on 2016/4/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    //=======view
    private EditText phoneNum,code,nickName,passWord,rePassWord;
    private Button btnGainCode,btnSubmit;

    //========variable
    private Context _this;
    private RequestQueue requestQueue;
    private Map result;

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

    private void register() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("======response", s);
                        try {
                            result = JsonHelper.toMap(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if("1".equals(result.get("status"))){
                            MyApplication.setIsLogin(true);
                            startActivity(new Intent(_this, MainActivity.class));
                            finish();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastHelper.makeText(_this, "网络请求失败，请检查网络设置");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userPhone",phoneNum.getText().toString());
                params.put("nickName",nickName.getText().toString());
                params.put("userPassword",passWord.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs,1,1.0f));
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        initView();
        initVariable();


    }

    private void initVariable() {
        _this = this;
        requestQueue = Volley.newRequestQueue(_this);
        //初始化短信验证sdk
        SMSSDK.initSDK(this, "10e49ebab1f3c", "49b99208bfc47f794ed344177e21a3a0");
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    /**
     * 初始化view
     */
    private void initView() {

        //titleBar
        ((TextView)findViewById(R.id.id_title_title_text)).setText("注 册");
        findViewById(R.id.id_title_right_text).setVisibility(View.GONE);

        //linear
        phoneNum=(EditText)findViewById(R.id.edt_register_phoneNum);
        code=(EditText)findViewById(R.id.edt_register_code);
        nickName=(EditText)findViewById(R.id.edt_register_userName);
        passWord=(EditText)findViewById(R.id.edt_register_passWord);
        rePassWord=(EditText)findViewById(R.id.edt_register_rePassWord);
        btnGainCode=(Button)findViewById(R.id.btn_register_gainCode);
        btnSubmit=(Button)findViewById(R.id.btn_register_submit);

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
            case R.id.btn_register_gainCode://获取验证码
                if(phoneNum.getText().toString().trim().length() == 11) {
                    TimeCount timeCount = new TimeCount(60 * 1000, 1000);
                    timeCount.start();
                    gainCode();
                }
                break;

            case R.id.btn_register_submit://提交
                checkMessage();
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
    private void checkMessage() {


        if (TextUtils.isEmpty(phoneNum.getText().toString())) {
            ToastHelper.makeText(_this, "手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(code.getText().toString())){
            ToastHelper.makeText(_this, "验证码不能为空");
            return;
        }
         if (TextUtils.isEmpty(nickName.getText().toString())) {
             ToastHelper.makeText(_this, "昵称不能为空");
             return;
         }
         if (TextUtils.isEmpty(passWord.getText().toString())){
             ToastHelper.makeText(_this, "密码不能为空");
             return;
          }
        if (!(passWord.getText().toString()).equals((rePassWord.getText().toString()))){
            ToastHelper.makeText(_this, "两次输入密码不一致");
            return;
         }

        checkCode();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
