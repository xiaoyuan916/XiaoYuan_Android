package com.sgcc.pda.jszp.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JszpAppStatusBean;
import com.sgcc.pda.jszp.bean.JszpSgAppTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:xuxiaoyuan
 * date:2018/9/6
 */
public class JszpSelectPickViewUtils {
    private static OptionsPickerView pvCustomOptions;

    public static void showOptionsPickerBuilder(Context context,
                                                List<IPickerViewData> cardItem,
                                                OnOptionsSelectListener listener) {
        /**
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(context, listener)
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_select_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                    }
                }).isDialog(true).build();
        pvCustomOptions.setPicker(cardItem);//添加数据
        pvCustomOptions.show();
    }

    public static List<IPickerViewData> initSgAppTypeData() {
        List<IPickerViewData> list = new ArrayList<>();
        list.add(new JszpSgAppTypeBean("01", "业扩类订单"));
        list.add(new JszpSgAppTypeBean("02", "工程类订单"));
        list.add(new JszpSgAppTypeBean("03", "维护类订单"));
        return list;
    }

    public static List<IPickerViewData> initStatusData() {
        List<IPickerViewData> list = new ArrayList<>();
        list.add(new JszpAppStatusBean("00", "订单已终止"));
        list.add(new JszpAppStatusBean("01", "订单已制定"));
        list.add(new JszpAppStatusBean("02", "县公司已审核"));
        list.add(new JszpAppStatusBean("03", "市公司已审核"));
        list.add(new JszpAppStatusBean("035", "计量中心待审批"));
        list.add(new JszpAppStatusBean("04", "计量中心已审批"));
        list.add(new JszpAppStatusBean("045", "已平衡未确认"));
        list.add(new JszpAppStatusBean("05", "已平衡确认"));
        list.add(new JszpAppStatusBean("06", "配送计划已制定9"));
        list.add(new JszpAppStatusBean("07", "配送任务已制定"));
        list.add(new JszpAppStatusBean("08", "已出库"));
        list.add(new JszpAppStatusBean("085", "配送中"));
        list.add(new JszpAppStatusBean("09", "订单已签收"));
        list.add(new JszpAppStatusBean("10", "已拒签"));
        list.add(new JszpAppStatusBean("11", "已入库"));
        list.add(new JszpAppStatusBean("12", "拒签返回入库"));
        list.add(new JszpAppStatusBean("13", "已完成"));
        list.add(new JszpAppStatusBean("98", "订单已退回"));
        list.add(new JszpAppStatusBean("99", "新建"));
        return list;
    }
}
