package com.sgcc.pda.jszp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.TaskQueryDetailActivity;
import com.sgcc.pda.jszp.adapter.TaskQueryAdapter;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:赵锦
 * date:2018/9/4 0004 10:15
 */
public class TaskOutQueryFragment extends Fragment {
    Unbinder unbinder;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    BaseItemAdapter baseItemAdapter;
    List<LogisticsDistAutoesItem> mList;

    private Activity mActivity;
    private int index;
    public static  TaskOutQueryFragment newInstance(int index )
    {
        TaskOutQueryFragment fragment = new TaskOutQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        fragment.setArguments(bundle);
        return fragment ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        index=getArguments().getInt("index");
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
        mList.add(new LogisticsDistAutoesItem());
        mList.add(new LogisticsDistAutoesItem());
        mList.add(new LogisticsDistAutoesItem());
        initListUiData();
    }

    private void initData() {
    }

    private void initListener() {

    }

    //初始化列表数据
    private void initListUiData(){
        rv.setLayoutManager(new LinearLayoutManager(mActivity));
        baseItemAdapter = new BaseItemAdapter();
        baseItemAdapter.register(LogisticsDistAutoesItem.class, new TaskQueryAdapter<LogisticsDistAutoesItem>(mActivity));

        rv.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(mList);
        baseItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                startActivity(new Intent(mActivity, TaskQueryDetailActivity.class));
            }
        });

    }
}
