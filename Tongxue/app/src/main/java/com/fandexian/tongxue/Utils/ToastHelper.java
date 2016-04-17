package com.fandexian.tongxue.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xia.yang on 2016/4/8.
 */
public class ToastHelper {

    public static void makeText(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
