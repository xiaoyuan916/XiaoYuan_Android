package com.sgcc.pda.jszp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.TaskQueryDetailActivity;
import com.sgcc.pda.jszp.adapter.TaskQueryAdapter;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.TaskQueryResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:赵锦
 * date:2018/9/4 0004 10:15
 */
public class TaskOutQueryFragment extends Fragment {
    /**
     * 请求码
     */
    public static final int GET_LIST_WHAT=1001;

    Unbinder unbinder;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    List<IoTaskDets> mList;

    private int pageNo = JzspConstants.PageStart;
    private Activity mActivity;
    private int index,type;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_LIST_WHAT:
                    //列表数据
                        TaskQueryResultEntity taskQueryResultEntity = (TaskQueryResultEntity) msg.obj;
                        if(pageNo == JzspConstants.PageStart){
                            mList.clear();
                        }
                        mList.addAll(taskQueryResultEntity.getIoTaskDets());
                        baseItemAdapter.notifyDataSetChanged();
                        if(taskQueryResultEntity.getIoTaskDets().size()< JzspConstants.PageSize){
                            refreshLayout.setEnableLoadmore(false);
                        }
                    break;
            }
        }
    };

    public static  TaskOutQueryFragment newInstance(int index,int type )
    {
        TaskOutQueryFragment fragment = new TaskOutQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        index=getArguments().getInt("index");
        type = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refresh_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        initListener();
    }

    public void initView() {

        mList = new ArrayList<>();
        initListUiData();
    }

    private void initData() {
        refreshListData();
    }

    private void initListener() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshListData();
                refreshlayout.finishRefresh(500);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNo++;
                getListData();
                refreshlayout.finishLoadmore(500);
            }
        });
    }

    //初始化列表数据
    private void initListUiData(){
        rv.setLayoutManager(new LinearLayoutManager(mActivity));
        baseItemAdapter = new BaseItemAdapter();
        baseItemAdapter.register(IoTaskDets.class, new TaskQueryAdapter<IoTaskDets>(mActivity));

        rv.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(mActivity, TaskQueryDetailActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("taskId",mList.get(baseViewHolder.getItemPosition()).getTaskId()+"");
                startActivity(intent);
            }
        });

    }


    private void refreshListData(){
        refreshLayout.setEnableLoadmore(true);
        pageNo =JzspConstants.PageStart;
        getListData();
    }
    /**
     * 获取出庫列表数据
     */
    private void getListData() {
        Map<String,String> map = new HashMap<>();
        map.put("ioFlag",type+"");
        map.put("status",index+"");
        map.put("pageNo",pageNo+"");
        map.put("pageSize",JzspConstants.PageSize+"");
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_IOTASK_QUERY_OUT_IN_PLANDETS,
                mActivity,map,
                mHandler,GET_LIST_WHAT,TaskQueryResultEntity.class);
    }
}
