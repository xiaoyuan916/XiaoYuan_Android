package com.sgcc.pda.hardware.model.founation.safetyunit;

import android.text.TextUtils;
import com.sgcc.pda.hardware.event.HardwareErroeEvent;
import com.sgcc.pda.hardware.protocol.safetyunit.ISafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitFrame;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.EventManager;
import com.sgcc.pda.sdk.utils.LogUtil;

/**
 * 创建者 陶蕾
 * 创建时间 2017/10/10
 * 通用安全单元2.0功能实现类
 */
public class CommonSafetyUnit2 implements ISafetyUnit2 {
    ISafetyUnitEssentialMethod<SafetyUnitFrame> method = null;

    public CommonSafetyUnit2(ISafetyUnitEssentialMethod<SafetyUnitFrame> m) {
        method = m;
    }

    /**
     * 获取安全单元信息
     *
     * @param status                        安全单元状态字
     * @param appVersion                    软件版本号
     * @param version                       硬件版本号
     * @param cEsamNum                      C-ESAM序列号
     * @param operator                      操作者代码
     * @param operatorPower                 权限
     * @param cover                         权限掩码
     * @param operatorInfo                  操作者信息
     * @param yEsamNum                      Y-ESAM序列号
     * @param yEsamVersion                  Y-ESAM对称密钥密钥版本
     * @param mainStationCertificateVersion 主站证书版本号
     * @param terminalCertificateVersion    终端证书版本号
     * @param mainStationCertificateNum     主站证书序列号
     * @param terminalCertificateNum        终端证书序列号
     * @param counter                       当前计数器
     * @param encryptNum                    转加密剩余次数
     * @param labelSecretVersion            标签密钥版本
     * @param mainStationCertificate        主站证书
     * @param terminalCertificate           终端证书
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int getSafeUnitMessage(StringBuffer status, StringBuffer appVersion, StringBuffer version, StringBuffer cEsamNum,
                                  StringBuffer operator, StringBuffer operatorPower, StringBuffer cover, StringBuffer operatorInfo,
                                  StringBuffer yEsamNum, StringBuffer yEsamVersion, StringBuffer mainStationCertificateVersion,
                                  StringBuffer terminalCertificateVersion, StringBuffer mainStationCertificateNum,
                                  StringBuffer terminalCertificateNum, StringBuffer counter, StringBuffer encryptNum,
                                  StringBuffer labelSecretVersion, StringBuffer mainStationCertificate, StringBuffer terminalCertificate) {
        if (status == null || appVersion == null || version == null || cEsamNum == null || operator == null || operatorPower == null ||
                cover == null || operatorInfo == null || yEsamNum == null || yEsamVersion == null
                || mainStationCertificateVersion == null || terminalCertificateVersion == null || mainStationCertificateNum == null ||
                terminalCertificateNum == null || counter == null || encryptNum == null || labelSecretVersion == null || mainStationCertificate == null || terminalCertificate == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("获取安全单元信息失败，出现在CommonSafetyUnit类 getSafeUnitMessage()方法中",
                    " status = " + status + " appVersion = " + appVersion + " version = " + version + " cEsamNum = " + cEsamNum + " operator = " + operator + " operatorPower = " + operatorPower +
                            " cover = " + cover + " operatorInfo = " + operatorInfo + " yEsamNum = " + yEsamNum + " yEsamVersion = " + yEsamVersion + " mainStationCertificateVersion = " + mainStationCertificateVersion + " terminalCertificateVersion = " + terminalCertificateVersion +
                            " mainStationCertificateNum = " + mainStationCertificateNum + " terminalCertificateNum = " + terminalCertificateNum + " counter = " + counter + " encryptNum = " + encryptNum + " labelSecretVersion = " + labelSecretVersion + " mainStationCertificate = " + mainStationCertificate +
                            " terminalCertificate = " + terminalCertificate));
            return ErrorManager.ErrorType.CommonBufferError.getValue();

        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "01", null, sb);
        LogUtil.e("TL", "sb.length()=======" + sb.length());
        if (ret == 0) {
            if (sb.length() < 6266) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取安全单元信息失败，出现在CommonSafetyUnit类 getSafeUnitMessage()方法中",
                        " ret = " + ret + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            } else {
                status.delete(0, status.length()).append(sb.substring(0, 2));
                appVersion.delete(0, appVersion.length()).append(sb.substring(2, 8));
                version.delete(0, version.length()).append(sb.substring(8, 14));
                cEsamNum.delete(0, cEsamNum.length()).append(sb.substring(14, 30));
                operator.delete(0, operator.length()).append(sb.substring(30, 38));
                operatorPower.delete(0, operatorPower.length()).append(sb.substring(38, 40));
                cover.delete(0, cover.length()).append(sb.substring(40, 56));
                operatorInfo.delete(0, operatorInfo.length()).append(sb.substring(56, 116));
                yEsamNum.delete(0, yEsamNum.length()).append(sb.substring(116, 132));
                yEsamVersion.delete(0, yEsamVersion.length()).append(sb.substring(132, 164));
                mainStationCertificateVersion.delete(0, mainStationCertificateVersion.length()).append(sb.substring(164, 166));
                terminalCertificateVersion.delete(0, terminalCertificateVersion.length()).append(sb.substring(166, 168));
                mainStationCertificateNum.delete(0, mainStationCertificateNum.length()).append(sb.substring(168, 200));
                terminalCertificateNum.delete(0, terminalCertificateNum.length()).append(sb.substring(200, 232));
                counter.delete(0, counter.length()).append(sb.substring(232, 240));
                encryptNum.delete(0, encryptNum.length()).append(sb.substring(240, 248));
                labelSecretVersion.delete(0, labelSecretVersion.length()).append(sb.substring(248, 264));
                //主站证书长度
                int mascert_cert_length = Integer.parseInt(sb.substring(264, 268), 16) * 2;
                mainStationCertificate.delete(0, mainStationCertificate.length()).append(sb.substring(268, mascert_cert_length + 268));
                //终端证书长度
                //  int terminal_cert_length = Integer.parseInt(sb.substring(mascert_cert_length + 268,mascert_cert_length + 272),16)*2;
                terminalCertificate.delete(0, terminalCertificate.length()).append(sb.substring(mascert_cert_length + 272, sb.length()));
                LogUtil.e("TL", "获取安全单元信息=======操作者代码" + operator);
            }


        } else {
            LogUtil.e("TL", "获取安全单元信息失败==========" + ret);
        }
        return ret;
    }

    /**
     * 验证操作员密码
     *
     * @param password    用户密码
     * @param remainCount 返回剩余验证次数
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    public int checkUserPassword(String password, StringBuffer remainCount) {
        if (remainCount == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("验证用户密码失败，出现在CommonSafetyUnit类 checkUserPassword()方法中",
                    " password = " + password + " remainCount = " + remainCount));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (password == null || password.length() != 6) {
            EventManager.getDefault().post(new HardwareErroeEvent("验证用户密码失败，出现在CommonSafetyUnit类 checkUserPassword()方法中",
                    " password = " + password + " remainCount = " + remainCount));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        return method.commitDown("00", "02", password, remainCount);
    }

    /**
     * 修改操作员密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    public int changePassword(String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.length() != 6 ||
                newPassword == null || newPassword.length() != 6) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "03", oldPassword + newPassword, sb);
    }

    /**
     * 应用层身份认证
     *
     * @param m1 密文m1
     * @param s1 m1签名
     * @param m2 密文m2
     * @param s2 m2签名
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int loginIdauth(String m1, String s1, StringBuffer m2, StringBuffer s2) {

        if (m1 == null || m1.length() != 64 || s1 == null || s1.length() != 128) {
            LogUtil.e("TL", "获取M2,S2,传入参数不合法");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (m2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (s2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("01", "02", m1 + s1, sb);


        if (sb.length() < 224) {
            return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
        } else {
            m2.delete(0, m2.length()).append(sb.substring(0, 96));
            s2.delete(0, s2.length()).append(sb.substring(96, 224));
            LogUtil.e("TL", "M2-------" + m2.delete(0, m2.length()).append(sb.substring(0, 96)));
            LogUtil.e("TL", "S2-------" + s2.delete(0, s2.length()).append(sb.substring(96, 224)));
        }


        return ret;
    }

    /**
     * 从安全单元获取随机数
     *
     * @param esamType     ESAM类型   01，操作员ESAM；02业务ESAM
     * @param randomLength 随机数长度  4，8，16
     * @param type         ESAM类型   其实是数据data?
     * @param random       随机数
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int getRandomNum(String esamType, String randomLength, StringBuffer type, StringBuffer random) {
        if (esamType == null || esamType.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (randomLength == null || randomLength.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (type == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (random == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("01", "01", esamType + randomLength, sb);
        if (ret == 0) {
            if (sb.length() < 14) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();

            }
            type.delete(0, type.length()).append(sb.substring(0, 2));
            random.delete(0, random.length()).append(sb.substring(6, sb.length()));
        }
        return ret;
    }

    /**
     * 锁定安全单元
     *
     * @param data 认证数据
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int lockingSafeUnit(String data) {
        if (data == null || data.length() != 16) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "04", data, sb);

        return ret;
    }

    /**
     * 二次发行安全单元
     *
     * @param esamType esam类型  01，C-ESAM；02Y-ESAM；
     * @param fxData   发行数据  2+N
     * @param esamData ESAM返回数据内容
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int safyUnitTwoSend(String esamType, String fxData, StringBuffer esamData) {
        if (esamType == null || esamType.length() != 2 || fxData == null || fxData.length() < 0) {
            LogUtil.e("TL", "二次发行参数不正确");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (esamData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }


        final String datalength = DataConvert.getHexLength(fxData, 2);

        int ret = method.commitDown("00", "07", esamType + datalength + fxData, esamData);


        return ret;


    }

    /**
     * 透明转发ESAM指令
     *
     * @param esamType    esam类型 01，C-ESAM；02Y-ESAM；
     * @param data        转发数据内容 转发数据长度+转发数据内容
     * @param rtnEsamType esam类型 01，C-ESAM；02Y-ESAM；
     * @param rtnData     转发返回数据内容
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int transmitEsamOrder(String esamType, String data, StringBuffer rtnEsamType, StringBuffer rtnData) {
        if (esamType == null || esamType.length() != 2 || data == null || data.length() < 4) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (rtnEsamType == null || rtnData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 2);
        int ret = method.commitDown("00", "0A", esamType + dataLength + data, sb);
        if (ret == 0) {
            rtnData.append(sb.substring(6, sb.length()));
        }
        return 0;
    }

    /**
     * 转加密初始化
     *
     * @param data   数据
     * @param result 执行结果数据
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int encryptInitialize(String data, StringBuffer result) {
        if (data == null || data.length() != 64) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (result == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        int ret = method.commitDown("01", "05", data, result);
        if (ret == 0) {
            if (result.length() != 8) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();

            }
        }
        return ret;
    }

    /**
     * 置离线计数器
     *
     * @param data 数据
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int resetOflineCounter(String data) {
        if (data == null || data.length() != 32) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("01", "06", data, sb);
        return ret;
    }

    /**
     * 本地密钥计算MAC
     *
     * @param randomM 随机数M
     * @param data    2+N 数据长度+数据
     * @param mac     Macl
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int localKeyComputeMac(String randomM, String data, StringBuffer mac) {
        if (randomM == null || randomM.length() != 32 ||
                data == null || data.length() <= 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 2);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("01", "07", randomM + dataLength + data, sb);
        if (ret == 0) {
            if (sb.length() < 8) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            mac.delete(0, mac.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 本地密钥验证MAC
     *
     * @param randomM 主站身份认证时返回的随机数M
     * @param data    待计算数据
     * @param mac     已有传输MAC
     * @param result  验证结果
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int localKeyCheckMac(String randomM, String data, String mac, StringBuffer result) {
        if (randomM == null || randomM.length() != 32 ||
                data == null || data.length() <= 0 ||
                mac == null || mac.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (result == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data += mac;

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 2);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("01", "09", randomM + dataLength + data + mac, sb);
        if (ret == 0) {
            result.delete(0, result.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 会话密钥计算MAC
     *
     * @param data 2+N 数据长度+数据
     * @param mac  Mac
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int sessionKeyComputeMac(String data, StringBuffer mac) {
        if (data == null || data.length() <= 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 2);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("01", "08", dataLength + data, sb);
        if (ret == 0) {
            if (sb.length() < 8) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            mac.delete(0, mac.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 会话密钥验证MAC
     *
     * @param data   待计算数据
     * @param mac    已有传输MAC
     * @param result 验证结果
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int sessionKeyCheckMac(String data, String mac, StringBuffer result) {
        if (data == null || data.length() <= 0 ||
                mac == null || mac.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (result == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data += mac;

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 2);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("01", "0A", dataLength + data + mac, sb);
        if (ret == 0) {
            result.delete(0, result.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 电能表红外认证（09、13）
     *
     * @param meterNo            表号  高位在前（非颠倒），高2字节补0x00
     * @param esamNo             ESAM序列号
     * @param mRandom1           随机数1密文
     * @param infraredAuthority1 红外认证权限1
     * @param infraredAuthority2 红外认证权限2
     * @param random2            随机数2
     * @param curTime            掌机当前时间  YYMMDDhhmmss
     * @param mRandom2           随机数2密文
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int meterInfraredAuthentication(String meterNo, String esamNo, String mRandom1, String infraredAuthority1,
                                           String infraredAuthority2, String random2, String curTime, StringBuffer mRandom2) {
        if (meterNo == null || meterNo.length() != 16 || esamNo == null || esamNo.length() != 16 ||
                mRandom1 == null || mRandom1.length() != 16 || infraredAuthority1 == null || infraredAuthority1.length() != 96 ||
                infraredAuthority2 == null || infraredAuthority2.length() != 96 || random2 == null || random2.length() != 16 ||
                curTime == null || curTime.length() != 14) {
            LogUtil.e("TL", "红外认证传入参数不正确");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (mRandom2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        mRandom2.delete(0, mRandom2.length());

        return method.commitDown("02", "01", meterNo + esamNo + mRandom1 + infraredAuthority1 + infraredAuthority2 + random2 + curTime, mRandom2);

    }

    /**
     * 电能表红外认证（09、13）
     *
     * @param meterNo            表号  高位在前（非颠倒），高2字节补0x00
     * @param esamNo             ESAM序列号
     * @param mRandom1           随机数1密文
     * @param infraredAuthority1 红外认证权限
     * @param infraredAuthority2 身份认证权限
     * @param random2            随机数2
     * @param curTime            掌机当前时间  YYMMDDhhmmss
     * @param type               公私钥标志 0为公钥，1为私钥
     * @param mRandom2           随机数2密文
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int meterInfraredAuthentication1(String meterNo, String esamNo, String mRandom1, String infraredAuthority1,
                                            String infraredAuthority2, String random2, String curTime, String type, StringBuffer mRandom2) {
        if (meterNo == null || meterNo.length() != 16 || esamNo == null || esamNo.length() != 16 ||
                mRandom1 == null || mRandom1.length() != 16 || infraredAuthority1 == null || infraredAuthority1.length() != 96 ||
                infraredAuthority2 == null || infraredAuthority2.length() != 96 || random2 == null || random2.length() != 16 ||
                curTime == null || curTime.length() != 14) {
            LogUtil.e("TL", "红外认证传入参数不正确");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (mRandom2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        mRandom2.delete(0, mRandom2.length());

        return method.commitDown("02", "01", meterNo + esamNo + mRandom1 + infraredAuthority1 + infraredAuthority2 + random2 + curTime + type, mRandom2);

    }

    /**
     * 远程身份认证（09、13）
     *
     * @param meterNo                 表号 高位在前（非颠倒），高2字节补0x00；
     * @param authenticationAuthority 身份认证权限
     * @param curTime                 掌机当前时间  YYMMDDhhmmss
     * @param keyType                 公私钥标志 0为公钥，1为私钥
     * @param cipher                  身份认证密文
     * @param random1                 随机数1
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int remoteAuthentication(String meterNo, String authenticationAuthority, String curTime, String keyType, StringBuffer cipher, StringBuffer random1) {
        if (meterNo == null || meterNo.length() != 16 || authenticationAuthority == null || authenticationAuthority.length() != 96 ||
                curTime == null || curTime.length() != 14 || keyType == null || keyType.length() != 2) {
            LogUtil.e("TL", "身份认证参数非法----");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (cipher == null || random1 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "02", meterNo + authenticationAuthority + curTime + keyType, sb);
        if (ret == 0) {
            if (sb.length() < 32) {
                LogUtil.e("TL", "身份认证返回值   非法----");
                EventManager.getDefault().post(new HardwareErroeEvent("获取远程身份认证数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        " meterNo = " + meterNo + " authenticationAuthority = " + authenticationAuthority + " curTime = " + curTime + " keyType = " + keyType + " cipher = " + cipher + " sb.length() = " + sb.length() + " ret = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            cipher.append(sb.substring(0, 16));
            random1.append(sb.substring(16, 32));
        }
        return ret;
    }

    /**
     * 电能表控制（09、13）
     *
     * @param meterNo                表号  高位在前（非颠倒），高2字节补0x00
     * @param random                 随机数  身份认证产生的随机数
     * @param remoteControlAuthority 远程控制权限
     * @param orderCode              命令码
     * @param endTime                截止时间（yyMMddhhmmss 6字节）+4字节MAC
     * @param mac1                   4字节MAC
     * @param curTime                掌机当前时间
     * @param cipher                 控制数据密文
     * @param mac                    MAC
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int meterControl(String meterNo, String random, String remoteControlAuthority, String orderCode, String endTime, String mac1, String curTime, StringBuffer cipher, StringBuffer mac) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 ||
                remoteControlAuthority == null || remoteControlAuthority.length() != 96 || endTime == null || endTime.length() != 12 || mac1 == null || mac1.length() != 8 || orderCode == null || orderCode.length() != 4) {
            LogUtil.e("TL", "获取控制权限数据参数不正确");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (cipher == null || mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "03", meterNo + random + remoteControlAuthority + orderCode + endTime + mac1 + curTime, sb);
        LogUtil.e("TL", "获取控制权限数据" + sb);
        if (ret == 0) {
            if (sb.length() < 40) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "remoteControlAuthority = " + remoteControlAuthority + "endTime = " + endTime + " curTime = " + curTime + " cipher = " + cipher + " sb.length() = " + sb.length() + " ret = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            cipher.append(sb.substring(0, 32));
            mac.append(sb.substring(32, 40));
        }
        return ret;
    }

    /**
     * 电能表设参（09、13）
     *
     * @param meterNo        表号  高位在前（非颠倒），高2字节补0x00
     * @param random         随机数  身份认证产生的随机数
     * @param paramAuthority 参数设置权限
     * @param paramType      参数类型 03，二类参数； 05，一类参数； 06，一套费率；  07，备用套费率；
     * @param dataSign       数据标识
     * @param paramValue     参数值
     * @param mac1           MAC  参数类型为05、06、07时，MAC值是参数值的MAC， 参数类型为03时，MAC值是数据标示及参数值的MAC
     * @param curTime        掌机当前时间  YYMMDDhhmmss
     * @param length         长度		包含MAC的长度
     * @param param          参数明文或密文
     * @param mac            MAC
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int meterParamSet(String meterNo, String random, String paramAuthority, String paramType, String dataSign, String paramValue, String mac1, String curTime, StringBuffer length, StringBuffer param, StringBuffer mac) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 || paramAuthority == null || paramAuthority.length() != 96 ||
                paramType == null || paramType.length() != 2 || dataSign == null || dataSign.length() != 8 || paramValue == null || paramValue.length() < 0
                || mac1 == null || mac1.length() != 8 || curTime == null || curTime.length() != 14) {
            LogUtil.e("TL", "电能表设参,传入参数不合法");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (length == null || param == null || mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "04", meterNo + random + paramAuthority + paramType + dataSign + paramValue + mac1 + curTime, sb);
        if (ret == 0) {
            int l = Integer.parseInt(sb.substring(0, 4), 16) * 2;
            if (sb.length() < l + 4) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "paramAuthority = " + paramAuthority + "paramType = " + paramType + "dataSign = " + dataSign + "paramValue = " + paramValue + " mac1 = " + mac1 + " curTime = " + curTime + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            length.append(sb.substring(0, 4));
            param.append(sb.substring(4, sb.length() - 8));
            mac.append(sb.substring(sb.length() - 8, sb.length()));
        }
        return ret;
    }

    /**
     * 电能表校时（09、13）
     *
     * @param meterNo        表号  高位在前（非颠倒），高2字节补0x00
     * @param random         随机数  身份认证产生的随机数
     * @param checkAuthority 校时权限
     * @param dataSign       数据标识
     * @param paramValue     参数值
     * @param mac1           MAC值是数据标示及参数值的MAC
     * @param week           星期
     * @param curTime        掌机当前时间  YYMMDDhhmmss
     * @param length         长度		包含MAC的长度
     * @param cipher         校时数据密文
     * @param mac            MAC
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int meterCheckTime(String meterNo, String random, String checkAuthority, String dataSign, String paramValue, String mac1, String week, String curTime, StringBuffer length, StringBuffer cipher, StringBuffer mac) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 || checkAuthority == null || checkAuthority.length() != 96 ||
                dataSign == null || dataSign.length() != 8 || paramValue == null || paramValue.length() < 0
                || mac1 == null || mac1.length() != 8 || week == null || week.length() != 2 || curTime == null || curTime.length() != 14) {
            LogUtil.e("TL", "获取校时密文数据时,传入参数不合法");
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (length == null || cipher == null || mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "05", meterNo + random + checkAuthority + dataSign + paramValue + mac1 + week + curTime, sb);
        LogUtil.e("TL", "获取校时任务数据---------" + sb);
        if (ret == 0) {
            int l = Integer.parseInt(sb.substring(0, 4), 16) * 2;
            if (sb.length() < l + 4) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "checkAuthority = " + checkAuthority + "dataSign = " + dataSign + "paramValue = " + paramValue + " mac1 = " + mac1 + " week = " + week + " curTime = " + curTime + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            length.append(sb.substring(0, 4));
            cipher.append(sb.substring(4, sb.length() - 8));
            mac.append(sb.substring(sb.length() - 8, sb.length()));
        }


        return ret;
    }

    /**
     * 电能表密钥更新(09)
     *
     * @param meterNo      表号  高位在前（非颠倒），高2字节补0x00
     * @param random       随机数  身份认证产生的随机数
     * @param athorityData 权限数据
     * @param cipher       密钥密文
     * @param curTime      掌机当前时间  YYMMDDhhmmss
     * @param keySign      密钥状态标志位  00：密钥恢复  01：密钥下装
     * @param length       数据长度
     * @param secret       密钥数据  密钥信息（4）+MAC（4）+密钥密文（32）
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     * SafetyReceiveDataError-安全单元接收的数据错误
     */
    @Override
    public int secretUpdate09(String meterNo, String random, String athorityData, String cipher, String curTime, String keySign, StringBuffer length, StringBuffer secret) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 || athorityData == null || athorityData.length() != 96 ||
                cipher == null || cipher.length() != 416 || curTime == null || curTime.length() != 14 || keySign == null || keySign.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (length == null || secret == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "06", meterNo + random + athorityData + cipher + curTime + keySign, sb);
        int l = Integer.parseInt(sb.substring(0, 4), 16) * 2;
        if (ret == 0) {
            if (sb.length() < l + 4) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "athorityData = " + athorityData + "cipher = " + cipher + " curTime = " + curTime + " keySign = " + keySign + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            // TODO: 2017/11/6
            length.append(sb.substring(0, 4));
            secret.append(sb.substring(4, sb.length()));
        }

