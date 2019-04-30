package com.xiao.project.ui.magicindicator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xiao.project.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MagicIndicatorActivity extends AppCompatActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private final String[] titles = {"盘点任务", "历史任务"};
    private int fragmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_indicator);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        initMagicIndicator();
        initViewPager();
    }

    //初始化Indicator
    private void initMagicIndicator() {
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
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.color_1c));
                colorTransitionPagerTitleView.setText(titles[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                fragmentIndex = index;
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                indicator.setBackgroundResource(R.drawable.bg_border_select);
//                indicator.setColors(getResources().getColor(R.color.title_green));//下划线的颜色
//                indicator.setLineHeight(DensityUtil.dp2px(3));//下划线高度
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    //初始化viewpager
    private void initViewPager() {
//        fragmentList = new ArrayList<>();
//        bulkInventoryTaskFragment = new BulkInventoryTaskFragment(isOneByOne);
//        bulkInventoryHistoryFragment = new BulkInventoryHistoryFragment();
//        fragmentList.add(bulkInventoryTaskFragment);
//        fragmentList.add(bulkInventoryHistoryFragment);
//        IndicatorFragmentAdapter indicatorFragmentAdapter = new IndicatorFragmentAdapter(getChildFragmentManager(), fragmentList);
//        mViewPager.setAdapter(indicatorFragmentAdapter);
//        mViewPager.setCurrentItem(0);
//        mViewPager.setOffscreenPageLimit(5);
        magicIndicator.onPageSelected(0);
    }
}
