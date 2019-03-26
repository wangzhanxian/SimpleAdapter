package com.wzx.app.smartadapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;


/**
 * 默认的加载更多实现
 */
public class DefaultLoadMoreImpl extends BaseLoadMoreCell {

    public static final int STATUS_DEFAULT = 1;

    public static final int STATUS_LOADING = 2;

    public static final int STATUS_FAILED = 3;

    public static final int STATUS_NO_MORE = 4;

    public static final int STATUS_FINISH = 5;

    public DefaultLoadMoreImpl(int initStatus, ILoadMoreListener listener) {
        super(initStatus,listener);
    }

    @Override
    protected void loadStart() {
        setLoadStatus(STATUS_LOADING);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_load_more;
    }

    @Override
    public void onBindViewHolder(int position, ViewHolder holder, SmartAdapter adapter) {
        TextView view = holder.getView(R.id.tv_status);
        switch (curStatus){
            case STATUS_DEFAULT:
            case STATUS_FINISH:
                holder.itemView.setVisibility(View.GONE);
                break;
            case STATUS_LOADING:
                view.setText(R.string.status_loading_start);
                Drawable drawable = holder.getContext().getResources().getDrawable(R.drawable.loading);
                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
                view.setCompoundDrawables(drawable,null,null,null);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
            case STATUS_FAILED:
                view.setText(R.string.status_loading_failed);
                view.setCompoundDrawables(null,null,null,null);
                holder.itemView.setVisibility(View.VISIBLE);
                break;

            case STATUS_NO_MORE:
                view.setText(R.string.status_loading_no_more);
                view.setCompoundDrawables(null,null,null,null);
                holder.itemView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
