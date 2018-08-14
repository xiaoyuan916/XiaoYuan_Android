package com.sgcc.pda.hardware.model.founation.safetyunit;

import android.util.Pair;

import com.sgcc.pda.hardware.event.HardwareErroeEvent;
import com.sgcc.pda.hardware.protocol.safetyunit.ISafetyUnitEssentialMethod;
import com.sgcc.pda.hardware.protocol.safetyunit.SafetyUnitFrame;
import com.sgcc.pda.hardware.util.DataConvert;
import com.sgcc.pda.hardware.util.ErrorManager;
import com.sgcc.pda.hardware.util.EventManager;
import com.sgcc.pda.hardware.util.IOMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 通用安全单元功能实现类
 */
public class CommonSafetyUnit implements ISafetyUnit {
    ISafetyUnitEssentialMethod<SafetyUnitFrame> method = null;

    public CommonSafetyUnit(ISafetyUnitEssentialMethod<SafetyUnitFrame> m) {
        method = m;
    }

    /**
     * 安全单元初始化
     *
     * @return 0-成功
     * CommonParamError-输入参数不合法
     * CommonBufferError-返回值缓冲区为空
     * CommonWriteError-数据写入错误
     * SafetyConfigError-协议和帧结构定义为空
     * SafetyUnitError-安全单元操作设备操作失败
     * SafetyFrameError-安全单元通讯帧格式错误
     * SafetyFrameMatchError-上下行帧不匹配
     * SafetyGetStatusError-安全单元下行帧返回了异常帧
     */
    public int init() {
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "14", null, sb);
    }

    /**
     * 安全单元同步
     *
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
    public int sync() {
        StringBuffer sb = new StringBuffer();
        int percent;
        int ret;
        int tryCount = 3;
        int currentTry = 1;
        do {
            ret = method.commitDown("00", "15", null, sb);
            if (ret != 0) { // 如果通讯失败，则直接返回错误
                return ret;
            }
            if (sb.length() < 2) { // 接收到的数据错误
                EventManager.getDefault().post(new HardwareErroeEvent("安全单元同步失败，出现在CommonSafetyUnit类 sync()方法中",
                        " ret = "+ret+" sb.length() = "+sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            try { // 获取同步百分比错误
                percent = Integer.parseInt(sb.substring(0, 2), 16);
            } catch (Exception ex) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            currentTry++;
        } while (percent != 100 && currentTry <= tryCount); // 如果安全单元没有同步完成或没有达到重试次数，则继续进行循环
        return ret;
    }

    /**
     * 获取安全单元工作状态
     *
     * @param status 状态字
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
    public int getStatus(StringBuffer status) {
        if (status == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("获取安全单元工作状态失败，出现在CommonSafetyUnit类 getStatus()方法中",
                    " status = null"));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        return method.commitDown("00", "10", null, status);
    }

    /**
     * 获取操作员卡号,业务卡号以及权限
     *
     * @param operator      操作员卡号
     * @param operatorPower 权限
     * @param cover         掩码信息
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
    public int getUserCodeAndPermission(StringBuffer operator, StringBuffer operatorPower, StringBuffer cover) {
        if (operator == null || operatorPower == null || cover == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("获取操作员卡号,业务卡号以及权限失败，出现在CommonSafetyUnit类 getUserCodeAndPermission()方法中",
                    " operator = "+operator+" operatorPower = "+operatorPower+" cover = "+cover));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "11", null, sb);
        if (ret == 0) {
            if (sb.length() < 26) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取操作员卡号,业务卡号以及权限失败，出现在CommonSafetyUnit类 getUserCodeAndPermission()方法中",
                        " ret = "+ret+" sb.length() = "+sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            operator.delete(0, operator.length()).append(sb.substring(0, 8));
            operatorPower.delete(0, operatorPower.length()).append(sb.substring(8, 10));
            cover.delete(0, cover.length()).append(sb.substring(10, 26));

        }
        return ret;
    }

    /**
     * 注销任务标识
     *
     * @param taskSign 任务标识
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
    public int logOffTaskSign(String taskSign) {
        if (taskSign == null || taskSign.length() != 4) {
            EventManager.getDefault().post(new HardwareErroeEvent("注销任务标识失败，出现在CommonSafetyUnit类 logOffTaskSign()方法中",
                    " taskSign = "+taskSign));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "03", taskSign, sb);
    }

    /**
     * 注册任务标志
     *
     * @param taskSign 任务标识
     * @param operator 操作员卡号
     * @param cipher   任务标识数据密文
     * @param mac      密文数据MAC
     * @param randomM  身份认证时返回的认证随机数
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
    public int registerHostCreatTaskSign(String taskSign, String operator, String cipher, String mac, String randomM) {
        if (taskSign == null || taskSign.length() != 4 ||
                operator == null || operator.length() != 8 ||
                cipher == null || cipher.length() != 32 ||
                mac == null || mac.length() != 8 ||
                randomM == null || randomM.length() != 16) {
            EventManager.getDefault().post(new HardwareErroeEvent("注册任务标志失败，出现在CommonSafetyUnit类 registerHostCreatTaskSign()方法中",
                    " taskSign = "+taskSign+" operator = "+operator+" cipher = "+cipher+" mac = "+mac));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "02", taskSign + operator + cipher + mac + randomM, sb);
    }

    /**
     * 红外认证获取随机数1
     *
     * @param cardType 卡类型
     * @param random1  随机数1
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
    public int getRandom1(String cardType, StringBuffer random1) {
        if (random1 == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("红外认证获取随机数1失败，出现在CommonSafetyUnit类 getRandom1()方法中",
                    " cardType = "+cardType+" random1 = "+random1));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (cardType == null || cardType.length() != 2) {
            EventManager.getDefault().post(new HardwareErroeEvent("红外认证获取随机数1失败，出现在CommonSafetyUnit类 getRandom1()方法中",
                    " cardType = "+cardType+" random1 = "+random1));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        return method.commitDown("00", "21", cardType, random1);
    }

    /**
     * 验证用户密码
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
                    " password = "+password+" remainCount = "+remainCount));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        if (password == null || password.length() != 6) {
            EventManager.getDefault().post(new HardwareErroeEvent("验证用户密码失败，出现在CommonSafetyUnit类 checkUserPassword()方法中",
                    " password = "+password+" remainCount = "+remainCount));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        return method.commitDown("00", "12", password, remainCount);
    }

    /**
     * 验证随机数1密文并加密随机数2
     *
     * @param keyType   卡类型
     * @param meterNo   电表表号
     * @param esamNo    ESAM模块编号
     * @param enRandom1 电表返回的随机数1密文
     * @param random2   电表返回的随机数2
     * @param enRandom2 随机数2密文
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
    public int checkRand1AndEncryptRand2(String keyType, String meterNo, String esamNo, String enRandom1,
                                         String random2, StringBuffer enRandom2) {
        // 判断数据格式是否合法
        if (keyType == null || keyType.length() != 2 ||
                meterNo == null || meterNo.length() != 12 ||
                esamNo == null || esamNo.length() != 16 ||
                enRandom1 == null || enRandom1.length() != 16 ||
                random2 == null || random2.length() != 16) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        // 判断是否有接收帧接收返回数据
        if (enRandom2 == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        enRandom2.delete(0, enRandom2.length());

        return method.commitDown("00", "71", keyType + meterNo + esamNo + enRandom1 + random2, enRandom2);
    }

    /**
     * 下装安全单元权限
     *
     * @param taskSign      任务标识
     * @param authorityData 权限数据
     * @param protectCode   权限数据保护码
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
    public int downWorkCardKeyCiphertext(String taskSign, String authorityData, String protectCode) {
        if (taskSign == null || taskSign.length() != 4 ||
                authorityData == null || authorityData.length() != 90 ||
                protectCode == null || protectCode.length() != 8) {
            EventManager.getDefault().post(new HardwareErroeEvent("下装安全单元权限失败，出现在CommonSafetyUnit类 downWorkCardKeyCiphertext()方法中",
                    " taskSign = "+taskSign+" authorityData = "+authorityData+" protectCode = "+protectCode));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "0B", taskSign + authorityData + protectCode, sb);
    }

    /**
     * 获取远程身份认证数据
     *
     * @param meterNo 电表表号
     * @param keyType 密钥类型
     * @param cipher  密文数据
     * @param random1 随机数1
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
    public int remoteAuthentication(String meterNo, String keyType, StringBuffer cipher, StringBuffer random1) {
        if (meterNo == null || meterNo.length() != 16 ||
                keyType == null || keyType.length() != 2) {
            EventManager.getDefault().post(new HardwareErroeEvent("获取远程身份认证数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                    " meterNo = "+meterNo+" keyType = "+keyType+" cipher = "+cipher));
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (cipher == null || random1 == null) {
            EventManager.getDefault().post(new HardwareErroeEvent("获取远程身份认证数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                    " meterNo = "+meterNo+" keyType = "+keyType+" cipher = "+cipher));
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "41", meterNo + keyType, sb);
        if (ret == 0) {
            if (sb.length() < 32) {
                EventManager.getDefault().post(new HardwareErroeEvent("获取远程身份认证数据失败，出现在CommonSafetyUnit类 remoteAuthentication()方法中",
                        " meterNo = "+meterNo+" keyType = "+keyType+" cipher = "+cipher+" sb.length() = "+sb.length()+" ret = "+sb.length()));
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            cipher.append(sb.substring(0, 16));
            random1.append(sb.substring(16, 32));
        }
        return ret;
    }

    /**
     * 获取卡片序列号
     *
     * @param cardType 卡片类型
     * @param serialNo 序列号
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
    public int getSerialNo(String cardType, StringBuffer serialNo) {
        if (cardType == null || cardType.length() != 2) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (serialNo == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "24", cardType, sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            serialNo.delete(0, serialNo.length()).append(sb.substring(0, 16));
        }
        return ret;
    }

    /**
     * 获取操作员信息
     *
     * @param name    姓名
     * @param company 公司
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
    public int getOperatorInfo(StringBuffer name, StringBuffer company) {
        if (name == null || company == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "29", null, sb);
        if (ret == 0) {
            if (sb.length() < 60) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            // 返回字符串，由于要查找固定的值，所以需要全部转化为大写。
            String retString = sb.toString().toUpperCase();
            int index = retString.indexOf("0D00", 0);
            if (index > 0) {
                name.delete(0, name.length()).append(DataConvert.hexStringToChinese(sb.substring(0, index)));
            }
            retString = retString.substring(index + 4, retString.length());
            company.delete(0, company.length()).append(DataConvert.hexStringToChinese(retString.substring(0, retString.length())));
//            index = retString.indexOf("0D00", 0);
//            if (index > 0) {
//                company.delete(0, company.length()).append(DataConvert.hexStringToChinese(retString.substring(0, index)));
//            }
        }
        return ret;
    }

    /**
     * 加密随机数
     *
     * @param random   随机数密文
     * @param randomEn 加密后的随机数
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
    public int encryptRandom(String random, StringBuffer randomEn) {
        if (random == null || random.length() != 16) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (randomEn == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "22", random, sb);
        if (ret == 0) {
            if (sb.length() < 32) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            randomEn.append(sb.substring(0, 32));
        }
        return ret;
    }

    /**
     * 解密随机数密文
     *
     * @param cipher 随机数密文
     * @param random 解密后的随机数
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
    public int decryptRandom(String cipher, StringBuffer random) {
        if (cipher == null || cipher.length() != 32) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (random == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "23", cipher, sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            random.append(sb.substring(0, 16));
        }
        return ret;
    }

    /**
     * 获取电表控制命令密文
     *
     * @param taskSign      任务标识
     * @param random2       远程认证时电表返回的随机数2
     * @param esamNo        远程认证时电表返回的ESAM编号
     * @param meterNo       电表表号
     * @param taskData      任务数据
     * @param protectCode   任务数据保护码
     * @param controlCmd    控制数据
     * @param timeLimit     任务有效期
     * @param enControlData 控制命令密文
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
    public int getRemoteControlData(String taskSign, String random2, String esamNo, String meterNo,
                                    String taskData, String protectCode, String controlCmd, String timeLimit,
                                    StringBuffer enControlData) {
        if (taskSign == null || taskSign.length() != 4 ||
                random2 == null || random2.length() != 8 ||
                esamNo == null || esamNo.length() != 16 ||
                meterNo == null || meterNo.length() != 16 ||
                taskData == null || taskData.length() != 18 ||
                protectCode == null || protectCode.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (enControlData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        // 如果没有命令码和截止日期，就直接发送必要信息
        // 如果有附加命令，则直接加上附加命令
        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "69",
                taskSign + random2 + esamNo + meterNo + taskData + protectCode +
                        (controlCmd == null ? "" : controlCmd) + (timeLimit == null ? "" : timeLimit),
//                        taskData,
                sb);
        if (ret == 0) {
            if (sb.length() < 40) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            enControlData.delete(0, enControlData.length()).append(sb.substring(0, 40));
        }
        return ret;
    }

    /**
     * 计算保护码
     *
     * @param taskSign    任务标识
     * @param data        待计算数据
     * @param protectCode 计算保护码结果
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
    public int computeProtectCode(String taskSign, String data, StringBuffer protectCode) {
        if (taskSign == null || taskSign.length() != 4 ||
                data == null || data.length() % 2 == 1) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (protectCode == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 1);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("00", "05", taskSign + dataLength + data, sb);
        if (ret == 0) {
            if (sb.length() < 8) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            protectCode.delete(0, protectCode.length()).append(sb.substring(0, 8));
        }
        return ret;
    }

    /**
     * 计算参数设置任务数据
     *
     * @param taskSign    任务标识
     * @param random2     电表远程身份认证返回的随机数
     * @param esamNo      电表远程身份认证返回的ESANNo
     * @param meterNo     电表表号
     * @param taskData    任务数据明文
     * @param protectCode 任务数据保护码
     * @param cipherData  任务数据密文
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
    public int getSetData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                          String protectCode, StringBuffer cipherData) {
        if (taskSign == null || taskSign.length() != 4 ||
                random2 == null || random2.length() != 8 ||
                esamNo == null || esamNo.length() != 16 ||
                meterNo == null || meterNo.length() != 16 ||
                protectCode == null || protectCode.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        // 任务数据中有任务数据长度信息，所以需要单独进行判断
        // 任务数据的第六字节保存了剩下字节的长度
        if (taskData == null || taskData.length() < 12) {
            return ErrorManager.ErrorType.SafetySendError.getValue();
        } else {
            try {
                int length = Integer.parseInt(taskData.substring(10, 12));
                if (taskData.length() < (length * 2 + 12)) {
                    return ErrorManager.ErrorType.CommonParamError.getValue();
                }
            } catch (Exception e) {
                ErrorManager.ErrorType.CommonParamError.getValue();
            }
        }
        if (cipherData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "67",
                taskSign + random2 + esamNo + meterNo + taskData + protectCode,
                sb);
        if (ret == 0) {
            cipherData.delete(0, cipherData.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 计算充值任务数据
     *
     * @param taskSign    任务标识
     * @param random2     电表远程身份认证返回的随机数
     * @param esamNo      电表远程身份认证返回的ESAMNo
     * @param meterNo     电表表号
     * @param taskData    任务数据明文
     * @param protectCode 任务数据保护码
     * @param cipherData  任务数据密文
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
    public int getChargeData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                             String protectCode, StringBuffer cipherData) {
        if (taskSign == null || taskSign.length() != 4 ||
                random2 == null || random2.length() != 8 ||
                esamNo == null || esamNo.length() != 16 ||
                meterNo == null || meterNo.length() != 16 ||
                taskData == null || taskData.length() != 36 ||
                protectCode == null || protectCode.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (cipherData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "68",
                taskSign + random2 + esamNo + meterNo + taskData + protectCode,
                sb);
        if (ret == 0) {
            cipherData.delete(0, cipherData.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 计算时钟设置命令
     *
     * @param taskSign   任务标识
     * @param random2    电表远程身份认证返回的随机数
     * @param esamNo     电表远程身份认证返回的ESAMNo
     * @param meterNo    电表表号
     * @param taskData   任务数据明文
     * @param cipherData 任务数据密文
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
    public int getClockData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                            StringBuffer cipherData) {
        if (taskSign == null || taskSign.length() != 4 ||
                random2 == null || random2.length() != 8 ||
                esamNo == null || esamNo.length() != 16 ||
                meterNo == null || meterNo.length() != 16) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        // 任务数据中有任务数据长度信息，所以需要单独进行判断
        // 任务数据的第六字节保存了剩下字节的长度
        if (taskData == null || taskData.length() < 12) {
            return ErrorManager.ErrorType.SafetySendError.getValue();
        } else {
            try {
                int length = Integer.parseInt(taskData.substring(8, 10));
                if (taskData.length() < (length * 2 + 10)) {
                    return ErrorManager.ErrorType.CommonParamError.getValue();
                }
            } catch (Exception e) {
                ErrorManager.ErrorType.CommonParamError.getValue();
            }
        }
        if (cipherData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "6C",
                taskSign + random2 + esamNo + meterNo + taskData,
                sb);
        if (ret == 0) {
            cipherData.delete(0, cipherData.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
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
        return method.commitDown("00", "13", oldPassword + newPassword, sb);
    }

    /**
     * 计算传输MAC
     *
     * @param data    待计算数据
     * @param randomM 主站身份认证时返回的随机数M
     * @param mac     计算MAC结果
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
    public int computeMac(String data, String randomM, StringBuffer mac) {
        if (randomM == null || randomM.length() != 16 ||
                data == null || data.length() <= 0) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (mac == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 1);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("00", "2A", randomM + dataLength + data, sb);
        if (ret == 0) {
            if (sb.length() < 8) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            mac.delete(0, mac.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 验证传输MAC
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
    public int checkMac(String randomM, String data, String mac, StringBuffer result) {
        if (randomM == null || randomM.length() != 16 ||
                data == null || data.length() <= 0 ||
                mac == null || mac.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (result == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }
        data += mac;

        StringBuffer sb = new StringBuffer();
        String dataLength = DataConvert.getHexLength(data, 1);
        if (dataLength == null) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        int ret = method.commitDown("00", "2B", randomM + dataLength + data + mac, sb);
        if (ret == 0) {
            result.delete(0, result.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 获取安全单元固件版本
     *
     * @param version 固件版本信息
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
    public int getVersion(StringBuffer version) {
        if (version == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "FC", null, sb);
        if (ret == 0) {
            version.delete(0, version.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 修改卡计数机器
     *
     * @param cardType 卡片类型
     * @param index    索引目录
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
    public int queryIndexCount(String cardType, String index, StringBuffer result) {
        if (cardType == null || cardType.length() != 2 ||
                index == null || index.length() != 4) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        if (result == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "25", cardType + index, sb);
        if (ret == 0) {
            if (sb.length() < 38) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }

            result.append(sb.substring(0, 38));
        }
        return ret;
    }

    /**
     * 修改卡计数机器
     *
     * @param cardType 卡片类型
     * @param index    索引目录
     * @param count    计数次数
     * @param mac      传输MAC
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
    public int changeCount(String cardType, String index, String count, String mac) {
        if (cardType == null || cardType.length() != 2 ||
                index == null || index.length() != 4 ||
                count == null || count.length() != 8 ||
                mac == null || mac.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }
        StringBuffer sb = new StringBuffer();
        return method.commitDown("00", "26", cardType + index + count + mac, sb);
    }

    /**
     * 获取密钥更新任务数据
     *
     * @param taskSign    任务标识
     * @param random2     电表远程身份认证返回的随机数
     * @param esamNo      电表远程身份认证返回的ESAMNo
     * @param meterNo     电表表号
     * @param taskData    密钥任务明文数据
     * @param protectCode 任务数据保护码
     * @param cipherData  密钥更新任务密文数据
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
    public int getKeyData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                          String protectCode, StringBuffer cipherData) {
        if (taskSign == null || taskSign.length() != 4 ||
                random2 == null || random2.length() != 8 ||
                esamNo == null || esamNo.length() != 16 ||
                meterNo == null || meterNo.length() != 16 ||
                taskData == null || taskData.length() != 82 ||
                protectCode == null || protectCode.length() != 8) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (cipherData == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "6A",
                taskSign + random2 + esamNo + meterNo + taskData + protectCode,
                sb);
        if (ret == 0) {
            if (sb.length() < 80) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            cipherData.delete(0, cipherData.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 对比随机数1密文并加密随机数2
     * 用户掌机红外认证升级方案（无任务数据直接进行红外人认证）
     *
     * @param keyType       密钥类型
     * @param meterNo       电表表号
     * @param esamNo        ESAMNo
     * @param random1Cipher 随机数1密文
     * @param random2       随机数2
     * @param random2Cipher 随机数2密文
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
    public int commonCheckRand1AndEncryptRandom(String keyType, String meterNo, String esamNo,
                                                String random1Cipher, String random2, StringBuffer random2Cipher) {
        if (keyType == null || keyType.length() != 2 ||
                meterNo == null || meterNo.length() != 16 ||
                esamNo == null || esamNo.length() != 16 ||
                random1Cipher == null || random1Cipher.length() != 16 ||
                random2 == null || random2.length() != 16) {
            return ErrorManager.ErrorType.CommonParamError.getValue();
        }

        if (random2Cipher == null) {
            return ErrorManager.ErrorType.CommonBufferError.getValue();
        }

        StringBuffer sb = new StringBuffer();
        int ret = method.commitDown("00", "72",
                keyType + meterNo + esamNo + random1Cipher + random2,
                sb);
        if (ret == 0) {
            if (sb.length() < 16) {
                return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
            }
            random2Cipher.delete(0, random2Cipher.length()).append(sb.substring(0, sb.length()));
        }
        return ret;
    }

    /**
     * 安全单元固件升级
     *
     * @param fileName 固件文件绝对路径
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
     * SafetyReceiveDataError-安全单元升级文件错误
     */
    public int fileUpdate(String fileName) {
        File updateFile = new File(fileName);
        try {
            InputStream is = new FileInputStream(updateFile);
            // 读取文件块信息和校验和信息
            StringBuffer fileBlocks = new StringBuffer();
            StringBuffer checkSum = new StringBuffer();
            int ret = IOMethod.streamRead(is, fileBlocks, 0, 2);
            if (ret != 0 || fileBlocks.length() != 4) { // 读取升级文件文件块数量失败
                return ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
            }
            ret = IOMethod.streamRead(is, checkSum, 2, 1);
            if (ret != 0 || checkSum.length() != 2) { // 读取升级文件校验和失败
                return ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
            }

            // 发起安全单元程序升级请求
            StringBuffer sb = new StringBuffer();
            ret = method.commitDown("01", "01", fileBlocks.toString() + checkSum.toString(), sb);
            if (ret != 0) {
                return ret;
            }
            // 预读升级文件程序块索引和长度信息
            int blockCount = Integer.parseInt(fileBlocks.toString(), 16);
            HashMap<Integer, Pair<Integer, Integer>> blockInfo = new HashMap<>();
            int currentIndex = 3;
            StringBuffer tmpLengthSB = new StringBuffer();
            int tmpLength;
            for (int i = 0; i < blockCount; i++) {
                ret = IOMethod.streamRead(is, tmpLengthSB, currentIndex, 2);
                if (ret != 0 || tmpLengthSB.length() != 4) {
                    return ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
                }
                tmpLength = Integer.parseInt(tmpLengthSB.toString(), 16);
                blockInfo.put(i + 1, new Pair<>(currentIndex, tmpLength));
                currentIndex += 2;
                currentIndex += tmpLength;
            }

            // 预读升级文件成功，则开始进行升级过程
            StringBuffer mainSign = new StringBuffer();
            StringBuffer control = new StringBuffer();
            StringBuffer status = new StringBuffer();
            StringBuffer data = new StringBuffer();
            do {
                ret = method.getFrame(mainSign, control, status, data);
                if (ret != 0) {
                    return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
                }
                if (!"01".equals(mainSign.toString())) { // 主功能标识不正确
                    return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
                }
                if ("02".equals(control.toString())) { // 请求升级文件数据块
                    if (data.length() != 4) {
                        return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
                    }
                    int index = Integer.parseInt(data.toString(), 16);
                    if (blockInfo.containsKey(Integer.valueOf(index))) {
                        StringBuffer blockValue = new StringBuffer();
                        ret = IOMethod.streamRead(is, blockValue,
                                blockInfo.get(Integer.valueOf(index)).first,
                                blockInfo.get(Integer.valueOf(index)).second);
                        if (ret != 0 || blockValue.length() != blockInfo.get(index).second) {
                            return ret | ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
                        }
                        ret = method.sendFrame("01", "82", "00", data.toString() + blockInfo.toString());
                        if (ret != 0) {
                            return ret;
                        }
                    } else {
                        return ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
                    }
                } else if ("03".equals(control.toString())) { // 升级完成
                    method.sendFrame("01", "91", "00", null); // 发送升级完成相应
                    break;
                } else { // 读取到的请求错误
                    return ErrorManager.ErrorType.SafetyReceiveDataError.getValue();
                }
            } while (true);

            return 0;

        } catch (Exception e) {
            return ErrorManager.ErrorType.SafetyUpdateFileError.getValue();
        }
    }

    /**
     * 获取操作员卡类型
     *
     * @return 操作员卡类型
     */
    public String getUserCardType() {
        return "01";
    }

    /**
     * 获取业务卡类型
     *
     * @return 业务卡类型
     */
    public String getWorkCardType() {
        return "02";
    }

    /**
     * 获取公钥类型
     *
     * @return 公钥类型
     */
    public String getPublicKeyType() {
        return "00";
    }

    /**
     * 获取私钥类型
     *
     * @return 私钥类型
     */
    public String getPrivateKeyType() {
        return "01";
    }

    /**
     * 获取红外权限类型
     *
     * @return 红外权限类型
     */
    public String getInfraAuthorityType() {
        return "11";
    }

    /**
     * 获取控制权限类型
     *
     * @return 控制权限类型
     */
    public String getControlAuthorityType() {
        return "02";
    }

    /**
     * 获取1类参数权限类型
     *
     * @return 1类参数权限类型
     */
    public String getParam1AuthorityType() {
        return "05";
    }

    /**
     * 获取2类参数权限类型
     *
     * @return 2类参数权限类型
     */
    public String getParam2AuthorityType() {
        return "03";
    }

    /**
     * 获取第一套费率设置权限类型
     *
     * @return 第一套费率权限类型
     */
    public String getRate1AuthorityType() {
        return "06";
    }

    /**
     * 获取第二套费率设置权限类型
     *
     * @return 第二套费率权限类型
     */
    public String getRate2AuthorityType() {
        return "07";
    }

    /**
     * 获取充值权限类型
     *
     * @return 充值权限类型
     */
    public String getChargeAuthorityType() {
        return "04";
    }
}
