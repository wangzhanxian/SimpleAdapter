package com.wzx.simpleadapter.model;

import com.wzx.simpleadapter.R;
import com.wzx.simpleadapter.adpter.Cell;
import com.wzx.simpleadapter.adpter.ViewHolder;

/**
 * Created by WangZhanXian on 2018/4/24.
 */

public class TypeOneModel implements Cell {
    @Override
    public int layoutId() {
        return R.layout.item_type_one;
    }

    @Override
    public void bindView(ViewHolder holder, int position) {
        holder.setText(R.id.tv_type_one,mInfo);
    }

    private String mInfo;

    public TypeOneModel(String info){
        mInfo=info;
    }
}
