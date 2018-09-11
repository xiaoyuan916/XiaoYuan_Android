package com.sgcc.pda.jszp.util;

import android.content.Context;
import android.os.Handler;

import com.sgcc.pda.jszp.bean.BaseRequestEntity;
import com.sgcc.pda.jszp.bean.JszpDpRequestEntity;
import com.sgcc.pda.jszp.bean.JszpDpsResultEntity;
import com.sgcc.pda.jszp.bean.JszpOrgListEntity;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.http.JSZPUrls;

/**
 * author:xuxiaoyuan
 * date:2018/9/10
 */
public class JszpDpInfoController {
    /**
     * 获取公司的信息
     */
    public static void obtainCompanyData(Context context, Handler mHandler,
                                         int what,
                                         JszpOrgListEntity jszpOrgListEntity,
                                         String orgType) {
        JszpDpRequestEntity requestEntity = new JszpDpRequestEntity();
        if (jszpOrgListEntity!=null){
            requestEntity.setOrgNo(jszpOrgListEntity.getOrgNo());
        }
        requestEntity.setOrgType(orgType);
        JSZPOkgoHttpUtils.postString(JSZPUrls.URL_QUERY_SUB_WH_AND_DP,
                context, requestEntity,
                mHandler, what, JszpDpsResultEntity.class);
    }
}
