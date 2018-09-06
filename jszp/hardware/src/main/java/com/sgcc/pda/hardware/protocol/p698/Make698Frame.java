package com.sgcc.pda.hardware.protocol.p698;


import com.sgcc.pda.hardware.protocol.p698.bean698.AppConnReq;
import com.sgcc.pda.hardware.protocol.p698.bean698.ComdcbDataTypeBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.DataItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.Frame698;
import com.sgcc.pda.hardware.protocol.p698.bean698.IPharesItem698;
import com.sgcc.pda.hardware.protocol.p698.bean698.LinkBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.RecordRcsdBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.RecordRsdBean;
import com.sgcc.pda.hardware.protocol.p698.bean698.Region;
import com.sgcc.pda.hardware.protocol.p698.bean698.Security;
import com.sgcc.pda.hardware.protocol.p698.bean698.TimeInterval;
import com.sgcc.pda.hardware.protocol.p698.constant.Constant698;
import com.sgcc.pda.hardware.protocol.p698.structure.ConstantStru;
import com.sgcc.pda.hardware.protocol.p698.structure.IStructure;
import com.sgcc.pda.hardware.protocol.p698.structure.StruUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CommUtils;
import com.sgcc.pda.hardware.protocol.p698.utils.CrcUtil;
import com.sgcc.pda.hardware.protocol.p698.utils.DataConvert;
import com.sgcc.pda.hardware.util.Constant;
import com.sgcc.pda.sdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Make698Frame {

    private byte[] bytes = new byte[6144];
    private byte[] secbytes = new byte[1024];   // 只用于安全传输------存储安全传输时的数据
    //报文当前位置
    private int pos = 0;
    private int hcs;  //头校验的开始下标
    private int fcs; //尾校验的开始下标
    private int secpos = 0;   //只用于安全传输，存储链路用户数据开始的位置----执行安全传输则该下标的位置是安全请求标识   10H
    private int secTimeTag = 0; //只用于安全传输，存储时间标签的下标-------有 secpos、 secTimeTag 两个变量可以计算出数据长度 ，secTimeTag +1 的下标位置是 数据验证信息开始的位置
    private int secindex = 0; //只用于安全传输，在组装安全传输报文是，作为游离下标指定位置
    private int secFrehead = 0;  //只用于安全传输，存储帧头数据长度（不包括帧头检验长度）、同时该值还是安全传输报文中头校验数据开始的下标

    /*****
     * 起始符   68H----------[0]
     * 长度L   2字节    （bit15	bit14）保留    	bit13 bit12	bit11 bit10	bit9 bit8 bit7	bit6 bit5 bit4	bit3 bit2 bit1 bit0
     *         长度为：除起始字符和结束字符之外的帧字节数。  [1] 、[2]  ----倒序
     *
     * 控制域  0 1 0（完整APDU）  00   功能码 011（应用连接管理及数据交换服务）  43  [3]
     * 地址域   服务器地址SA    客户机地址  CA
     *    bit7	bit6	bit5 bit4	 bit3 bit2 bit1 bit0     -----倒序
     *       地址类型(0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)       逻辑地址         地址长度
     *
     * 帧头校验  HCS    2字节    对帧头部分除起始字符和HCS本身之外的所有字节的校验
     *
     * 链路用户数据
     *
     * 帧校验   FCS    对整帧除起始字符、结束字符和FCS本身之外的所有字节的校验
     * 结束符   16H
     *
     */

    //根据Frame698生产698报文
    public String maker698(Frame698 frame698) {

        Arrays.fill(bytes, (byte) 0);
        //获取对象分类编码---对对象执行何种操作   读取  设置  操作  上报  代理
        byte objclassifi = frame698.getObjclassifi();    //对象分类
        //组装帧头信息
        assFrameHead(frame698);         //----{数据长度} 控制域、地址域【服务器地址、客户机地址】

        pos = hcs + 2;  //应用层（链路用户数据开始下标）
        secpos = pos; //只用于安全传输  ---------存储安全传输标识的位置下标
        //根据对象分类确定将要执行什么操作
        switch (objclassifi) {
            case Constant698.LINK_REQUEST:  //预连接
                maker01(frame698);
                break;
            case Constant698.CONNECT_REQUEST:  //建立应用连接请求
                maker02(frame698);
                break;
            case Constant698.RELEASE_REQUEST:  //断开应用连接请求
                maker03(frame698);
                break;
            case Constant698.GET_OBJECT_DATA:    //读取数据
                maker05(frame698);
                break;

            case Constant698.SET_OBJECT_DATA:    //设置请求
                maker06(frame698);
                break;

            case Constant698.ACTION_OBJECT_DATA:  //操作请求          //如果操作的方法 参数 数据类型 为 structure  或ARRAY  则 传人的参数格式 必须是 数据类型|数据：数据类型|数据：数据类型|数据.....
                maker07(frame698);
                break;

            case Constant698.REPORT_OBJECT_DATA:  //上报应答
                reportRes(frame698);
                break;

            case Constant698.PROXY_OBJECT_DATA:   //代理请求
                maker09(frame698);
                break;
            case Constant698.SECURITY_OBJECT_DATA: //安全传输
               // assSecFrame698(frame698);

                break;

        }
        secTimeTag = pos;  //只用于安全传输  ---------该下标指向的是应用层数据结束的位置
        if (objclassifi != 0x01) {  //预连接没有时间标签
            bytes[pos++] = 00;  //时间标签
        }
        if (frame698.getIsSecurity()) {  //判断执行的是否是安全传输------默认不执行
            frame698.setFrameCont(assSecFrame698_bak(frame698).getFrameCont());
        } else {
            fcs = pos; //尾校验数据下标
            //组装数据长度
            getFrameLen(pos + 1);  //pos现在指向尾校验的开始下标  +1：尾校验结束的位置----即所有数据的长度
            //校验
            byte[] hbyte = new byte[hcs + 1];
            byte[] fbyte = new byte[fcs + 1];
            for (int i = 1; i < hcs; i++) {  //头校验
                hbyte[i - 1] = bytes[i];   //获取头校验数据
            }
            byte[] hcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(hbyte, hcs - 1));
            LogUtil.e("TL","帧头校验"+ DataConvert.byte2HexStr(hcsvalue));
            System.arraycopy(hcsvalue, 0, bytes, hcs, 2);
            for (int i = 1; i < fcs; i++) { //尾校验
                fbyte[i - 1] = bytes[i];  //获取尾校验数据
            }
            byte[] fcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(fbyte, fcs - 1));
            System.arraycopy(fcsvalue, 0, bytes, fcs, 2);

            //结束符
            bytes[pos + 2] = 0x16;  //pos 现在指向尾校验开始的位置、 +2将下标定位到尾结束符
            byte[] downFrame = new byte[pos + 3];   //+3包括尾校验 和 尾结束符
            for (int i = 0; i < pos + 3; i++) {
                downFrame[i] = bytes[i];
            }
            frame698.setFrameCont(downFrame);    //698报文内容
        }
        String p645Code = CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
        return p645Code;
//        System.out.println(CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase());
//        return CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
    }
    //根据Frame698生产698报文
    public String maker698safe (Frame698 frame698,String data) {

        Arrays.fill(bytes, (byte) 0);
        //获取对象分类编码---对对象执行何种操作   读取  设置  操作  上报  代理
        byte objclassifi = frame698.getObjclassifi();    //对象分类
        //组装帧头信息
        assFrameHead(frame698);         //----{数据长度} 控制域、地址域【服务器地址、客户机地址】

        pos = hcs + 2;  //应用层（链路用户数据开始下标）
        secpos = pos; //只用于安全传输  ---------存储安全传输标识的位置下标
        //根据对象分类确定将要执行什么操作
        //LogUtil.e("TL","操作类型"+objclassifi);
//        switch (objclassifi) {
//            case Constant698.LINK_REQUEST:  //预连接
//                maker01(frame698);
//                break;
//            case Constant698.CONNECT_REQUEST:  //建立应用连接请求
//                maker02(frame698);
//                break;
//            case Constant698.RELEASE_REQUEST:  //断开应用连接请求
//                maker03(frame698);
//                break;
//            case Constant698.GET_OBJECT_DATA:    //读取数据
//                maker05(frame698);
//                break;
//
//            case Constant698.SET_OBJECT_DATA:    //设置请求
//                maker06(frame698);
//                break;
//
//            case Constant698.ACTION_OBJECT_DATA:  //操作请求          //如果操作的方法 参数 数据类型 为 structure  或ARRAY  则 传人的参数格式 必须是 数据类型|数据：数据类型|数据：数据类型|数据.....
//                maker07(frame698);
//                break;
//
//            case Constant698.REPORT_OBJECT_DATA:  //上报应答
//                reportRes(frame698);
//                break;
//
//            case Constant698.PROXY_OBJECT_DATA:   //代理请求
//                maker09(frame698);
//                break;
//            case Constant698.SECURITY_OBJECT_DATA: //安全传输
//                // assSecFrame698(frame698);
//                LogUtil.e("TL","执行了安全传输");
//                byte[] dataByte = DataConvert.toBytes(data);
//                System.arraycopy(dataByte, 0, bytes, pos, dataByte.length);
//                pos+=dataByte.length;
//                break;
//
//        }

        LogUtil.e("TL","执行了安全传输");
                byte[] dataByte = DataConvert.toBytes(data);
                System.arraycopy(dataByte, 0, bytes, pos, dataByte.length);
                pos+=dataByte.length;
                secTimeTag = pos;  //只用于安全传输  ---------该下标指向的是应用层数据结束的位置
//        if (objclassifi != 0x01||objclassifi != 0x16) {  //预连接没有时间标签 安全传输报文没有时间标签
//            bytes[pos++] = 00;  //时间标签
//            LogUtil.e("TL","时间标签--------");
//        }
      //  bytes[pos++] = 00;  //时间标签
        if (frame698.getIsSecurity()) {  //判断执行的是否是安全传输------默认不执行
            frame698.setFrameCont(assSecFrame698_bak(frame698).getFrameCont());
        } else {
            fcs = pos; //尾校验数据下标
            //组装数据长度
            getFrameLen(pos + 1);  //pos现在指向尾校验的开始下标  +1：尾校验结束的位置----即所有数据的长度
            //校验
            byte[] hbyte = new byte[hcs + 1];
            byte[] fbyte = new byte[fcs + 1];
            for (int i = 1; i < hcs; i++) {  //头校验
                hbyte[i - 1] = bytes[i];   //获取头校验数据
            }
            byte[] hcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(hbyte, hcs - 1));
            LogUtil.e("TL","帧头校验"+ DataConvert.byte2HexStr(hcsvalue));
            System.arraycopy(hcsvalue, 0, bytes, hcs, 2);
            for (int i = 1; i < fcs; i++) { //尾校验
                fbyte[i - 1] = bytes[i];  //获取尾校验数据
            }
            byte[] fcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(fbyte, fcs - 1));
            System.arraycopy(fcsvalue, 0, bytes, fcs, 2);

            //结束符
            bytes[pos + 2] = 0x16;  //pos 现在指向尾校验开始的位置、 +2将下标定位到尾结束符
            byte[] downFrame = new byte[pos + 3];   //+3包括尾校验 和 尾结束符
            for (int i = 0; i < pos + 3; i++) {
                downFrame[i] = bytes[i];
            }
            frame698.setFrameCont(downFrame);    //698报文内容
        }
        String p645Code = CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
        return p645Code;
