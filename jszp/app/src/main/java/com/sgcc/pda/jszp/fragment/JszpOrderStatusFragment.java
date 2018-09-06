package com.sgcc.pda.jszp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JSZPDeliveryReceipIoPlanDetsAdapter;
import com.sgcc.pda.jszp.adapter.JszpOrderStatusAdpter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.bean.JszpOrderTrackEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class JszpOrderStatusFragment extends Fragment {
    /**
     * 控件绑定
     */
    private Unbinder unbinder;

    /**
     * 控件
     */
    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;
    /**
     * 适配器
     */
    private JszpOrderStatusAdpter mStatusAdpter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jszp_order_status, container, false);
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

    private void initView() {
        rv_orders.setNestedScrollingEnabled(false);
        rv_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_orders.addItemDecoration(new SpaceItemDecoration(15));
        mStatusAdpter = new JszpOrderStatusAdpter();
        rv_orders.setAdapter(mStatusAdpter);
    }

    private void initData() {

    }

    private void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 更新UI
     * @param tracks
     */
    public void updataUI(List<JszpOrderTrackEntity> tracks) {
        mStatusAdpter.updataListUI(tracks);
    }
}
