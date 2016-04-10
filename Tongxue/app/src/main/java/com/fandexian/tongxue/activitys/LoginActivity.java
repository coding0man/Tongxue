package com.fandexian.tongxue.activitys;

import android.app.Activity;
import android.content.Context;
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

    //=======view
    private TextView register;
    private EditText phoneNum,passWord;
    private Button login;
    private TextView forgetPassword;


    //=========viriable
    private Context _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
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
        login= (Button) findViewById(R.id.btn_login);

        findViewById(R.id.id_login_tv_fogetpsw).setOnClickListener(this);

        login.setOnClickListener(this);

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
                startActivity(new Intent(_this,ForgetPasswordActivity.class));
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {

    }
}