//        System.out.println(CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase());
//        return CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
    }
    //组装698分帧报文
    public String maker698safeFz (Frame698 frame698,String data) {

        Arrays.fill(bytes, (byte) 0);
        //获取对象分类编码---对对象执行何种操作   读取  设置  操作  上报  代理
        byte objclassifi = frame698.getObjclassifi();    //对象分类
        //组装帧头信息
        assFrameHeadFz(frame698);         //----{数据长度} 控制域、地址域【服务器地址、客户机地址】

        pos = hcs + 2;  //应用层（链路用户数据开始下标）
        secpos = pos; //只用于安全传输  ---------存储安全传输标识的位置下标
        //根据对象分类确定将要执行什么操作
        //LogUtil.e("TL","操作类型"+objclassifi);
//        switch (objclassifi) {
//            case Constant698.LINK_REQUEST:  //预连接
//                maker01(frame698);
//                break;
//            case Constant698.CONNECT_REQUEST:  //建立应用连接请求
//                maker02(frame698);
//                break;
//            case Constant698.RELEASE_REQUEST:  //断开应用连接请求
//                maker03(frame698);
//                break;
//            case Constant698.GET_OBJECT_DATA:    //读取数据
//                maker05(frame698);
//                break;
//
//            case Constant698.SET_OBJECT_DATA:    //设置请求
//                maker06(frame698);
//                break;
//
//            case Constant698.ACTION_OBJECT_DATA:  //操作请求          //如果操作的方法 参数 数据类型 为 structure  或ARRAY  则 传人的参数格式 必须是 数据类型|数据：数据类型|数据：数据类型|数据.....
//                maker07(frame698);
//                break;
//
//            case Constant698.REPORT_OBJECT_DATA:  //上报应答
//                reportRes(frame698);
//                break;
//
//            case Constant698.PROXY_OBJECT_DATA:   //代理请求
//                maker09(frame698);
//                break;
//            case Constant698.SECURITY_OBJECT_DATA: //安全传输
//                // assSecFrame698(frame698);
//                LogUtil.e("TL","执行了安全传输");
//                byte[] dataByte = DataConvert.toBytes(data);
//                System.arraycopy(dataByte, 0, bytes, pos, dataByte.length);
//                pos+=dataByte.length;
//                break;
//
//        }

        LogUtil.e("TL","执行了安全传输");
        byte[] dataByte = DataConvert.toBytes(data);
        System.arraycopy(dataByte, 0, bytes, pos, dataByte.length);
        pos+=dataByte.length;
        secTimeTag = pos;  //只用于安全传输  ---------该下标指向的是应用层数据结束的位置
//        if (objclassifi != 0x01||objclassifi != 0x16) {  //预连接没有时间标签 安全传输报文没有时间标签
//            bytes[pos++] = 00;  //时间标签
//            LogUtil.e("TL","时间标签--------");
//        }
        //  bytes[pos++] = 00;  //时间标签
        if (frame698.getIsSecurity()) {  //判断执行的是否是安全传输------默认不执行
            frame698.setFrameCont(assSecFrame698_bak(frame698).getFrameCont());
        } else {
            fcs = pos; //尾校验数据下标
            //组装数据长度
            getFrameLen(pos + 1);  //pos现在指向尾校验的开始下标  +1：尾校验结束的位置----即所有数据的长度
            //校验
            byte[] hbyte = new byte[hcs + 1];
            byte[] fbyte = new byte[fcs + 1];
            for (int i = 1; i < hcs; i++) {  //头校验
                hbyte[i - 1] = bytes[i];   //获取头校验数据
            }
            byte[] hcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(hbyte, hcs - 1));
            LogUtil.e("TL","帧头校验"+ DataConvert.byte2HexStr(hcsvalue));
            System.arraycopy(hcsvalue, 0, bytes, hcs, 2);
            for (int i = 1; i < fcs; i++) { //尾校验
                fbyte[i - 1] = bytes[i];  //获取尾校验数据
            }
            byte[] fcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(fbyte, fcs - 1));
            System.arraycopy(fcsvalue, 0, bytes, fcs, 2);

            //结束符
            bytes[pos + 2] = 0x16;  //pos 现在指向尾校验开始的位置、 +2将下标定位到尾结束符
            byte[] downFrame = new byte[pos + 3];   //+3包括尾校验 和 尾结束符
            for (int i = 0; i < pos + 3; i++) {
                downFrame[i] = bytes[i];
            }
            frame698.setFrameCont(downFrame);    //698报文内容
        }
        String p645Code = CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
        return p645Code;
