package com.fandexian.tongxue.Utils;

/**
 * Created by fandexian on 16/4/16.
 */
public class Api {

    public static final String BASE_API = "http://192.168.31.101:8080/";

    public static final String IS_REGISTERED = BASE_API+"api/isRegistered";     //<!--判断是否注册-->
    public static final String REGISTER = BASE_API+"api/register";              //用户注册
    public static final String LOGIN = BASE_API+"api/login";                    //用户登录
    public static final String CHANGE_PASSWORD = BASE_API+"api/changePassword";
    public static final String RESET_PASSWORD = BASE_API+"api/resetPassword";
    public static final String GET_USER_INFO = BASE_API+"api/getUserInfo";
    public static final String EDIT_USER_INFO = BASE_API+"api/editUserInfo";
    public static final String GET_GOODS_LIST = BASE_API+"api/getGoodsList";
    public static final String GET_GOODS_DETAIL = BASE_API+"api/getGoodsDetail";
    public static final String RELEASE_GOODS = BASE_API+"api/releaseGoods";
    public static final String DELETE_GOODS = BASE_API+"api/deleteGoods";
    public static final String EDIT_GOODS_INFO = BASE_API+"api/editGoodsInfo";
    public static final String GET_MY_GOODS = BASE_API+"api/getMyGoods";
    public static final String VERIFY_STU = BASE_API+"api/verifyStu";
    public static final String ADD_COLLECT = BASE_API+"api/addCollect";
    public static final String DELETE_COLLECT = BASE_API+"api/deleteCollect";
    public static final String GET_MY_COLLECT = BASE_API+"api/getMyCollects";

}





//<!--获取个人收藏-->
//<servlet>
//<servlet-name>getMyCollects</servlet-name>
//<servlet-class>getMyCollects</servlet-class>
//</servlet>
//<servlet-mapping>
//<servlet-name>getMyCollects</servlet-name>
//<url-pattern>/api/getMyCollects</url-pattern>
//</servlet-mapping>