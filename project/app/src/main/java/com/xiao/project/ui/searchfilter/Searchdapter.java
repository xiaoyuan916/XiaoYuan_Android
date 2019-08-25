package com.xiao.project.ui.searchfilter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.project.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName Searchdapter
 * @Description TODO
 * @Author Administrator
 * @Date 2019/8/25 12:43
 * @Version 1.0
 */
public class Searchdapter extends RecyclerView.Adapter<Searchdapter.ViewHolder> {

    private List<SearchBean> mList;
    private String key = "";

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchBean bean = mList.get(position);
        holder.tvSearch.setText(bean.getStr());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void notifyDataSetChanged(List<SearchBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_search)
        TextView tvSearch;
        @BindView(R.id.ll_search)
        LinearLayout llSearch;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
