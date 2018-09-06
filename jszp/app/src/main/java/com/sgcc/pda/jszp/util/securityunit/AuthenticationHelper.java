package com.sgcc.pda.jszp.util.securityunit;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.lzy.okgo.model.HttpParams;
import com.sgcc.pda.hardware.hardware.A1BSafetyUnitModule;
import com.sgcc.pda.hardware.model.founation.safetyunit.CommonSafetyUnit2;
import com.sgcc.pda.hardware.model.founation.safetyunit.ISafetyUnit2;
import com.sgcc.pda.hardware.protocol.safetyunit.CommonSafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.ISafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitFrame;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitProtocol;
import com.sgcc.pda.hardware.util.Convert;
import com.sgcc.pda.jszp.bean.BaseEntity;
import com.sgcc.pda.jszp.bean.LoginIdauth2;
import com.sgcc.pda.jszp.bean.LoginUid2;
import com.sgcc.pda.jszp.http.JSZPOkgoHttpUtils;
import com.sgcc.pda.jszp.util.DeviceUtil;
import com.sgcc.pda.sdk.utils.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import static com.sgcc.pda.jszp.util.JzspConstants.IP_AUTH;
import static com.sgcc.pda.jszp.util.JzspConstants.PORT_AUTH;

/**
 * Created by qinling on 2018/8/15 16:53
 * Description: 安全单元2.0 认证工具
 */

@SuppressWarnings("needPermission: READ_PHONE_STATE)")
public class AuthenticationHelper {

    private static class SingletonHolder {
        private static final AuthenticationHelper mInstance = new AuthenticationHelper();
    }

    public static AuthenticationHelper getInstance() {
        return SingletonHolder.mInstance;
    }

    /**
     * 安全单元2.0
     */
    private ISafetyUnit2 safetyUnit2;
    private StatusBean status;


    private AuthenticationHelper() {
        ISafetyUnitEssentialMethod<SafetyUnitFrame> safetyMethod =
                new CommonSafetyUnitEssentialMethod(new SafetyUnitProtocol(), new A1BSafetyUnitModule());
        safetyUnit2 = new CommonSafetyUnit2(safetyMethod);
        status = new StatusBean();
    }

    public interface OnCertificationListener {
        // UID,           userId 操作者代码
        void onSuccess(String UID, String userId);

        void onError(StatusBean statusBean);
        void onProgress(String... progress);
    }

    /**
     * 认证开始
     */

    public void certificationStart(final Context mContext, final OnCertificationListener listener) {

        // 获取安全单元信息
        new SafetyUnitSyncTask2(safetyUnit2, new SafetyUnitSyncTask2.OnGetSafeUnitInfoListener() {
            @Override
            public void afterGetSafeUnitInfo(SafeUnit2Info result) {
                if (result != null && result.getStatusCode() == 0) {
                    // 获取 uid
                    loginUid(mContext, result, listener);
                } else {
                    if (null != listener) {
                        listener.onError(result);
                    }
                }
            }

            @Override
            public void onGetSafeUnitInfo(String... progress) {
                if (null != listener) {
                    listener.onProgress(progress);
                }
            }
        }).execute();
    }

