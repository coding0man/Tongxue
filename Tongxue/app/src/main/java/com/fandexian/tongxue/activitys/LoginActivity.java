package com.fandexian.tongxue.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
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

public class LoginActivity extends Activity implements View.OnClickListener{

    //=======view
    private TextView register;
    private EditText phoneNum,passWord;
    private Button login;
    private TextView forgetPassword;


    //=========viriable
    private Context _this;
    private RequestQueue requestQueue;
    private Map result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        initVariable();

    }

    private void initVariable() {
        _this = this;
        requestQueue = Volley.newRequestQueue(_this);
    }

    private void initView() {
        //title
        ((TextView)findViewById(R.id.id_title_title_text)).setText("登    录");
        findViewById(R.id.id_title_back_img).setOnClickListener(this);
        register = ((TextView)findViewById(R.id.id_title_right_text));
        register.setText("注 册");
        register.setOnClickListener(this);

        //linear
        phoneNum= (EditText) findViewById(R.id.edt_login_phoneNum);
        passWord= (EditText) findViewById(R.id.edt_login_passWord);


        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.id_login_tv_fogetpsw).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_title_back_img:
                finish();
                break;
            case R.id.id_title_right_text:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.id_login_tv_fogetpsw:
                startActivity(new Intent(_this,ResetPasswordActivity.class));
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        if(phoneNum.getText().toString().isEmpty()){
            ToastHelper.makeText(_this, "请输入手机号");
            return;
        }
        if(passWord.getText().toString().isEmpty()){
            ToastHelper.makeText(_this, "请输入密码");
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("======response",s);
                        try {
                            result = JsonHelper.toMap(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if("1".equals(result.get("status"))){
                            startActivity(new Intent(_this, MainActivity.class));

                            MyApplication.setIsLogin(true);
                            MyApplication.setUserPhone(phoneNum.getText().toString());

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
                params.put("userPassword",passWord.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs,1,1.0f));
        requestQueue.add(stringRequest);

    }




}
