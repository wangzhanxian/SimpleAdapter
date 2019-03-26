package com.wzx.app.smartadapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SmartAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private List<T> mDatas;
    private Context mContext;

    private SparseArray<ICell> cells;

    private int specialCellCount;

    private OnItemClickListener mClickListener;

    private OnItemLongClickListener mLongClickListener;

    private RecyclerView mRecyclerView;

    private BaseLoadMoreCell mLoadMoreCell;

    private boolean loadingMore;

    private int preLoadCount = 1;

    private boolean isAllowLoadIfNotFullPage;
    private boolean isLoadingMoreTouch;

    public SmartAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public SmartAdapter(Context context, @NonNull List<T> datas) {
        mContext = context;
        mDatas = datas;
        cells = new SparseArray<>();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getData(int pos) {
        if (pos>= 0 && mDatas.size() > pos) {
            return mDatas.get(pos);
        }
        return null;
    }

    public void setNewData(@Nullable List<T> data) {
        this.mDatas = data == null ? new ArrayList<T>() : data;
        loadMoreEnd(DefaultLoadMoreImpl.STATUS_DEFAULT);
        notifyDataSetChanged();
    }

    public SmartAdapter<T> addData(@IntRange(from = 0) int position, @NonNull Collection<? extends T> datas) {
        mDatas.addAll(position, datas);
        notifyItemRangeInserted(position,datas.size());
        return this;
    }
    public SmartAdapter<T> addData(@NonNull Collection<? extends T> datas) {
        return addData(mDatas.size(),datas);
    }

    public SmartAdapter<T> removeData(@IntRange(from = 0) int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        return this;
    }
    public SmartAdapter<T> removeData(@NonNull T data) {
        int position = mDatas.indexOf(data);
        if (position>=0) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
        return this;
    }

    public SmartAdapter<T> updateData(@IntRange(from = 0) int position, @NonNull T data) {
        mDatas.set(position, data);
        notifyItemChanged(position);
        return this;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        if (mLoadMoreCell != null && !isAllowLoadIfNotFullPage) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    isLoadingMoreTouch = dy > 0;
                }
            });
        }
    }

    public ViewHolder getHolderByPos(int pos) {
        if (mRecyclerView != null) {
            return (ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(pos);
        }
        return null;
    }


    public SmartAdapter<T> setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public OnItemClickListener getItemClickListener() {
        return mClickListener;
    }


    public SmartAdapter<T> setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
        return this;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return mLongClickListener;
    }


    public SmartAdapter<T> registCell(@NonNull ICell cell) {
        cells.put(cell.hashCode(), cell);
        return this;
    }

    public SmartAdapter<T> registSpecialCell(@NonNull ICell cell) {
        registCell(cell);
        specialCellCount++;
        return this;
    }

    public SmartAdapter<T> registLoadMoreCell(boolean allowLoadIfNotFullPage, BaseLoadMoreCell loadMoreCell) {
        return registLoadMoreCell(1,allowLoadIfNotFullPage, loadMoreCell);
    }


    /**
     * @param preLoadCount           提前多少个加载更多
     * @param loadMoreCell
     * @param allowLoadIfNotFullPage 如果不满一屏幕是否允许自动加载更多
     * @return
     */
    public SmartAdapter<T> registLoadMoreCell( int preLoadCount,boolean allowLoadIfNotFullPage, BaseLoadMoreCell loadMoreCell) {
        this.preLoadCount = preLoadCount;
        mLoadMoreCell = loadMoreCell;
        isAllowLoadIfNotFullPage = allowLoadIfNotFullPage;
        registSpecialCell(loadMoreCell);
        return this;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ICell cell = cells.get(viewType);
        return ViewHolder.getHolder(mContext, cell.getLayoutId(), parent, this).setCell(cell);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        checkLoadMore(position);
        ICell cell = holder.getCell();
        if (cell != null) {
            cell.onBindViewHolder(position, holder, this);
        }
    }


    @Override
    public int getItemCount() {
        return mDatas.size() + specialCellCount;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdByPos(position);
    }

    public @LayoutRes
    int getLayoutIdByPos(int pos) {
        for (int i = 0; i < cells.size(); i++) {
            int key = cells.keyAt(i);
            if (cells.get(key).isCurType(pos, this)) {
                return key;
            }
        }
        return 0;
    }


    private void checkLoadMore(int position) {
        if (mLoadMoreCell != null && !loadingMore && position >= getItemCount() - preLoadCount) {
            //不满足一屏幕是否允许自动加载
            if (isAllowLoadIfNotFullPage || isLoadingMoreTouch) {
                loadingMore = true;
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadMoreCell.loadMoreStart();
                        notifyItemChanged(getItemCount() - 1);
                    }
                });
            }
        }
    }

    public SmartAdapter<T> loadMoreEnd(int loadMoreStatus) {
        if (mLoadMoreCell != null) {
            loadingMore = false;
            mLoadMoreCell.loadFinish(loadMoreStatus);
            notifyItemChanged(getItemCount() - 1);
        }
        return this;
    }

    /**
     * 自动根据规则判断加载更多状态
     * datas ==null 加载失败
     * datas.size() == 0 没有更多了
     * datas.size() > 0 加载完成
     *
     * @param datas
     * @return
     */
    public SmartAdapter<T> loadMoreEnd(List<T> datas) {
        int loadMoreStatus;
        if (datas == null) {
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_FAILED;
        } else if (datas.size() == 0) {
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_NO_MORE;
        } else {
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_FINISH;
            mDatas.addAll(datas);
            notifyItemRangeInserted(mDatas.size() - datas.size(), datas.size());
        }

        return loadMoreEnd(loadMoreStatus);
    }

    public interface OnItemClickListener {

        void onClick(View view, ViewHolder holder, int position);
    }

    public interface OnItemLongClickListener {

        boolean onLongClick(View view, ViewHolder holder, int position);
    }

}
