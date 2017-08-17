package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2016/6/27.
 */
public class CommonListAdapter extends BaseAdapter {
    Context mContext;
    List<String> mStringList = new ArrayList<>();

    public CommonListAdapter(Context context){
        this.mContext = context;
    }


    public void add(List<String> list){
        mStringList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        mStringList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<String> list){
        clear();
        add(list);
        notifyDataSetChanged();
    }

    public void setSelectedItem(int position){
        ViewHolder viewHolder = (ViewHolder) getView(position,null,null).getTag();
        viewHolder.tvContent.setTextColor(((Activity)mContext).getResources().getColor(R.color.colorPrimary));
        viewHolder.imgTag.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view,null);
            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.imgTag = (ImageView) convertView.findViewById(R.id.imgTag);
            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }

        viewHolder.tvContent.setText(mStringList.get(position));
        return convertView;
    }

    private class ViewHolder{
        TextView tvContent;
        ImageView imgTag;
    }
}
