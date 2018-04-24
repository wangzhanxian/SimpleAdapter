package com.wzx.simpleadapter.model;

import com.wzx.simpleadapter.R;
import com.wzx.simpleadapter.adpter.Cell;
import com.wzx.simpleadapter.adpter.ViewHolder;

/**
 * Created by WangZhanXian on 2018/4/24.
 */

public class TypeTwoModel implements Cell {
    @Override
    public int layoutId() {
        return R.layout.item_type_two;
    }

    @Override
    public void bindView(ViewHolder holder, int position) {
        //TODO
    }
}
