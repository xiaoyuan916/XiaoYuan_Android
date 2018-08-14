package com.sgcc.pda.jszp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.adapter.logisticsSendAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.DriverItem;
import com.sgcc.pda.jszp.bean.TaskItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LogisticsSendActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_not_send_count)
    TextView tvNotSendCount;
    @BindView(R.id.tv_sended_count)
    TextView tvSendedCount;
    @BindView(R.id.rv_cars)
    RecyclerView rvCars;

    BaseItemAdapter taskAdapter;
    List<TaskItem> data;

    Calendar ca = Calendar.getInstance();
    int mYear = ca.get(Calendar.YEAR);
    int mMonth = ca.get(Calendar.MONTH);
    int mDay = ca.get(Calendar.DAY_OF_MONTH);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");


    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistics_send;
    }

    @Override
    public void initView() {
        tvTitle.setText("物流派车");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.search_hui_small);

        rvCars.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new BaseItemAdapter();
        data = new ArrayList<>();
        data.add(new TaskItem("", "南京", 0, new DriverItem("张三", "", "", "2018-08-03")));
        data.add(new TaskItem("", "南京", 1, new DriverItem("张三", "", "", "2018-08-03")));
        data.add(new TaskItem("", "南京", 1, new DriverItem("张三", "", "", "2018-08-03")));
        taskAdapter.register(TaskItem.class, new logisticsSendAdapter<TaskItem>());
        rvCars.addItemDecoration(new SpaceItemDecoration(7));

        rvCars.setAdapter(taskAdapter);
        taskAdapter.setDataItems(data);
        taskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(LogisticsSendActivity.this, LogisticDetailActivity.class);
                startActivityForResult(intent,101);
            }
        });

    }

    @Override
    public void initData() {
    }

    @Override
    public void initListener() {

    }


    @Override
    public void onIvRightClick(View v) {
        super.onIvRightClick(v);
        startActivityForResult(new Intent(this, LogisticScanActivity.class), 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        switch (requestCode) {
            case 100:
                break;
            case 101:
                break;
            case 102:
                break;
        }
    }


    @OnClick({R.id.tv_date, R.id.tv_previous, R.id.tv_next})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                new DatePickerDialog(this, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.tv_previous:

                try {
                    Date date = sdf.parse(tvDate.getText().toString());
                    ca.setTime(date);
                    int day = ca.get(Calendar.DATE);
                    ca.set(Calendar.DATE, day - 1);
                    mYear = ca.get(Calendar.YEAR);
                    mMonth = ca.get(Calendar.MONTH);
                    mDay = ca.get(Calendar.DAY_OF_MONTH);
                    String dayAfter = sdf.format(ca.getTime());
                    tvDate.setText(dayAfter);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_next:
                try {
                    Date date = sdf.parse(tvDate.getText().toString());
                    ca.setTime(date);
                    int day = ca.get(Calendar.DATE);
                    ca.set(Calendar.DATE, day + 1);
                    mYear = ca.get(Calendar.YEAR);
                    mMonth = ca.get(Calendar.MONTH);
                    mDay = ca.get(Calendar.DAY_OF_MONTH);
                    String dayAfter = sdf.format(ca.getTime());
                    tvDate.setText(dayAfter);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").append("0").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("年").
                            append(mMonth + 1).append("月").append(mDay).append("日").toString();
                }

            }
            tvDate.setText(days);


        }
    };
}
