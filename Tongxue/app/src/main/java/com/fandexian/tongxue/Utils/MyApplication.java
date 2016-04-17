package com.fandexian.tongxue.Utils;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by fandexian on 16/4/16.
 */
public class MyApplication extends Application {

    private static Boolean isLogin = false;
    private static int userId;
    private static String userPhone;
    private static String nickName;
    private static String userSex;
    private static String userHead;
    private static String userQq;
    private static String userWechat;
    private static int isCertified;
    private static String  userDepartment;
    private static String stuNumber;



    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Boolean getIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(Boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        MyApplication.userId = userId;
    }

    public static String getUserPhone() {
        return userPhone;
    }

    public static void setUserPhone(String userPhone) {
        MyApplication.userPhone = userPhone;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        MyApplication.nickName = nickName;
    }

    public static String getUserSex() {
        return userSex;
    }

    public static void setUserSex(String userSex) {
        MyApplication.userSex = userSex;
    }

    public static String getUserHead() {
        return userHead;
    }

    public static void setUserHead(String userHead) {
        MyApplication.userHead = userHead;
    }

    public static String getUserQq() {
        return userQq;
    }

    public static void setUserQq(String userQq) {
        MyApplication.userQq = userQq;
    }

    public static String getUserWechat() {
        return userWechat;
    }

    public static void setUserWechat(String userWechat) {
        MyApplication.userWechat = userWechat;
    }

    public static int getIsCertified() {
        return isCertified;
    }

    public static void setIsCertified(int isCertified) {
        MyApplication.isCertified = isCertified;
    }

    public static String getUserDepartment() {
        return userDepartment;
    }

    public static void setUserDepartment(String userDepartment) {
        MyApplication.userDepartment = userDepartment;
    }

    public static String getStuNumber() {
        return stuNumber;
    }

    public static void setStuNumber(String stuNumber) {
        MyApplication.stuNumber = stuNumber;
    }
}