        return ret;
    }

    /**
     * 电能表密钥更新(13)
     *
     * @param meterNo      表号  高位在前（非颠倒），高2字节补0x00
     * @param random       随机数  身份认证产生的随机数
     * @param athorityData 权限数据
     * @param cipher       密钥密文
     * @param curTime      掌机当前时间  YYMMDDhhmmss
     * @param secret       密钥数据块   2+N  数据长度+密钥1…密钥N
     * @param mac          MAC
     * @return
     */
    @Override
    public int secretUpdate13(String meterNo, String random, String athorityData, String cipher, String curTime, StringBuffer secret, StringBuffer mac) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 || athorityData == null || athorityData.length() != 96 ||
                cipher == null || cipher.length() != 1472 || curTime == null || curTime.length() != 14) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (mac == null || secret == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "07", meterNo + random + athorityData + cipher + curTime, sb);
        if (ret == 0) {
            int l = Integer.parseInt(sb.substring(0, 4), 16) * 2;
            if (sb.length() < l + 12) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "athorityData = " + athorityData + "cipher = " + cipher + " curTime = " + curTime + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            // TODO: 2017/11/6
            secret.append(sb.substring(4, sb.length() - 8));
            mac.append(sb.substring(sb.length() - 8, sb.length()));
        }

        return ret;
    }


    /**
     * 电能表开户充值（09、13）
     *
     * @param meterNo       表号  高位在前（非颠倒），高2字节补0x00
     * @param random        随机数  身份认证产生的随机数
     * @param athorityData1 权限数据1
     * @param athorityData2 权限数据2
     * @param taskData      开户数据 数据标识（4字节）+购电金额（4字节）+购电次数（4字节）+MAC1（4字节）+客户编号（6字节）+MAC2（4字节）
     * @param curTime       掌机当前时间  YYMMDDhhmmss
     * @param rechargeData  安全单元返回数据
     * @return 0-成功 其它-错误码
     */
    @Override
    public int meterRecharge(String meterNo, String random, String athorityData1, String athorityData2, String taskData, String curTime, StringBuffer rechargeData) {
        if (meterNo == null || meterNo.length() != 16 || random == null || random.length() != 8 || athorityData1 == null || athorityData1.length() != 96 || athorityData2 == null || athorityData2.length() != 96 ||
                taskData == null || taskData.length() != 52 || curTime == null || curTime.length() != 14) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (rechargeData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "08", meterNo + random + athorityData1 + athorityData2 + taskData + curTime, sb);
        if (ret == 0) {
            if (sb.length() < 44) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取电能表控制数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        "meterNo = " + meterNo + " random = " + random + "athorityData1 = " + athorityData1 + "athorityData2 = " + athorityData2 + "rechargeData = " + rechargeData + " curTime = " + curTime + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            rechargeData.append(sb);
        }
        return ret;
    }

    /**
     * 698电能表会话协商
     *
     * @param secretData 密钥包
     * @param meterNo    若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param counter    会话计数器(698电表中读取)
     * @param rntData    会话数据
     * @return 0-成功 其它-错误码
     */
    @Override
    public int meterSessionTalk698(String secretData, String meterNo, String counter, StringBuffer rntData) {
        if (secretData == null || secretData.length() != 384
                || meterNo == null || meterNo.length() != 16
                || counter == null || counter.length() != 8) {
            LogUtil.e("TL", "698电能表会话协商,参数不合法");
            return -1;
        }
        if (rntData == null) {
            return -1;
        }
        String time = DataConvert.getCurrentTimeString("yyyyMMddHHmmss");
        LogUtil.e("TL", "secretData---------------" + secretData);
        int ret = method.commitDown("02", "09", secretData + meterNo + counter + time, rntData);

        if (ret != 0 || rntData.length() != 72) {
            return ret;
        }
        return 0;
    }

    /**
     * 698电能表会话协商验证
     *
     * @param secretData  密钥包
     * @param meterNo     若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param secretSign  密钥标识
     * @param sessionData 会话数据
     * @return 0-成功 其它-错误码
     */
    @Override
    public int meterSessionTalk698Check(String secretData, String meterNo, String secretSign, String sessionData) {
        if (secretData == null || secretData.length() != 384 || meterNo == null || meterNo.length() != 16 || sessionData == null || sessionData.length() != 104
                || secretSign == null || secretSign.length() != 32) {
            LogUtil.e("TL", "698电能表会话协商验证,参数不合法");
            return -1;
        }
        String time = DataConvert.getCurrentTimeString("yyyyMMddHHmmss");
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "0A", secretData + meterNo + secretSign + sessionData + time, sb);

        if (ret != 0) {
            return ret;
        }
        return 0;
    }

    /**
     * 698电能表安全数据生成
     * @param taskData 主站任务数据   1字节安全模式字+任务参数类型+2字节应用层数据长度+应用层数据+4字节保护码（根据应用层数据长度可得知整个任务数据长度）
     * @param rtnData  返回数据
     * @return
     */
    @Override
    public int meterSafeData698(String taskData, StringBuffer rtnData) {
        if (taskData == null) {
            return -1;
        }
        if (rtnData == null) {
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "0B", taskData, sb);
        if (ret != 0) {
            return ret;
        } else {
            rtnData.append(sb.substring(4, sb.length()));
        }
        return 0;
    }

    /**
     * 698电能表安全传输解密
     *
     * @param secretData 电表返回的密文链路数据
     * @param rtnData    应用层数据明文
     * @return
     */
    @Override
    public int meterSafeTransJieMi698(String secretData, StringBuffer rtnData) {
        if (secretData == null) {
            return -1;
        }
        if (rtnData == null) {
            return -1;
        }
        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(secretData, 2);
        int ret = method.commitDown("02", "0C", dataLength + secretData, sb);
        if (ret != 0) {
            return -1;
        } else {
            rtnData.append(sb.substring(4, sb.length()));
        }
        return 0;
    }

    /**
     * 698电能表抄读数据验证
     *
     * @param disFactor 分散因子
     * @param data      应用层数据明文
     * @param rtnData   返回的应用层数据明文
     * @return 0-成功 其它-错误码
     */
    @Override
    public int meterDataCheck698(String disFactor, String data, StringBuffer rtnData) {
        if (disFactor == null || disFactor.length() != 16 || data == null) {
            return -1;
        }
        if (rtnData == null) {
            return -1;
        }
        String dataLength = DataConvert.getHexLength(data, 2);
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "0D", disFactor + dataLength + data, sb);
        if (ret != 0) {
            return -1;
        } else {
            rtnData.append(sb.substring(4, sb.length()));
        }
        return 0;
    }

    /**
     * 698电能表抄读ESAM参数验证
     *
     * @param meterNo 若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param oad     OAD
     * @param data    应用层数据
     * @return
     */
    @Override
    public int meterEsamParamCheck698(String meterNo, String oad, String data) {
        if (meterNo == null || meterNo.length() != 16 || oad == null || oad.length() != 8 || data == null) {
            return -1;
        }
        String dataLength = DataConvert.getHexLength(data, 2);
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("02", "0D", meterNo + oad + dataLength + data, sb);
        if (ret != 0) {
            return -1;
        }
        return 0;
    }


    /**
     * 与外设进行秘钥协商
     *
     * @param kid      秘钥kid。
     * @param wesamNum wesam芯片序列号
     * @param hhjsq    外设返回的会话计数器
     * @param M1       安全单元返回的密文1
     * @param Mac1     安全单元返回的Mac1
     * @return 0：成功；其他-错误码
     */
    @Override
    public int wsSecretXieShang(String kid, String wesamNum, String hhjsq, StringBuffer M1, StringBuffer Mac1) {
        if (kid == null || kid.length() != 8 || wesamNum == null || wesamNum.length() != 16 || hhjsq == null || hhjsq.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (M1 == null || Mac1 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("06", "01", kid + wesamNum + hhjsq, sb);
        if (ret == 0) {
            if (sb.length() < 72) {
                EventManager.getDefault().post(new HardwareErroeEvent("与外设进行秘钥协商失败，出现在CommonSafetyUnit类 wsSecretXieShang()方法中",
                        "kid = " + kid + " wesamNum = " + wesamNum + "hhjsq = " + hhjsq + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            M1.append(sb.substring(0, 64));
            Mac1.append(sb.substring(64, 72));
        }
        return ret;
    }

    /**
     * 与外设进行秘钥协商确认
     *
     * @param kid      秘钥kid。
     * @param wesamNum wesam芯片序列号
     * @param M2       外设返回的密文2
     * @param Mac2     外设返回的Mac2
     * @return 0：成功；其他-错误码
     */
    @Override
    public int wsSecretXieShangConfirm(String kid, String wesamNum, String M2, String Mac2) {
        if (kid == null || kid.length() != 8 || wesamNum == null || wesamNum.length() != 16 || M2 == null || M2.length() != 96 || Mac2 == null || Mac2.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("06", "02", kid + wesamNum + M2 + Mac2, sb);

        return ret;
    }

    /**
     * 与外设秘钥协商后进行加密数据
     *
     * @param wsType            读取外设信息时返回的类型
     * @param data              要加密的数据
     * @param encryptDataLength 返回的加密后数据的长度  密文+mac的长度
     * @param encryptData       返回的加密后的数据  密文+mac的数据
     * @return 0：成功；其他-错误码
     */
    @Override
    public int wsEncryptData(String wsType, String data, StringBuffer encryptDataLength, StringBuffer encryptData) {
        if (wsType == null || wsType.length() != 2 || data == null || data.length() == 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("06", "03", DataConvert.getHexLength(data, 2) + data, sb);
        if (ret == 0) {
            if (sb.length() < 4 || sb.length() < (Integer.parseInt(sb.substring(0, 4), 16) * 2 + 4)) {
                EventManager.getDefault().post(new HardwareErroeEvent("与外设进行秘钥协商失败，出现在CommonSafetyUnit类 wsSecretXieShang()方法中",
                        "wsType = " + wsType + " data = " + data + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            encryptDataLength.append(sb.substring(0, 4));
            encryptData.append(sb.substring(4, Integer.parseInt(encryptDataLength.toString(), 16) * 2));
        }
        return ret;
    }

    /**
     * 与外设秘钥协商后进行解密数据
     *
     * @param wsType      读取外设信息时返回的类型
     * @param encryptData 要解密的数据  密文+mac的数据
     * @param dataLength  返回的解密后数据的长度
     * @param data        返回的解密后的数据     明文
     * @return 0：成功；其他-错误码
     */
    @Override
    public int wsDecryptData(String wsType, String encryptData, StringBuffer dataLength, StringBuffer data) {
        if (wsType == null || wsType.length() != 2 || encryptData == null || encryptData.length() == 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("06", "04", DataConvert.getHexLength(encryptData, 2) + data, sb);
        if (ret == 0) {
            if (sb.length() < 4 || sb.length() < (Integer.parseInt(sb.substring(0, 4), 16) * 2 + 4)) {
                EventManager.getDefault().post(new HardwareErroeEvent("与外设进行秘钥协商失败，出现在CommonSafetyUnit类 wsSecretXieShang()方法中",
                        "wsType = " + wsType + " encryptData = " + encryptData + " sb.length() = " + sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            dataLength.append(sb.substring(0, 4));
            data.append(sb.substring(4, Integer.parseInt(dataLength.toString(), 16) * 2));
        }

        // todo 当 ret ！=0 时 ，应该返回 ret 吧？
        return 0;
    }

    /**
     * @param blockCountHexStr     升级程序总块数  2字节
     * @param compareCodeSumHexStr 升级程序校验和  1字节
     * @return
     */
    @Override
    public int upgradeCommand1(String blockCountHexStr, String compareCodeSumHexStr) {

        if (valueIsNotOk(2, blockCountHexStr)
                || valueIsNotOk(1, compareCodeSumHexStr))
            return ErrorManager.ErrorType.CommonParamError.getValue();

        String data = blockCountHexStr + compareCodeSumHexStr;
        return method.commitDown("FE","01",data,new StringBuffer());
    }

    /**
     * 检查字符串是否不满足条件
     *
     * @param hexByteSize 该字符串 需要的 字节长度
     * @param hexStr      16进制 字符串
     * @return 若字符串为空，或者不是16进制  返回 true，且字节长度是 与 hexByteSize 不一致  为 true
     */
    private boolean valueIsNotOk(int hexByteSize, String hexStr) {
        return valueIsNotOk(hexStr) || hexStr.length() != hexByteSize * 2;

    }

    /**
     * 检查16进制字符串格式
     *
     * @param hexStr
     * @return 若字符串为空，或者不是16进制  返回 true
     */
    private boolean valueIsNotOk(String hexStr) {
       // return !(!TextUtils.isEmpty(hexStr) && DataConvert.isHexUnsignedStr(hexStr));
        return TextUtils.isEmpty(hexStr)|| !DataConvert.isHexUnsignedStr(hexStr);

    }

    /**
     *
     * @param blockNoHexStr 数据块块号
     * @param blockData 数据块
     * @return
     */
    @Override
    public int upgradeCommand2_send(String blockNoHexStr, String blockData) {
       /* StringBuffer mainSign = new StringBuffer();
        StringBuffer control = new StringBuffer();
        StringBuffer status = new StringBuffer();
        int responseStatus = method.getFrame(mainSign,control,status,blockNoHexStr);

        if (responseStatus != 0) return responseStatus;
        // 若不是升级命令2 返回错误码
        if (! mainSign.append(control).toString().equalsIgnoreCase("FE02"))
            return ErrorManager.ErrorType.CommonParamError.getValue();*/

        // 若是 数据块块号或数据块格式出错，则直接返会错误码
        if (valueIsNotOk(2,blockNoHexStr)
                ||valueIsNotOk(blockData))
            return ErrorManager.ErrorType.CommonParamError.getValue();

        String data = blockNoHexStr + blockData;
        return  method.sendFrame("FE","82","00",data);
    }

    @Override
    public int upgradeCommand_get(StringBuffer mainSign,StringBuffer control, StringBuffer upgradleStatus) {
        StringBuffer status = new StringBuffer(); //  分析之后貌似没有有用到
        status.delete(0,status.length());
        return method.getFrame(mainSign,control,status,upgradleStatus);

       /* // 若不是升级命令3  返回错误码  FE02
        if (! mainSign.append(control).toString().equalsIgnoreCase(mainsignAndControl))
            return ErrorManager.ErrorType.SafetyFrameMatchError.getValue();*/
    }

    @Override
    public int switchArea(String area, StringBuffer data) {
        if (valueIsNotOk(1,area)|| data == null)
            return ErrorManager.ErrorType.CommonParamError.getValue();

        return method.commitDown("FE","04",area,new StringBuffer());
    }


}
