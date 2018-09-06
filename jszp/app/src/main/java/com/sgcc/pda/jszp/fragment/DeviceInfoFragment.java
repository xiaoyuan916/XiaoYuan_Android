package com.sgcc.pda.jszp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.DeviceQueryResultEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeviceInfoFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_tender)
    TextView tvTender;
    @BindView(R.id.tv_aog)
    TextView tvAog;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_box_num)
    TextView tvBoxNum;
    @BindView(R.id.tv_factory)
    TextView tvFactory;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    /**
     * 依赖主体的上下文
     */
    private Context context;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);
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
        initData();
        initListener();
    }

    public void initView() {
    }

    private void initData() {
    }

    private void initListener() {

    }


    /**
     * 更新数据UI
     * @param obj
     */
    public void upDataUI(DeviceQueryResultEntity obj) {
        tvState.setText(obj.getAsset().getStatusLabel());
        tvType.setText(obj.getAsset().getEquipCateg());
        tvTender.setText(obj.getAsset().getBidBachNo());
        tvAog.setText(obj.getAsset().getArriveBatchNo());
        tvNum.setText(obj.getAsset().getBarCode());
        tvBoxNum.setText(obj.getAsset().getBoxBarCode());
        tvFactory.setText(obj.getAsset().getManufacturer());
        tvCompany.setText(obj.getAsset().getOrgName());
    }
}
