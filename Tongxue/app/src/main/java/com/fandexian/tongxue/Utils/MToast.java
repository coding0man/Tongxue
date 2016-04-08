package com.fandexian.tongxue.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xia.yang on 2016/4/8.
 */
public class MToast {

    public static void mToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show();
    }
}
