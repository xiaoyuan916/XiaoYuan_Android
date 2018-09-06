package com.sgcc.pda.hardware.protocol.p698;


import android.text.TextUtils;

import com.sgcc.pda.hardware.hardware.A1BInfra;
import com.sgcc.pda.hardware.protocol.p698.bean698.AppConnReq;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Frame698;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.Constant;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 698操作类
 */

public class P698OptMethod {
    private A1BInfra infra = null;

    public P698OptMethod(A1BInfra infra) {
        this.infra = infra;
    }

    /**
     * 红外认证请求
     * @param meterAddress
     * @param random1
     * @return
     */
    public List<String> getRandom1Ciper(String meterAddress, String random1) {
        String esamInfoFrame = CommonP698EssentialMethod.getRandom1Ciper(meterAddress,random1);
        String counterFrame = sendInfra(esamInfoFrame);
        LogUtil.e("TL","红外认证请求返回帧"+counterFrame);

        if (TextUtils.isEmpty(counterFrame)){
            LogUtil.e("TL", "红外认证请求返回帧为空" );
            return null;
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(counterFrame);
        Frame698 frame698 = par.parser698(b);
        byte[] linkUserData = frame698.getLinkUserData();
        String str = DataConvert.toHexString(linkUserData);
        LogUtil.e("TL", "红外认证请求--" + str);
        if (str.length()<102){
            return null;
        }
        List<String> list=new ArrayList<>();

      String length = str.substring(24,26);
      if ("08".equals(length)){
          //三相698表
          list.add(str.substring(26,42)); //表号
          list.add(str.substring(46,62)); //ESAM序列号
          list.add(str.substring(66,82)); //随机数1密文
          list.add(str.substring(86,102));//随机数2
      }else {
          //单相698表
          list.add(str.substring(26,38)); //表号
          list.add(str.substring(42,58)); //ESAM序列号
          list.add(str.substring(62,78)); //随机数1密文
          list.add(str.substring(82,98));//随机数2
      }



        return list;
    }

    /**
     * 红外认证指令
     * @param meterAddress
     * @param random2
     * @return
     */
    public Integer checkRandom2Ciper(String meterAddress,String random2) {
        int ret=0;
        String esamInfoFrame = CommonP698EssentialMethod.checkRandom2Ciper(meterAddress,random2);
        String counterFrame = sendInfra(esamInfoFrame);
        LogUtil.e("TL","红外认证指令返回帧"+counterFrame);

        if (TextUtils.isEmpty(counterFrame)){
            LogUtil.e("TL", "红外认证指令返回帧为空" );
            return -1;
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(counterFrame);
        Frame698 frame698 = par.parser698(b);
        byte[] linkUserData = frame698.getLinkUserData();
        String str = DataConvert.toHexString(linkUserData);
        LogUtil.e("TL", "红外认证请求--" + str);
        if (str.length()<22){
            return -1;
        }
        String dar= str.substring(14,16);
        if (!"00".equals(dar)){
        //    publishProgress(new String[]{"红外认证指令失败:"+ Constant.paser698Dar(dar)});
            return -1;
        }
        return ret;
    }


    /**
     * 获取会话计数器
     * @param meterAddress
     * @return
     */
    public String getMeterCounter(String meterAddress) {
        String esamInfoFrame = CommonP698EssentialMethod.getEsamInfoFrame(meterAddress);
        String counterFrame = sendInfra(esamInfoFrame);
        LogUtil.e("TL", "会话计时器返回帧" + counterFrame);
        if (TextUtils.isEmpty(counterFrame)){
            LogUtil.e("TL", "会话计时器返回帧为空" );
            return "";
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(counterFrame);
        Frame698 frame698 = par.parser698(b);
        byte[] linkUserData = frame698.getLinkUserData();
        String str = DataConvert.toHexString(linkUserData);
        if (str.length() < 30 || str == null) {

            return "";
        }
        str = str.substring(22, 30);
        LogUtil.e("TL", "会话计时器--" + str);

        return str;
    }

    /**
     * 获取通信地址
     * @param meterAddress
     * @return
     */
    public String getMeterAddress(String meterAddress) {
        String esamInfoFrame ="fefefefe"+ CommonP698EssentialMethod.getEsamInfoFrame(meterAddress);
        String counterFrame = sendInfra(esamInfoFrame);
        LogUtil.e("TL", "通信地址返回帧" + counterFrame);
        if (TextUtils.isEmpty(counterFrame)){
            LogUtil.e("TL", "通信地址返回帧为空" );
            return "";
        }
        Parser698 par = new Parser698();  //解析698报文对象
        byte[] b = CommUtils.hex2Binary(counterFrame);
        Frame698 frame698 = par.parser698(b);
        byte[] linkUserData = frame698.getLinkUserData();
        String str = DataConvert.toHexString(linkUserData);
//        if (str.length() < 30 || str == null) {
//
//            return "";
//        }
//        str = str.substring(22, 30);
//        LogUtil.e("TL", "会话计时器--" + str);

        return str;
    }


    /**
     * 读取表号
     * @param meterAddress
     * @return
     */
    public String getMeterNum(String meterAddress) {
        String esamInfoFrame = CommonP698EssentialMethod.getMeterNum(meterAddress);
        String counterFrame = sendInfra(esamInfoFrame);
        LogUtil.e("TL", "读取表号返回帧" + counterFrame);

        if (TextUtils.isEmpty(counterFrame)){
            LogUtil.e("TL", "读取表号返回帧为空" );
            return "";
        }
        Parser698 par = new Parser698(); //解析698报文对象
        byte[] b = CommUtils.hex2Binary(counterFrame);
        Frame698 frame698 = par.parser698(b);
        byte[] linkUserData = frame698.getLinkUserData();
        String str = DataConvert.toHexString(linkUserData);
        if (str.length() < 32 || str == null) {

            return "";
        }
        str = str.substring(20, 32);
        LogUtil.e("TL", "表号--" + str);

        return str;
    }


    private String sendInfra(String frame) {
        int ret = infra.write(frame);

        StringBuffer downFrame = new StringBuffer();

        // 清空缓冲区，放置外来数据的干扰
        downFrame.delete(0, downFrame.length());
        int readRet = infra.read(downFrame, 5000);
        if (readRet != 0) {
            return "";
        }
        return downFrame.toString();
    }
}



