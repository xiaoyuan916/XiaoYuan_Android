package com.sgcc.pda.hardware.frame.bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * 自定义baseadapter,所有的listadapter都继承自该类
 * 
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected List<T> mList;// 需要显示的数据集

	protected LayoutInflater mLayoutInflater;// 布局填充器

	protected Context context;

	public BaseListAdapter(Context context) {
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		this.mList = new ArrayList<T>();
	}

	public BaseListAdapter(Context context, List<T> mList) {
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		this.mList = mList;
	}

	public BaseListAdapter(Context context, T[] all) {
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		this.mList = new ArrayList<T>();
		for (int i = 0; i < all.length; i++) {
			this.mList.add(all[i]);
		}
	}

	/**
	 * 设置数据，并自动通知刷新界面
	 * 
	 * @param list
	 */
	public void setData(List<T> list) {
		if(list == null)return;
		this.mList = list;
		notifyDataSetChanged();
	}

	public void addAll(T[] all) {
		if(all == null)return;
		if (mList == null)
			return;
		for (int i = 0; i < all.length; i++) {
			add(all[i], false);
		}
		notifyDataSetChanged();
	}

	private void add(T bean, boolean flag) {
		if(bean == null)return;
		if (mList == null)
			return;
		this.mList.add(bean);
		if (flag) {
			notifyDataSetChanged();
		}
	}

	/**
	 * 清空数据
	 */
	public void clear() {
		if (mList == null)
			return;
		this.mList.clear();
		notifyDataSetChanged();
	}

	/**
	 * 添加一些列数据，并自动刷新
	 * 
	 * @param list
	 */
	public void addAll(List<T> list) {
		if (mList == null)
			return;
		if (list == null)
			return;
		for (int i = 0; i < list.size(); i++) {
			add(list.get(i), false);
		}
		notifyDataSetChanged();
	}

	/**
	 * 添加一条数据，并自动通知刷新界面
	 * 
	 * @param bean
	 */
	public void add(T bean) {
		add(bean, true);
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList == null ? null : mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return mList == null ? 0 : arg0;
	}

	public void remove(int location) {
		mList.remove(location);
		notifyDataSetChanged();
	}

	public void remove(T bean) {
		mList.remove(bean);
		notifyDataSetChanged();
	}

	public T getItemObject(int pos) {
		if(mList == null)return null;
		return mList.get(pos);
	}

	public int getListCount() {
		return mList == null ? 0 : mList.size();
	}

	public void addTop(T bean) {
		if(bean == null)return ;
		mList.add(0, bean);
		notifyDataSetChanged();
	}

	public boolean itemExists(String code) {
		return false;
	}
	
	public List<T> getItemList(){
		if(mList == null)return mList;
		return mList;
	}
}
