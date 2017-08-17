package com.example.refresh.holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by James on 2016/12/26.
 */

public class CustomRVHolder extends RecyclerView.ViewHolder {
    SparseArray<View> mViews;
    View mItemView;

    private CustomRVHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public static CustomRVHolder getViewHolder(View itemView){
        return new CustomRVHolder(itemView);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

}
