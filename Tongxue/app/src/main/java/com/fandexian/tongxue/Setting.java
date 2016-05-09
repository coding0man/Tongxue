package com.fandexian.tongxue;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.fandexian.tongxue.Utils.PreferenceHelper;
import com.fandexian.tongxue.fragments.UserCenter;
import com.google.gson.Gson;



public class Setting extends Activity implements View.OnClickListener{
    //=============view
    private ImageView head;
    private TextView name,jifen;

    private RelativeLayout rl_logout,rl_change_psw;

    //==============variable
    private PopupWindow popOut;
    private LayoutInflater inflater;
    private int userId ;//当前登录用户的用户id
    private Context _this = Setting.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        
    }

    @Override
    public void onClick(View v) {

    }

}


