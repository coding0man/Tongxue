package com.fandexian.tongxue;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.fandexian.tongxue.Utils.Api;
import com.fandexian.tongxue.Utils.JsonHelper;
import com.fandexian.tongxue.Utils.PreferenceHelper;
import com.mob.tools.utils.SharePrefrenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyColects extends Activity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private RelativeLayout progressBar;
    private ListView collectList;
    private Context _this;
    private RequestQueue requestQueue;
    private MyAdapter myAdapter;
    private List<GoodsBriefMessage> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_colects);

        initView();
        initVariable();
    }

    private void initView() {
        //===title
        findViewById(R.id.id_title_back_img).setOnClickListener(this);
        ((TextView)findViewById(R.id.id_title_title_text)).setText("我的收藏");
        findViewById(R.id.id_title_right_text).setVisibility(View.GONE);

        progressBar = (RelativeLayout) findViewById(R.id.id_my_collect_progress);

        collectList = (ListView) findViewById(R.id.id_my_collects_lv);
        collectList.setOnItemClickListener(this);
    }

    private void initVariable() {
        _this = this;
        requestQueue = Volley.newRequestQueue(_this);
        myAdapter = new MyAdapter();
        dataList = new ArrayList();
        collectList.setAdapter(myAdapter);
        getMyCollects();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(_this,GoodsDetail.class);
        intent.putExtra("goodsId",dataList.get(position).getGoodsId());
        startActivity(intent);
    }
    private void getMyCollects() {

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.GET_MY_COLLECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        dataList.clear();
                        Log.e("===response", s);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(s);
                            if ("1".equals(jsonObject.getString("status"))){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0;i<jsonArray.length();i++){
                                    GoodsBriefMessage goodsBriefMessage = new GoodsBriefMessage();
                                    JsonHelper.toJavaBean(goodsBriefMessage, jsonArray.get(i).toString());
                                    dataList.add(goodsBriefMessage);
                                }
                                //tempDataList = JSON.parseArray(jsonArray.toString(),GoodsBriefMessage.class);

                                progressBar.setVisibility(View.GONE);
                                myAdapter.notifyDataSetChanged();

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
                map.put("userId", PreferenceHelper.from(_this).getInt(PreferenceHelper.userId) + "");
                Log.e("===params", PreferenceHelper.from(_this).getInt(PreferenceHelper.userId)+"");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_title_back_img:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(_this).inflate(R.layout.item_of_goods,null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) convertView.findViewById(R.id.id_item_of_goods_pic);
                viewHolder.title = (TextView) convertView.findViewById(R.id.id_item_of_goods_title);
                viewHolder.place = (TextView) convertView.findViewById(R.id.id_item_of_goods_trade_position);
                viewHolder.price = (TextView) convertView.findViewById(R.id.id_item_of_goods_price);
                viewHolder.time = (TextView) convertView.findViewById(R.id.id_item_of_goods_time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            GoodsBriefMessage message = dataList.get(position);
            viewHolder.title.setText(message.getGoodsName());
            viewHolder.price.setText(message.getGoodsPrice()+"");
            viewHolder.time.setText(message.getReleaseTime()+"");
            return convertView;
        }
        class ViewHolder{
            private ImageView img;
            private TextView title ,place ,price ,time;
        }
    }
}
