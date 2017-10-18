package zhanghegang.com.bawei.date0928jingdong.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by asus on 2017/10/17.
 */

public class OnRcvScrollListener extends OnScrollListener implements OnBottomListener{
    protected LayoutManagerEnum layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;
/*    *//**
     * 是否正在加载
     *//*
    private boolean isLoadingMore = false;*/

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //  int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerEnum.linear;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerEnum.grid;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType =  LayoutManagerEnum.staggel;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case linear:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case grid:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case staggel:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    private int findMax(int[] lastPositions) {
        return 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                (lastVisibleItemPosition) >= totalItemCount - 1)) {
            //Log.d(TAG, "is loading more");
            onBottom();
        }
    }

    @Override
    public void onBottom() {

    }
}
