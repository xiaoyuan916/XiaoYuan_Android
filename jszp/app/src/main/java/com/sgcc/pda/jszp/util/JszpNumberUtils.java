package com.sgcc.pda.jszp.util;

import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;

import java.util.ArrayList;

public class JszpNumberUtils {
    public static String sumNumber(ArrayList<JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity
            .JSZPDeliveryReceiptResultIoPlanDetsEntity> ioPlanDets) {
        int finishQtySum = 0;
        int finishBoxQtySum=0;
        for (JSZPDeliveryReceiptResultEntity.JSZPDeliveryReceiptResultSplitTaskEntity
                .JSZPDeliveryReceiptResultIoPlanDetsEntity ioPlanDet : ioPlanDets) {
            finishQtySum+=ioPlanDet.getFinishQty();
            finishBoxQtySum+=ioPlanDet.getFinishBoxQty();
        }
        return finishQtySum+"只/"+finishBoxQtySum+"箱";
    }
}
