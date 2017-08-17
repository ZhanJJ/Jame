package com.example.refresh.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.refresh.R;

import java.util.List;

/**
 * Created by James on 2016/11/17.
 */

public class ContactPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int TYPE_NORMAL = 1;
    public static int TYPE_HEADER = 2;
    public static int TYPE_FOOTER = 3;

    private View mHeaderView;   //头部view
    private View mFooterView;   //底部view

    private Context mContext;
    private List<String> mList;

    public ContactPersonAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER && mHeaderView != null) {
            return new PersonViewHolder(mHeaderView);
        }
        if (viewType == TYPE_FOOTER && mFooterView != null) {
            return new PersonViewHolder(mFooterView);
        }
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_contact_person, null);

        return new PersonViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof PersonViewHolder) {
            ((PersonViewHolder) holder).tvName.setText(mList.get(position - 1));
            ((PersonViewHolder) holder).tvName.setTag(position);
            ((PersonViewHolder) holder).tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    Snackbar.make(view, mList.get(position - 1), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        return;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView != null && mFooterView != null) {
            return mList.size() + 2;
        }
        if ((mHeaderView != null && mFooterView == null) ||
                (mHeaderView == null && mFooterView != null)) {
            return mList.size() + 1;
        }
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (mFooterView != null && position == mList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public PersonViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }


    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }
}
