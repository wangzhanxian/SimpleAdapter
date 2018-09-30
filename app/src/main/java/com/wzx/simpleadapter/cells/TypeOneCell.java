package com.wzx.simpleadapter.cells;

import com.wzx.app.smartadapter.ICell;
import com.wzx.app.smartadapter.LayoutId;
import com.wzx.app.smartadapter.SmartAdapter;
import com.wzx.app.smartadapter.ViewHolder;
import com.wzx.simpleadapter.R;
import com.wzx.simpleadapter.model.TypeModel;

@LayoutId(R.layout.item_type_one)
public class TypeOneCell implements ICell<TypeModel> {


    @Override
    public boolean handle(int pos, SmartAdapter<TypeModel> adapter) {
        TypeModel data = adapter.getData(pos);
        return data!= null && data.getName() != null;
    }

    @Override
    public void onBindViewHolder(int position, ViewHolder holder, SmartAdapter<TypeModel> adapter) {
        holder.setText(R.id.tv_type_one,adapter.getData(position).getName());
    }
}
