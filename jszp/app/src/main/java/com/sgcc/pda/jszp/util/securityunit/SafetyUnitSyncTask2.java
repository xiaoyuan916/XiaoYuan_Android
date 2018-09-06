package com.sgcc.pda.jszp.util.securityunit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;


import com.sgcc.pda.hardware.model.founation.safetyunit.ISafetyUnit2;
import com.sgcc.pda.hardware.shell.Shell;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.sdk.utils.LogUtil;
import com.sgcc.pda.sdk.utils.SharepreferenceUtil;
import com.sgcc.pda.sdk.utils.ToastUtils;


/**
 * Created by TL on 2017/10/10.
 * 启动界面安全单元 获取安全单元信息异步任务
 */
public class SafetyUnitSyncTask2 extends AsyncTask<String, String, SafeUnit2Info> {

    private static final String CESAM_VERSION = "00000000000000000000000000000000";//CESAM_VERSION公钥
    private static final String YESAM_VERSION = "00000000000000000000000000000000";//YESAM_VERSION公钥
    private static final String YESAM_VERSION1 = "00000000200000000000000000000000";//YESAM_VERSION公钥
    //安全单元状态字
    private StringBuffer status = new StringBuffer();
    //软件版本号
    private StringBuffer softVersion = new StringBuffer();
    //硬件版本号
    private StringBuffer hardVersion = new StringBuffer();
    //C-ESAM序列号
    private StringBuffer cEsamNum = new StringBuffer();
    //操作者代码
    private StringBuffer operator = new StringBuffer();
    //权限
    private StringBuffer operatorPower = new StringBuffer();
    //权限掩码
    private StringBuffer cover = new StringBuffer();
    //操作者信息
    private StringBuffer operatorInfo = new StringBuffer();
    //Y-ESAM序列号
    private StringBuffer yEsamNum = new StringBuffer();
    //Y-ESAM对称密钥密钥版本
    private StringBuffer yEsamVersion = new StringBuffer();
    //主站证书版本号
    private StringBuffer mainStationCertificateVersion = new StringBuffer();
    //终端证书版本号
    private StringBuffer terminalCertificateVersion = new StringBuffer();
    //主站证书序列号
    private StringBuffer mainStationCertificateNum = new StringBuffer();
    //终端证书序列号
    private StringBuffer terminalCertificateNum = new StringBuffer();
    //当前计数器
    private StringBuffer counter = new StringBuffer();
    //转加密剩余次数
    private StringBuffer encryptNum = new StringBuffer();
    //标签密钥版本
    private StringBuffer labelSecretVersion = new StringBuffer();
    //主站证书
    private StringBuffer mainStationCertificate = new StringBuffer();
    //终端证书
    private StringBuffer terminalCertificate = new StringBuffer();

    private ISafetyUnit2 safetyUnit2;

    public SafetyUnitSyncTask2(ISafetyUnit2 safetyUnit2) {
        this.safetyUnit2 = safetyUnit2;
    }

    public SafetyUnitSyncTask2(ISafetyUnit2 safetyUnit2, OnGetSafeUnitInfoListener listener) {
        this.safetyUnit2 = safetyUnit2;
        this.listener = listener;
    }

