package com.wzx.simpleadapter.cells;

import com.wzx.app.smartadapter.ICell;
import com.wzx.app.smartadapter.SmartAdapter;
import com.wzx.app.smartadapter.ViewHolder;
import com.wzx.simpleadapter.R;
import com.wzx.simpleadapter.model.TypeModel;

public class TypeOneCell implements ICell<TypeModel> {


    @Override
    public int getLayoutId() {
        return R.layout.item_type_one;
    }

    @Override
    public boolean isCurType(int pos, SmartAdapter<TypeModel> adapter) {
        TypeModel data = adapter.getData(pos);
        return data!= null && data.getName() != null;
    }

    @Override
    public void onBindViewHolder(int position, ViewHolder holder, SmartAdapter<TypeModel> adapter) {
        holder.setText(R.id.tv_type_one,adapter.getData(position).getName());
    }
}
