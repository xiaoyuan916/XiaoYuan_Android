package com.sgcc.pda.jszp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/4
 */
public class JszpOrderDetailsTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public JszpOrderDetailsTabPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    private String[] titles={"订单状态","订单明细"};
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
