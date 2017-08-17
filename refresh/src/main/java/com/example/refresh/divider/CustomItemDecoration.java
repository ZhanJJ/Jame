package com.example.refresh.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by James on 2016/11/18.
 * 自定义RecyclerView分割线
 * <p>
 * 第一件是：获取Divider
 * 第二件事：就是找到我们需要添加Divider的位置，从onDraw方法中去找到，并将Divider添加进去。
 * 第三个是：得到Item的偏移量，在getItemOffsets设置
 */

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int mOrientation;
    private Drawable mDrawable;

    public static int ORIENTATION_VERTICAL = LinearLayoutManager.VERTICAL;
    public static int ORIENTATION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    private static final int[] Attrs = new int[]{
            android.R.attr.listDivider
    };

    public CustomItemDecoration(Context context, int orientation) {
        this.mContext = context;
        TypedArray ta = mContext.obtainStyledAttributes(Attrs);
        if (ta == null || ta.getDrawable(0) == null) {
            ta = mContext.obtainStyledAttributes(Attrs);
        }
        this.mDrawable = ta.getDrawable(0);
        ta.recycle();
        setOrientation(orientation);
    }

    //设置屏幕的方向
    private void setOrientation(int orientation) {
        if (orientation != ORIENTATION_HORIZONTAL && orientation != ORIENTATION_VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation = orientation;
    }

    //设置分割线drawable
    private void setDivider(int divider){
        Drawable drawable =mContext.getResources().getDrawable(divider);
        if (drawable!=null){
            this.mDrawable = drawable;
        }
    }
    //设置分割线drawable
    private void setDivider(Drawable divider){
        if (divider!=null){
            this.mDrawable = divider;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == ORIENTATION_VERTICAL) {
            drawVerticalLine(c, parent);
        } else if (mOrientation == ORIENTATION_HORIZONTAL) {
            drawHorizontalLine(c, parent);
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    private void drawHorizontalLine(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getRight() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    //画竖线
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getBottom() - parent.getPaddingBottom();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == ORIENTATION_HORIZONTAL) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else if (mOrientation == ORIENTATION_VERTICAL) {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        }
    }
}
