package com.sgcc.pda.jszp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class QueryOrderActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_order_num)
    EditText etOrderNum;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_supply)
    TextView tvSupply;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    /**
     * 日期选择
     */
    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_query_order;
    }

    @Override
    public void initView() {
        tvTitle.setText("订单查询");

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.rl_order_type, R.id.rl_company, R.id.rl_supply, R.id.rl_order_state, R.id.rl_start_time, R.id.rl_end_time, R.id.bt_query})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, PickOptionActivity.class);
        switch (view.getId()) {
            case R.id.rl_order_type:
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_company:
//                startActivityForResult(intent, 101);

                break;
            case R.id.rl_supply:
                startActivityForResult(intent, 102);
                break;
            case R.id.rl_order_state:
                startActivityForResult(intent, 103);
                break;
            case R.id.rl_start_time:
                new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.rl_end_time:
                new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.bt_query:
                Intent intent1=new Intent(this,QueryOrderListActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        }
    };
}