//        System.out.println(CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase());
//        return CommUtils.byteToHexString(frame698.getFrameCont()).replaceAll(",", "").replaceAll("0x", "").toUpperCase();
    }
    /**
     * 组装安全传输报文
     *
     * @param frame698
     * @return
     */
    private void assSecFrame698(Frame698 frame698) {
        bytes[pos++] = Constant698.SECURITY_OBJECT_DATA;
        bytes[pos++] = CommUtils.hex2Binary(frame698.getSecurityObj().getType())[0];
        int enLen = frame698.getSecurityObj().getTransferData().length() / 2;
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(enLen))[0];
        System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getTransferData()), 0, bytes, pos, enLen);
        pos += enLen;
        String secType = frame698.getSecurityObj().getSecType();
        if (secType.equals("0")) {    //SID_MAC
            bytes[pos++] = 0x0;
            //组装SID信息
            int sLen = frame698.getSecurityObj().getSecSid().length() / 2;
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getSecSid()), 0, bytes, pos, sLen);
            pos += sLen;
            //组装SID附加数据域信息
            int attLen = frame698.getSecurityObj().getAttrachData().length() / 2;
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(attLen))[0];
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getAttrachData()), 0, bytes, pos, attLen);
            pos += attLen;
            //组装MAC信息
            int mLen = frame698.getSecurityObj().getMac().length() / 2;
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(mLen))[0];
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getMac()), 0, bytes, pos, mLen);
            pos += mLen;
        } else if (secType.equals("1")) {    //RN
            bytes[pos++] = 0x1;
            //组装RN信息
            int rnLen = frame698.getSecurityObj().getRn().length() / 2;
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getRn()), 0, bytes, pos, rnLen);
            pos += rnLen;
        } else if (secType.equals("2")) {    // RN_MAC
            bytes[pos++] = 0x2;
            //组装RN信息
            int rnLen = frame698.getSecurityObj().getRn().length() / 2;
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getRn()), 0, bytes, pos, rnLen);
            pos += rnLen;
            //组装MAC信息
            int mLen = frame698.getSecurityObj().getMac().length() / 2;
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(mLen))[0];
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getMac()), 0, bytes, pos, mLen);
            pos += mLen;
        } else if (secType.equals("3")) {    //SID
            bytes[pos++] = 0x3;
            //组装SID标示
            int sLen = frame698.getSecurityObj().getSecSid().length() / 2;
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getSecSid()), 0, bytes, pos, sLen);
            pos += sLen;
            //组装SID中附加数据域信息
            int attLen = frame698.getSecurityObj().getAttrachData().length() / 2;
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(attLen))[0];
            System.arraycopy(CommUtils.hex2Binary(frame698.getSecurityObj().getAttrachData()), 0, bytes, pos, attLen);
            pos += attLen;
        }
    }

    //组装安全传输报文
    private Frame698 assSecFrame698_bak(Frame698 frame698) {
        Frame698 frame = new Frame698();
        secFrehead = secpos - 2;  //帧头数据长度     secpos指向安全传输标识下标 ,同时还是bytes数组中链路用户数据开始的下标
        Arrays.fill(secbytes, (byte) 0);
        //拷贝字节数组bytes中从开始字符到客户地址的数据到secbytes中
        System.arraycopy(bytes, 0, secbytes, 0, secFrehead);
        secindex = secpos;   //将secindex 定位到安全传输标识的位置          secindex 是组装安全传输报文的游离下标
        secbytes[secindex++] = Constant698.SECURITY_OBJECT_DATA;  //------安全传输标识，  将下标定位到应用数据单元【明文/密文】
        Security sec = frame698.getSecurityObj();  //获取安全对象
        // 0：明文/1：密文
        secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(sec.getType())))[0];
        //明文数据长度   时间标签下标【secTimeTag】减去应用层数据开始的下标【secpos】+1
        int len = 0;  // 明文或密文 数据长度
        if ("0".equals(sec.getType())) {  //明文
            len = secTimeTag - secpos + 1;  //
            secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(len))[0]; // secindex++ 指向明文数据开始的下标
            //将数组bytes中明文数据拷贝到数组secbytes中
            System.arraycopy(bytes, secpos, secbytes, secindex, len);
        } else {//  密文
            // TODO: 2018/1/3  这个解析密文。。。需要连接加密机。。。加密机返回明文在解析
//    			 byte  []  express =  new  byte[len];  //存储要加密的明文
//    			 byte  []  ciphertext;    //存储密文
//    			 System.arraycopy(bytes, secpos,  express, 0, len);
//    			 ciphertext   =  Sp698ProtoUtil.getCiphertext(express, frame698); //获取密文
//    			 len =  ciphertext.length;
//    			 secbytes[secindex++]= CommUtils.hex2Binary(Integer.toHexString(len))[0]; // secindex++ 指向密文数据开始的下标
//    			 System.arraycopy(ciphertext, 0, secbytes, secindex, len);
        }
        secindex = secindex + len;  //将secindex指向数据验证信息开始的位置
        assSecInfo(frame698.getSecurityObj());   //--------------组装验证信息
        fcs = secindex; //尾校验数据下标
        //组装数据长度
        getsecFrameLen(secindex + 1);  //pos现在指向尾校验的开始下标  +1：尾校验结束的位置----即所有数据的长度
        //校验
        byte[] hbyte = new byte[hcs + 1];
        byte[] fbyte = new byte[fcs + 1];
        for (int i = 1; i < hcs; i++) {  //头校验
            hbyte[i - 1] = secbytes[i];   //获取头校验数据
        }
        byte[] hcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(hbyte, hcs - 1));

        System.arraycopy(hcsvalue, 0, secbytes, hcs, 2);
        for (int i = 1; i < fcs; i++) { //尾校验
            fbyte[i - 1] = secbytes[i];  //获取尾校验数据
        }
        byte[] fcsvalue = CommUtils.hex2Binary(CrcUtil.tryfcs16(fbyte, fcs - 1));
        System.arraycopy(fcsvalue, 0, secbytes, fcs, 2);
        //结束符
        secbytes[secindex + 2] = 0x16;  //pos 现在指向尾校验开始的位置、 +2将下标定位到尾结束符
        byte[] downFrame = new byte[secindex + 3];   //+3包括尾校验 和 尾结束符
        for (int i = 0; i < secindex + 3; i++) {
            downFrame[i] = secbytes[i];
        }
        frame.setFrameCont(downFrame);    //698报文内容
        return frame;
    }

    //上报应答  响应
    private void reportRes(Frame698 frame698) {
        List<DataItem698> list = frame698.getDataItem698();
        bytes[pos++] = Constant698.REPORT_OBJECT_DATA;   //  上报响应   08
        if (frame698.getSetOrOpt() == null || "".equals(frame698.getSetOrOpt())) {
            bytes[pos++] = 0x01;  //上报若干个对象属性的响应
            bytes[pos++] = Constant698.CLIENT_ADDR; //PIID
        } else {
            bytes[pos++] = 0x02;  ////上报若干个记录型对象属性的响应
            bytes[pos++] = Constant698.CLIENT_ADDR; //PIID
        }
        //对应上报若干个对象属性描述符  SEQUENCE OF OAD
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];//对象个数
        for (DataItem698 dataItem698 : list) {
            assObjData(dataItem698);   //对象标识
        }
    }

    //根据数据验证信息编码 组装验证信息
    private void assSecInfo(Security sec) {
        //数据验证方式 0：数据验证码【SID_MAC】、  1：随机数【RN】、  2：随机数+数据MAC【RN_MAC】、3：安全标识【SID】
        int secType = Integer.parseInt(sec.getSecType());   //------执行验证方式编码
        secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(secType))[0];
        byte[] secId;
        byte[] mdata;
        byte[] mac;
        switch (secType) {
            case 0:  //----------数据验证码【SID_MAC】
                //组装SID_MAC数据类型
                secbytes[secindex++] = 0x0;   //数据验证类型
                //安全标识 SID{标识【double-long-unsigned--4字节】、  附加数据[octet-string]}
                // 组装标识数据
                secId = CommUtils.hex2Binary(sec.getSecSid());  //标识
                System.arraycopy(secId, 0, secbytes, secindex, secId.length);
                secindex = secindex + secId.length;   //将下标定位到附加数据长度的位置
                mdata = CommUtils.hex2Binary(sec.getAttrachData());  //附加数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mdata.length))[0]; //下标定位到附加数据开始的位置
                System.arraycopy(mdata, 0, secbytes, secindex, mdata.length);
                secindex = secindex + mdata.length;  //下标定位到MAC数据长度的位置
                //数据MAC
                mac = CommUtils.hex2Binary(sec.getMac());  //MAC数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mac.length))[0]; //下标定位到MAC数据开始的位置
                System.arraycopy(mac, 0, secbytes, secindex, mac.length);
                secindex = secindex + mac.length; //尾校验开始的下标
                break;
            case 1:  //----------随机数【RN】  octet-string
                secbytes[secindex++] = 0x1;   //数据验证类型
                mdata = CommUtils.hex2Binary(Constant698.RAND_HOST);  //随机数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mdata.length))[0]; //下标定位到随机数据开始的位置
                System.arraycopy(mdata, 0, secbytes, secindex, mdata.length);
                secindex = secindex + mdata.length; //尾校验开始的下标
                break;
            case 2:   //----------随机数+数据MAC【RN_MAC】
                secbytes[secindex++] = 0x2;   //数据验证类型
                mdata = CommUtils.hex2Binary(Constant698.RAND_HOST);  //随机数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mdata.length))[0]; //下标定位到随机数据开始的位置
                System.arraycopy(mdata, 0, secbytes, secindex, mdata.length);
                secindex = secindex + mdata.length; //下标定位到MAC数据长度的位置
                //数据MAC
                mac = CommUtils.hex2Binary(sec.getMac());  //MAC数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mac.length))[0]; //下标定位到MAC数据开始的位置
                System.arraycopy(mac, 0, secbytes, secindex, mac.length);
                secindex = secindex + mac.length; //尾校验开始的下标
                break;
            case 3:  //----------安全标识【SID】
                //安全标识 SID{标识【double-long-unsigned--4字节】、  附加数据[octet-string]}
                // 组装标识数据
                secbytes[secindex++] = 0x3; //数据验证类型
                secId = CommUtils.hex2Binary(sec.getSecSid());  //标识
                System.arraycopy(secId, 0, secbytes, secindex, secId.length);
                secindex = secindex + secId.length;   //将下标定位到附加数据长度的位置
                mdata = CommUtils.hex2Binary(sec.getAttrachData());  //附加数据
                secbytes[secindex++] = CommUtils.hex2Binary(Integer.toHexString(mdata.length))[0]; //下标定位到附加数据开始的位置
                System.arraycopy(mdata, 0, secbytes, secindex, mdata.length);
                secindex = secindex + mdata.length;  //尾校验开始的下标
                break;
        }
        secId = null;
        mdata = null;
        mac = null;
    }

    //读取数据
    private void maker05(Frame698 frame698) {
        List<DataItem698> list = frame698.getDataItem698();
        bytes[pos++] = Constant698.GET_OBJECT_DATA; //--14 读取对象属性  05
        if (list.size() == 1 && (frame698.getSetOrOpt() == null || "".equals(frame698.getSetOrOpt()))) {   //对象一个对象属性
            bytes[pos++] = 0x01;   //--读取一个对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR1;   //pIID   序号--优先级   //--16
            assObjData(list.get(0));   //对象标识
        } else if (list.size() > 1 && (frame698.getSetOrOpt() == null || "".equals(frame698.getSetOrOpt()))) {  //对象多个对象属性
            bytes[pos++] = 0x02;  //读取多个对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];//对象个数
            for (DataItem698 dataItem698 : list) {
                assObjData(dataItem698);   //对象标识
            }
        } else if (list.size() == 1 && frame698.getSetOrOpt() != null && !"".equals(frame698.getSetOrOpt())) {   //读取一个记录型对象属性
            bytes[pos++] = 0x03;    //读取一个记录型对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
            //组装记录型对象属性标识
            assObjData(list.get(0));    //对象属性描述符 OAD
            //记录选择描述符 RSD      选择方法1--10
            selectMethod(frame698.getRsd().get(0));
            //记录列选择描述符 RCSD    rcsd个数   00  对象属性描述符   01 记录型
            //-----------
            optRcsd(frame698.getRcsd().get(0));
        } else if (list.size() > 1 && frame698.getSetOrOpt() != null && !"".equals(frame698.getSetOrOpt())) {     //读取多个记录项对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR;//  PIID   服务序号-优先级
            int size = list.size();//读取记录型对象个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(size))[0];//记录型对象个数
            for (int i = 0; i < size; i++) {
                //组装记录型对象属性标识
                assObjData(list.get(i));    //对象属性描述符 OAD
                //记录选择描述符 RSD      选择方法1--10
                selectMethod(frame698.getRsd().get(i));
                //记录列选择描述符 RCSD    rcsd个数   00  对象属性描述符   01 记录型
                //-----------
                optRcsd(frame698.getRcsd().get(i));
            }
        } else {  //读取分帧响应的下一个数据块------------------需要一个标识  该什么时候执行分帧读取
            if ("".equals(Parser698.frameNum)) {
                bytes[pos++] = 0x01;   //--读取一个对象属性
                bytes[pos++] = Constant698.CLIENT_ADDR;   //pIID   序号--优先级
                assObjData(list.get(0));   //组装对象标识对象标识
            } else {  //读取分帧的下一帧：
                bytes[pos++] = 0x05;   //--读取分帧响应的下一个数据块
                bytes[pos++] = Constant698.CLIENT_ADDR;  //pIID   序号--优先级
                byte[] num = CommUtils.hex2Binary(Parser698.frameNum);   //正确接收的最近一次数据块序号
                System.arraycopy(num, 0, bytes, pos, num.length);
                pos += num.length;
                Parser698.frameNum = "";
            }
        }
        list.clear();
        list = null;
    }

    //组装读取纪录型对象的列数据
    private void optRcsd(RecordRcsdBean rcsd) {
        List<Map<String, List<DataItem698>>> list = rcsd.getRecordRcse();
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];    //列个数
        for (Map<String, List<DataItem698>> map : list) {
            for (Map.Entry<String, List<DataItem698>> entry : map.entrySet()) {
                if ("00".equals(entry.getKey())) {    //对象属性描述符
                    bytes[pos++] = 0x00;
                    if (null != entry.getValue().get(0)) {
                        assObjData(entry.getValue().get(0));    //对象属性描述符 OAD
                    }
                }
                if ("01".equals(entry.getKey())) {   //记录型对象属性描述符
                    bytes[pos++] = 0x01;
                    List<DataItem698> item = entry.getValue();
                    //记录型对象属性  标识
                    assObjData(item.get(0));
                    //移除第一个记录型对象
                    item.remove(0);    //----第一个对象为记录型对象，后面的为记录型对象关联的对象
                    //关联对象个数
                    bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
                    //关联对象
                    for (DataItem698 data : item) {
                        assObjData(data);
                    }
                }
            }
        }
    }

    //组装读取记录型对象属性的记录选择描述符RSD
    private void selectMethod(RecordRsdBean rb) {
        //选择执行方法  RSD
        switch (Integer.parseInt(rb.getMethod())) {
            case 1:
                methodOne(rb);     //RSD方法一
                break;
            case 2:
                methodTwo(rb);    //RSD方法二
                break;
            case 3:
                methodThree(rb);  //RSD方法三
                break;
            case 4:
                methodFore(rb);  //RSD方法四
                break;
            case 5:
                methodFive(rb);  //RSD方法五
                break;
            case 6:
                methodSix(rb);  //RSD方法六
                break;
            case 7:
                methodSeven(rb);  //RSD方法七
                break;
            case 8:
                methodEight(rb);  //RSD方法八
                break;
            case 9:
                methodNine(rb);  //RSD方法九
                break;
            case 10:
                methodTen(rb);   //RSD方法十
                break;
        }

    }

    //RSD  方法一
    private void methodOne(RecordRsdBean rb) {
        //RSD方法1
        bytes[pos++] = 0x01;
        DataItem698 dataitem = rb.getListItem().get(0);
        //对象属性描述符
        assObjData(dataitem);   //对象标识
        //对象属性数据类型
        assObjDataType(dataitem);   //对象属性数据类型
        //数据
        dataitem.setInputParam(rb.getData().get(0));   //设置数值
        assObjValue(dataitem);  //组装数据

    }

    //RSD  方法二
    private void methodTwo(RecordRsdBean rb) {
        //选择方法2
        bytes[pos++] = 0x02;
        //对象属性描述符
        assObjData(rb.getListItem().get(0));   //获取对象，  并组装OAD  -----OAD(对象标识 、 属性标识及其对象特征、元素索引)
        DataItem698 di = null;
        Map<String, String> map;
        //起始值
        di = new DataItem698();
        map = rb.getBeginData().get(0);
        String data = getKeyandValue(map);   //调用该方法获取数据数据类型  和  数据
        di.setDataType(data.split("\\|")[0]);   //数据类型
        di.setInputParam(data.split("\\|")[1]);  //数据
        //组装数据类型
        assObjDataType(di);
        //组装数据
        assObjValue(di);

        //结束值
        map.clear();
        map = rb.getEndData().get(0);
        data = getKeyandValue(map);   //调用该方法获取数据数据类型  和  数据
        di.setDataType(data.split("\\|")[0]);   //数据类型
        di.setInputParam(data.split("\\|")[1]);  //数据
        //组装数据类型
        assObjDataType(di);
        //组装数据
        assObjValue(di);

        //数据间隔
        if ("date_time".equals(data.split("\\|")[0]) || "date_time_s".equals(data.split("\\|")[0])) {
            //时间间隔    数据类型  TI   enum
            di.setDataType("TI");
            assObjDataType(di);   //组装时间间隔数据类型
            di.setDataType("enum");
            assObjDataType(di);   //组装枚举数据类型
            TimeInterval ti = rb.getTi().get(0);
            di.setDataType("unsigned");
            di.setInputParam(ti.getTimeUnit());
            assObjValue(di);  //组装时间间隔单位
            //组装时间间隔值
            di.setDataType("long-unsigned");
            di.setInputParam(ti.getIntervalValue());
            assObjDataType(di);   //组装数据类型
            assObjValue(di);  //组装间隔值
        } else {
            di.setInputParam(rb.getData().get(0));
            //组装数据类型
            assObjDataType(di);
            //组装数据
            assObjValue(di);
        }
    }

    //
    @SuppressWarnings("unchecked")
    private String getKeyandValue(Map<String, String> map) {
        Iterator it = map.entrySet().iterator();
        Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
        return entry.getKey() + "|" + entry.getValue();
    }

    //RSD  方法三
    private void methodThree(RecordRsdBean rb) {
        //选择方法3
        bytes[pos++] = 0x03;
        //个数
        int count = rb.getListItem().size();
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(count))[0];

        DataItem698 di = null;   //698数据项对象
        Map<String, String> map;  //装有起始值或结束值的数据类型、数据容器
        String data;    //起始值或结束值的数据类型、数据
        for (int i = 0; i < count; i++) {
            //对象属性描述符
            assObjData(rb.getListItem().get(i));       // ---------获取一个对象，  并组装OAD  -----OAD(对象标识 、 属性标识及其对象特征、元素索引)
            di = new DataItem698();
            //起始值
            map = rb.getBeginData().get(i);
            data = getKeyandValue(map);   //调用该方法获取数据数据类型  和  数据
            di.setDataType(data.split("\\|")[0]);   //数据类型
            di.setInputParam(data.split("\\|")[1]);  //数据
            //组装数据类型
            assObjDataType(di);
            //组装数据
            assObjValue(di);
            //结束值
            map.clear();
            map = rb.getEndData().get(i);
            data = getKeyandValue(map);   //调用该方法获取数据数据类型  和  数据
            di.setDataType(data.split("\\|")[0]);   //数据类型
            di.setInputParam(data.split("\\|")[1]);  //数据
            //组装数据类型
            assObjDataType(di);
            //组装数据
            assObjValue(di);
            map.clear();
            //数据间隔
            if ("date_time".equals(data.split("\\|")[0]) || "date_time_s".equals(data.split("\\|")[0])) {
                //时间间隔    数据类型  TI   enum
                di.setDataType("TI");
                assObjDataType(di);   //组装时间间隔数据类型
                di.setDataType("enum");
                assObjDataType(di);   //组装枚举数据类型
                TimeInterval ti = rb.getTi().get(0);
                di.setDataType("unsigned");
                di.setInputParam(ti.getTimeUnit());
                assObjValue(di);  //组装时间间隔单位
                //组装时间间隔值
                di.setDataType("long-unsigned");
                di.setInputParam(ti.getIntervalValue());
                assObjDataType(di);   //组装数据类型
                assObjValue(di);  //组装间隔值
            } else {
                di.setInputParam(rb.getData().get(0));
                //组装数据类型
                assObjDataType(di);
                //组装数据
                assObjValue(di);
            }
        }
    }

    //RSD  方法四
    private void methodFore(RecordRsdBean rb) {
        bytes[pos++] = 0x04;  //选择方法四
        //采集启动时间
        DataItem698 dataitem = new DataItem698();
        dataitem.setDataType("date_time_s");  //设置时间数据类型
        dataitem.setInputParam(rb.getStartUpTime());  //设置采集启动时间
        assObjValue(dataitem);   //组装采集启动时间
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }
    }

    //RSD  方法五
    private void methodFive(RecordRsdBean rb) {
        bytes[pos++] = 0x05;  //选择方法五
        //采集存储时间   date_time_s
        DataItem698 dataitem = new DataItem698();
        dataitem.setDataType("date_time_s");
        dataitem.setInputParam(rb.getCollStorTime());
        assObjValue(dataitem);   //组装采集存储时间
        //电能表集合  MS
        //MS  编号[0---7]0\1   数据个数   数据
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }
    }

    //RSD  方法六  采集启动
    private void methodSix(RecordRsdBean rb) {
        bytes[pos++] = 0x06;   //选择方法6
        DataItem698 di = null;
        //采集启动时间起始值             date_time_s      数据类型  +   值
        di = new DataItem698();
        di.setDataType("date_time_s");   //数据类型
        //启动时间
        di.setInputParam(rb.getTimeBegin());
        assObjDataType(di);
        assObjValue(di);
        //采集启动时间结束值
        di.setInputParam(rb.getTimeEnd());  //结束时间
        assObjDataType(di);
        assObjValue(di);
        //时间间隔      数据类型  TI   enum
        di.setDataType("TI");
        assObjDataType(di);   //组装时间间隔数据类型
        di.setDataType("enum");
        assObjDataType(di);   //组装枚举数据类型
        TimeInterval ti = rb.getTi().get(0);
        di.setDataType("unsigned");
        di.setInputParam(ti.getTimeUnit());
        assObjValue(di);  //组装时间间隔单位
        //组装时间间隔值
        di.setDataType("long-unsigned");
        di.setInputParam(ti.getIntervalValue());
        assObjDataType(di);   //组装数据类型
        assObjValue(di);  //组装间隔值
        //电能表集合  MS
        //MS  编号[0---7]0\1   数据个数   数据
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }
    }

    //RSD  方法七   采集存储
    private void methodSeven(RecordRsdBean rb) {
        bytes[pos++] = 0x07;  //选择方法7
        DataItem698 di = null;
        //采集存储时间起始值             date_time_s      数据类型  +   值
        di = new DataItem698();
        di.setDataType("date_time_s");   //数据类型
        //存储时间
        di.setInputParam(rb.getTimeBegin());
        assObjDataType(di);
        assObjValue(di);
        //采集存储时间结束值
        di.setInputParam(rb.getTimeEnd());  //结束时间
        assObjDataType(di);
        assObjValue(di);
        //时间间隔     数据类型  TI   enum
        di.setDataType("TI");
        assObjDataType(di);   //组装时间间隔数据类型
        di.setDataType("enum");
        assObjDataType(di);   //组装枚举数据类型
        TimeInterval ti = rb.getTi().get(0);
        di.setDataType("unsigned");
        di.setInputParam(ti.getTimeUnit());
        assObjValue(di);  //组装时间间隔单位
        //组装时间间隔值
        di.setDataType("long-unsigned");
        di.setInputParam(ti.getIntervalValue());
        assObjDataType(di);   //组装数据类型
        assObjValue(di);  //组装间隔值
        //电能表集合  MS
        //MS  编号[0---7]0\1   数据个数   数据
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }

    }

    //RSD  方法八   采集成功
    private void methodEight(RecordRsdBean rb) {
        bytes[pos++] = 0x08;     //选择方法8
        DataItem698 di = null;
        //采集成功时间起始值             date_time_s      数据类型  +   值
        di = new DataItem698();
        di.setDataType("date_time_s");   //数据类型
        //成功时间
        di.setInputParam(rb.getTimeBegin());
        assObjDataType(di);
        assObjValue(di);
        //采集成功时间结束值
        di.setInputParam(rb.getTimeEnd());  //结束时间
        assObjDataType(di);
        assObjValue(di);
        //时间间隔      数据类型  TI   enum
        di.setDataType("TI");
        assObjDataType(di);   //组装时间间隔数据类型
        di.setDataType("enum");
        assObjDataType(di);   //组装枚举数据类型
        TimeInterval ti = rb.getTi().get(0);
        di.setDataType("unsigned");
        di.setInputParam(ti.getTimeUnit());
        assObjValue(di);  //组装时间间隔单位
        //组装时间间隔值
        di.setDataType("long-unsigned");
        di.setInputParam(ti.getIntervalValue());
        assObjDataType(di);   //组装数据类型
        assObjValue(di);  //组装间隔值
        //电能表集合  MS
        //MS  编号[0---7]0\1   数据个数   数据
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }
    }

    //RSD  方法九
    private void methodNine(RecordRsdBean rb) {
        bytes[pos++] = 0x09;   //选择方法9
        //上第N次记录
        DataItem698 di = new DataItem698();  //？？？？？数据类型
        di.setDataType("unsigned");
        di.setInputParam(rb.getRecord());   //上N次记录
        assObjDataType(di);//组装数据类型
        assObjValue(di);   //组装上N次记录值
    }

    //RSD  方法十
    private void methodTen(RecordRsdBean rb) {
        bytes[pos++] = 0x10;   //选择方法10
        //上N条记录
        //上第N次记录
        DataItem698 di = new DataItem698();
        di.setDataType("unsigned");
        di.setInputParam(rb.getRecord());   //上N次记录
        assObjDataType(di);//组装数据类型
        assObjValue(di);   //组装上N次记录值
        //电能表集合  MS
        //MS  编号[0---7]0\1   数据个数   数据
        //组装电能表集合数据
        if (Integer.parseInt(rb.getMs()) < 5) {
            optionMs(rb.getMs(), rb.getMsData());    //（ms编号，   MS集合数据）
        } else {
            optionMsRegion(rb.getMs(), rb.getRegion());    //（ms编号，   MS区间集合数据）
        }
    }


    //处理电能表集合 MS

    /**
     * ms   表示电能表集合编号  0-4
     * list  表示集合数据
     */
    private void optionMs(String ms, List<String> list) {

        switch (Integer.parseInt(ms)) {
            case 0:   //无电能表
                bytes[pos++] = 0x00;
                break;
            case 1:  //全部用户地址
                bytes[pos++] = 0x01;
                break;
            case 2:   //一组用户类型   unsigned   8位 一个字节
                bytes[pos++] = 0x02;
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
                //数据类型 +  数据
                DataItem698 di = null;
                for (String value : list) {
                    di = new DataItem698();
                    di.setDataType("unsigned");
                    di.setInputParam(value);
                    assObjDataType(di);//组装数据类型
                    assObjValue(di);   //组装数据
                }
                break;
            case 3:   //一组用户地址   TSA
                bytes[pos++] = 0x03;
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
                //TSA
                byte[] btsa;
                for (String tsa : list) {
                    bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get("TSA"))))[0];   //组装数据类型
                    //整个长度 +地址长度+ 数据
                    //代理服务器地址    TSA
                    btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息长度
                    btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];  //地址数据 +  地址长度
                    btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);  //地址数据长度
                    System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
                    System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
                    pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
                }
                break;
            case 4:   //一组配置序号     long-unsigned   64位  8个字节
                bytes[pos++] = 0x04;
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0]; //pos已经定位到TSA第一个数据长度开始的位置
                //数据类型 +  数据
                DataItem698 dit = null;
                for (String value : list) {
                    dit = new DataItem698();
                    dit.setDataType("long-unsigned");
                    dit.setInputParam(value);
                    assObjDataType(dit);//组装数据类型
                    assObjValue(dit);   //组装数据
                }
                break;
        }

    }

    //处理电能表集合 MS

    /**
     * ms   表示电能表集合编号  5-7
     * list  表示区间集合数据
     */
    private void optionMsRegion(String ms, List<Region> list) {

        switch (Integer.parseInt(ms)) {
            case 5:   //一组用户类型区间   用户类型   unsigned   8位 一个字节
                bytes[pos++] = 0x05;
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
                //组装用户类型区间数据
                //数据类型Region
                for (Region r : list) {
                    assRegion(r, "unsigned");
                }
                break;
            case 6:    //一组用户地址区间   用户地址   TSA
                bytes[pos++] = 0x06;
                //组装用户地址区间数据
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
                //组装用户类型区间数据
                //数据类型Region
                for (Region r : list) {
                    assRegion(r, "TSA");
                }
                break;
            case 7:    //一组配置序号区间     序号     long-unsigned   64位  8个字节
                bytes[pos++] = 0x07;
                //组装配置序号区间数据
                //个数
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];
                //组装用户类型区间数据
                //数据类型Region
                for (Region r : list) {
                    assRegion(r, "long-unsigned");
                }
                break;

        }
    }

    //组装区间数据类型
    private void assRegion(Region r, String type) {
        DataItem698 di = new DataItem698();
        di.setDataType("Region");   //区间类型
        assObjDataType(di);//组装区间数据类型
        //组装枚举数据类型  、 枚举单位数据值
        di.setDataType("enum");
        di.setInputParam(r.getUnit());
        assObjDataType(di);//组装枚举数据类型
        assObjValue(di);   //组装枚举数据值
        if ("TSA".equals(type)) {  //TSA----目标服务器地址
            //整个长度 +地址长度+ 数据
            //代理服务器地址    TSA
            di.setDataType("TSA");
            assObjDataType(di);//组装枚举数据类型
            //起始值
            byte[] btsa;
            btsa = new byte[r.getBeginData().length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息长度
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(r.getBeginData().length() / 2 + 1))[0];  //地址数据 +  地址长度
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(r.getBeginData().length() / 2 - 1))[0]);  //地址数据长度
            System.arraycopy(CommUtils.hex2Binary(r.getBeginData()), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            di.setDataType("TSA");
            assObjDataType(di);//组装枚举数据类型
            //结束值
            btsa = new byte[r.getEndData().length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息长度
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(r.getEndData().length() / 2 + 1))[0];  //地址数据 +  地址长度
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(r.getEndData().length() / 2 - 1))[0]);  //地址数据长度
            System.arraycopy(CommUtils.hex2Binary(r.getEndData()), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            btsa = null;
        } else {
            //起始值
            di.setDataType(type);
            di.setInputParam(r.getBeginData());
            assObjDataType(di);//组装数据类型
            assObjValue(di);   //组装起始数据值
            //结束值
            di.setDataType(type);
            di.setInputParam(r.getEndData());
            assObjDataType(di);//组装数据类型
            assObjValue(di);   //组装结束数据值
        }
        di = null;
    }


    //设置数据

    /***
     * 设置完对象属性后读取对象属性的数据定义
     *  若干个设置后读取对象属性  SEQUENCE OF
     {
     一个设置的对象属性     OAD，
     数据                Data，
     一个读取的对象属性    OAD，
     延时读取时间         unsigned
     }
     */
    private void maker06(Frame698 frame698) {
        List<DataItem698> list = frame698.getDataItem698();
        bytes[pos++] = Constant698.SET_OBJECT_DATA;  //---设置对象属性   06
        if (list.size() == 1 && (frame698.getSetOrOpt() == null || frame698.equals(""))) {   //执行设置一个对象属性
            bytes[pos++] = 0x01;   //设置一个对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
            for (DataItem698 dataItem698 : list) {
                assObjData(dataItem698);  //一个对象属性描述符   OAD
                assObjDataType(dataItem698);   //对象属性数据类型
                if (null == ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent())) {
                    if ("4200".equals(dataItem698.getObjIdent()) || "4016".equals(dataItem698.getObjIdent()) ||
                            "4017".equals(dataItem698.getObjIdent()) || "7010".equals(dataItem698.getObjIdent())) {
                        byte[] param = StruUtils.assMethodStru(dataItem698.getInputParam(), dataItem698.getDataType());
                        System.arraycopy(param, 0, bytes, pos, param.length);
                        pos += param.length;
                    } else {
                        assObjValue(dataItem698);    //拼接数据参数值
                    }
                } else {  //设置复杂对象类型数据
                    byte[] str;    //存储组装的方法参数
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent());
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                    System.arraycopy(str, 0, bytes, pos, str.length);
                    pos += str.length;
                }

            }
        } else if (list.size() > 1 && (frame698.getSetOrOpt() == null || frame698.equals(""))) { //执行设置多个对象属性
            bytes[pos++] = 0x02;   //设置多个个对象属性
            bytes[pos++] = Constant698.CLIENT_ADDR;//PIID
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //设置属性的个数
            for (DataItem698 dataItem698 : list) {
                assObjData(dataItem698);  //一个对象属性描述符   OAD
                assObjDataType(dataItem698);   //对象属性数据类型
                if (null == ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent())) {
                    assObjValue(dataItem698);    //拼接数据参数值
                } else {  //设置复杂对象类型数据
                    byte[] str;    //存储组装的方法参数
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent());
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                    System.arraycopy(str, 0, bytes, pos, str.length);
                    pos += str.length;
                }
            }
        } else if (frame698.getSetOrOpt() != null && !"".equals(frame698.getSetOrOpt())) {                  //设置对象属性后读取属性值
            bytes[pos++] = 0x03;   //设置后读取多个对象属性请求
            bytes[pos++] = Constant698.CLIENT_ADDR;//PIID
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //设置个数
            for (DataItem698 dataItem698 : list) {
                assObjData(dataItem698);  //一个对象属性描述符   OAD
                assObjDataType(dataItem698);   //对象属性数据类型
                if (null == ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent())) {
                    assObjValue(dataItem698);    //拼接数据参数值
                } else {  //设置复杂对象类型数据
                    byte[] str;    //存储组装的方法参数
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent());
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                    System.arraycopy(str, 0, bytes, pos, str.length);
                    pos += str.length;
                }
                assObjData(dataItem698.getDataItem698() == null ? dataItem698 : dataItem698.getDataItem698());    //读取对象属性的 OAD  如果设置的对象中不包含其他的对象，则设置后读取设置对象，如果有其他对象 ，则读取其他对象
                //设置后读取的698数据项对象延时数据在所读取的698数据项中
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(!"".equals(dataItem698.getDelayTime()) && dataItem698.getDelayTime() != null ? dataItem698.getDelayTime() : "00")))[0];  //读取延时时间
            }
        }
        list.clear();
        list = null;
    }


    //操作数据方法

    /**
     * 执行完操作方法后读取对象属性的数据类型定义
     * 若干个操作对象方法后读取对象属性  SEQUENCE OF
     * {
     * 一个设置的对象方法描述符   OMD，
     * 方法参数                   Data，
     * 一个读取的对象属性描述符   OAD，
     * 读取延时                   unsigned
     * }
     */
    private void maker07(Frame698 frame698) {
        List<DataItem698> list = frame698.getDataItem698();
        bytes[pos++] = 0x07;   //操作对象   ----07
        if (list.size() == 1 && ("".equals(frame698.getSetOrOpt()) || frame698.getSetOrOpt() == null)) { //操作一个对象方法请求
            bytes[pos++] = 0x01;   //----操作一个对象方法请求
            bytes[pos++] = Constant698.CLIENT_ADDR;   //----PIID
            for (DataItem698 dataItem698 : list) {
                assObjOptMethod(dataItem698);
            }
        } else if (list.size() > 2 && ("".equals(frame698.getSetOrOpt()) || frame698.getSetOrOpt() == null)) {//操作多个对象方法请求
            bytes[pos++] = 0x02;   //操作若干个对象方法
            bytes[pos++] = Constant698.CLIENT_ADDR;//PIID
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //操作方法的个数
            for (DataItem698 dataItem698 : list) {
                assObjOptMethod(dataItem698);
            }
        } else if (frame698 != null && !"".equals(frame698.getSetOrOpt())) {//操作多个对象方法后读取多个对象属性值
            bytes[pos++] = 0x03;   //操作若干个对象方法
            bytes[pos++] = Constant698.CLIENT_ADDR; //PIID
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //操作对象个数
            for (DataItem698 dataItem698 : list) {
                assObjOptMethod(dataItem698);
                assObjData(dataItem698.getDataItem698()); //读取对象属性的  OAD
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(!"".equals(dataItem698.getDataItem698().getDelayTime()) && dataItem698.getDataItem698().getDelayTime() != null ? dataItem698.getDataItem698().getDelayTime() : "00")))[0]; //读取延时时间
            }
        }
        list.clear();
        list = null;
    }


    //代理操作
    private void maker09(Frame698 frame698) {
        bytes[pos++] = Constant698.PROXY_OBJECT_DATA;   //0x09代理操作
        switch (Integer.parseInt(frame698.getSetOrOpt())) {
            case 1:
                //代理读取若干个服务器的若干个对象属性    1
                proxyReadParam(frame698);
                break;
            case 2:
                //代理读取一个服务器的一个记录型对象属性请求  2
                proxyReadRecord(frame698);
                break;
            case 3:
                //代理设置若干个服务器的若干个对象属性请求  3
                proxySetParam(frame698);
                break;
            case 4:
                //代理设置后读取若干个服务器的若干个对象属性请求  4
                proxySetAndReadParam(frame698);
                break;
            case 5:
                //代理操作若干个服务器的若干个对象方法请求   5
                proxyOptObjMethod(frame698);
                break;
            case 6:
                //代理操作后读取若干个服务器的若干个对象方法和属性请求  6
                proxyOptobjMethodReadParam(frame698);
                break;
            case 7:
                //代理透明转发命令请求   7
                proxyTransForWardComm(frame698);
                break;
        }
    }


    //代理读取若干个服务器的若干个对象属性
    private void proxyReadParam(Frame698 frame698) {
        List<String> list = frame698.getTsa();    //代理地址
        bytes[pos++] = 0x01;
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        //整个代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到代理个数
        //代理个数
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //下标定位到第一个TSA开始的位置
        int i = 0;  //获取要代理对象 和 超时时间的下标
        //代理服务器超时时间
        List<String> timeout = frame698.getOneTimeOut();
        //代理读取的所有对象
        List<List<DataItem698>> proxyObj = frame698.getProxy698();
        //代理读取的某一个目标服务器地址的代理对象
        List<DataItem698> item;
        byte[] btsa;    //暂存TAS
        for (String tsa : list) {    //代理服务器地址
            //代理服务器地址    TSA
            btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息长度
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];  //地址数据整个长度
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);  //描述地址信息
            System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            //代理服务器超时时间
            phares.setInputValue(timeout.get(i));
            System.arraycopy(phares.getBytes(), 0, bytes, pos, 2);
            pos = pos + 2;   //将下标定位到代理个数
            item = proxyObj.get(i);   //获取目标服务器所代理的对象
            //代理读取对象属性个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];   //下标定位到代理对象标识
            //代理对象属性标识（一个或多个）
            for (DataItem698 dataItem : item) {
                assObjData(dataItem);      //对象属性描述符
            }
            i++;
        }
    }

    //代理读取一个服务器的一个记录型对象属性请求
    private void proxyReadRecord(Frame698 frame698) {
        bytes[pos++] = 0x02;
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        //代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到目标服务器地址开始的位置
        //目标服务器地址
        String tsa = frame698.getTsa().get(0);   //获取代理服务器地址
        byte[] btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息
        btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];
        btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);
        System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
        System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
        pos = pos + btsa.length;  //将下标定位到对象属性描述符的位置
        //组装记录型对象属性标识
        assObjData(frame698.getDataItem698().get(0));    //对象属性描述符 OAD
        //记录选择描述符 RSD      选择方法1--10
        selectMethod(frame698.getRsd().get(0));
        //记录列选择描述符 RCSD    rcsd个数   00  对象属性描述符   01 记录型
        optRcsd(frame698.getRcsd().get(0));

    }

    //代理设置若干个服务器的若干个对象属性请求
    private void proxySetParam(Frame698 frame698) {
        List<String> list = frame698.getTsa();    //代理服务器地址
        bytes[pos++] = 0x03;
        bytes[pos++] = Constant698.CLIENT_ADDR;   //PIID
        //整个代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到代理个数
        //代理服个数
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //将下标定位到目标服务器地址
        int i = 0;  //获取要代理对象 和 超时时间的下标
        //代理服务器超时时间
        List<String> timeout = frame698.getOneTimeOut();
        //代理设置的所有对象
        List<List<DataItem698>> proxyObj = frame698.getProxy698();
        List<DataItem698> item;  //某一个目标服务器的代理设置对象
        byte[] btsa;    //暂存TAS
        for (String tsa : list) {    //代理服务器地址
            //代理服务器地址    TSA
            btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);
            System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            //代理服务器超时时间
            phares.setInputValue(timeout.get(i));
            System.arraycopy(phares.getBytes(), 0, bytes, pos, 2);
            pos = pos + 2;   //将下标定位到代理个数
            item = proxyObj.get(i);
            //代理设置对象属性个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];   //下标定位到代理对象标识
            //代理对象属性标识（一个或多个）
            for (DataItem698 dataItem : item) {
                assObjData(dataItem);      //对象属性描述符
                assObjDataType(dataItem);   //对象属性数据类型
                assObjValue(dataItem);    //拼接数据参数值
            }
            i++;
        }
    }


    //代理设置后读取若干个服务器的若干个对象属性请求
    private void proxySetAndReadParam(Frame698 frame698) {
        List<String> list = frame698.getTsa();    //代理服务器个数
        bytes[pos++] = 0x04;
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        //整个代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到代理个数
        //代理若干服务器设置对象属性后读取对象属性个数
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //将下标定位到目标服务器地址
        int i = 0;  //获取要代理对象 和 超时时间的下标
        //代理服务器超时时间
        List<String> timeout = frame698.getOneTimeOut();
        //代理读取的对象
        List<List<DataItem698>> proxyObj = frame698.getProxy698();
        List<DataItem698> item; //代理某一个目标服务器的设置后读取对象
        byte[] btsa;    //暂存TAS
        for (String tsa : list) {
            //代理服务器地址    TSA
            btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);
            System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            //代理服务器超时时间
            phares.setInputValue(timeout.get(i));
            System.arraycopy(phares.getBytes(), 0, bytes, pos, 2);
            pos = pos + 2;   //将下标定位到代理个数
            //一个服务器代理操作的对象方法
            item = proxyObj.get(i);
            //代理设置对象属性个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];   //下标定位到代理对象标识
            //代理对象属性标识（一个或多个）
            for (DataItem698 dataItem : item) {
                assObjData(dataItem);      //对象属性描述符
                assObjDataType(dataItem);   //对象属性数据类型
                assObjValue(dataItem);    //拼接数据参数值
                assObjData(dataItem.getDataItem698()); //读取对象属性的  OAD
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(!"".equals(dataItem.getDataItem698().getDelayTime()) && dataItem.getDataItem698().getDelayTime() != null ? dataItem.getDataItem698().getDelayTime() : "00")))[0]; //读取延时时间
            }
            i++;
        }
    }

    //代理操作若干个服务器的若干个对象方法请求
    private void proxyOptObjMethod(Frame698 frame698) {
        List<String> list = frame698.getTsa();    //目标服务器地址
        bytes[pos++] = 0x05;
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        //整个代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到代理个数
        //代理操作若干服务器对象方法个数
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //将下标定位到目标服务器地址
        int i = 0;  //获取要代理对象 和 超时时间的下标
        //代理某一个服务器超时时间
        List<String> timeout = frame698.getOneTimeOut();
        //代理操作的所有对象
        List<List<DataItem698>> proxyObj = frame698.getProxy698();
        //代理操作某一个服务器的操作对象
        List<DataItem698> item;
        byte[] btsa;    //暂存TAS
        for (String tsa : list) {
            //代理服务器地址    TSA
            btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);
            System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            //代理服务器超时时间
            phares.setInputValue(timeout.get(i));
            System.arraycopy(phares.getBytes(), 0, bytes, pos, 2);
            pos = pos + 2;   //将下标定位到代理个数
            //一个服务器代理操作的对象方法
            item = proxyObj.get(i);
            //代理设置对象属性个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];   //下标定位到代理对象标识
            //代理服务器操作对象
            for (DataItem698 dataitem : item) {
                assObjOptMethod(dataitem);   //代理操作对象  （对象方法描述符、 方法参数）
            }
            i++;
        }
    }

    //代理操作后读取若干个服务器的若干个对象方法和属性请求
    private void proxyOptobjMethodReadParam(Frame698 frame698) {
        List<String> list = frame698.getTsa();    //目标服务器地址
        bytes[pos++] = 0x06;
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        //整个代理请求的超时时间
        IPharesItem698 phares = Constant698.getiPharesItem698("long_unsigned_0");   //数据类型（long-unsigned）
        phares.setInputValue(frame698.getAllTimeOut());
        byte[] allTimeout = phares.getBytes();
        System.arraycopy(allTimeout, 0, bytes, pos, allTimeout.length);
        pos = pos + allTimeout.length;  //pos -----  将下标定位到代理个数
        //代理操作若干服务器对象方法个数
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(list.size()))[0];  //将下标定位到目标服务器地址
        int i = 0;  //获取要代理对象 和 超时时间的下标
        //代理某一个服务器超时时间
        List<String> timeout = frame698.getOneTimeOut();
        //代理操作的所有对象
        List<List<DataItem698>> proxyObj = frame698.getProxy698();
        //代理操作某一个服务器的操作对象
        List<DataItem698> item;
        byte[] btsa;    //暂存TAS
        for (String tsa : list) {
            //代理服务器地址    TSA
            btsa = new byte[tsa.length() / 2 + 1 + 1];  //+1数据长度  +1 地址信息
            btsa[0] = CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 + 1))[0];
            btsa[1] = (byte) (0x0F & CommUtils.hex2Binary(Integer.toHexString(tsa.length() / 2 - 1))[0]);
            System.arraycopy(CommUtils.hex2Binary(tsa), 0, btsa, 2, btsa.length - 2);
            System.arraycopy(btsa, 0, bytes, pos, btsa.length);     // 将TSA数据放入bytes中
            pos = pos + btsa.length;  //将下标定位到一个服务器超时时间
            //代理服务器超时时间
            phares.setInputValue(timeout.get(i));
            System.arraycopy(phares.getBytes(), 0, bytes, pos, 2);
            pos = pos + 2;   //将下标定位到代理个数
            //一个服务器代理操作的对象方法
            item = proxyObj.get(i);
            //代理设置对象属性个数
            bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(item.size()))[0];   //下标定位到代理对象标识
            //代理服务器操作对象
            for (DataItem698 dataitem : item) {
                assObjOptMethod(dataitem);   //代理操作对象  （对象方法描述符、 方法参数）
                assObjData(dataitem.getDataItem698()); //读取对象属性的  OAD
                //读取延时时间
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(!"".equals(dataitem.getDelayTime()) && dataitem.getDelayTime() != null ? dataitem.getDelayTime() : "00")))[0];
            }
            i++;
        }
    }

    // 代理透明转发命令请求
    private void proxyTransForWardComm(Frame698 frame698) {
        bytes[pos++] = 0x07; //代理透明转发命令请求
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID //服务序号-优先级 0A
        //数据转发端口    OAD
        assObjData(frame698.getDataItem698().get(0));
        //端口通信控制块  COMDCB
        assComdcb(frame698.getCdtb());
        DataItem698 di = new DataItem698();
        di.setDataType("long-unsigned");
        //接收等待报文超时时间（秒）  long-unsigned
        assObjDataType(di);
        di.setInputParam(frame698.getWaitMessOutOfTime());
        assObjValue(di);
        //接收等待字节超时时间(毫秒） long-unsigned
        assObjDataType(di);
        di.setInputParam(frame698.getWaitByteOutOfTiem());
        assObjValue(di);
        //透明转发命令  octet-string
        di.setDataType("octet-string");  //------------
        assObjDataType(di);
        di.setInputParam(frame698.getTransForwardCommd());
        assObjValue(di);
    }

    //组装端口通信控制块
    private void assComdcb(ComdcbDataTypeBean cdtb) {
        DataItem698 di = new DataItem698();
        //组装端口通信控制块数据类型
        di.setDataType("COMDCB");
        assObjDataType(di);
        //个数
        di.setDataType("unsigned");
        di.setInputParam("5");
        assObjValue(di);
        di = null;
        //以下五种数据类型均为 枚举
        //波特率
        assParamData(cdtb.getBaudRate());
        //校验位
        assParamData(cdtb.getCheckBite());
        //数据位
        assParamData(cdtb.getDataBits());
        //停止位
        assParamData(cdtb.getStopBit());
        //流控
        assParamData(cdtb.getFlowControl());
    }

    //用于端口通信数据块组装属性数据--波特率、校验位、数据位、停止位、流控
    private void assParamData(String value) {
        DataItem698 di = new DataItem698();
        //组装枚举数据类型
        di.setDataType("enum");
        assObjDataType(di);
        //组装波特率数值
        di.setDataType("unsigned");
        di.setInputParam(value);
        assObjValue(di);
        di = null;
    }

    //组装对象属性描述符【OAD】  -------对象标识、属性标识及其特征、属性内元素索引
    private void assObjData(DataItem698 dataItem698) {
        byte[] objId = CommUtils.hex2Binary(dataItem698.getObjIdent().length() > 4 ? dataItem698.getObjIdent().substring(0, 4) : dataItem698.getObjIdent());
        System.arraycopy(objId, 0, bytes, pos, objId.length);  ////对象标识
        pos = pos + objId.length;
        bytes[pos++] = getParam(!"".equals(dataItem698.getObjFeatures()) && dataItem698.getObjFeatures() != null ? Integer.toHexString(Integer.parseInt(dataItem698.getObjFeatures())) : "00", Integer.toHexString(Integer.parseInt(dataItem698.getMemberCode()))); //特征(5--7) ------属性编号(0---4)
        bytes[pos++] = !"".equals(dataItem698.getElementIndex()) && dataItem698.getElementIndex() != null ? CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dataItem698.getElementIndex())))[0] : 00; //属性内元素索引
    }

    //返回属性标识  及其特征
    private static byte getParam(String fea, String param) {
        byte b = (byte) (((CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(fea)))[0] << 5) & 0xE0) | (CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(param)))[0] & 0x1F));
        return b;
    }


    //设置对象属性时，拼接对象属性数据类型
    private void assObjDataType(DataItem698 dataItem698) {
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType()))))[0];
    }


    //设置对象属性时，拼接对象属性值
    private void assObjValue(DataItem698 dataItem698) {
        IPharesItem698 phares = Constant698.getiPharesItem698(dataItem698.getDataType());
        phares.setInputValue(dataItem698.getInputParam());
        byte[] value = phares.getBytes();
        System.arraycopy(value, 0, bytes, pos, value.length);
        pos = pos + value.length;  //设置下一个对象属性下标（如果设置多个对象属性）或时间标签的下标
    }


    //操作对象方法时，拼接对象参数方法
    private void assObjOptMethod(DataItem698 dataItem698) {
        byte[] objId = CommUtils.hex2Binary(dataItem698.getObjIdent());   //对象标识
        System.arraycopy(objId, 0, bytes, pos, objId.length);
        pos = pos + objId.length;
        // TODO: 2018/5/7
      String str1=  "0"+Integer.toHexString(Integer.parseInt(dataItem698.getMemberCode()));
        bytes[pos++] = CommUtils.hex2Binary(str1)[0]; //对象方法编号
       // bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(dataItem698.getMemberCode())))[1]; //对象方法编号
        bytes[pos++] = 00;  //操作模式默认为0
        if (null == dataItem698.getSetOrOpt() || "".equals(dataItem698.getSetOrOpt())) {  //当dataItem698.getSetOrOpt()不为空 或null 时，说明操作方法不需要参数 只需要 对象方法描述符【OMD】
            byte[] str;    //存储组装的方法参数
            //执行数据类型为structure 的 特殊方法
            if ((null != ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent()))  //&&"127".equals(dataItem698.getMemberCode()
                    || "6000".equals(dataItem698.getObjIdent()) && "128".equals(dataItem698.getMemberCode())
                    || "6000".equals(dataItem698.getObjIdent()) && "129".equals(dataItem698.getMemberCode())
                    || "6000".equals(dataItem698.getObjIdent()) && "130".equals(dataItem698.getMemberCode())
                    || "6018".equals(dataItem698.getObjIdent()) && "128".equals(dataItem698.getMemberCode())) {
                if (null != dataItem698.getDataType() && !"".equals(dataItem698.getDataType())) {
                    bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType()))))[0];
                }
                if ("6000".equals(dataItem698.getObjIdent()) && "128".equals(dataItem698.getMemberCode())) {  //更新配置单元的基本信息对象
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent() + "128");
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                } else if ("6000".equals(dataItem698.getObjIdent()) && "129".equals(dataItem698.getMemberCode())) {  //更新配置单元的基本信息对象
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent() + "129");
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                } else if ("6000".equals(dataItem698.getObjIdent()) && "130".equals(dataItem698.getMemberCode())) { //更新配置单元的扩展信息以及附属信息，对象为NULL表示不更新。
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent() + "130");
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                } else if ("6018".equals(dataItem698.getObjIdent()) && "128".equals(dataItem698.getMemberCode())) { // 透明方案  6018  方法128
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent() + "128");
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                } else {
                    IStructure structure = ConstantStru.STRUCTURE_MAP.get(dataItem698.getObjIdent());
                    structure.setObj(dataItem698.getObj());
                    str = structure.getBytes();
                }
                System.arraycopy(str, 0, bytes, pos, str.length);
                pos += str.length;
            } else {
                //参数数据类型
                IPharesItem698 phares = null;
                if (!dataItem698.getDataType().contains("##")) {
                    if (null == Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType())) { //避免传递过来的数据类型为数字类型
                        dataItem698.setDataType(StruUtils.getTypeData(dataItem698.getDataType()));
                    }
                    bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(Constant698.DATA_TYPE_MAP.get(dataItem698.getDataType()))))[0];
                    if ("NULL" != dataItem698.getDataType()) { //如果数据类型为NULL 只拼接NULL数据类型
                        phares = Constant698.getiPharesItem698(dataItem698.getDataType());
                        if ("structure".equalsIgnoreCase(dataItem698.getDataType()) || "array".equalsIgnoreCase(dataItem698.getDataType())) {
                            //数据类型|数据:数据类型|数据:数据类型|数据：
                            // structure|数据类型*数据#数据类型*数据#数据类型*数据#structure*数据类型&数据^数据类型&数据^数据类型&数据#array*数据类型&数据^数据类型&数据^数据类型&数据
                            //：array|数据类型*数据#数据类型*数据#structure*数据类型&数据^数据类型&数据^数据类型&数据#array*数据类型&数据^数据类型&数据^数据类型&数据...
                            // ":" 分割 表示 structure 或 array  的元素个数
                            // "|" 分割 表示 structure 或 array 中元素的数据类型 和  数值
                            // "#" 分割 表示 structure 或 array 中元素的数据类型 包含多个数值   eg: TI (单位、间隔时间)   TI|0#10   或  CSD|00#OAD#OAD
                            byte[] param = StruUtils.assMethodStru(dataItem698.getInputParam(), dataItem698.getDataType());
                            System.arraycopy(param, 0, bytes, pos, param.length);
                            pos += param.length;
                        } else if ("OAD".equalsIgnoreCase(dataItem698.getDataType())) {    //操作方法时， 操作对象属性
                            if (null != dataItem698.getDataItem698()) {
                                phares.setObj(dataItem698.getDataItem698());
                            } else {
                                dataItem698.setInputParam(dataItem698.getInputParam());
                                assMethValue(dataItem698);
                            }
                        } else {
                            phares.setInputValue(dataItem698.getInputParam());
                            // TODO: 2018/5/7  
                          //  str = phares.getBytes();
                          str=  com.sgcc.pda.hardware.util.DataConvert.toBytes(dataItem698.getInputParam());
                            System.arraycopy(str, 0, bytes, pos, str.length);
                            pos += str.length;
                        }
                    }
                } else {
                    String methodValue = dataItem698.getInputParam() == null || "".equals(dataItem698.getInputParam()) ? "00" : dataItem698.getInputParam();
                    //根据数据类型组装方法参数
                    if (dataItem698.getDataType().contains("##")) { //多个参数、多个值
                        String[] type = dataItem698.getDataType().split("\\##");  //数据类型
                        String[] value = dataItem698.getInputParam().split("\\##");  //值
                        for (int i = 0; i < type.length; i++) {
                            dataItem698.setInputParam(value[i]);
                            dataItem698.setDataType(type[i]);
                            //拼接数据类型
                            byte typeValue = StruUtils.assDataType(type[i]);
                            byte[] midtype = {typeValue};
                            System.arraycopy(midtype, 0, bytes, pos, 1);
                            pos += 1;
                            if ("structure".equalsIgnoreCase(type[i]) || "array".equalsIgnoreCase(type[i])) {
                                byte[] param = StruUtils.assMethodStru(dataItem698.getInputParam(), dataItem698.getDataType());
                                System.arraycopy(param, 0, bytes, pos, param.length);
                                pos += param.length;
                            } else {
                                assMethValue(dataItem698);
                            }
                        }
                    } else {
                        dataItem698.setInputParam(methodValue);
                        assMethValue(dataItem698);
                    }
                }
            }
        }
    }

    //设置对象属性时，拼接对象属性值  或 操作方法时，拼接方法参数
    private void assMethValue(DataItem698 dataItem698) {
        IPharesItem698 phares = Constant698.getiPharesItem698(dataItem698.getDataType());
        phares.setInputValue(dataItem698.getInputParam());
        byte[] value = phares.getBytes();
        System.arraycopy(value, 0, bytes, pos, value.length);
        pos = pos + value.length;  //操作下一个对象方法下标（如果操作多个对象方法）或时间标签的下标
    }


    //拼接报文长度
    private void getFrameLen(int len) {
        byte[] fralen = new byte[2];
        String mlen = "0000".substring(0, 4 - Integer.toHexString(len).length()) + Integer.toHexString(len);
        fralen = CommUtils.hex2BinaryLH(mlen);
        System.arraycopy(fralen, 0, bytes, 1, fralen.length);
    }

    //拼接安全传输报文长度
    private void getsecFrameLen(int len) {
        byte[] fralen = new byte[2];
        String mlen = "0000".substring(0, 4 - Integer.toHexString(len).length()) + Integer.toHexString(len);
        fralen = CommUtils.hex2BinaryLH(mlen);
        System.arraycopy(fralen, 0, secbytes, 1, fralen.length);
    }

    //预连接
    private void maker01(Frame698 frame698) {
        bytes[3] = (byte) 0x81;
        bytes[pos++] = Constant698.LINK_REQUEST;    //预连接标识  01
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        LinkBean lb = frame698.getLinkBean();
        bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(lb.getReqType())))[0];  //请求类型   0  登录  1 心跳   2 退出登录
        DataItem698 di;
        //心跳周期
        di = new DataItem698();
        di.setDataType("long_unsigned");
        di.setInputParam(lb.getHeartCycle());
        assObjValue(di);
        //请求时间
        di = new DataItem698();
        di.setDataType("date_time");
        di.setInputParam(lb.getReqTime());
        assObjValue(di);
    }

    //建立应用连接请求
    private void maker02(Frame698 frame698) {
        bytes[pos++] = Constant698.CONNECT_REQUEST;    //断开应用连接请求标识
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
        DataItem698 di = new DataItem698();
        //获取建立应用连接请求对象
        AppConnReq acr = frame698.getAppConnReq();
        //期望的应用层协议版本号
        di.setDataType("long-unsigned");
        di.setInputParam(acr.getVersion());
        assObjValue(di);
        byte[] temp;
        //协议一致性(64位   8字节)
        temp = CommUtils.hex2Binary(Constant698.PROTOCOL_CONFORMANCE);   //暂这样处理 该属性
        System.arraycopy(temp, 0, bytes, pos, 8);
        pos += 8;
        //功能一致性(128位   16字节)
        temp = CommUtils.hex2Binary(Constant698.FUNCTION_CONFORMANCE);  //暂这样处理 该属性
        System.arraycopy(temp, 0, bytes, pos, 16);
        pos += 16;
        //客户机发送帧最大尺寸
        di.setInputParam(acr.getSendMaxSize());
        assObjValue(di);
        //客户机接收帧最大尺寸
        di.setInputParam(acr.getReviceMaxSize());
        assObjValue(di);
        //客户机接收帧最大窗口尺寸
        di.setDataType("unsigned");
        di.setInputParam(acr.getReviceMaxWinSize());
        assObjValue(di);
        //客户机最大可处理APDU尺寸
        di.setDataType("long-unsigned");
        di.setInputParam(acr.getMaxApduSize());
        assObjValue(di);
        //期望的应用连接超时时间
        di.setDataType("double_long_unsigned");
        di.setInputParam(acr.getLinkOverTime());
        assObjValue(di);
        //认证请求对象
        connMechInfo(acr.getConnMechInfo(), di);
    }

    //处理应用连接请求认证的机制信息ConnectMechanismInfo
    private void connMechInfo(String value, DataItem698 di) {
        switch (Integer.parseInt(value)) {
            case 0:  //公共连接    [0] NullSecurity   -----NULL
                bytes[pos++] = 0x0;
                break;
            case 1: //一般密码    [1] PasswordSecurity   ---------visible-string
                // ？？？？数据怎么来
                break;
            case 2: //对称加密    [2] SymmetrySecurity
                byte[] temp = new byte[1024];
                bytes[pos++] = 0x2;
                bytes[pos++] = CommUtils.hex2Binary(Integer.toHexString(32))[0];
                temp = CommUtils.hex2Binary(Constant698.M1);
                System.arraycopy(temp, 0, bytes, pos, temp.length); //*密文1        octet-string，
                pos = pos + temp.length;
                bytes[pos++] = 0x04;
                temp = CommUtils.hex2Binary(Constant698.SIGE1);
                System.arraycopy(temp, 0, bytes, pos, temp.length);//客户机签名1  octet-string
                pos = pos + temp.length;
                break;
            case 3: //数字签名    [3] SignatureSecurity
                // ？？？？数据怎么来
                  /*密文2        octet-string，
	    		  客户机签名2  octet-string*/
                break;
        }
    }


    //断开应用连接请求
    private void maker03(Frame698 frame698) {
        bytes[pos++] = Constant698.RELEASE_REQUEST;    //断开应用连接请求标识
        bytes[pos++] = Constant698.CLIENT_ADDR;  //PIID
    }


    //拼接控制域    地址域信息
    private void assFrameHead(Frame698 frame698) {
        bytes[0] = 0x68;  //  ---0
        //bytes[Constant698.ContArea] = 0x43;  //01000011//------控制域  --3   01(传输方向,启动标志位,此处01 为客户机发出的请求) 0(不分帧) 0(保留) 0 (绕码 不加33H ) 011 (功能码3 数据交互)
        bytes[Constant698.ContArea] = frame698.getControlCode();
        String meterComIdType = frame698.getMeterComIdType();
        int parseInt = Integer.parseInt(meterComIdType);
        String s = Integer.toHexString(parseInt);
        byte i1 = CommUtils.hex2Binary(s)[0];
        byte i2 = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getLogiAdd())))[0];
        byte type = (byte) (i1 << 6 | i2 << 4);   //地址类型|逻辑地址
