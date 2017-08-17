package com.example.james.jame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.james.jame.R;

import java.util.List;

/**
 * Created by James on 2016/5/29.
 */
public class Recycler2Adapter extends RecyclerView.Adapter<Recycler2Adapter.ViewHolder> {
    Context mContext;
    List<String> mList;
    OnItemClickListener mOnItemClickListener;

    public Recycler2Adapter(Context context,List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvContent);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v,getLayoutPosition());
            }
        }
    }

    public void setOnItemClickListener(Recycler2Adapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
         void onItemClick(View v,int position);
    }
}
