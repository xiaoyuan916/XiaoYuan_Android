package com.sgcc.pda.jszp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.DeliveryActivity;
import com.sgcc.pda.jszp.activity.DeliveryInConfirmActivity;
import com.sgcc.pda.jszp.adapter.DeliveryConfirmDetailItemAdapter;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesDetsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 装车确认列表--》装车确认详情  配送单信息列表
 */
public class DeliveryConfirmDetailFragment extends Fragment {
    /**
     * 请求码
     */
    private static final int NUMBER_CHECK_REQUEST_CODE = 1001;
    /**
     * 控件
     */
    Unbinder unbinder;
    @BindView(R.id.rv_orders)
    RecyclerView rvmList;
    /**
     * 列表
     */
    BaseItemAdapter orderAdapter;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 数据list
     */
    List<LogisticsDistAutoesDetsItem> mList;
    /**
     * 点击的条目
     */
    private int checkposition;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListener();
        initData();
    }

    private void initData() {

    }

    public void initView() {
        rvmList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvmList.setNestedScrollingEnabled(false);
        mList = ((DeliveryActivity)getActivity()).getLogisticsDistAutoesItem().getLogisticsDistAuto().getLogisticsDistAutoDets();
        orderAdapter = new BaseItemAdapter();
        DeliveryConfirmDetailItemAdapter<LogisticsDistAutoesDetsItem> orderItemAdapter=new DeliveryConfirmDetailItemAdapter<LogisticsDistAutoesDetsItem>(getContext());
        orderItemAdapter.setCountNotifiCallBack(new DeliveryConfirmDetailItemAdapter.CountNotifiCallBack() {
            @Override
            public int getcount() {
                return mList.size();
            }
        });
        orderAdapter.register(LogisticsDistAutoesDetsItem.class, orderItemAdapter);
        rvmList.setAdapter(orderAdapter);
        orderAdapter.setDataItems(mList);

    }

    /**
     * 条目点击事件
     */
    private void initListener() {
        orderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                checkposition = baseViewHolder.getItemPosition();
                LogisticsDistAutoesDetsItem itemData = (LogisticsDistAutoesDetsItem) baseViewHolder.getItemData();
                Intent intent = new Intent(getContext(), DeliveryInConfirmActivity.class);
                intent.putExtra("LogisticsDistAutoesDetsItem",itemData);
                startActivityForResult(intent,NUMBER_CHECK_REQUEST_CODE);
            }
        });
    }

    /**
     * 回调更新当前界面的选中的情况
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==NUMBER_CHECK_REQUEST_CODE&&resultCode== Activity.RESULT_OK){
            String realcount = data.getStringExtra("realcount");
            mList.get(checkposition ).setRealcount(realcount);
            mList.get(checkposition ).setSelected(true);
            mList.get(checkposition ).setConfirmed(true);
            orderAdapter.notifyItemChanged(checkposition);
        }
    }

    /**
     * 核验配送单信息
     * @return
     */
    public boolean checkInfo() {
        boolean flag =false;
        for (LogisticsDistAutoesDetsItem item:mList){
            flag= item.isSelected();
        }
        return flag;
    }
}
