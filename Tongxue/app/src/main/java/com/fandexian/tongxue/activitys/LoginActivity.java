package com.fandexian.tongxue.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fandexian.tongxue.R;

/**
 * Created by xia.yang on 2016/4/8.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    TextView register;
    EditText phoneNum,passWord;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        register= (TextView) findViewById(R.id.tv_login_register);
        phoneNum= (EditText) findViewById(R.id.edt_login_phoneNum);
        passWord= (EditText) findViewById(R.id.edt_login_passWord);
        login= (Button) findViewById(R.id.btn_login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_login_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {

    }
}
