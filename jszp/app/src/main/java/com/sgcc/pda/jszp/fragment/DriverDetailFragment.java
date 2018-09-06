package com.sgcc.pda.jszp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.DeliveryActivity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.util.StrUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DriverDetailFragment extends Fragment {
    /**
     * 控件UI
     */
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_car_num)
    TextView tv_car_no;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_receive_right)
    CheckBox iv_receive_right;
    @BindView(R.id.iv_count_right)
    CheckBox iv_count_right;
    @BindView(R.id.iv_guige_right)
    CheckBox iv_guige_right;
    @BindView(R.id.iv_beijian_right)
    CheckBox iv_beijian_right;

    Unbinder unbinder;
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_driver_detail, container, false);
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

    private void initListener() {

    }

    public void initView() {
        LogisticsDistAutoesItem logisticsDistAutoesItem = ((DeliveryActivity) getActivity()).getLogisticsDistAutoesItem().getLogisticsDistAuto();
        tv_car_no.setText(logisticsDistAutoesItem.getAutoBrandNo());
        tv_name.setText(logisticsDistAutoesItem.getStaffName());
        tv_phone.setText(logisticsDistAutoesItem.getPhoneNo());
        if (!TextUtils.isEmpty(logisticsDistAutoesItem.getPlanPlaceTime())) {
            tv_time.setText(StrUtil.longToString(Long.parseLong(logisticsDistAutoesItem.getPlanPlaceTime())));
        }
    }

    /**
     * 获取核查检验
     * @return 核查信息
     */
    public boolean checkInfo() {
        if (iv_receive_right==null)return false;
        boolean flag =false;
        if (iv_receive_right.isChecked()&&iv_count_right.isChecked()
                &&iv_guige_right.isChecked()&&iv_beijian_right.isChecked()){
            flag=true;
        }
        return flag;
    }
}
