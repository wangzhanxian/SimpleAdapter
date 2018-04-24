package com.wzx.simpleadapter.adpter;

import android.support.annotation.LayoutRes;

/**
 * Created by WangZhanXian on 2018/4/12.
 */

public interface Cell {

   public @LayoutRes
   int layoutId();

   public void bindView(ViewHolder holder, int position);
}