    @Override
    protected SafeUnit2Info doInBackground(String... params) {
        SafeUnit2Info info = new SafeUnit2Info();
        // 执行后台操作
        publishProgress("打开安全单元...");
        int ret = 0;
        for (int i = 0; i < 3; i++) {
            ret = Shell.SecurityUnit_init(); //模拟安全单元上电
            if (ret == 0) {
                LogUtil.e("TL", " safetyUnit.sync()  打开安全单元成功");
                break;
            }
        }

        if (ret != 0) {
            if (ret == -1) {
                info.setErrorStatus(-1, "安全单元打开失败，请确认SO库是否存在");

            }
            info.setErrorStatus(-1, "安全单元打开失败,请确认SO库是否异常,错误码："+ret);
        }
        if (info.getStatusCode() != 0) return info;
        publishProgress("获取安全单元信息...");
        // 安全单元初始化
        LogUtil.e("TL", " safetyUnit.init()  获取安全单元信息...");
        ret = safetyUnit2.getSafeUnitMessage(status, softVersion, hardVersion, cEsamNum, operator, operatorPower, cover,
                operatorInfo, yEsamNum, yEsamVersion, mainStationCertificateVersion, terminalCertificateVersion,
                mainStationCertificateNum, terminalCertificateNum, counter, encryptNum, labelSecretVersion,
                mainStationCertificate, terminalCertificate);
        if (ret != 0) {
            info.setErrorStatus(-2, "获取安全单元信息失败，错误码："+ret);

        } else {

            //保存登录所需信息
            info.setOperator(operator.toString().trim());//操作者代码
            info.setOperatorInfo(operatorInfo.toString().trim());//操作者信息
            info.setSoftVersion(softVersion.toString().trim());//安全单元软件版本号
            info.setHardVersion(hardVersion.toString().trim());//安全单元硬件版本号
            info.setSystemVersion(Build.VERSION.SDK_INT + "");//操作系统版本号
            info.setYEsamVersion(yEsamVersion.toString().trim());//Yesam对称秘钥版本
            //    SharepreferenceUtil.setCounter( counter.toString().trim());//会话计数器
            info.setMainStationCertificate(mainStationCertificate.toString().trim());//主站证书
            info.setTerminalCertificate(terminalCertificate.toString().trim());//终端证书
            info.setMainStationCertificateVersion(mainStationCertificateVersion.toString().trim());//主站证书版本号
            info.setMainStationCertificateNum(mainStationCertificateNum.toString().trim());//主站证书序列号
            info.setTerminalCertificateVersion(terminalCertificateVersion.toString().trim());//终端证书版本号
            info.setTerminalCertificateNum(terminalCertificateNum.toString().trim());//终端证书序列号
            info.setUserName(operatorInfo.substring(0, 10).toString().trim());
            info.setCEsamNum(cEsamNum.toString());
            info.setYEsamNum(yEsamNum.toString());
            LogUtil.d("TL", "安全单元软件版本号" + softVersion);
            LogUtil.d("TL", "安全单元硬件版本号" + hardVersion);
            LogUtil.d("TL", "操作系统版本号" + Build.VERSION.SDK_INT);
            LogUtil.d("TL", "Yesam对称秘钥版本" + yEsamVersion);
            LogUtil.d("TL", "Yesam序列号" + yEsamNum);
            LogUtil.d("TL", "会话计数器" + counter.toString().trim());
            LogUtil.d("TL", "主站证书" + mainStationCertificate);
            LogUtil.d("TL", "终端证书" + terminalCertificate);
            LogUtil.d("TL", "主站证书版本号" + mainStationCertificateVersion);
            LogUtil.d("TL", "主站证书序列号" + mainStationCertificateNum);
            LogUtil.d("TL", "终端证书版本号" + terminalCertificateVersion);
            LogUtil.d("TL", "终端证书序列号" + terminalCertificateNum);
            LogUtil.d("TL", "操作者代码" + operator);
            LogUtil.e("TL", " safetyUnit.init()  获取安全单元信息成功");
        }
        if (info.getStatusCode() != 0) return info;
        LogUtil.e("TL", "  获取CESAM信息");
        publishProgress("获取CESAM信息");
        getCEsamVersion(info);
        if (info.getStatusCode() != 0) return info;
        //获取Yesam计数器
        publishProgress("获取Yesam计数器");
        getYesamCounter(info);
        if (info.getStatusCode() != 0) return info;
        //校验密码
        publishProgress("校验操作员密码");
        checkPassWord(info);
        if (info.getStatusCode() != 0) return info;
        info.setErrorStatus(0, "获取安全单元信息成功");
        return info;
    }

