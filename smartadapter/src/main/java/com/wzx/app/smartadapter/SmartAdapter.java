package com.wzx.app.smartadapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.wzx.app.smartadapter.util.ComnUtil;

import java.util.ArrayList;
import java.util.List;

public class SmartAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = SmartAdapter.class.getSimpleName();
    private List<T> mDatas;
    private Context mContext;

    private SparseArray<ICell> cells;

    private int specialCellCount;

    private OnItemClickListener mClickListener;

    private int[] clickIds;

    private OnItemLongClickListener mLongClickListener;

    private int[] longClickIds;

    private RecyclerView mRecyclerView;

    private LoadMoreHelper mLoadMoreHelper;

    private boolean loadingMore;

    private int preLoadCount = 1;


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
        if (mDatas.size() > pos) {
            return mDatas.get(pos);
        }
        return null;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public ViewHolder getHolderByPos(int pos) {
        if (mRecyclerView != null) {
            return (ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(pos);
        }
        return null;
    }


    public SmartAdapter<T> setOnItemClickListener(OnItemClickListener listener, @IdRes int... ids) {
        mClickListener = listener;
        clickIds = ids;
        return this;
    }

    public OnItemClickListener getItemClickListener() {
        return mClickListener;
    }

    public int[] getClickIds() {
        return clickIds;
    }

    public SmartAdapter<T> setOnItemLongClickListener(OnItemLongClickListener listener, @IdRes int... ids) {
        mLongClickListener = listener;
        longClickIds = ids;
        return this;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return mLongClickListener;
    }

    public int[] getLongClickIds() {
        return longClickIds;
    }


    public SmartAdapter<T> registCell(@NonNull ICell cell) {
        int layoutId = ComnUtil.getLayoutId(cell);
        if (layoutId == 0) {
            layoutId = ComnUtil.getLayoutIdByName(mContext, ComnUtil.getLayoutName(cell));
            if (layoutId == 0) {
                throw new RuntimeException("the class " + cell.getClass().getName() + " missing the @LayoutId or @LayoutResName");
            }
        }
        cells.put(layoutId, cell);
        return this;
    }

    public SmartAdapter<T> registCell(@LayoutRes int layoutId,@NonNull ICell cell) {
        cells.put(layoutId, cell);
        return this;
    }
    public SmartAdapter<T> registSpecialCell(@LayoutRes int layoutId,@NonNull ICell cell) {
        registCell(layoutId,cell);
        specialCellCount++;
        return this;
    }

    public SmartAdapter<T> registSpecialCell(@NonNull ICell cell) {
        registCell(cell);
        specialCellCount++;
        return this;
    }

    public SmartAdapter<T> registLoadMoreHelper(LoadMoreHelper loadMoreHelper) {
        return registLoadMoreHelper(1, loadMoreHelper);
    }


    public SmartAdapter<T> registLoadMoreHelper(int preLoadCount, LoadMoreHelper loadMoreHelper) {
        this.preLoadCount = preLoadCount;
        mLoadMoreHelper = loadMoreHelper;
        registSpecialCell(loadMoreHelper.getLoadMoreCell());
        return this;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.getHolder(mContext, viewType, parent, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        checkLoadMore(position);
        ICell cell = getCellByPos(position);
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
            if (cells.get(key).handle(pos, this)) {
                return key;
            }
        }
        return 0;
    }

    public ICell<T> getCellByPos(int pos) {
        for (int i = 0; i < cells.size(); i++) {
            ICell<T> cell = cells.valueAt(i);
            if (cell.handle(pos, this)) {
                return cell;
            }
        }
        return null;
    }


    private void checkLoadMore(int pos) {
        if (mLoadMoreHelper != null && !loadingMore && getItemCount() - preLoadCount == pos) {
            loadingMore = true;
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mLoadMoreHelper.getLoadMoreCell().loadStart();
                    notifyItemChanged(getItemCount() - 1);
                    mLoadMoreHelper.requestLoadMore();
                }
            });
        }
    }

    public SmartAdapter<T> loadMoreEnd(int loadMoreStatus) {
        loadingMore = false;
        mLoadMoreHelper.getLoadMoreCell().loadFinish(loadMoreStatus);
        notifyDataSetChanged();
        return this;
    }

    /** 自动根据规则判断加载更多状态
     * datas ==null 加载失败
     * datas.size() == 0 没有更多了
     * datas.size() >= 0 加载完成
     * @param datas
     * @return
     */
    public SmartAdapter<T> loadMoreEnd(List<T> datas) {
        int loadMoreStatus;
        if (datas == null){
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_FAILED;
        }else if (datas.size() == 0){
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_NO_MORE;
        }else {
            loadMoreStatus = DefaultLoadMoreImpl.STATUS_FINISH;
            mDatas.addAll(datas);
        }
        return loadMoreEnd(loadMoreStatus);
    }


    public interface LoadMoreHelper {

        BaseLoadMoreCell getLoadMoreCell();

        void requestLoadMore();
    }

    public interface OnItemClickListener {

        void onClick(View view, ViewHolder holder, int position);
    }

    public interface OnItemLongClickListener {

        boolean onLongClick(View view, ViewHolder holder, int position);
    }

}
