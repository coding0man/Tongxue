package com.fandexian.tongxue.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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
import com.fandexian.tongxue.GoodsDetail;
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
    private RelativeLayout progressBar;
    private Button bt_chooseCampus,bt_chooseCatrgory;
    private XListView goodsList;
    private PopupWindow campusPop,categoryPop;

    //====variable
    private final int UP = 0;
    private final int DOWN = 1;

    final String [] campus = {"全部校区","东区","本部","北区","阳澄湖校区","独墅湖校区"};
    final String []mainCategory = {"全部分类","校园代步","电子产品","图书教材","衣物伞帽","日常用品","电器","娱乐健身","其他"};
    final String [][] minorCategory = {
            {"全部校园代步","自行车","电瓶车"},
            {"全部电子产品","电脑/手机", "鼠标/键盘", "U盘/内存卡",  "相机/单反","移动电源", "耳机","其他",},
            {"全部图书教材","教材", "四六级相关资料", "托福/雅思", "考研资料", "课外书", "其他",},
            {"全部衣物伞帽","上衣", "裤子", "鞋", "背包", "雨伞", "帽子", "其他",},
            {"全部日常用品","电脑桌", "开水壶", "盆子", "凉席", "被子", "蚊帐", "其他",},
            {"全部电器","洗衣机", "电吹风","冰箱", "电风扇", "台灯", "暖手宝", "其他"},
            {"全部娱乐健身","篮球/足球/排球","乒乓球拍","羽毛球拍", "网球拍","轮滑","哑铃/臂力器","飞盘","其他",},
            {"其他"}};
    final int[] minorStart = {1,3,10,16,23,30,37};
    private int campus_status = DOWN,category_status = DOWN;
    private int selectedCampusId = 0,selectedMainCategoryId = 0,selectedMinorCategoryId = 0;

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
        //===title
        progressBar = (RelativeLayout) view.findViewById(R.id.id_home_progress);

        progressBar.setVisibility(View.VISIBLE);
        view.findViewById(R.id.id_title_back_img).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.id_title_title_text)).setText("商品列表");
        view.findViewById(R.id.id_title_right_text).setVisibility(View.GONE);

        //===分类选择列表
        bt_chooseCampus = (Button) view.findViewById(R.id.id_home_choose_campus);
        bt_chooseCampus.setText(campus[selectedCampusId]);
        bt_chooseCampus.setOnClickListener(this);
        bt_chooseCatrgory = (Button) view.findViewById(R.id.id_home_choose_category);
        bt_chooseCatrgory.setText(mainCategory[selectedMainCategoryId]);
        bt_chooseCatrgory.setOnClickListener(this);

        //==xListView
        goodsList = (XListView) view.findViewById(android.R.id.list);
        goodsList.setPullRefreshEnable(true);
        goodsList.setPullLoadEnable(true);
        goodsList.setXListViewListener(this);


    }

    private void initVariable() {
        _this = getActivity();
        requestQueue = Volley.newRequestQueue(_this);
        myAdapter = new MyAdapter();
        dataList = new ArrayList();
        tempDataList = new ArrayList();
        goodsList.setAdapter(myAdapter);
        getBriefGoods();
        goodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(_this, GoodsDetail.class);
                intent.putExtra("goodsId", dataList.get(position - 1).getGoodsId());
                startActivity(intent);
            }
        });

    }

    private void getBriefGoods() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.GET_GOODS_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        tempDataList.clear();
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
                                progressBar.setVisibility(View.GONE);
                                dataList.clear();
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
                map.put("goodsCampus",selectedCampusId+"");
                map.put("mainCategoryId",selectedMainCategoryId+"");
                map.put("minorCategoryId",selectedMinorCategoryId+"");
                map.put("pageSize","20");
                map.put("page","1");
                Log.e("===params",selectedCampusId+""+selectedMainCategoryId+""+selectedMinorCategoryId);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRefresh() {


        goodsList.stopRefresh();
        goodsList.stopLoadMore();
        goodsList.setRefreshTime("刚刚");
    }

    @Override
    public void onLoadMore() {

        goodsList.stopRefresh();
        goodsList.stopLoadMore();
        goodsList.setRefreshTime("刚刚");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_home_choose_campus:
                if(campus_status == DOWN) {
                    Drawable drawable1 = getResources().getDrawable(R.mipmap.icon_up_arrow);
                    /// 这一步必须要做,否则不会显示.
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    bt_chooseCampus.setCompoundDrawables(null, null, drawable1, null);
                    campus_status = UP;

                }else{
                    Drawable drawable1 = getResources().getDrawable(R.mipmap.icon_down_arrow);
                    /// 这一步必须要做,否则不会显示.
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                    bt_chooseCampus.setCompoundDrawables(null, null, drawable1, null);
                    campus_status = DOWN;
                }
                popCampus();
                break;
            case R.id.id_home_choose_category:
                if (category_status == DOWN) {
                    Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_up_arrow);
                    /// 这一步必须要做,否则不会显示.
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    //左，上，右，下
                    bt_chooseCatrgory.setCompoundDrawables(null, null, drawable2, null);
                    category_status = UP;
                }else{
                    Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_down_arrow);
                    /// 这一步必须要做,否则不会显示.
                    drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                    //左，上，右，下
                    bt_chooseCatrgory.setCompoundDrawables(null, null, drawable2, null);
                    category_status = DOWN;
                }
                popCategory();
                break;
        }
    }

    private void popCampus() {
        if(campusPop != null && campusPop.isShowing()){
            campusPop.dismiss();
            return;
        }

        View view = LayoutInflater.from(_this).inflate(R.layout.pop_campus,null);
        ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(_this,R.layout.item_of_campus,R.id.id_item_of_campus_tv,campus);
        final ListView listCampus = (ListView) view.findViewById(R.id.id_pop_campus_list);
        listCampus.setAdapter(campusAdapter);
        listCampus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedCampusId = position;
                campusPop.dismiss();
                bt_chooseCampus.setText(campus[position]);

            }
        });
        campusPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        campusPop.setBackgroundDrawable(new BitmapDrawable());
        campusPop.setOutsideTouchable(true);
        campusPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable drawable1 = getResources().getDrawable(R.mipmap.icon_down_arrow);
                /// 这一步必须要做,否则不会显示.
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                bt_chooseCampus.setCompoundDrawables(null, null, drawable1, null);
                campus_status = DOWN;
                getBriefGoods();
            }
        });

        campusPop.showAsDropDown(bt_chooseCampus);
        view.findViewById(R.id.id_pop_campus_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campusPop.dismiss();
            }
        });

    }

    private void popCategory() {
        if(categoryPop != null && categoryPop.isShowing()){
            categoryPop.dismiss();
            return;
        }

        View view = LayoutInflater.from(_this).inflate(R.layout.pop_category,null);

        ArrayAdapter<String> mainAdapter = new ArrayAdapter<String>(_this,R.layout.item_of_category,R.id.id_item_category_tv,mainCategory);
        final ListView listMain = (ListView) view.findViewById(R.id.id_pop_category_main);
        final ListView listMinor = (ListView) view.findViewById(R.id.id_pop_category_minor);
        listMain.setAdapter(mainAdapter);
        listMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int mainPosition = position;
                if (position == 0) {
                    selectedMainCategoryId = 0;
                    selectedMinorCategoryId = 0;
                    categoryPop.dismiss();
                    bt_chooseCatrgory.setText("全部分类");
                } else {
                    selectedMainCategoryId = mainPosition;
                    ArrayAdapter<String> minorAdapter = new ArrayAdapter<String>(_this, R.layout.item_of_category, R.id.id_item_category_tv, minorCategory[position - 1]);
                    listMinor.setAdapter(minorAdapter);
                    listMinor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                selectedMinorCategoryId = 0;
                                categoryPop.dismiss();
                                bt_chooseCatrgory.setText(minorCategory[mainPosition - 1][position]);
                            }else{
                                selectedMinorCategoryId = minorStart[mainPosition - 1] + position - 1;
                                categoryPop.dismiss();
                                bt_chooseCatrgory.setText(minorCategory[mainPosition - 1][position]);
                            }
                        }
                    });
                }
            }
        });
        categoryPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        categoryPop.setBackgroundDrawable(new BitmapDrawable());
        categoryPop.setOutsideTouchable(true);
        categoryPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable drawable2 = getResources().getDrawable(R.mipmap.icon_down_arrow);
                /// 这一步必须要做,否则不会显示.
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                //左，上，右，下
                bt_chooseCatrgory.setCompoundDrawables(null, null, drawable2, null);
                category_status = DOWN;
                getBriefGoods();
            }
        });

        categoryPop.showAsDropDown(bt_chooseCatrgory);
        view.findViewById(R.id.id_pop_category_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryPop.dismiss();
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
