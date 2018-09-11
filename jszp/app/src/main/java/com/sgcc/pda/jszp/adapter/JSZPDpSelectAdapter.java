package com.sgcc.pda.jszp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.activity.JszpDpSelect2Activity;
import com.sgcc.pda.jszp.activity.JszpDpSelect3Activity;
import com.sgcc.pda.jszp.bean.JszpOrgListEntity;

import java.util.ArrayList;

/**
 * author:xuxiaoyuan
 * date:2018/9/10
 */
public class JSZPDpSelectAdapter extends RecyclerView.Adapter<JSZPDpSelectAdapter.ViewHolder> {
    /**
     * 响应码
     */
    public static final int SELECT_DP_OPTION_REQUEST_2 = 12;
    public static final int SELECT_DP_OPTION_REQUEST_3 = 13;
    private int selectNo;


    private Activity context;

    public JSZPDpSelectAdapter(Activity activity, int selectNo) {
        this.context = activity;
        this.selectNo = selectNo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dp, parent, false);
        return new JSZPDpSelectAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JszpOrgListEntity jszpOrgListEntity = orgList.get(position);
        holder.mTvDpOption.setText(jszpOrgListEntity.getOrgName());
        if (2==selectNo){
            holder.mTvDpNext.setVisibility(View.VISIBLE);
        }else {
            holder.mTvDpNext.setVisibility(View.GONE);
        }
        initListener(holder, jszpOrgListEntity);
    }

    /**
     * 点击事件
     *
     * @param holder
     * @param jszpOrgListEntity
     */
    private void initListener(ViewHolder holder, final JszpOrgListEntity jszpOrgListEntity) {
        holder.mTvDpOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (2==selectNo||3==selectNo){
                    Intent intent = new Intent();
                    intent.putExtra("jszpOrgListEntity", jszpOrgListEntity);
                    context.setResult(context.RESULT_OK,intent);
                    context.finish();
                    return;
                }
                Intent intent = new Intent(context, JszpDpSelect2Activity.class);
                intent.putExtra("jszpOrgListEntity", jszpOrgListEntity);
                context.startActivityForResult(intent, SELECT_DP_OPTION_REQUEST_2);
            }
        });

        holder.mTvDpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JszpDpSelect3Activity.class);
                intent.putExtra("jszpOrgListEntity", jszpOrgListEntity);
                context.startActivityForResult(intent, SELECT_DP_OPTION_REQUEST_3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orgList.size();
    }

    ArrayList<JszpOrgListEntity> orgList = new ArrayList<>();

    public void setOrgList(ArrayList<JszpOrgListEntity> orgList) {
        this.orgList.clear();
        this.orgList.addAll(orgList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTvDpOption;
        public TextView mTvDpNext;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvDpOption = itemView.findViewById(R.id.tv_dp_option);
            mTvDpNext = itemView.findViewById(R.id.tv_dp_next);
        }
    }
}
