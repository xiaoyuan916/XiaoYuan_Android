package com.sgcc.pda.jszp.util;

import com.sgcc.pda.jszp.bean.IoTaskDets;
import com.sgcc.pda.jszp.bean.JSZPDeliveryReceiptResultEntity;

import java.util.ArrayList;

public class JszpNumberUtils {
    public static String sumNumber(ArrayList<IoTaskDets> ioPlanDets) {
        int finishQtySum = 0;
        int finishBoxQtySum=0;
        if(ioPlanDets==null){
            return "";
        }
        for (IoTaskDets ioPlanDet : ioPlanDets) {
            finishQtySum+=ioPlanDet.getQty();
            finishBoxQtySum+=ioPlanDet.getBoxQty();
        }
        return finishQtySum+"只/"+finishBoxQtySum+"箱";
    }
}
