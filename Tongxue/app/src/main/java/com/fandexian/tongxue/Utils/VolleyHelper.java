package com.fandexian.tongxue.Utils;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fandexian.tongxue.Bean.UserInfo;
import com.fandexian.tongxue.MainActivity;
import com.fandexian.tongxue.activitys.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by fandexian on 16/4/15.
 */
public class VolleyHelper {
    private RequestQueue requestQueue;
    private final int Post = Request.Method.POST ;
    private String url;
    private Object parametersBean;
    private Class clazz;
    private Resp resp = new Resp();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){

            }
        }
    };

    public VolleyHelper(String url, Object parametersBean, Class clazz,Context context) {
        this.url = url;
        this.parametersBean = parametersBean;
        this.clazz = clazz;
        requestQueue = Volley.newRequestQueue(context);
    }

    public Resp post(){
        StringRequest stringRequest = new StringRequest(Post, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("======response",s);
                        try {
                            Log.e("====volley","onResponse");
                            JsonHelper.toJavaBean(resp,s);
                            Log.e("======response", s);
                            Log.e("====resp", resp.getMessage());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("===error","volleyError");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return JsonHelper.toMap(parametersBean);
            }
        };
        requestQueue.add(stringRequest);
        Log.e("====volley", "beforeReturn");
        return resp;
    }




}
