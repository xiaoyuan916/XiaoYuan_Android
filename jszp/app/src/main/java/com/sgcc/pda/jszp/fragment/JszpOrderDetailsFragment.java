package com.sgcc.pda.jszp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.JszpOrderDetailsAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.bean.JszpQueryDistAppsItemEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class JszpOrderDetailsFragment extends Fragment {

    /**
     * 控件绑定
     */
    private Unbinder unbinder;

    /**
     * 控件
     */
    @BindView(R.id.rv_order_distribution)
    RecyclerView rv_order_distribution;
    @BindView(R.id.rv_order_return)
    RecyclerView rv_order_return;
    /**
     * 刷新的adapter
     */
    private JszpOrderDetailsAdapter mDistributionAdpter;
    private JszpOrderDetailsAdapter mReturnAdpter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jszp_order_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        rv_order_distribution.setNestedScrollingEnabled(false);
        rv_order_distribution.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_order_distribution.addItemDecoration(new SpaceItemDecoration(5));
        mDistributionAdpter = new JszpOrderDetailsAdapter();
        rv_order_distribution.setAdapter(mDistributionAdpter);

        rv_order_return.setNestedScrollingEnabled(false);
        rv_order_return.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_order_return.addItemDecoration(new SpaceItemDecoration(5));
        mReturnAdpter = new JszpOrderDetailsAdapter();
        rv_order_return.setAdapter(mReturnAdpter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 更新UI
     *
     * @param distApp
     */
    public void updataUI(JszpQueryDistAppsItemEntity distApp) {
        mDistributionAdpter.updataListUI(distApp.getDistDistAppDets());
        mReturnAdpter.updataListUI(distApp.getRetuenDistAppDets());
    }
}


