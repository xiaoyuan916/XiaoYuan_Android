package com.sgcc.pda.jszp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.ExpressBindAdapter;
import com.sgcc.pda.jszp.adapter.SpaceItemDecoration;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.ExpressBindItem;
import com.sgcc.pda.jszp.bean.ExpressBindRequestEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;
import com.sgcc.pda.jszp.util.JzspConstants;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快递绑定
 */
public class ExpressBindActivitiy extends BaseActivity{
    /**
     * 请求码
     */
    public static final int GET_CONFIRM_WHAT = 1001;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.et_express_no)
    EditText et_express_no;

    BaseItemAdapter baseItemAdapter;
    ExpressBindAdapter<ExpressBindItem> expressBindAdapter;
    ArrayList<ExpressBindItem> expressBindItems;

    private ExpressBindRequestEntity expressBindRequestEntity;

    private String taskNo,distAutoId;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_CONFIRM_WHAT:
                    //快递绑定
                    ToastUtils.showToast(ExpressBindActivitiy.this,"绑定成功");
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };
    @Override
    public int getLayoutResId() {
        return R.layout.activity_express_bind;
    }

    @Override
    public void initView() {
        taskNo = getIntent().getStringExtra("taskNo");
        distAutoId = getIntent().getStringExtra("distAutoId");

        expressBindItems = new ArrayList<>();

        //初始化请求体
        expressBindRequestEntity = new ExpressBindRequestEntity();
        expressBindRequestEntity.setUserId(JzspConstants.userId);
        expressBindRequestEntity.setDistAutoId(distAutoId);
        expressBindRequestEntity.setTaskNo(taskNo);
        expressBindRequestEntity.setExpressPkgs(expressBindItems);

        //初始化列表
        rv.setLayoutManager(new LinearLayoutManager(this));
        baseItemAdapter = new BaseItemAdapter();
        expressBindAdapter = new ExpressBindAdapter(this);
        baseItemAdapter.register(ExpressBindItem.class, expressBindAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(1));
        rv.setAdapter(baseItemAdapter);
        baseItemAdapter.setDataItems(expressBindItems);

        expressBindAdapter.setOnExpressBindClickListener(new ExpressBindAdapter.OnExpressBindClickListener() {
            @Override
            public void onSaoma(int position) {
                //扫描添加设备
                Intent intent = new Intent(ExpressBindActivitiy.this, ScanActivity.class);
                intent.putExtra("type", MyComment.SCAN_EXPRESS_DEVICE);
                intent.putExtra("sub_type", 0);
                intent.putExtra("position",position);
                startActivityForResult(intent, 100);
            }


            @Override
            public void onDeleteDevice(int parentPosition, int childPosition) {
                //删除设备
                expressBindItems.get(parentPosition).getExpressDevs().remove(childPosition);
                baseItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPackNoChange(int position, String str) {
                //修改包裹号
                expressBindItems.get(position).setPkgNo(str);
            }
        });
    }

    @Override
    public void initData() {
        tvTitle.setText("快递绑定");
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_plus,R.id.iv_delete,R.id.tv_confirm})
   void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_plus:
                //增加
                expressBindItems.add(new ExpressBindItem(et_express_no.getText().toString(),taskNo));
                baseItemAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_delete:
                //刪除
                expressBindItems.clear();
                baseItemAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_confirm:
                //确认提交
                confirm();
                break;
        }
    }
    /**
     * 确认
     */
    private void confirm() {
        if(TextUtils.isEmpty(et_express_no.getText().toString())){
            ToastUtils.showToast(this,"请输入快递单号");
            return;
        }
        for(ExpressBindItem expressBindItem :expressBindItems){
            if(TextUtils.isEmpty(expressBindItem.getPkgNo())) {
                ToastUtils.showToast(this, "请输入包号");
                return;
            }

            if(expressBindItem.getExpressDevs()==null || expressBindItem.getExpressDevs().size()==0) {
                ToastUtils.showToast(this, "包裹中设备不能为空");
                return;
            }
        }

        expressBindRequestEntity.setBillNo(et_express_no.getText().toString());
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_EXPRESS_BIND,
                this,expressBindRequestEntity,
                mHandler,GET_CONFIRM_WHAT,BaseEntity.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case 100: {
                //扫描设备
                String number = data.getStringExtra("number");
                int position =  data.getIntExtra("position",0);
                ExpressBindItem expressBindItem=  expressBindItems.get(position);
                expressBindItem.addExpressDeviceItem(new Date().getTime()+"","扫描表",taskNo,JzspConstants.Device_Category_DianYaHuGanQi,"8200000000100222");
                baseItemAdapter.notifyDataSetChanged();
            }
            break;
        }

    }
}
