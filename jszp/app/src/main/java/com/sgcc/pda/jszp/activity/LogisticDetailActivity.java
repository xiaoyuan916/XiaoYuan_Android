package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.liuwan.customdatepicker.widget.CustomDatePicker;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.DeliveryConfirmDetailItemAdapter;
import com.sgcc.pda.jszp.adapter.LogisticSendDetailAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.AutoDocsItem;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesDetsItem;
import com.sgcc.pda.jszp.bean.LogisticsDistAutoesItem;
import com.sgcc.pda.jszp.bean.LogisticsSendDetailResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.jszp.util.StrUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流派车详情
 */
public class LogisticDetailActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_Detail_WHAT = 1001;
    public static final int Confirm_WHAT = 1002;


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_chexing)
    TextView tvChexing;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_driver_name)
    EditText etDriverName;
    @BindView(R.id.tv_driver_phone)
    EditText etDriverPhone;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.iv_dp)
    ImageView ivDp;
    @BindView(R.id.dotted_line)
    View dottedLine;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    BaseItemAdapter baseItemAdapter;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    CustomDatePicker customDatePicker;

    private LogisticsDistAutoesItem logisticsDistAutoesItem;//派车明细实体

    private String distAutoId, taskNo;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_Detail_WHAT:
                    //详情数据
                    LogisticsSendDetailResultEntity logisticsSendDetailResultEntity = (LogisticsSendDetailResultEntity) msg.obj;
                    logisticsDistAutoesItem = logisticsSendDetailResultEntity.getLogisticsDistAuto();
                    //初始化数据
                    initUiData();
                    break;
                case Confirm_WHAT:
                    //派车
                    ToastUtils.showToast(LogisticDetailActivity.this, "派车成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_logistic_detail;
    }

    @Override
    public void initView() {
        distAutoId = getIntent().getStringExtra("distAutoId");
        taskNo = getIntent().getStringExtra("taskNo");

        tvTitle.setText("派车");
    }

    @Override
    public void initData() {
        getDetailData();
    }

    @Override
    public void initListener() {

    }

    //初始化页面数据
    private void initUiData() {
        tvCarNum.setText(logisticsDistAutoesItem.getAutoBrandNo());
        tvChexing.setText(logisticsDistAutoesItem.getLoaded() + "T");
        etDriverName.setText(logisticsDistAutoesItem.getStaffName());
        etDriverPhone.setText(logisticsDistAutoesItem.getPhoneNo());
        if(!TextUtils.isEmpty(logisticsDistAutoesItem.getPlanPlaceTime())) {
            tvTime.setText(StrUtil.longToString(Long.parseLong(logisticsDistAutoesItem.getPlanPlaceTime())));
        }
        if (!TextUtils.isEmpty(logisticsDistAutoesItem.getStatus()) && logisticsDistAutoesItem.getStatus().equals(JzspConstants.Car_Status_Sended)) {
            //已派车
            etDriverName.setFocusable(false);
            etDriverName.setEnabled(false);
            etDriverPhone.setEnabled(false);
            etDriverPhone.setFocusable(false);
            ivDp.setBackgroundResource(R.mipmap.jiedian_yuanxin);
            if (logisticsDistAutoesItem.getLogisticsDistAutoDets() != null && logisticsDistAutoesItem.getLogisticsDistAutoDets().size() != 0 && logisticsDistAutoesItem.getLogisticsDistAutoDets().get(0).isArrived()) {
                dottedLine.setBackground(getResources().getDrawable(R.drawable.shape_yoko_xuxian_green));
            } else {
                dottedLine.setBackground(getResources().getDrawable(R.drawable.shape_yoko_xuxian));
            }

        } else {
            //未派车
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText("提交");
            ivDp.setBackgroundResource(R.mipmap.jiedian_yuanxinhui);
            dottedLine.setBackground(getResources().getDrawable(R.drawable.shape_yoko_xuxian));
            initDatePicker();
        }
        tvAddress.setText(logisticsDistAutoesItem.getWhName());
        if(logisticsDistAutoesItem.getLogisticsDistAutoDets()!=null) {
            //初始化物流列表
            rvOrders.setLayoutManager(new LinearLayoutManager(this));
            baseItemAdapter = new BaseItemAdapter();
            LogisticSendDetailAdapter<LogisticsDistAutoesDetsItem> logisticSendDetailAdapter = new LogisticSendDetailAdapter<>(this, logisticsDistAutoesItem.getLogisticsDistAutoDets());
            logisticSendDetailAdapter.setCountNotifiCallBack(new DeliveryConfirmDetailItemAdapter.CountNotifiCallBack() {
                @Override
                public int getcount() {
                    return logisticsDistAutoesItem.getLogisticsDistAutoDets().size();
                }
            });
            baseItemAdapter.register(LogisticsDistAutoesDetsItem.class, logisticSendDetailAdapter);
            rvOrders.setAdapter(baseItemAdapter);
            baseItemAdapter.setDataItems(logisticsDistAutoesItem.getLogisticsDistAutoDets());
        }
    }

    //获取详情数据
    private void getDetailData() {

        Map<String, String> map = new HashMap<>();
        map.put("distAutoId", distAutoId);
        map.put("taskNo", taskNo);

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_GET_LOGISTIC_DETAIL,
                this, map,
                mHandler, GET_Detail_WHAT, LogisticsSendDetailResultEntity.class);
    }

    //派车
    private void confirm() {
        if (TextUtils.isEmpty(tvCarNum.getText().toString())) {
            ToastUtils.showToast(this, "请选择车牌号");
            return;
        }

        if (TextUtils.isEmpty(etDriverName.getText().toString())) {
            ToastUtils.showToast(this, "请输入司机姓名");
            return;
        }

        if (TextUtils.isEmpty(etDriverPhone.getText().toString())) {
            ToastUtils.showToast(this, "请输入司机电话");
            return;
        }
        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtils.showToast(this, "请选择到位时间");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("distAutoId", distAutoId);
        map.put("autoBrandNo", tvCarNum.getText().toString());
        map.put("staffName", etDriverName.getText().toString());
        map.put("phoneNo", etDriverPhone.getText().toString());
        map.put("planPlaceTime", tvTime.getText().toString());

        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_ASSIGN_CAR,
                this, map,
                mHandler, Confirm_WHAT, BaseEntity.class);
    }


    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        //派车
        confirm();
    }

    @OnClick({R.id.rl_car_num, R.id.rl_time})
    public void onViewClicked(View view) {
        if (!TextUtils.isEmpty(logisticsDistAutoesItem.getStatus()) && logisticsDistAutoesItem.getStatus().equals(JzspConstants.Car_Status_Sended)) {
            //已派车
            return;
        }
        switch (view.getId()) {
            case R.id.rl_car_num:
                Intent intent = new Intent(this, LogisticSendChooseDriveOptionActivity.class);
                intent.putExtra("whNo", logisticsDistAutoesItem.getWhNo());
                intent.putExtra("autoType", logisticsDistAutoesItem.getAutoType());
                startActivityForResult(intent, 100);
                break;
            case R.id.rl_time:
                //选择时间
                customDatePicker.show(tvTime.getText().toString());
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    //选择车辆
                    AutoDocsItem autoDocsItem = (AutoDocsItem) data.getSerializableExtra("driver");
                    tvCarNum.setText(autoDocsItem.getAutoBrandNo());
                    etDriverPhone.setText(autoDocsItem.getPhoneNo());
                    etDriverName.setText(autoDocsItem.getStaffName());
                    break;
                case 101:
                    break;
                case 102:
                    break;
            }
        }
    }

    private void initDatePicker() {
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvTime.setText(time);
            }
        }, "2018-01-01", "2020-12-30"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true);
        customDatePicker.setIsLoop(true);
        String now = sdf.format(new Date());
        tvTime.setText(now);
    }
}
