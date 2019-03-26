package com.wzx.simpleadapter;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wzx.app.smartadapter.BaseLoadMoreCell;
import com.wzx.app.smartadapter.DefaultLoadMoreImpl;
import com.wzx.app.smartadapter.ILoadMoreListener;
import com.wzx.app.smartadapter.SmartAdapter;
import com.wzx.app.smartadapter.ViewHolder;
import com.wzx.simpleadapter.cells.TypeOneCell;
import com.wzx.simpleadapter.cells.TypeTwoCell;
import com.wzx.simpleadapter.model.TypeModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private SmartAdapter<TypeModel> mSmartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        rv_list.setAdapter(mSmartAdapter = new SmartAdapter<TypeModel>(this,getList())
                .registCell(new TypeOneCell())
                .registCell(new TypeTwoCell())
                .registLoadMoreCell(3,false, new DefaultLoadMoreImpl(DefaultLoadMoreImpl.STATUS_DEFAULT, new ILoadMoreListener() {

                    @Override
                    public void loadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mSmartAdapter.loadMoreEnd(getList());
                            }
                        }, 2000);
                    }
                }))
                .setOnItemClickListener(new SmartAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(View view, ViewHolder holder, int position) {
                        if (!(holder.getCell() instanceof BaseLoadMoreCell)){

                        }
                    }
                })
                .setOnItemLongClickListener(new SmartAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onLongClick(View view, ViewHolder holder, int position) {
                        return false;
                    }
                }));
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setHasFixedSize(true);
    }

    public List<TypeModel> getList() {
        List<TypeModel> datas = new ArrayList<>();
        for (int i = 0; i <40; i++) {
            if (i % 4 == 0) {
                datas.add(new TypeModel("aa " + i));
            } else {
                datas.add(new TypeModel(R.drawable.loading));
            }
        }
        return datas;
    }
}
