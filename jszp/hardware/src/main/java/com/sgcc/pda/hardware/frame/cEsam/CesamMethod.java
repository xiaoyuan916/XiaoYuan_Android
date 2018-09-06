package com.sgcc.pda.hardware.frame.cEsam;

import android.util.Log;

import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.sdk.utils.LogUtil;


public class CesamMethod {

    private static final int BUFFER_LENGTH = 1024;



     /* 获取resam 对称密钥版本
     *
     * @return
     */
    public static String getCesamSeckeyVer() {
        byte[] byteFrame = new byte[]{(byte) 0x90, (byte) 0x46, (byte) 0x00, (byte) 0x04,
                (byte) 0x00, (byte) 0x00};
        String upValue = resamOption(byteFrame);
        return upValue;
    }


    /**
     * 向resam发送命令 并解析响应帧
     *
     * @return
     */
    private static String resamOption(byte[] data) {
        // 发送数据
        int send = Shell.RESAM_sendData(data, 0, data.length);
      LogUtil.e("tag","send==="+send);
        //接收数据
        StringBuffer downFrame = new StringBuffer();
        // 清空缓冲区，防止外来数据的干扰
        downFrame.delete(0, downFrame.length());
        //清除读取缓存
        Shell.RESAM_clearRevCache();

        Shell.RESAM_setTimeOut(1,20000);
        //开始读取
        byte[] buffer = new byte[BUFFER_LENGTH];
        int offset = 0;
        byte buf[];
        int len;
        do {
            len = Shell.RESAM_recvData(buffer, offset,1058);
            if (len > 0) {
                buf = new byte[len];
                System.arraycopy(buffer, 0, buf, 0, len);
                offset += len;
                downFrame.append(Convert.toHexString(buf));
            } else {
                buf = null;
                Log.e("tag", "-----read--error--");
            }

        } while (buf != null && buf.length >= BUFFER_LENGTH);
        Log.e("tag", "--------downFrame----" + downFrame);

        //900000085602000000000027
//        Parser parser = new Parser();
//        return parser.parser(DataConvert.toBytes(downFrame.toString())).getUpValue();
        return downFrame.toString();
    }
}
