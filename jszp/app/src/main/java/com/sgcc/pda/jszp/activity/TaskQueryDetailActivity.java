package com.sgcc.pda.jszp.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.TaskQueryDetailResultEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * author:赵锦
 * date:2018/9/4 0004 15:12
 * <p>
 * 出库查询 详情
 */
public class TaskQueryDetailActivity extends BaseActivity {
    /**
     * 请求码
     */
    public static final int GET_DETAIL_WHAT = 1001;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_task_no)
    TextView tvTaskNo;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_platform)
    TextView tvPlatform;
    @BindView(R.id.tv_pici)
    TextView tvPici;
    @BindView(R.id.tv_receive_dp)
    TextView tvReceiveDp;
    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;
    @BindView(R.id.tv_drive_no)
    TextView tvDriveNo;
    @BindView(R.id.tv_logistic_company)
    TextView tvLogisticCompany;
    @BindView(R.id.iv_device)
    ImageView ivDevice;

    private IoTaskDets ioTaskDets;

    private int type;
    private String taskId;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DETAIL_WHAT:
                    //列表数据
                    TaskQueryDetailResultEntity taskQueryResultEntity = (TaskQueryDetailResultEntity) msg.obj;
                    ioTaskDets = taskQueryResultEntity.getIoTaskDet();
                    setDetailData();
                    break;
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_task_detail;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type",0);
        taskId = getIntent().getStringExtra("taskId");
        if(type==1) {
            tvTitle.setText("出库详情");
        }else {
            tvTitle.setText("入库详情");
        }
    }

    @Override
    public void initData() {
      getDetailData();
    }

    @Override
    public void initListener() {

    }

    //设置详情数据
    private void setDetailData() {
        tvDesc.setText(ioTaskDets.getEquipDesc());
        tvDeviceName.setText(ioTaskDets.getEquipCategLabel());
        tvDriveNo.setText(ioTaskDets.getAutoDocs().getAutoBrandNo());
        tvDriverName.setText(ioTaskDets.getAutoDocs().getStaffName());
        tvLogisticCompany.setText(ioTaskDets.getAutoDocs().getCompanyName());
        tvNum.setText(ioTaskDets.getFinishQty()+"/"+ioTaskDets.getQty());
        tvPici.setText(ioTaskDets.getArriveBatchNo());
        tvPlatform.setText(ioTaskDets.getPlatformName());
        tvReceiveDp.setText(ioTaskDets.getDpName());
        tvTaskNo.setText("出库任务："+ioTaskDets.getTaskNo());
        if(ioTaskDets.getEquipCateg()!=null) {
            switch (ioTaskDets.getEquipCateg()) {
                default:
                case JzspConstants.Device_Category_DianNengBiao:
                    //电能表
                    ivDevice.setBackgroundResource(R.drawable.dianneng);
                    break;
                case JzspConstants.Device_Category_DianYaHuGanQi:
                case JzspConstants.Device_Category_DianLiuHuGanQi:
                case JzspConstants.Device_Category_ZuHeHuGanQi:
                    //互感器
                    ivDevice.setBackgroundResource(R.drawable.huganqi);
                    break;
                case JzspConstants.Device_Category_CaiJiZhongDuan:
                    //采集器
                    ivDevice.setBackgroundResource(R.drawable.caijiqi);
                    break;
                case JzspConstants.Device_Category_TongXunMoKuai:
                    //通讯模块
                    ivDevice.setBackgroundResource(R.drawable.tongxun);
                    break;

            }
        }
    }

    /**
     * 获取出庫列表数据
     */
    private void getDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", taskId);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_IOTASK_GET_OUT_IN_PLANDET,
                this, map,
                mHandler, GET_DETAIL_WHAT, TaskQueryDetailResultEntity.class);
    }
}
