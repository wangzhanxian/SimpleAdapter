package com.wzx.simpleadapter.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
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

public class ViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;
    private Context mContext;
    private View mConvertView;
    private Cell data;
    /**
     * init holder
     */
    public ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext=context;
        mConvertView=itemView;
        mViews = new SparseArray<View>();
    }
    public void setCurData(Cell data){
        this.data=data;
    }
    public Cell getCurData(){
        return data;
    }
    /**
     *  获取viewHolder
     */
    public static ViewHolder getHolder(Context context, int layoutId, ViewGroup parent) {
        return new ViewHolder(context, LayoutInflater.from(context).inflate(layoutId, parent,
                false));
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * get view
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
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
     *  set image res
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
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
     *  set image bitmap
     */
    public ViewHolder setImageDraw(int viewId,Drawable drawable){
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
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

}
