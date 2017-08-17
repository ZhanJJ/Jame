package com.example.refresh.holder;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 万能适配器之ViewHolder
 * Created by James on 2016/12/23.
 */

public class CustomBaseViewHolder {
    Context mContext;
    SparseArray<View> views;
    public static View mContentView;


    private CustomBaseViewHolder(Context context, int position, View contentView, int layoutId) {
        this.mContext = context;
        views = new SparseArray<>();
        contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        this.mContentView = contentView;
        contentView.setTag(this);
    }

    public static CustomBaseViewHolder get(Context context, int position, View contentView, int layoutId) {
        if (contentView == null) {
            return new CustomBaseViewHolder(context, position, contentView, layoutId);
        }
        mContentView = contentView;
        return (CustomBaseViewHolder) contentView.getTag();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getContentView() {
        return mContentView;
    }

    public CustomBaseViewHolder setText(int viewId, String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
