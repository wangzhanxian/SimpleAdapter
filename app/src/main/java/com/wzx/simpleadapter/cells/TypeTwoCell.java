package com.wzx.simpleadapter.cells;

import com.wzx.app.smartadapter.ICell;
import com.wzx.app.smartadapter.SmartAdapter;
import com.wzx.app.smartadapter.ViewHolder;
import com.wzx.simpleadapter.R;
import com.wzx.simpleadapter.model.TypeModel;

public class TypeTwoCell implements ICell<TypeModel> {


    @Override
    public int getLayoutId() {
        return R.layout.item_type_two;
    }

    @Override
    public boolean isCurType(int pos, SmartAdapter<TypeModel> adapter) {
        TypeModel data = adapter.getData(pos);
        return data!= null && data.getDrawableId() != 0;
    }

    @Override
    public void onBindViewHolder(int position, ViewHolder holder, SmartAdapter<TypeModel> adapter) {
        holder.setImageRes(R.id.iv_type_two,adapter.getData(position).getDrawableId());
    }
}
