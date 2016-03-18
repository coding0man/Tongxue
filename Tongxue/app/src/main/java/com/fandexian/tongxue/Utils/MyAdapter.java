package com.fandexian.tongxue.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by dexian.fan on 2016/3/18.
 */
public class MyAdapter extends BaseAdapter {
    List<Map<String,String>> data;
    Context context;

    public MyAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_item_img);
            viewHolder.title = (TextView) convertView.findViewById(R.id.id_item_title);
            viewHolder.describe = (TextView) convertView.findViewById(R.id.id_item_des);
            viewHolder.price = (TextView) convertView.findViewById(R.id.id_item_pri);
            viewHolder.time = (TextView) convertView.findViewById(R.id.id_item_time);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.title.setText(data.get(position).get("name"));
        viewHolder.describe.setText(data.get(position).get("name"));
        viewHolder.price.setText(data.get(position).get("name"));
        viewHolder.time.setText(data.get(position).get("name"));

        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView title,describe,price,time;
    }
}
