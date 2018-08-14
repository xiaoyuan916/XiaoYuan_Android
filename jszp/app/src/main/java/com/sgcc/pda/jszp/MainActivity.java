package com.sgcc.pda.jszp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelib.multiitem.adapter.BaseItemAdapter;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.listener.OnItemClickListener;
import com.sgcc.pda.jszp.activity.DeviceOutActivity;
import com.sgcc.pda.jszp.activity.DevicePickActivity;
import com.sgcc.pda.jszp.activity.ExpressConfirmListActivity;
import com.sgcc.pda.jszp.activity.LogisticsSendActivity;
import com.sgcc.pda.jszp.activity.QueryOrderActivity;
import com.sgcc.pda.jszp.activity.ReturnWarehouseActivity;
import com.sgcc.pda.jszp.activity.ScanActivity;
import com.sgcc.pda.jszp.activity.SendCheckActivity;
import com.sgcc.pda.jszp.adapter.mainItemAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.base.MyComment;
import com.sgcc.pda.jszp.bean.MainItem;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private float llHeight;         //搜索框高度
    private float dscHeight;         //搜索框高度
    private float dsc_margin_left;  //滑动到上面时左间距
    private float dsc_margin_right;//滑动到上面时右间距
    private float src_margin;//初始左右间距
    private float src_margin_top;//初始上间距
    private float dsc_margin_top;//终止上间距

    private FrameLayout.LayoutParams etparams;


    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.iv_saoma)
    ImageView ivSaoma;
    @BindView(R.id.et_devicenum)
    EditText etDevicenum;
    @BindView(R.id.rv_peisong)
    RecyclerView rvPeisong;
    @BindView(R.id.rv_shebei)
    RecyclerView rvShebei;
    @BindView(R.id.rv_chaxun)
    RecyclerView rvChaxun;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_banner)
    ImageView iv_banner;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.v_bg)
    View vbg;
    BaseItemAdapter psadapter;
    BaseItemAdapter fzadapter;
    BaseItemAdapter cxadapter;
    private List<MainItem> psitems;
    private List<MainItem> fzitems;
    private List<MainItem> cxitems;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        dsc_margin_left = getResources().getDimension(R.dimen.dsc_margin_left);
        dsc_margin_right = getResources().getDimension(R.dimen.dsc_margin_right);
        dsc_margin_top = getResources().getDimension(R.dimen.dsc_margin_top);
        dscHeight = getResources().getDimension(R.dimen.dsc_et_height);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                //第一次进入获取高度，以及差值 高度差比值
                if (llHeight == 0) {
                    llHeight = rlScan.getMeasuredHeight();
                    etparams = (FrameLayout.LayoutParams) rlScan.getLayoutParams();
                    src_margin = etparams.leftMargin;
                    src_margin_top = etparams.topMargin;
                }

                //滑动一次 得到渐变缩放值

                float distance = src_margin_top + verticalOffset;
                if (distance < dsc_margin_top) {
                    if (etparams.topMargin > dsc_margin_top)
                        distance = dsc_margin_top;
                    else
                        return;
                }
                float alphaScale = (distance - dsc_margin_top) / (src_margin_top - dsc_margin_top);
                iv_banner.setAlpha(alphaScale);
                etparams.leftMargin = (int) (dsc_margin_left - alphaScale * (dsc_margin_left - src_margin));
                etparams.rightMargin = (int) (dsc_margin_right - alphaScale * (dsc_margin_right - src_margin));
                etparams.topMargin = (int) distance;
                etparams.height = (int) (dscHeight - alphaScale * (dscHeight - llHeight));
                vbg.setAlpha(1 - alphaScale);
                rlScan.getParent().requestLayout();


            }
        });

    }

    @Override
    public void initData() {
        rvPeisong.setNestedScrollingEnabled(false);
        rvShebei.setNestedScrollingEnabled(false);
        rvChaxun.setNestedScrollingEnabled(false);
        psitems = new ArrayList<>();
        psitems.add(new MainItem(R.drawable.wlpaiche, R.string.ps1));
        psitems.add(new MainItem(R.drawable.sbchuku, R.string.ps2));
        psitems.add(new MainItem(R.drawable.pszhuangche, R.string.ps3));
        psitems.add(new MainItem(R.drawable.pszhuangche, R.string.ps4));
        psitems.add(new MainItem(R.drawable.psfache, R.string.ps5));
        psitems.add(new MainItem(R.drawable.psqianshou, R.string.ps6));
        psitems.add(new MainItem(R.drawable.cxchuku, R.string.ps7));
        psitems.add(new MainItem(R.drawable.psfanhui, R.string.ps8));
        psitems.add(new MainItem(R.drawable.psfanqian, R.string.ps9));
        psitems.add(new MainItem(R.drawable.sbruku, R.string.ps10));
        fzitems = new ArrayList<>();
        fzitems.add(new MainItem(R.drawable.sbzhouzhuan, R.string.fz1));
        fzitems.add(new MainItem(R.drawable.sbzuxiang, R.string.fz2));
        fzitems.add(new MainItem(R.drawable.sbjianxuan, R.string.fz3));
        cxitems = new ArrayList<>();
        cxitems.add(new MainItem(R.drawable.cxshebei, R.string.cx1));
        cxitems.add(new MainItem(R.drawable.cxdingdan, R.string.cx2));
        cxitems.add(new MainItem(R.drawable.cxchuku, R.string.cx3));
        cxitems.add(new MainItem(R.drawable.cxruku, R.string.cx4));


        rvPeisong.setLayoutManager(new GridLayoutManager(this, 4));
        rvShebei.setLayoutManager(new GridLayoutManager(this, 4));
        rvChaxun.setLayoutManager(new GridLayoutManager(this, 4));
        psadapter = new BaseItemAdapter();
        psadapter.register(MainItem.class, new mainItemAdapter<MainItem>(this));
        rvPeisong.setAdapter(psadapter);
        psadapter.setDataItems(psitems);

        fzadapter = new BaseItemAdapter();
        fzadapter.register(MainItem.class, new mainItemAdapter<MainItem>(this));
        rvShebei.setAdapter(fzadapter);
        fzadapter.setDataItems(fzitems);


        cxadapter = new BaseItemAdapter();
        cxadapter.register(MainItem.class, new mainItemAdapter<MainItem>(this));
        rvChaxun.setAdapter(cxadapter);
        cxadapter.setDataItems(cxitems);

        psadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                switch (baseViewHolder.getItemPosition()) {
                    case 0: {
                        Intent intent1 = new Intent(MainActivity.this, LogisticsSendActivity.class);
                        startActivity(intent1);
                    }
                    break;
                    case 1: {
                        Intent intent1 = new Intent(MainActivity.this, DeviceOutActivity.class);
                        startActivity(intent1);
                    }
                    break;
                    case 2: {
                        intent.putExtra("type", MyComment.SCAN_DELIVERY);//配送发车
                        startActivity(intent);
                    }
                    break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ExpressConfirmListActivity.class));
                    break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SendCheckActivity.class));
                        break;
                    case 5:
                        intent.putExtra("type", MyComment.SCAN_SIGN_FOR);//配送签收
                        startActivity(intent);
                        break;
                    case 6:
                        intent.putExtra("type", MyComment.SCAN_DEVICE_IN);//配送签收
                        startActivity(intent);
                        break;
                    case 7: {
                        intent.putExtra("type", MyComment.SCAN_RETURN);//返回装车
                        startActivity(intent);
                    }
                    break;
                    case 8:
                        intent.putExtra("type", MyComment.SCAN_RETURN_SIGN);//返程签收
                        intent.putExtra("sub_type", 0);//返程签收
                        startActivity(intent);
                        break;
                    case 9:
//                        ToastUtils.showToast(MainActivity.this,"返程入库");
                        startActivity(new Intent(MainActivity.this, ReturnWarehouseActivity.class));
                        break;

                }
            }
        });

        fzadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                switch (baseViewHolder.getItemPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2: {
                        Intent intent1 = new Intent(MainActivity.this, DevicePickActivity.class);
                        startActivity(intent1);
                    }
                    break;
                }
            }
        });

        cxadapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder baseViewHolder) {
                switch (baseViewHolder.getItemPosition()) {
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                        intent.putExtra("type", MyComment.QUERY_DEVICE);
                        startActivity(intent);
                    }
                    break;
                    case 1: {
                        Intent intent = new Intent(MainActivity.this, QueryOrderActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case 2: {
                    }
                    break;
                    case 3: {
                    }
                    break;

                }
            }
        });


    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.iv_menu, R.id.tv_manager, R.id.iv_saoma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                break;
            case R.id.tv_manager:
                break;
            case R.id.iv_saoma:
                break;
        }
    }


}
