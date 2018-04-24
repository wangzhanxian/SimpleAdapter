package com.wzx.simpleadapter.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class SmartAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<Cell> mDatas;

    public SmartAdapter(Context context, @NonNull List<Cell> datas){
        mContext = context;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.getHolder(mContext,viewType,parent);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).layoutId();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mDatas.get(position).bindView(holder,position);
    }

}
