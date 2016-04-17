package com.fandexian.tongxue.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.fandexian.tongxue.MyReleasedGoods;

/**
 * Created by fandexian on 16/4/16.
 */
public class SharedPreferenceHelper {
    private boolean isLogin;
    private int userId;
    private String userPhone;
    private String nickName;
    private String userSex;
    private String userHead;
    private String userQq;
    private String userWechat;
    private int isCertified;
    private String  userDepartment;
    private String stuNumber;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferences getSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return sharedPreferences;
    }

    public static boolean isLogin() {

        return sharedPreferences.getBoolean("isLogin",false);

    }

    public static void setIsLogin(boolean isLogin) {
        editor.putBoolean("isLogin",isLogin);
        editor.commit();
    }

    public static int getUserId() {
        return sharedPreferences.getInt("userId",-1);
    }

    public static void setUserId(int userId) {
        editor.putInt("userId", userId);
        editor.commit();
    }

    public static String getUserPhone() {
        return sharedPreferences.getString("userPhone","-1");
    }

    public static void setUserPhone(String userPhone) {
        editor.putString("userPhone",userPhone);
        editor.commit();
    }

    public static String getNickName() {
        return sharedPreferences.getString("nickName","-1");
    }

    public static void setNickName(String nickName) {
        editor.putString("nickName",nickName);
        editor.commit();
    }

    public static String getUserSex() {
        return sharedPreferences.getString("userSex","-1");
    }

    public static void setUserSex(String userSex) {

        editor.putString("userSex",userSex);
        editor.commit();
    }

    public static String getUserHead() {
        return sharedPreferences.getString("userHead","-1");
    }

    public static void setUserHead(String userHead) {

        editor.putString("userHead",userHead);
        editor.commit();
    }

    public static String getUserQq() {
        return sharedPreferences.getString("userQq","-1");
    }

    public static void setUserQq(String userQq) {

        editor.putString("userQq",userQq);
        editor.commit();
    }

    public static String getUserWechat() {
        return sharedPreferences.getString("userWechat","-1");
    }

    public static void setUserWechat(String userWechat) {

        editor.putString("userWechat",userWechat);
        editor.commit();
    }

    public static int getIsCertified() {
        return sharedPreferences.getInt("isCertified", -1);
    }

    public static void setIsCertified(int isCertified) {

        editor.putInt("isCertified", isCertified);
        editor.commit();
    }

    public static String getUserDepartment() {
        return sharedPreferences.getString("userDepartment","-1");
    }

    public static void setUserDepartment(String userDepartment) {

        editor.putString("userDepartment",userDepartment);
        editor.commit();
    }

    public static String getStuNumber() {
        return sharedPreferences.getString("stuNumber","-1");
    }

    public static void setStuNumber(String stuNumber) {

        editor.putString("stuNumber",stuNumber);
        editor.commit();
    }

}
