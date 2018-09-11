package com.sgcc.pda.jszp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.sgcc.pda.jszp.R;
import com.sgcc.pda.jszp.bean.JSZPOutboundScanQueryResultEntity;
import com.sgcc.pda.jszp.bean.ScanDeleteRequest;
import com.sgcc.pda.jszp.bean.ScanDeleteResult;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;

import java.util.ArrayList;

public class JSZPOutboundScanQueryAdapter extends RecyclerView.Adapter<JSZPOutboundScanQueryAdapter.ViewHolder> {

private DeleteItemListerner deleteItemListerner;

    private ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devDatas;

    public JSZPOutboundScanQueryAdapter(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devDatas) {
        this.devDatas = devDatas;
    }

    public void setDatas(ArrayList<JSZPOutboundScanQueryResultEntity.
            JSZPOutboundScanQueryScanResultEntity.JSZPOutboundScanQueryDevData> devDatas) {
        this.devDatas.addAll(devDatas);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outbound_scan_query, parent, false);
        return new JSZPOutboundScanQueryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        JSZPOutboundScanQueryResultEntity.JSZPOutboundScanQueryScanResultEntity.
                JSZPOutboundScanQueryDevData jszpOutboundScanQueryDevData = devDatas.get(position);
        holder.tv_asset_barcode.setText(jszpOutboundScanQueryDevData.getBarCode());
        holder.tv_device_status.setText(jszpOutboundScanQueryDevData.getStatusCode());
        //单个删除
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteItemListerner != null)
                    deleteItemListerner.deleteItem(position);
            }

        });
    }


    @Override
    public int getItemCount() {
        return devDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_asset_barcode;
        private TextView tv_device_status;
        private ImageView iv_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_asset_barcode = (TextView) itemView.findViewById(R.id.tv_asset_barcode);
            tv_device_status = (TextView) itemView.findViewById(R.id.tv_device_status);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_device_delete);
        }
    }

    public DeleteItemListerner getDeleteItemListerner() {
        return deleteItemListerner;
    }

    public void setDeleteItemListerner(DeleteItemListerner deleteItemListerner) {
        this.deleteItemListerner = deleteItemListerner;
    }

    public interface DeleteItemListerner {

        void deleteItem(int position);



    }
}
