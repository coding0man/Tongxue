package com.fandexian.tongxue;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fandexian.tongxue.Bean.GoodsBriefMessage;
import com.fandexian.tongxue.Bean.GoodsDetailMessage;
import com.fandexian.tongxue.Utils.Api;
import com.fandexian.tongxue.Utils.JsonHelper;
import com.fandexian.tongxue.Utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodsDetail extends Activity implements View.OnClickListener{
    //===view
    private RelativeLayout progressBar;
    private TextView goodsTitle,goodsReleaseTime,goodsPrice,goodsCampus,goodsDes,goodsContact;

    //====variable
    private Context _this;
    private RequestQueue requestQueue;
    private GoodsDetailMessage goodsDetail = new GoodsDetailMessage();
    private int goodsId;
    final String [] campus = {"全部校区","东区","本部","北区","阳澄湖校区","独墅湖校区"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        goodsId = getIntent().getIntExtra("goodsId",-1);
        initView();
        initVariable();
    }

    private void initView() {
        //===title
        findViewById(R.id.id_title_back_img).setOnClickListener(this);
        ((TextView)findViewById(R.id.id_title_title_text)).setText("商品详情");
        findViewById(R.id.id_title_right_text).setVisibility(View.GONE);

        goodsTitle = (TextView) findViewById(R.id.id_detail_goods_title);
        goodsReleaseTime = (TextView) findViewById(R.id.id_detail_goods_release_time);
        goodsPrice = (TextView) findViewById(R.id.id_detail_goods_price);
        goodsCampus = (TextView) findViewById(R.id.id_detail_goods_campus);
        goodsDes = (TextView) findViewById(R.id.id_detail_des);
        goodsContact = (TextView) findViewById(R.id.id_detail_contact);

        progressBar = (RelativeLayout) findViewById(R.id.id_detail_progress);
    }
    void initVariable() {
        _this = this;
        requestQueue = Volley.newRequestQueue(_this);
        getGoodsDetail();
    }
    private void getGoodsDetail() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.GET_GOODS_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("===response", s);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(s);
                            if ("1".equals(jsonObject.getString("status"))){
                                JsonHelper.toJavaBean(goodsDetail,jsonObject.getJSONObject("data").toString());
                                progressBar.setVisibility(View.GONE);
                                fillData();

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
                Log.e("===>","volleyError");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap();
                map.put("goodsId",goodsId+"");
                Log.e("===params", goodsId+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void fillData() {
        int YEAR = goodsDetail.getReleaseTime().getYear()+1900;
        int MONTH = goodsDetail.getReleaseTime().getMonth()+1;
        int DAY = goodsDetail.getReleaseTime().getDate();
        goodsTitle.setText(goodsDetail.getGoodsName());
        goodsReleaseTime.setText(YEAR+"-" + MONTH+"-" +DAY);
        goodsPrice.setText(goodsDetail.getGoodsPrice()+"");
        goodsCampus.setText(campus[Integer.parseInt(goodsDetail.getGoodsCampus())]);
        goodsDes.setText(goodsDetail.getGoodsDescription());
        //goodsContact.setText(goodsDetail.getGoodsName());
    }

    @Override
    public void onClick(View v) {

    }
}
