package com.wzx.app.smartadapter;

/** 加载更多基类，可以继承该类自由定制布局和加载状态
 * @param <T>
 */
public abstract class BaseLoadMoreCell<T> implements ICell<T> {


    /**
     * 当前状态
     */
    protected int curStatus;


    /**
     * @param initStatus 初始状态
     */
    public BaseLoadMoreCell(int initStatus){
        curStatus = initStatus;
    }


    protected void setLoadStatus(int status){
        curStatus = status;
    }

    /**
     * 开始加载更多，请设置正在加载数据时的状态
     */
    protected abstract void loadStart();


    /** 加载更多结束后的状态，
     * @param status
     */
    protected void loadFinish(int status){
        setLoadStatus(status);
    }

    @Override
    public boolean handle(int pos, SmartAdapter<T> adapter) {
        return pos == adapter.getItemCount()-1;
    }

}