//        byte type = (byte) (CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getMeterComIdType())))[0] << 6 | CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getLogiAdd())))[0] << 4);   //地址类型|逻辑地址
        type = (byte) (type & 0xf0);
      int len = frame698.getMeterComId().length() / 2 - 1;   //地址长度
       // int len  = CommUtils.hexStr2BinStr(frame698.getMeterComId()).length()/2-1;

        type = (byte) (type | CommUtils.hex2Binary(Integer.toHexString(len))[0]);
        this.bytes[Constant698.addArea] = type;   //服务器地址  ----4
        byte[] add = CommUtils.hex2BinaryLH(frame698.getMeterComId());
        System.arraycopy(add, 0, this.bytes, 5, add.length);
       // pos = Constant698.addArea +len + 1;  //服务器地址+
        pos = Constant698.addArea +frame698.getMeterComId().length()/2;  //服务器地址+
        pos = pos + 1;  //客户机地址下标  CA
        this.bytes[pos] = Constant698.CLIENT_ADDR;   //客户机地址
        hcs = pos + 1;  //头校验下标
    }

    //拼接控制域    地址域信息
    private void assFrameHeadFz(Frame698 frame698) {
        bytes[0] = 0x68;  //  ---0
        bytes[Constant698.ContArea] = 0x63;  //------控制域  --3
        String meterComIdType = frame698.getMeterComIdType();
        int parseInt = Integer.parseInt(meterComIdType);
        String s = Integer.toHexString(parseInt);
        byte i1 = CommUtils.hex2Binary(s)[0];
        byte i2 = CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getLogiAdd())))[0];
        byte type = (byte) (i1 << 6 | i2 << 4);   //地址类型|逻辑地址
