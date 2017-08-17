package com.example.refresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.refresh.holder.CustomRVHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 2016/12/26.
 */

public abstract class CustomRVAdapter<T> extends RecyclerView.Adapter {
    Context mContext;
    int mLayoutId;
    List<T> mTList;

    public CustomRVAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mTList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        return CustomRVHolder.getViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((CustomRVHolder)holder, mTList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mTList.size();
    }

    public abstract void convert(CustomRVHolder holder, T item, int position);

    public void addAll(List<T> tList) {
        if (tList == null || tList.size() <= 0) {
            return;
        }
        mTList.addAll(tList);
        notifyDataSetChanged();
    }

    public void clear(){
        mTList.clear();
    }

    public void add(T item) {
        if (item == null) {
            return;
        }
        mTList.add(1,item);
        notifyItemInserted(1);
    }

    public void add(T item,int position){
        synchronized (this) {
            if (item==null){
                return;
            }
            mTList.add(position,item);
            notifyItemInserted(position);
        }
    }

    public void delete(T item){
        if (item==null){
            return;
        }
        mTList.remove(item);
        notifyItemRemoved(mTList.indexOf(item));
    }
}