    /**
     * 身份认证
     */
    private void loginIdauth(Context mContext, final SafeUnit2Info info, final LoginUid2 loginUid, final OnCertificationListener listener) {
        if (null != listener) {
            listener.onProgress("开始身份认证");
        }
        Pair<String, String> m2_s2 = doLoginIdauth(loginUid.getM1(), loginUid.getS1(), listener);

        String url = getUrl() + "loginIdauth.do";
        HttpParams params = new HttpParams();
        params.put("UID", loginUid.getUID());
        params.put("M2", m2_s2.first);//密文M2
        params.put("S2", m2_s2.second);// 签名S2
        params.put("APP_TYPE", "03");   //      APP业务类型  00：采集运维闭环管理 01：采集运维闭环管理（大屏） 03：统一版采集运维闭环管理
        params.put("ESAM_SDV", info.getSoftVersion());//安全单元软件版本号
        params.put("ESAM_HDV", info.getHardVersion());//安全单元硬件版本号
        params.put("SYS_V", info.getSystemVersion()); //操作系统版本号
        params.put("DV", DeviceUtil.getVersion(mContext)); //终端序列号
        params.put("KV", "00.00.00.00"); //知识库版本号
        params.put("MV", "00.00.00.00"); //地图版本号
        params.put("LV", "00.00.00.00");  //语言字典版本号
        params.put("RAND_NO", "");
        params.put("MAC", "");
        JSZPOkgoHttpUtils.postParams(url, mContext, params, new JSZPOkgoHttpUtils.JSZPCallcak<LoginIdauth2>() {

            @Override
            public void onError(Throwable e) {
                status.setErrorStatus(-8, "身份认证失败：" + e.getMessage());
                if (null != listener) {
                    listener.onError(status);
                }
            }

            @Override
            public void onSuccess(LoginIdauth2 loginIdauth, String json) {
                if (!TextUtils.isEmpty(json)) {
                    if (loginIdauth != null) {
                        if ("1".equals(loginIdauth.getRT_F())) {
                            LogUtil.e("TL", "身份认证成功");
                            status.setErrorStatus(0, "身份认证成功!");
                            //todo 需要保存 UID，可在此保存

                            if (null != listener) {
                                listener.onSuccess(loginUid.getUID(), info.getOperator());
                            }
                        }
                    } else {
                        status.setErrorStatus(-8, "身份认证返回JSON解析失败：");
                        if (null != listener) {
                            listener.onError(status);
                        }
                    }
                } else {
                    status.setErrorStatus(-8, "身份认证时，服务器无返回数据");
                    if (null != listener) {
                        listener.onError(status);
                    }
                }
            }
        });
    }

    /**
     * 组建URL
     * @return
     */
    private String getUrl() {
        //http://10.230.24.185:6002/eomfront/
        return String.format(Locale.CHINA, "http://%s:%s/eomfront/", IP_AUTH, PORT_AUTH);
    }


    /**
     * 获取UID
     */
    private void loginUid(final Context mContext, final SafeUnit2Info info, final OnCertificationListener listener) {
        if (null != listener) {
            listener.onProgress("获取UID");
        }
        String url = getUrl()+"loginUid.do"; // 没有用方法名去组建url。

        HttpParams params = new HttpParams();
        params.put("OPTCARDNO", info.getCEsamNum()); //操作员ESAM序列号
        params.put("BUSICARDNO", info.getYEsamNum());//业务ESAM序列号
        params.put("DEV_SN", DeviceUtil.getDeviceIMEI(mContext));//现场服务终端序列号
        params.put("SIMCARD_SN", DeviceUtil.getSimSerialNumber(mContext));//SIM卡号
        params.put("USER_CODE", "");//登录人账号
        params.put("YESAM_VER", info.getYEsamVersion());//业务ESAM密钥版本
        params.put("CESAM_VER", info.getCEsamVersion());//操作ESAM密钥版本
        params.put("YASCTR", info.getYEsamcounter());//会话计数器   掌机发送需加1
        params.put("MASCERT_VER", info.getMainStationCertificateVersion());//主站证书版本号
        params.put("TMNL_VER", info.getTerminalCertificateVersion());//终端证书版本号
        params.put("TMNL_SN", info.getTerminalCertificateNum());//终端证书序列号
        params.put("MASCERT_SN", info.getMainStationCertificateNum());//主站证书序列号
        params.put("MASTER_CERT", info.getMainStationCertificate());//主站证书
        params.put("TMNL_CERT", info.getTerminalCertificate());//终端证书
        params.put("RAND_NO", "");//随机数
        params.put("MAC", "");//传输MAC
        LogUtil.d("TL", "url: " + url+ " params: "+params.toString() );
        JSZPOkgoHttpUtils.postParams(url, mContext, params, new JSZPOkgoHttpUtils.JSZPCallcak<LoginUid2>() {

            @Override
            public void onError(Throwable e) {
                status.setErrorStatus(-6, "loginUid: " + e.getMessage());
                if (null != listener) {
                    listener.onError(status);
                }
            }

            @Override
            public void onSuccess(LoginUid2 loginUid, String response) {
                LogUtil.d("TL", "loginUid" + response);
                if (loginUid != null) {
                    if (loginUid.RT_F .equalsIgnoreCase("1") ){

                        loginIdauth(mContext, info, loginUid, listener);
                    }else{
                        status.setErrorStatus(-6, loginUid.getRT_D());
                        if (null != listener) {
                            listener.onError(status);
                        }
                    }

                } else {
                    status.setErrorStatus(-6, "获取UID,以及M1,S1失败");
                    if (null != listener) {
                        listener.onError(status);
                    }
                }

            }
        });
    }

