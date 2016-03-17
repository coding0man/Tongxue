package com.fandexian.tongxue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fandexian.tongxue.fragments.Fabu;
import com.fandexian.tongxue.fragments.Home;
import com.fandexian.tongxue.fragments.UserCenter;

public class MainActivity extends Activity implements View.OnClickListener{

    private LinearLayout ll_home,ll_fabu,ll_user_center;
    private ImageView img1,img2,img3;
    private TextView tv1,tv2,tv3;
    private Fragment home,fabu,user_center;
    private FragmentManager fm = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setDefaultFragment(savedInstanceState);
    }

    private void initView() {
        //绑定View
        //底部单个组合按钮
        ll_home = (LinearLayout) findViewById(R.id.id_ll_home);
        ll_fabu = (LinearLayout) findViewById(R.id.id_ll_fabu);
        ll_user_center = (LinearLayout) findViewById(R.id.id_ll_user_center);
        //底部的三个小图标
        img1 = (ImageView) findViewById(R.id.id_main_img1);
        img2 = (ImageView) findViewById(R.id.id_main_img2);
        img3 = (ImageView) findViewById(R.id.id_main_img3);
        //底部的三个文字
        tv1 = (TextView) findViewById(R.id.id_main_tv1);
        tv2 = (TextView) findViewById(R.id.id_main_tv2);
        tv3 = (TextView) findViewById(R.id.id_main_tv3);

        //设置点击事件监听器
        ll_home.setOnClickListener(this);
        ll_fabu.setOnClickListener(this);
        ll_user_center.setOnClickListener(this);
    }

    private void setDefaultFragment(Bundle save) {
        //设置默认fragment
        if(save==null){
            //防止屏幕方向切换时再次新建fragment
            img1.setImageDrawable(getResources().getDrawable(R.mipmap.icon_homepage_after_click));
            tv1.setTextColor(getResources().getColor(R.color.mainColor));
            home = new Home();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.id_main_vp,home);
            ft.commit();
        }
    }

    @Override
    public void onClick(View v) {
        //监听点击事件
        FragmentTransaction ft = fm.beginTransaction();
        resetView();//重设底部的按钮和文字
        hindFragment(ft);//隐藏所有的fragment

        switch (v.getId()){
            case R.id.id_ll_home:
                img1.setImageDrawable(getResources().getDrawable(R.mipmap.icon_homepage_after_click));
                tv1.setTextColor(getResources().getColor(R.color.mainColor));
                if(home == null){
                    home = new Home();
                    ft.add(R.id.id_main_vp,home);
                }
                ft.show(home);
                break;
            case R.id.id_ll_fabu:
                img2.setImageDrawable(getResources().getDrawable(R.mipmap.icon_fabu_after_click));
                tv2.setTextColor(getResources().getColor(R.color.mainColor));
                if(fabu == null){
                    fabu = new Fabu();
                    ft.add(R.id.id_main_vp,fabu);
                }
                ft.show(fabu);
                break;
            case R.id.id_ll_user_center:
                img3.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user_center_after_click));
                tv3.setTextColor(getResources().getColor(R.color.mainColor));
                if(user_center == null){
                    user_center = new UserCenter();
                    ft.add(R.id.id_main_vp,user_center);
                }
                ft.show(user_center);
                break;
        }
        ft.commit();
    }

    private void hindFragment(FragmentTransaction ft) {
        if(home != null && home.isVisible()) ft.hide(home);
        if(fabu != null && fabu.isVisible()) ft.hide(fabu);
        if(user_center != null && user_center.isVisible()) ft.hide(user_center);

    }

    private void resetView() {
        img1.setImageDrawable(getResources().getDrawable(R.mipmap.icon_homepage_default));
        tv1.setTextColor(getResources().getColor(R.color.defaultTextColor));

        img2.setImageDrawable(getResources().getDrawable(R.mipmap.icon_fabu_default));
        tv2.setTextColor(getResources().getColor(R.color.defaultTextColor));

        img3.setImageDrawable(getResources().getDrawable(R.mipmap.icon_user_center_default));
        tv3.setTextColor(getResources().getColor(R.color.defaultTextColor));
    }
}
