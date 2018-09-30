package com.wzx.app.smartadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * author
 */

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = ViewHolder.class.getSimpleName();

    private Context mContext;
    private SparseArray<View> mViews;

    private SmartAdapter mAdapter;

    /**
     * init holder
     */
    public ViewHolder(Context context, View itemView, SmartAdapter adapter) {
        super(itemView);
        mContext = context;
        mAdapter = adapter;
        mViews = new SparseArray<>();
        if (mAdapter.getItemClickListener() != null && mAdapter.getClickIds() != null){
            for (int id :mAdapter.getClickIds()) {
                View view = getView(id);
                if (view != null) {
                    view.setOnClickListener(this);
                }
            }
        }
        if (mAdapter.getItemLongClickListener() != null && mAdapter.getLongClickIds() != null){
            for (int id :mAdapter.getLongClickIds()) {
                View view = getView(id);
                if (view != null) {
                    view.setOnLongClickListener(this);
                }
            }
        }
    }
    /**
     *  获取viewHolder
     */
    public static ViewHolder getHolder(Context context, int layoutId, ViewGroup parent, SmartAdapter adapter) {
        return new ViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent,
                false),adapter);
    }

    public Context getContext() {
        return mContext;
    }

    public SmartAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * get view
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * set text
     */
    public ViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * set text by strId
     */
    public ViewHolder setText(int viewId, @StringRes int strId){
        TextView tv = getView(viewId);
        tv.setText(strId);
        return this;
    }

    /**
     *  set image res
     */
    public ViewHolder setImageRes(int viewId,int resId){
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     *  set image bitmap
     */
    public ViewHolder setImageDraw(int viewId,Drawable drawable){
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
        return this;
    }

    /**
     *  set image res
     */
    public ViewHolder setBackgroundRes(int viewId,int resId){
        TextView tv = getView(viewId);
        tv.setBackgroundResource(resId);
        return this;
    }

    /**
     *  set check
     */
    public ViewHolder setIsCheck(int viewId,boolean isCheck){
        CheckBox iv = getView(viewId);
        iv.setChecked(isCheck);
        return this;
    }


    public ViewHolder addOnClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(this);
        }
        return this;
    }

    public ViewHolder addOnLongClickListener(@IdRes int viewId) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(this);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        try {
            if (mAdapter.getItemClickListener() != null) {
                mAdapter.getItemClickListener().onClick(v, ViewHolder.this, getLayoutPosition());
            }
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        try {
            return mAdapter.getItemLongClickListener() != null && mAdapter.getItemLongClickListener().onLongClick(v, ViewHolder.this, getLayoutPosition());
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
            return false;
        }
    }
}
