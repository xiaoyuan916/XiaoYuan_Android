package com.xiao.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiao.project.R;

/**
 * 
 * @version
 * @author NY
 * @Description U型、环形线路的公交站点的自定义控件类(下方的)。
 * @CreatTime 2015年8月19日09:39:04
 */
public class BusStationViewNether extends BusStationViewAbs {

	public BusStationViewNether(Context context) {
		super(context);
	}

	/**
	 * 两个参数的构造方法 从布局文件创建view对象的时候调用.
	 * 
	 * @param context
	 * @param attrs
	 */
	public BusStationViewNether(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BusStationViewNether(Context context, AttributeSet attrs,
                                int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * view对象的初始化
	 * 
	 * @param context
	 */
	protected void initLayoutView(Context context) {
		View layout_view = null;
			layout_view = View.inflate(context,
					R.layout.view_bus_station_nether_1920_477_new, this);


		iv_station_dot = (ImageView) layout_view
				.findViewById(R.id.iv_station_dot);
		tv_station_name = (TextView) layout_view
				.findViewById(R.id.tv_station_name);
		tv_station_no = (TextView) layout_view.findViewById(R.id.tv_station_no);
	}
}