    private void checkPassWord(SafeUnit2Info info) {
        if (isNeedCheckPassWord(info.getCEsamVersion(), info.getYEsamVersion())) {
            // 校验密码
            StringBuffer remainCount = new StringBuffer();
            int ret = safetyUnit2.checkUserPassword("111111", remainCount);
            if (ret != 0) {
                int leftTime = String2Integer(remainCount);
                if (-1 == leftTime) {
                    info.setErrorStatus(-4, "获取校验操作员密码时，剩余次数解析失败");
                }
                info.setRemainCount(leftTime);
                info.setErrorStatus(-4, "校验操作员密码失败，错误码： "+ret);
            }
        }
    }

    private void getCEsamVersion(SafeUnit2Info info) {
        int ret;
        byte[] byteFrame = new byte[]{(byte) 0x80, (byte) 0x36, (byte) 0x00, (byte) 0x04,
                (byte) 0x00, (byte) 0x00};
        String data = DataConvert.byte2HexStr(byteFrame);
        StringBuffer esamType = new StringBuffer();
        StringBuffer rtnData = new StringBuffer();
        ret = safetyUnit2.transmitEsamOrder("01", data, esamType, rtnData);
        if (ret != 0 || rtnData.length() == 0) {
            info.setErrorStatus(-3, "获取Cesam对称秘钥版本失败，错误码："+ret);
        } else {
            String cEsamVersion = rtnData.substring(8, rtnData.length());
            if (cEsamVersion.length() == 32) {
                info.setCEsamVersion(cEsamVersion); //保存C-esam对称秘钥版本
            } else {
                info.setErrorStatus(-3, "获取Cesam对称秘钥版本错误，错误码："+ret);
            }
        }
    }

    private void getYesamCounter(SafeUnit2Info info) {
        byte[] byteFrame = new byte[]{(byte) 0x80, (byte) 0x36, (byte) 0x00, (byte) 0x08,
                (byte) 0x00, (byte) 0x00};
        String data = DataConvert.byte2HexStr(byteFrame);
        StringBuffer esamType = new StringBuffer();
        StringBuffer rtnData = new StringBuffer();
        int ret = safetyUnit2.transmitEsamOrder("02", data, esamType, rtnData);
        String counter = rtnData.substring(8, rtnData.length());
        if (ret != 0 || counter.length() != 24) {
            info.setErrorStatus(-5, "获取Yesam计数器失败");
        }
        info.setYEsamcounter(counter.substring(0, 8));

    }

    private int String2Integer(StringBuffer remainCount) {
        try {
            return Integer.valueOf(remainCount.toString());
        } catch (Exception e) {
            return -1;
        }
    }


    /**
     * 若是公钥类型，则不用校验安全单元密码
     *
     * @param cEsamVersion
     * @param yEsamVersion
     * @return true 不是公钥，需要校验密码
     * false 是公钥，不需要校验密码
     */
    private boolean isNeedCheckPassWord(String cEsamVersion, String yEsamVersion) {
        return !isPublicKeyType(cEsamVersion, yEsamVersion);
    }

    /**
     * 是不是公钥类型
     *
     * @param cEsamVersion
     * @param yEsamVersion
     * @return
     */
    private boolean isPublicKeyType(String cEsamVersion, String yEsamVersion) {
        return cEsamVersion.equalsIgnoreCase(CESAM_VERSION)
                || yEsamVersion.equalsIgnoreCase(YESAM_VERSION)
                || yEsamVersion.equalsIgnoreCase(YESAM_VERSION1);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if (null != listener) {
            listener.onGetSafeUnitInfo(values);
        }
    }

    @Override
    protected void onPostExecute(SafeUnit2Info result) {
        if (null != listener) {
            listener.afterGetSafeUnitInfo(result);
        }

    }

    public void setListener(OnGetSafeUnitInfoListener listener) {
        this.listener = listener;
    }

    private OnGetSafeUnitInfoListener listener;

    public interface OnGetSafeUnitInfoListener {
        void afterGetSafeUnitInfo(SafeUnit2Info success);
        void onGetSafeUnitInfo(String...progress);
    }
}
