package com.wzx.app.smartadapter;

import android.view.View;
import android.widget.TextView;


/**
 * 默认的加载更多实现
 */
@LayoutResName("item_load_more")
public class DefaultLoadMoreImpl extends BaseLoadMoreCell {

    public static final int STATUS_DEFAULT = 1;

    public static final int STATUS_LOADING = 2;

    public static final int STATUS_FAILED = 3;

    public static final int STATUS_NO_MORE = 4;

    public static final int STATUS_FINISH = 5;

    public DefaultLoadMoreImpl(int initStatus) {
        super(initStatus);
    }

    @Override
    protected void loadStart() {
        setLoadStatus(STATUS_LOADING);
    }

    @Override
    public void onBindViewHolder(int position, ViewHolder holder, SmartAdapter adapter) {
        TextView view = holder.getView(R.id.tv_status);
        switch (curStatus){

            case STATUS_DEFAULT:
                view.setText(R.string.status_loading_start);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
            case STATUS_LOADING:
                view.setText(R.string.status_loading_start);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
            case STATUS_FAILED:
                view.setText(R.string.status_loading_failed);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
            case STATUS_NO_MORE:
                view.setText(R.string.status_loading_no_more);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
            case STATUS_FINISH:
                holder.itemView.setVisibility(View.GONE);
                break;
        }
    }
}
