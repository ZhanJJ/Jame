package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2016/6/27.
 */
public class ClassifyListAdapter extends BaseAdapter {
    Context mContext;
    List<ClassifyInfo> mList = new ArrayList<>();

    public ClassifyListAdapter(Context context){
        this.mContext = context;
    }

    public void add(List<ClassifyInfo> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear(){
        mList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ClassifyInfo> list){
        clear();
        add(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(mList.get(position).name);
        return convertView;
    }

    private class ViewHolder{
        TextView tvContent;
    }
}
