package com.fandexian.tongxue.fragments;

import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fandexian.tongxue.Bean.GoodsBriefMessage;
import com.fandexian.tongxue.MyView.XListView;
import com.fandexian.tongxue.R;
import com.fandexian.tongxue.Utils.Api;
import com.fandexian.tongxue.Utils.JsonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dexian.fan on 2016/3/17.
 */
public class Home extends ListFragment implements View.OnClickListener,XListView.IXListViewListener{

    //=====view
    private View view;
    private Button bt_chooseCampus;
    private XListView xListView;
    private PopupWindow campusPop,categoryPop;

    //====variable
    private Context _this;
    private MyAdapter myAdapter;
    private List<GoodsBriefMessage> dataList ,tempDataList;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        initVariable();
        return view;
    }



    private void initView() {
        bt_chooseCampus = (Button) view.findViewById(R.id.id_home_choose_campus);
        bt_chooseCampus.setOnClickListener(this);
        xListView = (XListView) view.findViewById(android.R.id.list);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);

    }

    private void initVariable() {
        _this = getActivity();
        requestQueue = Volley.newRequestQueue(_this);
        myAdapter = new MyAdapter();
        dataList = new ArrayList();
        tempDataList = new ArrayList();
        xListView.setAdapter(myAdapter);
        getBriefGoods();

    }

    private void getBriefGoods() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.GET_GOODS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("===response", s);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(s);
                            if ("1".equals(jsonObject.getString("status"))){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0;i<jsonArray.length();i++){
                                    GoodsBriefMessage goodsBriefMessage = new GoodsBriefMessage();
                                    JsonHelper.toJavaBean(goodsBriefMessage,jsonArray.get(i).toString());
                                    tempDataList.add(goodsBriefMessage);
                                }
                                //tempDataList = JSON.parseArray(jsonArray.toString(),GoodsBriefMessage.class);
                                dataList.addAll(tempDataList);
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
                map.put("goodsCampus","-1");
                map.put("mainCategoryId","-1");
                map.put("minorCategoryId","-1");
                map.put("pageSize","20");
                map.put("page","1");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {


        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {

        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_home_choose_campus:
                popCampus();
        }
    }

    private void popCampus() {
        if(campusPop != null || campusPop.isShowing()){
            campusPop.dismiss();
            return;
        }
        View view = LayoutInflater.from(_this).inflate(R.layout.pop_campus,null);
        campusPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        campusPop.setBackgroundDrawable(new BitmapDrawable());
        campusPop.setOutsideTouchable(true);
        //campusPop.setTouchable(true);
        campusPop.showAsDropDown(bt_chooseCampus);
        view.findViewById(R.id.id_pop_campus_bt_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("====>","点击了确定");
            }
        });
    }

    class MyAdapter extends BaseAdapter{

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