    /**
     *  从安全单元进行认证
     * @param m1
     * @param s1
     * @param listener
     * @return
     */
    private Pair<String, String> doLoginIdauth(String m1, String s1, OnCertificationListener listener) {
        if (null != listener) {
            listener.onProgress("获取M2,S2");
        }
        int ret = 0;
        LogUtil.d("TL", "M1====" + m1);
        LogUtil.d("TL", "S1====" + s1);
        StringBuffer m2s = new StringBuffer();
        StringBuffer s2s = new StringBuffer();
        ret = safetyUnit2.loginIdauth(m1, s1, m2s, s2s);

        if (ret != 0) {
            LogUtil.d("TL", "安全单元应用层身份认证失败");
            status.setErrorStatus(-7, "安全单元应用层身份认证失败");
            if (null != listener) {
                listener.onError(status);
            }
        }
        String m2 = m2s.toString();
        String s2 = s2s.toString();
        return new Pair<>(m2, s2);
    }

    /**
     * 获取终端使用人信息
     * @param mContext
     * @param UID
     * @param callback
     */
    public void getHandsetUsers(final Context mContext, final String UID, JSZPOkgoHttpUtils.JSZPCallcak callback) {
        String url = getUrl()+"getHandsetUsers.do";
        HttpParams params = new HttpParams();
        params.put("UID", UID);
        //params.put("MAC", getMac(params.toString()));//传输MAC
        JSZPOkgoHttpUtils.postParams(url, mContext, params, callback);
    }

    /**
     *  退出登陆,注销 UID
     * @param mContext
     * @param UID
     */
    public void logout(final Context mContext, final String UID) {
        String url = getUrl()+"logout.do";
        HttpParams params = new HttpParams();
        params.put("UID", UID);
        //params.put("MAC", getMac(params.toString()));//传输MAC
        JSZPOkgoHttpUtils.postParams(url, mContext, params, new JSZPOkgoHttpUtils.JSZPCallcak<BaseEntity>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(BaseEntity response, String json) {
                if (response != null) {
                    if ("1".equals(response.getRT_F())) {
                        Log.d("logout", "onSuccess: "+json);
                        //todo 需要清除 UID，
                    }else{
                        // 失败仍需要清除 UID.
                    }
                }
            }
        });
    }
    /**
     * 若是失败，可以查看 HttpParams与SDK moudle 下的 BaseRequestParams 对比，查看toString的结果是否一致
     *
     * @param params
     * @return
     */
    public String getMac(String params, String random) {
        StringBuffer mac = new StringBuffer();
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(params.getBytes());
        byte[] md5Bytes = md5.digest();
        for (int i = 0; i < 3; i++) {
            //本地密钥计算MAC
            int ret = safetyUnit2.localKeyComputeMac(Convert.toHexString(md5Bytes), random, mac);
            if (ret == 0) {
                break;
            }
        }
        return mac.toString();
    }
   /* http://10.230.24.185:6002/eomfront/getHandsetUsers.do  getHandsetUsers: UID=08161437425792
            08-16 14:37:08.414 30272-30272/com.sgcc.pda E/uid: getUserList result:{"RT_F":"1","USERS":[{"USER_NAME":"qinling","USER_CODE":"QINLING3"}],"RT_D":"获取作业终端使用人成功"}*/

}

