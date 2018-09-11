package com.sgcc.pda.jszp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.contrarywind.interfaces.IPickerViewData;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.JszpAppStatusBean;
import com.sgcc.pda.jszp.bean.JszpOrgListEntity;
import com.sgcc.pda.jszp.bean.JszpQueryOrderRequestEntity;
import com.sgcc.pda.jszp.bean.JszpSgAppTypeBean;
import com.sgcc.pda.jszp.util.JszpSelectPickViewUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单查询
 * 1，处理订单筛选的逻辑
 */
public class QueryOrderActivity extends BaseActivity {
    /**
     * 响应码
     */
    private static final int SELECT_DP_OPTION_REQUEST = 10;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_order_num)
    EditText etOrderNum;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    /**
     * 日期选择
     */
    Calendar ca = Calendar.getInstance();
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rl_order_type)
    RelativeLayout rlOrderType;
    @BindView(R.id.rl_company)
    RelativeLayout rlCompany;
    @BindView(R.id.rl_order_state)
    RelativeLayout rlOrderState;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.bt_query)
    Button btQuery;
    private int mYear1 = ca.get(Calendar.YEAR);
    private int mMonth1 = ca.get(Calendar.MONTH);
    private int mDay1 = ca.get(Calendar.DAY_OF_MONTH);

    private int mYear2 = ca.get(Calendar.YEAR);
    private int mMonth2 = ca.get(Calendar.MONTH);
    private int mDay2 = ca.get(Calendar.DAY_OF_MONTH);
    /**
     * 选择后的bean
     */
    private JszpSgAppTypeBean mSgAppType;
    private JszpAppStatusBean mStatus;
    private JszpOrgListEntity orgBean;


    /**
     * 展示供应区域
     */
//    private void initShowDp() {
//        JszpSelectPickViewUtils.showOptionsPickerBuilder(QueryOrderActivity.this,
//                mOrgList, new OnOptionsSelectListener() {
//                    @Override
//                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                        orgBean = (JszpOrgListEntity) mOrgList.get(options1);
//                        tvCompany.setText(orgBean.getOrgName());
//                    }
//                });
//    }

    /**
     * 区域的列表
     */
    private List<IPickerViewData> mOrgList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_order;
    }

    @Override
    public void initView() {
        tvTitle.setText("订单查询");
        ivClose.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.rl_order_type,R.id.iv_close, R.id.rl_company, R.id.rl_order_state, R.id.rl_start_time, R.id.rl_end_time, R.id.bt_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_order_type:
                final List<IPickerViewData> list = JszpSelectPickViewUtils.initSgAppTypeData();
                JszpSelectPickViewUtils.showOptionsPickerBuilder(this, list, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mSgAppType = (JszpSgAppTypeBean) list.get(options1);
                        tvOrderType.setText(mSgAppType.getSgAppTypeName());
                    }
                });
                break;
            case R.id.rl_company:
//                if (mOrgList!=null&&mOrgList.size()>0){
//                    initShowDp();
//                    return;
//                }
//                obtainCompanyData();
                startActivityForResult(new Intent(this, JszpDpSelect1Activity.class), SELECT_DP_OPTION_REQUEST);
                break;
            case R.id.rl_order_state:
                final List<IPickerViewData> statusList = JszpSelectPickViewUtils.initStatusData();
                JszpSelectPickViewUtils.showOptionsPickerBuilder(this, statusList, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mStatus = (JszpAppStatusBean) statusList.get(options1);
                        tvOrderState.setText(mStatus.getAppStatusName());
                    }
                });
                break;
            case R.id.rl_start_time:
                new DatePickerDialog(this, onDateSetListener1, mYear1, mMonth1, mDay1).show();
                break;
            case R.id.rl_end_time:
                new DatePickerDialog(this, onDateSetListener2, mYear2, mMonth2, mDay2).show();
                break;
            case R.id.bt_query:
                Intent intent1 = new Intent(this, QueryOrderListActivity.class);
                JszpQueryOrderRequestEntity requestEntity = getJszpQueryOrderRequestEntity();
                intent1.putExtra("JszpQueryOrderRequestEntity", requestEntity);
                startActivity(intent1);
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_DP_OPTION_REQUEST && resultCode == RESULT_OK) {
            JszpOrgListEntity orgEntity = (JszpOrgListEntity) data.getSerializableExtra("jszpOrgListEntity");
            if (orgEntity != null) {
                orgBean = orgEntity;
                tvCompany.setText(orgBean.getOrgName());
            }
        }
    }

    /**
     * 得到查询条件
     */
    @NonNull
    private JszpQueryOrderRequestEntity getJszpQueryOrderRequestEntity() {
        JszpQueryOrderRequestEntity requestEntity = new JszpQueryOrderRequestEntity();
        if (!TextUtils.isEmpty(etOrderNum.getText().toString())) {
            requestEntity.setAppNo(etOrderNum.getText().toString());
        }
        if (mSgAppType != null) {
            requestEntity.setSgAppType(mSgAppType.getSgAppTypeId());
        }
        if (mStatus != null) {
            requestEntity.setAppStatus(mStatus.getAppStatusId());
        }
        if (orgBean != null) {
            requestEntity.setOrgNo(orgBean.getOrgNo());
        }
        if (!TextUtils.isEmpty(tvStartTime.getText().toString())) {
            requestEntity.setBeginAppDate(initDate(mYear1, mMonth1, mDay1));
        }
        if (!TextUtils.isEmpty(tvEndTime.getText().toString())) {
            requestEntity.setEndAppDate(initDate(mYear2, mMonth2, mDay2));
        }
        return requestEntity;
    }

    /**
     * 初始化日期的使用
     */
    private String initDate(int year, int month, int day) {
        String stringBuffer;
        if (month + 1 < 10) {
            if (day < 10) {
                stringBuffer = new StringBuffer().append(year).append("0").
                        append(month + 1).append("0").append(day).toString();
            } else {
                stringBuffer = new StringBuffer().append(year).append("0").
                        append(month + 1).append(day).toString();
            }
        } else {
            if (day < 10) {
                stringBuffer = new StringBuffer().append(year).
                        append(month + 1).append("0").append(day).toString();
            } else {
                stringBuffer = new StringBuffer().append(year).
                        append(month + 1).append(day).toString();
            }
        }
        return stringBuffer;
    }


    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear1 = year;
            mMonth1 = monthOfYear;
            mDay1 = dayOfMonth;
            tvStartTime.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
        }
    };
    private DatePickerDialog.OnDateSetListener onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear2 = year;
            mMonth2 = monthOfYear;
            mDay2 = dayOfMonth;
            tvEndTime.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
        }
    };
}
