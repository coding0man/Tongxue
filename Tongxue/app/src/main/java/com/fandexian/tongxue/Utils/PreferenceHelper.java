package com.fandexian.tongxue.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fandexian on 16/4/16.
 */
public class PreferenceHelper {
    public static final String isLogin = "isLogin";
    public static final String  userId = "userId";
    public static final String  userPhone = "userPhone";
    public static final String  nickName = "nickName";
    public static final String  userSex = "userSex";
    public static final String  userHead = "userHead";
    public static final String  userQq = "userQq";
    public static final String  userWechat = "userWechat";
    public static final String  isCertified = "isCertified";
    public static final String   userDepartment = "userDepartment";
    public static final String  stuNumber = "stuNumber";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static PreferenceHelper preferenceHelper;

    private PreferenceHelper() {
    }

    public static PreferenceHelper from(Context context) {
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (preferenceHelper == null) {
            preferenceHelper = new PreferenceHelper();
            preferenceHelper.setDefaultValue();
        }
        return preferenceHelper;
    }

    private void setDefaultValue() {
        saveBoolean(isLogin,false);
    }

    public boolean saveString(String key,String value){
        editor.putString(key,value).commit();
        return true;
    }
    public String getString(String key){

        return sharedPreferences.getString(key,"-1");
    }

    public boolean saveInt(String key,int value){
        editor.putInt(key, value).commit();
        return true;
    }
    public int getInt(String key){

        return sharedPreferences.getInt(key, -1);
    }

    public boolean saveBoolean(String key,boolean value){
        editor.putBoolean(key, value).commit();
        return true;
    }
    public boolean getBoolean(String key){

        return sharedPreferences.getBoolean(key, false);
    }

    public boolean saveFloat(String key,float value){
        editor.putFloat(key, value).commit();
        return true;
    }
    public float getFloat(String key){

        return sharedPreferences.getFloat(key, -1);
    }

//    public static boolean isLogin() {
//
//        return sharedPreferences.getBoolean("isLogin",false);
//
//    }
//
//    public static void setIsLogin(boolean isLogin) {
//        editor.putBoolean("isLogin",isLogin);
//        editor.commit();
//    }
//
//    public static int getUserId() {
//        return sharedPreferences.getInt("userId",-1);
//    }
//
//    public static void setUserId(int userId) {
//        editor.putInt("userId", userId);
//        editor.commit();
//    }
//
//    public static String getUserPhone() {
//        return sharedPreferences.getString("userPhone","-1");
//    }
//
//    public static void setUserPhone(String userPhone) {
//        editor.putString("userPhone",userPhone);
//        editor.commit();
//    }
//
//    public static String getNickName() {
//        return sharedPreferences.getString("nickName","-1");
//    }
//
//    public static void setNickName(String nickName) {
//        editor.putString("nickName",nickName);
//        editor.commit();
//    }
//
//    public static String getUserSex() {
//        return sharedPreferences.getString("userSex","-1");
//    }
//
//    public static void setUserSex(String userSex) {
//
//        editor.putString("userSex",userSex);
//        editor.commit();
//    }
//
//    public static String getUserHead() {
//        return sharedPreferences.getString("userHead","-1");
//    }
//
//    public static void setUserHead(String userHead) {
//
//        editor.putString("userHead",userHead);
//        editor.commit();
//    }
//
//    public static String getUserQq() {
//        return sharedPreferences.getString("userQq","-1");
//    }
//
//    public static void setUserQq(String userQq) {
//
//        editor.putString("userQq",userQq);
//        editor.commit();
//    }
//
//    public static String getUserWechat() {
//        return sharedPreferences.getString("userWechat","-1");
//    }
//
//    public static void setUserWechat(String userWechat) {
//
//        editor.putString("userWechat",userWechat);
//        editor.commit();
//    }
//
//    public static int getIsCertified() {
//        return sharedPreferences.getInt("isCertified", -1);
//    }
//
//    public static void setIsCertified(int isCertified) {
//
//        editor.putInt("isCertified", isCertified);
//        editor.commit();
//    }
//
//    public static String getUserDepartment() {
//        return sharedPreferences.getString("userDepartment","-1");
//    }
//
//    public static void setUserDepartment(String userDepartment) {
//
//        editor.putString("userDepartment",userDepartment);
//        editor.commit();
//    }
//
//    public static String getStuNumber() {
//        return sharedPreferences.getString("stuNumber","-1");
//    }
//
//    public static void setStuNumber(String stuNumber) {
//
//        editor.putString("stuNumber",stuNumber);
//        editor.commit();
//    }

}
