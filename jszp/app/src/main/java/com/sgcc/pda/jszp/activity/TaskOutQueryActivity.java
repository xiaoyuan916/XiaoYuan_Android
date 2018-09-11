package com.sgcc.pda.jszp.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.adapter.IndicatorFragmentAdapter;
import com.sgcc.pda.jszp.base.BaseActivity;
import com.sgcc.pda.jszp.fragment.TaskOutQueryFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author:赵锦
 * date:2018/9/4 0004 09:42
 *
 * 出库任务查询
 */
public class TaskOutQueryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private String[] titles = {"排队中","执行中","已完成","已暂停","已终止"};

    private int type;//0入库  1出库
    @Override
    public int getLayoutResId() {
        return R.layout.activity_task_out_query;
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type",0);
        if(type==1) {
            tvTitle.setText("出库查询");
        }else {
            tvTitle.setText("入库查询");
        }

        initMagicIndicator();
        initViewPager();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    //初始化Indicator
   private void initMagicIndicator(){
       CommonNavigator commonNavigator = new CommonNavigator(this);
       //平分
       commonNavigator.setAdjustMode(true);
       commonNavigator.setScrollPivotX(0.65f);
       commonNavigator.setAdapter(new CommonNavigatorAdapter() {

           @Override
           public int getCount() {
               return titles == null ? 0 : titles.length;
           }

           @Override
           public IPagerTitleView getTitleView(Context context, final int index) {
               ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
               colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.text2));
               colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.title_green));
               colorTransitionPagerTitleView.setText(titles[index]);
               colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       mViewPager.setCurrentItem(index);
                   }
               });
               return colorTransitionPagerTitleView;
           }

           @Override
           public IPagerIndicator getIndicator(Context context) {
               LinePagerIndicator indicator = new LinePagerIndicator(context);
               indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
               indicator.setColors(getResources().getColor(R.color.title_green));//下划线的颜色
               indicator.setLineHeight(DensityUtil.dip2px(3));//下划线高度
               return indicator;
           }
       });
       magicIndicator.setNavigator(commonNavigator);

       ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    //初始化viewpager
   private void initViewPager(){
       List<Fragment> fragmentList = new ArrayList<>();
       for (int i=0;i<titles.length;i++){
           fragmentList.add(TaskOutQueryFragment.newInstance(i,type));
       }
        IndicatorFragmentAdapter indicatorFragmentAdapter = new IndicatorFragmentAdapter(getSupportFragmentManager(),fragmentList);
       mViewPager.setAdapter(indicatorFragmentAdapter);
       mViewPager.setCurrentItem(0);
       magicIndicator.onPageSelected(0);
    }
}
