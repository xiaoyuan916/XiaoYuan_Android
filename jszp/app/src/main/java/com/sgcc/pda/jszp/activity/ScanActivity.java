package com.sgcc.pda.jszp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.util.AppManager;
import com.sgcc.pda.jszp.util.LogUtils;

import wangfei.scan2.Scan2Activity;
import wangfei.scan2.client.android.AutoScannerView;

public class ScanActivity extends Scan2Activity implements View.OnClickListener {

//    @BindView(R.id.scanner_view)
//    ScannerView scannerView;
//    @BindView(R.id.iv_return)
    ImageView ivReturn;
    //    @BindView(R.id.bt_manual)
    TextView btmanual;
    //    @BindView(R.id.tv_content)
    TextView tvcontent;
    //    @BindView(R.id.tv_title)
    TextView tvtitle;
    //    @BindView(R.id.tv_task_num)
    TextView tvTaskNum;
    //    @BindView(R.id.tv_count)
    TextView tvCount;
    //    @BindView(R.id.tv_sure)
    TextView tvSure;
    //    @BindView(R.id.tv_checked_count)
    TextView tvCheckedCount;
    //    @BindView(R.id.ll_check_count)
    LinearLayout llCheckCount;
    private int type;
    private int sub_type = 0;//子分类
    private int position;//列表位置
    private int flag =0;


    //    @Override
//    public int getLayoutResId() {
//        return R.layout.activity_scan;
//    }
    @Override
    protected void initView() {
        //设置屏幕方向为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_scan);

        ivReturn = (ImageView) findViewById(R.id.iv_return);
        btmanual = (TextView) findViewById(R.id.bt_manual);
        tvcontent = (TextView) findViewById(R.id.tv_content);
        tvtitle = (TextView) findViewById(R.id.tv_title);
        tvTaskNum = (TextView) findViewById(R.id.tv_task_num);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvSure = (TextView) findViewById(R.id.tv_sure);
        tvCheckedCount = (TextView) findViewById(R.id.tv_checked_count);
        llCheckCount = (LinearLayout) findViewById(R.id.ll_check_count);

        initData();
        initListener();
    }

    public void initData() {
        type = getIntent().getIntExtra("type", 0);
        sub_type = getIntent().getIntExtra("sub_type", 0);
        position = getIntent().getIntExtra("position", 0);
        switch (type) {
            case MyComment.SCAN_DELIVERY:
                tvtitle.setText("配送装车");
                tvcontent.setText("请扫描配送任务二维或手动输入配送任务编号");
                break;
            case MyComment.SCAN_RETURN:
                tvtitle.setText("返回装车");
                tvcontent.setText("请扫描返回设备所属配送单条码或手动输入配送任务编号");
                break;
            case MyComment.RETURN_CHECK: {
                tvtitle.setText("返回设备");
                llCheckCount.setVisibility(View.VISIBLE);
                tvcontent.setVisibility(View.GONE);
                String tasknum = getIntent().getStringExtra("tasknum");
                tvTaskNum.setText(tasknum);
                break;
            }
            case MyComment.SCAN_SIGN_FOR:
                tvtitle.setText("配送签收");
                tvcontent.setText("请扫描配送任务二维或手动输入配送任务编号");
                break;
            case MyComment.SIGN_FOR:
                tvtitle.setText("配送签收");
                tvcontent.setText("请扫描配送任务二维或手动输入配送任务编号完成签收");
                break;
            case MyComment.SCAN_RETURN_SIGN:
                tvtitle.setText("返程签收");
                if (sub_type == 0)//0是扫任务，1是扫配送单,2是扫设备
                    tvcontent.setText("请扫描返回设备所属配送单条码或手动输入配送任务编号");
                else if (sub_type == 1)
                    tvcontent.setText("请扫描返回设备所属配送单条码或手动输入配送单编号");
                else {
                    tvcontent.setText("请扫描返回设备条码或手动输入设备编号");
                    llCheckCount.setVisibility(View.VISIBLE);
                    tvcontent.setVisibility(View.GONE);
                    String tasknum = getIntent().getStringExtra("tasknum");
                    int plancount = getIntent().getIntExtra("plancount", 0);
                    tvCount.setText("" + plancount);
                    tvTaskNum.setText(tasknum);
                }
                break;
            case MyComment.SCAN_DEVICE_OUT:
                if (sub_type == 0)//0是扫任务，1是扫设备
                {
                    tvtitle.setText("设备出库查询");
                    tvcontent.setText("请扫描接收单位任务编号或手动输入接收单位任务编号");
                } else if (sub_type == 1) {
                    tvtitle.setText("资产编号查询");
                    tvcontent.setText("请扫描或手动输入资产编号");
                }
                break;
            case MyComment.SCAN_DEVICE_IN:
                tvtitle.setText("补库入库");
                if (sub_type == 0) {
                    tvcontent.setText("请扫描配送单编号或手动输入配送单编号");
                } else {
                    tvSure.setVisibility(View.VISIBLE);
                    llCheckCount.setVisibility(View.VISIBLE);
                    tvcontent.setVisibility(View.GONE);
                }
                break;
            case MyComment.SCAN_DEVICE_PICK:
                tvtitle.setText("设备拣选");
                tvcontent.setText("请扫描或者手动输入返回设备条码进行设备拣选");
                break;
            case MyComment.QUERY_DEVICE:
                tvtitle.setText(R.string.cx1);
                tvcontent.setText("请扫描或者手动输入返回设备条码进行设备拣选");
                break;
            case MyComment.SCAN_EXPRESS_DEVICE:
                if (sub_type == 0) {
                    tvtitle.setText("设备扫描");
                    tvcontent.setText("请扫描或者手动输入设备条码");
                } else if (sub_type == 1) {
                    tvtitle.setText("快递单号扫描");
                    tvcontent.setText("请扫描或者手动输入快递单号");
                }
                break;
            case MyComment.SCAN_RETURN_WAREHOUSE:
                tvtitle.setText("返程入库");
                tvcontent.setText("请扫描配送单二维或手动输入配送任务编号完成签收");
                break;

        }
//        scannerView.setOnScannerCompletionListener(this);
//
//        scannerView.setLaserLineResId(R.drawable.ic_saomxian);//线图
//
//        scannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
//        scannerView.setDrawText("请对准条形码完成扫描", true);
//        scannerView.setDrawTextColor(getResources().getColor(R.color.white));
//        scannerView.setLaserFrameBoundColor(getResources().getColor(R.color.title_green));
        //扫描框大小
//        scannerView.setLaserFrameSize(); //dp
        //四个角度颜色
//        scannerView.setLaserFrameBoundColor(Color.RED);
    }


