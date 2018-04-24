package com.wzx.simpleadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.wzx.simpleadapter.adpter.Cell;
import com.wzx.simpleadapter.adpter.SmartAdapter;
import com.wzx.simpleadapter.model.TypeOneModel;
import com.wzx.simpleadapter.model.TypeTwoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        List<Cell> datas=initData();
        rv_list.setAdapter(new SmartAdapter(this,datas));
    }

    private void initView() {
        rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setHasFixedSize(true);
    }

    private List<Cell> initData() {
        List<Cell> datas = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            if (i % 5 == 0) {
                datas.add(new TypeTwoModel());
                continue;
            }
            datas.add(new TypeOneModel("我是类型一:" + i));
        }
        return datas;
    }
}
