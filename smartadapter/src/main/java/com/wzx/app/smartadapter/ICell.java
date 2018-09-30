package com.wzx.app.smartadapter;


/**
 * Created by WangZhanXian on 2018/4/12.
 */

public interface ICell<T> {

  boolean handle(int pos, SmartAdapter<T> adapter);

  void onBindViewHolder(int position, ViewHolder holder, SmartAdapter<T> adapter);
}
