package com.fandexian.tongxue;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fandexian.tongxue.Bean.UserInfo;
import com.fandexian.tongxue.Utils.Api;
import com.fandexian.tongxue.Utils.Constants;
import com.fandexian.tongxue.Utils.JsonHelper;
import com.fandexian.tongxue.Utils.MyApplication;
import com.fandexian.tongxue.Utils.Resp;
import com.fandexian.tongxue.Utils.SharedPreferenceHelper;
import com.fandexian.tongxue.Utils.ToastHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetail extends Activity implements View.OnClickListener{
    //======view
    private ImageView back;
    private CircleImageView head;
    private TextView edit_save;
    private LinearLayout nickName,sex,qq,wechat,department,stuNumber;
    private TextView tv_nick,tv_sex,tv_qq,tv_wechat,tv_department,tv_stuNumber;
    private RadioGroup rg_sex;
    private EditText et_nick,et_qq,et_wechat,et_department,et_stuNumber;

    //===========variable
    private Context _this;
    private RequestQueue requestQueue;
    private UserInfo userInfo = new UserInfo(),editing_userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_detail);

        initView();
        initVariable();

        getUserInfo();

    }



    private void initView() {
        findViewById(R.id.id_detail_back_img).setOnClickListener(this);
        head = (CircleImageView) findViewById(R.id.id_detail_img_head);
        edit_save = (TextView) findViewById(R.id.id_detail_tv_edit_save);
        edit_save.setOnClickListener(this);

        tv_nick = (TextView) findViewById(R.id.id_detail_tv_nick);
        tv_sex = (TextView) findViewById(R.id.id_detail_tv_sex);
        tv_qq = (TextView) findViewById(R.id.id_detail_tv_qq);
        tv_wechat = (TextView) findViewById(R.id.id_detail_tv_wechat);
        tv_department = (TextView) findViewById(R.id.id_detail_tv_department);
        tv_stuNumber = (TextView) findViewById(R.id.id_detail_tv_stunumber);

        et_nick = (EditText) findViewById(R.id.id_detail_et_nick);
        rg_sex = (RadioGroup) findViewById(R.id.id_detail_rg_sex);
        et_qq = (EditText) findViewById(R.id.id_detail_et_qq);
        et_wechat = (EditText) findViewById(R.id.id_detail_et_wechat);
        et_department = (EditText) findViewById(R.id.id_detail_et_department);
        et_stuNumber = (EditText) findViewById(R.id.id_detail_et_stunumber);
    }
    private void initVariable() {
        _this = this;
        requestQueue = Volley.newRequestQueue(_this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_detail_back_img:
                finish();
                break;
            case R.id.id_detail_tv_edit_save:
                if ("修改".equals(edit_save.getText().toString())){
                    edit_save.setText("保存");
                    findViewById(R.id.id_detail_ll_et).setVisibility(View.VISIBLE);
                    findViewById(R.id.id_detail_ll_tv).setVisibility(View.GONE);
                }else if ("保存".equals(edit_save.getText().toString())){
                    edit_save.setText("修改");
                    findViewById(R.id.id_detail_ll_et).setVisibility(View.GONE);
                    findViewById(R.id.id_detail_ll_tv).setVisibility(View.VISIBLE);
                    getTexts();
                    editUserInfo();
                }
                break;
        }
    }



    private void getUserInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.GET_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("======response", s);

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if("1".equals(jsonObject.getString("status"))){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JsonHelper.toJavaBean(userInfo,jsonObject1.toString());
                                JsonHelper.toJavaBean(editing_userInfo,jsonObject1.toString());
                                setTexts();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
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
                params.put("userPhone", MyApplication.getUserPhone());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs, 1, 1.0f));
        requestQueue.add(stringRequest);
    }

    private void getTexts() {
        userInfo.setNickName(et_nick.getText().toString().trim());
        //userInfo.setUserSex(et_nick.getText().toString().trim());
        userInfo.setUserQq(et_qq.getText().toString().trim());
        userInfo.setUserWechat(et_wechat.getText().toString().trim());
        userInfo.setUserDepartment(et_department.getText().toString().trim());
        userInfo.setStuNumber(et_stuNumber.getText().toString().trim());

    }
    private void setTexts() {
        //将数据存入text
        tv_nick.setText(userInfo.getNickName());
        tv_sex.setText(userInfo.getUserSex());
        tv_qq.setText(userInfo.getUserQq());
        tv_wechat.setText(userInfo.getUserWechat());
        tv_department.setText(userInfo.getUserDepartment());
        tv_stuNumber.setText(userInfo.getStuNumber());

//        //将数据存入et
//        et_nick.setText(userInfo.getNickName());
//        if("男".equals(userInfo.getUserSex())){
//            findViewById(R.id.id_detail_rb_male).setSelected(true);
//        }else if("女".equals(userInfo.getUserSex())){
//            findViewById(R.id.id_detail_rb_female).setSelected(true);
//        }
//        et_qq.setText(userInfo.getUserQq());
//        et_wechat.setText(userInfo.getUserWechat());
//        et_department.setText(userInfo.getUserDepartment());
//        et_stuNumber.setText(userInfo.getStuNumber());


        //将输入存入application中
        setToApplication();
    }

    private void setToApplication() {


        MyApplication.setUserPhone(userInfo.getNickName());
        MyApplication.setNickName(userInfo.getNickName());
        MyApplication.setUserSex(userInfo.getNickName());
        MyApplication.setUserHead(userInfo.getUserHead());
        MyApplication.setUserQq(userInfo.getNickName());
        MyApplication.setUserWechat(userInfo.getNickName());
        MyApplication.setIsCertified(userInfo.getIsCertified());
        MyApplication.setUserDepartment(userInfo.getNickName());
        MyApplication.setStuNumber(userInfo.getNickName());
    }

    private void editUserInfo() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.EDIT_USER_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Map map = null;
                        Log.e("======response", s);
                        try {
                            map = JsonHelper.toMap(s);
                            if("1".equals(map.get("status"))){
                                ToastHelper.makeText(_this,"修改信息成功");

                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
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
                return JsonHelper.toMap(editing_userInfo);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(Constants.initialTimeoutMs,1,1.0f));
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
