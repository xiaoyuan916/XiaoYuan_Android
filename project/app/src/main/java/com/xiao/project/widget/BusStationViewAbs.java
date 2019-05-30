package com.xiao.project.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.project.R;

/**
 * 
 * @version
 * @author NY
 * @Description U型、环形线路的公交站点的自定义控件类的超类。
 * @CreatTime 2015年8月19日09:39:04
 */
public abstract class BusStationViewAbs extends LinearLayout {

	/**
	 * 用于显示“站点”的 点icon。
	 */
	protected ImageView iv_station_dot;

	/**
	 * 用于显示“站点”的 名称。
	 */
	protected TextView tv_station_name;

	/**
	 * 用于显示“站点”的 站序。
	 */
	protected TextView tv_station_no;

	/**
	 * 表示当前对象是above还是nether类型的。
	 */
	protected String flag;

	protected BusStationViewAbs(Context context) {
		this(context, null);
	}

	/**
	 * 两个参数的构造方法 从布局文件创建view对象的时候调用.
	 * 
	 * @param context
	 * @param attrs
	 */
	protected BusStationViewAbs(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	protected BusStationViewAbs(Context context, AttributeSet attrs,
                                int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initLayoutView(context);

		// 系统在解析 xml布局文件的时候 已经自动把所有的属性 封装到了 attrs的 对象里面了.
		// 把attrs的属性集合 和 我们的 属性数组建立对应关系
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.bus_station_view);

		// 从布局的xml文件 获取到 设置的数据
		int stationDot_id = a.getResourceId(
				R.styleable.bus_station_view_stationDot,
				R.drawable.station_dot_black);

		String stationNameText = a
				.getString(R.styleable.bus_station_view_stationNameText);
		String stationNoText = a
				.getString(R.styleable.bus_station_view_stationNoText);
		int stationNameTextColor = a.getColor(
				R.styleable.bus_station_view_stationNameTextColor,
				context.getResources().getColor(
						R.color.general_font_color_black));

		a.recycle();

		initUIContent(stationDot_id, stationNameText, stationNoText,
				stationNameTextColor);
	}

	/**
	 * 初始化各个子控件的内容。
	 */
	private void initUIContent(int stationDot_id, String stationNameText,
                               String stationNoText, int stationNameTextColor) {

		iv_station_dot.setBackgroundResource(stationDot_id);
		tv_station_name.setText(stationNameText);
		tv_station_name.setTextColor(stationNameTextColor);
		tv_station_no.setText(stationNoText);
		tv_station_no.setTextColor(stationNameTextColor);
	}

	/**
	 * view对象的初始化
	 * 
	 * @param context
	 */
	protected abstract void initLayoutView(Context context);

	public ImageView getIv_station_dot() {
		return iv_station_dot;
	}

	public TextView getTv_station_no() {
		return tv_station_no;
	}

	public TextView getTv_station_name() {
		return tv_station_name;
	}

	public String getFlag() {
		return flag;
	}

}
