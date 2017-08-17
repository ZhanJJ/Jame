package com.example.refresh.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.refresh.holder.CustomBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 万能适配器
 * Created by James on 2016/12/23.
 */

public abstract class CustomBaseAdapter<T> extends BaseAdapter {
    Context mContext;
    List<T> mList;
    int mLayoutId;


    public CustomBaseAdapter(Context context,int layoutId){
        this.mContext = context;
        this.mList  = new ArrayList<>();
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        if (mList.size()-1>i&&mList.get(i)!=null){
            return mList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        CustomBaseViewHolder viewHolder = CustomBaseViewHolder.get(mContext,position,contentView,mLayoutId);
        convert(viewHolder, (T) getItem(position));
        return viewHolder.getContentView();
    }

    public void clear(){
        mList.clear();
    }

    public void add(List<T> list){
        if (list!=null && list.size()>0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public abstract void convert(CustomBaseViewHolder viewHolder, T item);

}
