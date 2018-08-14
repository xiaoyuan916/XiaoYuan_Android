package com.sgcc.pda.sdk.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * Created by xuzl on 2016/11/24.
 * 自定义listview控件，实现更多加载功能：当滚动到底部时，底部显示动画自动加载数据，当失败时，显示重试按钮，当成功时，动画消失
 * 配合下拉加载swiperefreshlayout控件使用
 */
public class MyListView extends ListView {
    //载入更多动画
    private ProgressBar loadingProgressBar;
    private TextView tvRetry;
    //是否正在载入
    private boolean isLoadingMore = false;
    //滚动到底部载入更多回调的开关
    private boolean canLoadingMore = true;
    //载入更多接口定义
    public interface onLoadMoreListener {
        public void loadingMore();
    }
    //载入更多接口
    private onLoadMoreListener listener;

    //------------------------构造方法，指定滚动监听器--------------------------------------
    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    //初始化
    private void init(Context context) {
        //更多动画，需要改变样式为小圆将来
        loadingProgressBar = new ProgressBar(context);
        //失败提示
        tvRetry = new TextView(context);
        tvRetry.setText("加载失败，请重试");
        tvRetry.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tvRetry.setPadding(0,8,0,8);
        tvRetry.setTextColor(Color.GRAY);
        tvRetry.setGravity(Gravity.CENTER);
        //设置滚动监听
        setOnScrollListener(onScrollListener);
        //设置footer分割线
        setFooterDividersEnabled(false);
    }
    //--------------------------滚动监听器---------------------------------------------
    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case OnScrollListener.SCROLL_STATE_IDLE:
//                    LogUtil.d("已经停止：SCROLL_STATE_IDLE");
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        //产生回调(如果监听设置了，并且不是正在载入中，并且打开了载入更多开关)
                        if (listener != null && !isLoadingMore && canLoadingMore) {
                            //载入更多回调
                            listener.loadingMore();
                            //显示载入更多底部view
                            showLoadingMoreView();
                        }
                    }
                    break;
                case OnScrollListener.SCROLL_STATE_FLING:
//                    LogUtil.d("开始滚动：SCROLL_STATE_FLING");
                    break;
                case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                    LogUtil.d("正在滚动：SCROLL_STATE_TOUCH_SCROLL");
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            LogUtil.d("onScroll:firstVisibleItem"+firstVisibleItem+",visibleItemCount:"+visibleItemCount+",totalItemCount");
        }
    };

    /**
     * 设置载入更多监听
     *
     * @param listener
     */
    public void setOnLoadMoreListener(onLoadMoreListener listener) {
        this.listener = listener;
    }

    /**
     * 是否打开载入更多功能
     * @param canLoadingMore
     */
    public void setCanLoadingMore(boolean canLoadingMore) {
        this.canLoadingMore = canLoadingMore;
    }

    /**
     * 显示载入更多动画
     */
    private void showLoadingMoreView() {
        LogUtil.d("更多加载中...");
        isLoadingMore = true;
//        removeFooterView(loadingProgressBar);
        removeFooterView(tvRetry);
        addFooterView(loadingProgressBar);
        //滚动到底部
        setSelection(getBottom());
    }

    /**
     * 显示载入更多失败提示
     */
    public void showRetry() {
        LogUtil.d("加载失败，请重试");

        isLoadingMore = false;
        removeFooterView(loadingProgressBar);
//        removeFooterView(tvRetry);
        addFooterView(tvRetry);
        //滚动到底部
//        setSelection(getBottom());
    }

    /**
     * 加载成功，隐藏加载动画或失败提示
     */
    public void dissmissLoadingMoreView() {
        LogUtil.d("加载成功，隐藏加载更多动画");
        isLoadingMore = false;
        removeFooterView(loadingProgressBar);
        removeFooterView(tvRetry);
        //滚动到底部
//        setSelection(getBottom());
    }
}