    public void initListener() {
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case MyComment.SCAN_DEVICE_IN:
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        finish();
                        break;
                }
            }
        });
        btmanual.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
//        scannerView.onResume();
        super.onResume();
    }

    @Override
    protected AutoScannerView getAutoScannerView() {
        return (AutoScannerView) findViewById(R.id.autoScanner);
    }

    @Override
    protected void onPause() {
//        scannerView.onPause();
        super.onPause();
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (SurfaceView) findViewById(R.id.surfaceView);
    }

    @Override
    public void handleResult(String text) {
        onGetNumberCallBack(text);
//        reScan();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //输出跳转点击
            case R.id.bt_manual:
                Intent intent = new Intent(this, ManualActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("sub_type", sub_type);
                int requestCode = 100 + type;
                switch (type) {
                    case MyComment.SCAN_DELIVERY:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SCAN_RETURN:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.RETURN_CHECK:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SCAN_SIGN_FOR:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SIGN_FOR:
                        intent.putExtra("sub_type", 0);
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SCAN_RETURN_SIGN:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SCAN_DEVICE_OUT:
                        if (sub_type == 0) {
                            finish();
                        } else {
                            startActivityForResult(intent, requestCode);
                        }
                        break;
                    case MyComment.SCAN_DEVICE_IN:
                        if (sub_type == 0) {
                            startActivityForResult(intent, requestCode);
                        } else {
                            startActivityForResult(intent, requestCode);
                        }
                        break;

                    case MyComment.SCAN_DEVICE_PICK:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.QUERY_DEVICE:
                        startActivityForResult(intent, requestCode);
                        break;
                    case MyComment.SCAN_EXPRESS_DEVICE:
                        intent.putExtra("position", position);
                        startActivityForResult(intent, requestCode);
                        break;
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("扫描结果"+data);
        if (resultCode == RESULT_OK) {
            String number = data.getStringExtra("number");
            onGetNumberCallBack(number);
            return;
        } else {
            switch (requestCode) {
                case 100 + MyComment.SCAN_DELIVERY:
                    break;
                case 100 + MyComment.SCAN_RETURN:
                    break;
                case 100 + MyComment.RETURN_CHECK://返回设备确认
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                case 100 + MyComment.SCAN_SIGN_FOR:
                    break;
                case 100 + MyComment.SIGN_FOR://配送签收
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
                case 100 + MyComment.SCAN_RETURN_SIGN:
                    break;
                case 100 + MyComment.SCAN_DEVICE_OUT:
                    break;
                case 100 + MyComment.QUERY_DEVICE:
                    break;
                case 100 + MyComment.SCAN_EXPRESS_DEVICE:
                    break;
            }
        }
    }

    private void onGetNumberCallBack(String number) {
        Intent data = new Intent();
        data.putExtra("number", number);
        switch (type) {
            case MyComment.SCAN_DELIVERY: {
                Intent intent = new Intent(this, DeliveryActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                finish();
                break;
            }
            case MyComment.SCAN_RETURN: {
                Intent intent = new Intent(this, ReturnCarActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                finish();
                break;
            }
            case MyComment.SCAN_SIGN_FOR: {
                Intent intent = new Intent(this, DeliverySignActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                finish();
            }
            break;
            case MyComment.SCAN_RETURN_SIGN: {
                if (sub_type == 0) {
                    Intent intent = new Intent(this, ReturnSignActivity.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                } else if (sub_type == 1) {
                    Intent intent = new Intent(this, ReturnSignOrderActivity.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                } else {
                    setResult(RESULT_OK, data);
                }
                finish();
                break;
            }
            case MyComment.SCAN_DEVICE_IN: {
                if (sub_type == 0) {
                    Intent intent = new Intent(this, DeviceInActivity.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                    finish();
                } else {


                }
            }
            break;
            case MyComment.QUERY_DEVICE: {
                if (sub_type == 0) {
                    Intent intent = new Intent(this, QueryDeviceActivity.class);
                    intent.putExtra("number", number);
                    startActivity(intent);
                    finish();
                } else {


                }
            }
            break;
            case MyComment.RETURN_CHECK:
            case MyComment.SIGN_FOR:
            case MyComment.SCAN_DEVICE_PICK:
            case MyComment.SCAN_DEVICE_OUT:
                setResult(RESULT_OK, data);
                finish();
                break;
            case MyComment.SCAN_EXPRESS_DEVICE:
                //快递绑定扫描
                data.putExtra("position", position);
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面消失取消掉网络请求
        JSZPOkgoHttpUtils.cancelHttp(this);
        AppManager.getAppManager().finishActivity(this);
    }
}
