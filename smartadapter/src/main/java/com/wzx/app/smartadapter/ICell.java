package com.wzx.app.smartadapter;


import android.support.annotation.LayoutRes;
/**
 * Created by WangZhanXian on 2018/4/12.
 * must @ViewType
 */

public interface ICell<T> {

  @LayoutRes int getLayoutId();

  boolean isCurType(int pos, SmartAdapter<T> adapter);

  void onBindViewHolder(int position, ViewHolder holder, SmartAdapter<T> adapter);
}