//        byte type = (byte) (CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getMeterComIdType())))[0] << 6 | CommUtils.hex2Binary(Integer.toHexString(Integer.parseInt(frame698.getLogiAdd())))[0] << 4);   //地址类型|逻辑地址
        type = (byte) (type & 0xf0);
        int len = frame698.getMeterComId().length() / 2 - 1;   //地址长度
        // int len  = CommUtils.hexStr2BinStr(frame698.getMeterComId()).length()/2-1;

        type = (byte) (type | CommUtils.hex2Binary(Integer.toHexString(len))[0]);
        this.bytes[Constant698.addArea] = type;   //服务器地址  ----4
        byte[] add = CommUtils.hex2BinaryLH(frame698.getMeterComId());
        System.arraycopy(add, 0, this.bytes, 5, add.length);
        // pos = Constant698.addArea +len + 1;  //服务器地址+
        pos = Constant698.addArea +frame698.getMeterComId().length()/2;  //服务器地址+
        pos = pos + 1;  //客户机地址下标  CA
        this.bytes[pos] = Constant698.CLIENT_ADDR;   //客户机地址
        hcs = pos + 1;  //头校验下标
    }
    //读取    设置       操作   上报     代理
    public static void main(String[] args) {
        //公共信息
        Make698Frame aci = new Make698Frame();
        List<DataItem698> list = new ArrayList<DataItem698>();    //存放DataItem698对象
        Frame698 frame698 = new Frame698();
        //frame698.setObjclassifi((byte)1);  //预连接
        //frame698.setObjclassifi((byte)2);  //建立应用连接
        //  frame698.setObjclassifi((byte) 5);   //对象分类    读取
        frame698.setObjclassifi((byte) 6);   //对象分类    设置
        // frame698.setObjclassifi((byte) 7);   //对象分类    操作
        frame698.setMeterComId("888888888888");  //地址
        frame698.setMeterComIdType("00");  //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
        frame698.setLogiAdd("00");  //逻辑地址


        //   读取一个或多个对象属性
	    	/* DataItem698   di =new   DataItem698();
	    	 di.setObjIdent("1010");    //对象标识
	    	 di.setMemberCode("2");     //成员编号【属性编号】

	    	 DataItem698   di1 =new   DataItem698();
	    	 di1.setObjIdent("2001");
	    	 di1.setMemberCode("2");
	    	 list.add(di);
	    	 list.add(di1);*/

        //明文+MAC  方式 读取一个对象属性
			    	 /*frame698.setIsSecurity(true);
			    	 Security   sec  =  new  Security();
			    	 sec.setType("0");  //读取方式  0 明文  1 密文
			    	 sec.setSecType("0");    //数据验证方式 0：数据验证码【SID_MAC】、  1：随机数【RN】、  2：随机数+数据MAC【RN_MAC】、3：安全标识【SID】
			    	 sec.setSecSid("85010203"); //标识
			    	 sec.setData("123456789012");  //附加数据
			    	 sec.setMac("12345678");//MAC数据

			    	 DataItem698   di =new   DataItem698();
			    	 di.setObjIdent("1010");    //对象标识
			    	 di.setMemberCode("2");     //成员编号【属性编号】
			    	 list.add(di);
			    	 frame698.setSecurityObj(sec);*/


        //设置数据
        //设置一个或多个对象属性
           /* DataItem698   di =new   DataItem698();
	    	 di.setObjIdent("2000");
	    	 di.setMemberCode("2");
	    	 di.setDataType("octet-string");
	    	 di.setInputParam("000000000001");

	    	 DataItem698   di1 =new   DataItem698();
	    	 di1.setObjIdent("2001");
	    	 di1.setMemberCode("2");
	    	 di1.setDataType("date_time_s");
	    	 di1.setInputParam("20160120162711");
	    	 list.add(di);
	    	 list.add(di1);*/
        // 设置 数据类型  为   structured  的对象
			   /*    3000   -----    电能表失压事件
			       属性5（配置参数）∷=structure
			       {
			         电压触发上限  long-unsigned（单位：V，换算：-1），
			         电压恢复下限  long-unsigned（单位：V，换算：-1），
			         电流触发下限  double-long（单位：A，换算：-4），
			         判定延时时间  unsigned（单位：s，换算：0）
			       }*/
			       /*  DataItem698   di1 =new   DataItem698();
			    	 di1.setObjIdent("3000");
			    	 di1.setMemberCode("2");
			    	 di1.setDataType("structure");
			    	 di1.setInputParam("long-unsigned*0020#long-unsigned*0010#double-long*00000001#unsigned*2");
			         list.add(di1);*/

        //   4004	----	设备地理位置
			      /* 属性2∷=structure
			       {
					       经度  structure
					       {
					                   方位   enum{E（0），W（1）}，
					                    度     unsigned，
					                    分     unsigned，
					                   秒     unsigned
					       }，
					       纬度  structure
					       {
							       方位   enum{S（0），N（1）}，
							       度     unsigned，
							       分     unsigned，
							       秒     unsigned
					       }，
				                     高度（cm）  double-long-unsigned
			       }*/
        DataItem698 di1 = new DataItem698();
        di1.setObjIdent("4004");
        di1.setMemberCode("2");
        di1.setDataType("structure");
        di1.setInputParam("structure*enum&0^unsigned&120^unsigned&100^unsigned&90#structure*enum&0^unsigned&120^unsigned&100^unsigned&90#double-long-unsigned*111");
        list.add(di1);

        //设置后读取对象属性
	    	 /*DataItem698  di2 =new DataItem698();
	    	 di2.setObjIdent("4500");
	    	 di2.setMemberCode("2");
	    	 di2.setDataType("long-unsigned");
	    	 di2.setInputParam("104");
	    	 di2.setDelayTime("3");
	    	 di2.setElementIndex("12");
	    	 list.add(di2);
	    	 frame698.setSetOrOpt("1");*/


        //操作对象方法
	    	/* DataItem698   di =new   DataItem698();
	    	 di.setObjIdent("0010");   //对象id
	    	 di.setMemberCode("1");  //方法编号
	    	 di.setDataType("integer");  //参数类型
	    	 di.setInputParam("00");    //参数值
	    	 list.add(di);*/


        //操作完方法后读取对象属性
	    	 /*DataItem698   di =new   DataItem698();
	    	 di.setObjIdent("0010");   //对象id
	    	 di.setMemberCode("1");  //方法编号
	    	 di.setDataType("integer");  //参数类型
	    	 di.setInputParam("00");    //参数值

	    	 DataItem698   di1 =new   DataItem698();
	    	 di1.setObjIdent("2001");
	    	 di1.setMemberCode("2");;  //操作完方法后读取对象属性
	    	 di.setDataItem698(di1);
	    	 list.add(di);
	    	 frame698.setSetOrOpt("1");*/

        //操作执行冻结数据接口类  方法4  添加一个冻结对象属性
			   /* DataItem698   method = new DataItem698();
			    method.setMemberCode("4");
			    method.setDataType("structure");
			    method.setInputParam("long-unsigned|2:OAD|00210200:long-unsigned|6");
			    method.setObjIdent("5000");
			    list.add(method);   */

        //预连接
			 /*    LinkBean  lb = new  LinkBean();
			     lb.setReqType("01");  //  请求类型  0 登录   1 心跳   2 退出登录
			     lb.setHeartCycle("180");  //心跳周期
			     lb.setReqTime("2016-05-19 04 08:05:00:0164");  //请求时间
			     frame698.setLinkBean(lb);  */

        //建立应用连接
			    	/*AppConnReq   acr  =  new  AppConnReq();
			    	 acr.setVersion("16");
			    	 acr.setSendMaxSize("1024");
			    	 acr.setReviceMaxSize("1024");
			    	 acr.setReviceMaxWinSize("1");
			    	 acr.setMaxApduSize("1024");
			    	 acr.setLinkOverTime("100");
			    	 acr.setConnMechInfo("0");
			    	 frame698.setAppConnReq(acr);*/

        /**
         * 读取记录型对象组装的Frame698数据
         *
         *    frame698.getDataItem698();   //对象属性描述符
         *    frame698.getRsd();           记录选择描述符-------包括选择执行的方法、 和方法所需要的数据
         *    frame698.getRcsd();          记录列选择描述符--------包括所选择列的对象
         *
         */
        //读取记录型对象------------OAD
	    	 /*DataItem698   dim  =new  DataItem698();
	    	 dim.setObjIdent("5004");  //对象标识
	    	 dim.setMemberCode("2");  //属性编号
	    	 dim.setElementIndex("0"); //元素索引         //--------------组装记录型对象数据

	    	 //组装记录选择描述符对象数据-----------RSD

	    	 RecordRsdBean   rrn =new RecordRsdBean();  //记录选择描述符 RSD
	    	 rrn.setMethod("1");   //执行方法     [方法一：需要数据 对象属性描述符, DATA]         //---------记录选择描述符对象

	    	 DataItem698   dim1  =new  DataItem698();
	    	 dim1.setObjIdent("2021");
	    	 dim1.setMemberCode("2");  //属性编号
	    	 dim1.setElementIndex("0"); //元素索引
	    	 dim1.setDataType("date_time_s");  //数据类型                //-----------OAD数据

	    	 List<DataItem698>    l698= new ArrayList<DataItem698>();
	    	 l698.add(dim1);
	    	 rrn.setListItem(l698);

	    	 List<String>    l =new  ArrayList<String>();
	    	 l.add("20160120000000");
	    	 rrn.setData(l);

	    	 //----------组装记录列对象数据信息 ----------------RCSD
	    	 RecordRcsdBean    rrbn=new RecordRcsdBean();  //定义一个记录列对象
	    	  // 00 表示一般对象   01 表示记录型对象     Map 中   key  表示列对象属于什么类型   value（list）表示 对象属性数据
	    	 Map<String, List<DataItem698>>   m1=new HashMap<String, List<DataItem698>>();
	    	 List<DataItem698>    l1=new ArrayList<DataItem698>();
	    	 DataItem698   d1=new DataItem698();
	    	 d1.setObjIdent("2021");
	    	 d1.setMemberCode("2");
	    	 d1.setElementIndex("0");
	    	 l1.add(d1);                                //------------定义一个列对象
	    	 m1.put("00", l1);

	    	 Map<String, List<DataItem698>>   m2=new HashMap<String, List<DataItem698>>();
	    	 List<DataItem698>    l2=new ArrayList<DataItem698>();
	    	 DataItem698   d2=new DataItem698();
	    	 d2.setObjIdent("0010");
	    	 d2.setMemberCode("2");
	    	 d2.setElementIndex("0");                     //-----------定义一个列对象
	    	 l2.add(d2);
	    	 m2.put("00", l2);

	    	 List<Map<String,List<DataItem698>>>   lt= new ArrayList<Map<String,List<DataItem698>>>();
	    	 lt.add(m1);
	    	 lt.add(m2);

	    	 rrbn.setRecordRcse(lt);                    //------组装记录列对象数据

	    	 List<RecordRsdBean>   lrrn =new  ArrayList<RecordRsdBean>();
	    	 List<RecordRcsdBean>  lrrbn  = new  ArrayList<RecordRcsdBean>();
	    	 lrrn.add(rrn);
	    	 lrrbn.add(rrbn);
	    	 frame698.setRsd(lrrn);     //读取记录型对象
	    	 frame698.setRcsd(lrrbn);
	         list.add(dim);      //读取记录型对象
	    	 frame698.setSetOrOpt("1");*/

	     /*  //安全传输
	 		Make698Frame make698 = new Make698Frame();

	 		Security  secObj = new Security();
	 		 secObj.setType("1");
	 		 secObj.setSecType("0");
	 		 secObj.setTransferData("AADCEC2CD6CB3A6378768CCB798B1FBA660E6A181336E1D9784AAAB7F6287433");
	 		Frame698 frame698 = new Frame698();
	  		frame698.setObjclassifi((byte)16);     //对象分类   安全传输
	  		frame698.setMeterComId("FFFFFFFFFFFF"); //地址
	  		frame698.setMeterComIdType("00");       //地址类型  (0表示单地址，1表示通配地址，2表示组地址，3表示广播地址)
	  		frame698.setLogiAdd("00"); 				//逻辑地址
	  		frame698.setSecurityObj(secObj);
	  		Constant698.SID_IDENT = "811C8310";
	  		Constant698.ATTACH_DATA ="0024";
	  		Constant698.MAC ="61AF5B6D";
	     	make698.maker698(frame698);*/

        frame698.setDataItem698(list);
        aci.maker698(frame698);

    }

}
