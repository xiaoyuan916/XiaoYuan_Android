package com.sgcc.pda.hardware.model.founation.safetyunit;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 安全单元功能接口
 */

public interface ISafetyUnit {
    /**
     * 安全单元初始化
     *
     * @return 0-成功 其它-错误码
     */
    int init();

    /**
     * 安全单元同步
     *
     * @return 0-成功 其它-错误码
     */
    int sync();

    /**
     * 获取安全单元工作状态
     *
     * @param status 状态字
     * @return 0-成功 其它-错误码
     */
    int getStatus(StringBuffer status);

    /**
     * 获取操作员卡号,业务卡号以及权限
     *
     * @param operator      操作员卡号
     * @param operatorPower 权限
     * @param cover         掩码信息
     * @return 0-成功 其他-错误码
     */
    int getUserCodeAndPermission(StringBuffer operator, StringBuffer operatorPower, StringBuffer cover);

    /**
     * 注销任务标识
     *
     * @param taskSign 任务标识
     * @return 0-成功 其他-错误码
     */
    int logOffTaskSign(String taskSign);

    /**
     * 注册任务标志
     *
     * @param taskSign 任务标识
     * @param operator 操作员卡号
     * @param cipher   任务标识数据密文
     * @param mac      密文数据MAC
     * @param randomM  身份认证时返回的认证随机数
     * @return 0-成功 其他-错误码
     */
    int registerHostCreatTaskSign(String taskSign, String operator, String cipher, String mac, String randomM);

    /**
     * 红外认证获取随机数1
     *
     * @param cardType 卡片类型 01 操作员卡  02 业务卡
     * @param random1  随机数1
     * @return 0-成功 其他-错误码
     */
    int getRandom1(String cardType, StringBuffer random1);

    /**
     * 验证用户密码
     *
     * @param password    用户密码
     * @param remainCount 返回剩余验证次数
     * @return 0-成功 其它-错误码
     */
    int checkUserPassword(String password, StringBuffer remainCount);

    /**
     * 验证随机数1密文并加密随机数2
     *
     * @param keyType   密钥类型 00 公钥状态  01 私钥状态
     * @param meterNo   电表表号
     * @param esamNo    ESAM模块编号
     * @param enRandom1 电表返回的随机数1密文
     * @param random2   电表返回的随机数2
     * @param enRandom2 随机数2密文
     * @return 0-成功 其他-错误码
     */
    int checkRand1AndEncryptRand2(String keyType, String meterNo, String esamNo, String enRandom1,
                                  String random2, StringBuffer enRandom2);

    /**
     * 下装安全单元权限
     *
     * @param taskSign      任务标识
     * @param authorityData 权限数据
     * @param protectCode   权限数据保护码
     * @return 0-成功 其他-错误码
     */
    int downWorkCardKeyCiphertext(String taskSign, String authorityData, String protectCode);

    /**
     * 获取远程身份认证数据
     *
     * @param meterNo 电表表号
     * @param keyType 密钥类型
     * @param cipher  密文数据
     * @param random1 随机数1
     * @return 0-成功 其他-错误码
     */
    int remoteAuthentication(String meterNo, String keyType, StringBuffer cipher, StringBuffer random1);

    /**
     * 获取卡片序列号
     *
     * @param cardType 卡片类型
     * @param serialNo 序列号
     * @return 0-成功 其它-错误码
     */
    int getSerialNo(String cardType, StringBuffer serialNo);

    /**
     * 获取操作员信息
     *
     * @param name    姓名
     * @param company 公司
     * @return 0-成功 其它-错误码
     */
    int getOperatorInfo(StringBuffer name, StringBuffer company);

    /**
     * 加密随机数
     *
     * @param random   随机数密文
     * @param randomEn 加密后的随机数
     * @return 0-成功 其它-错误码
     */
    int encryptRandom(String random, StringBuffer randomEn);

    /**
     * 解密随机数密文
     *
     * @param cipher 随机数密文
     * @param random 解密后的随机数
     * @return 0-成功 其它-错误码
     */
    int decryptRandom(String cipher, StringBuffer random);

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
     * @return 0-成功 其他-错误码
     */
    int getRemoteControlData(String taskSign, String random2, String esamNo, String meterNo,
                             String taskData, String protectCode, String controlCmd, String timeLimit,
                             StringBuffer enControlData);

    /**
     * 计算保护码
     *
     * @param taskSign    任务标识
     * @param data        待计算数据
     * @param protectCode 计算保护码结果
     * @return 0-成功 其它-错误码
     */
    int computeProtectCode(String taskSign, String data, StringBuffer protectCode);

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
     * @return 0-成功 其它-错误码
     */
    int getSetData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                   String protectCode, StringBuffer cipherData);

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
     * @return 0-成功 其它-错误码
     */
    int getChargeData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                      String protectCode, StringBuffer cipherData);

    /**
     * 计算时钟设置命令
     *
     * @param taskSign   任务标识
     * @param random2    电表远程身份认证返回的随机数
     * @param esamNo     电表远程身份认证返回的ESAMNo
     * @param meterNo    电表表号
     * @param taskData   任务数据明文
     * @param cipherData 任务数据密文
     * @return 0-成功 其它-失败
     */
    int getClockData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                     StringBuffer cipherData);

    /**
     * 修改操作员密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 0-成功 其它-错误码
     */
    int changePassword(String oldPassword, String newPassword);

    /**
     * 计算传输MAC
     *
     * @param data    待计算数据
     * @param randomM 主站身份认证时返回的随机数M
     * @param mac     计算MAC结果
     * @return 0-成功 其它-错误码
     */
    int computeMac(String data, String randomM, StringBuffer mac);

    /**
     * 验证传输MAC
     *
     * @param randomM 主站身份认证时返回的随机数M
     * @param data    待计算数据
     * @param mac     已有传输MAC
     * @param result  验证结果
     * @return 0-成功 其它-错误码
     */
    int checkMac(String randomM, String data, String mac, StringBuffer result);

    /**
     * 获取安全单元固件版本
     *
     * @param version 固件版本信息
     * @return 0-成功 其它-错误码
     */
    int getVersion(StringBuffer version);

    /**
     * 查询索引目录计数次数
     *
     * @param cardType 卡片类型
     * @param index    目录索引
     *                 操作员：0X3F01  业务员：0xDF01-0xDF04
     * @param result   返回结果
     * @return 0-成功 其它-错误码
     */
    int queryIndexCount(String cardType, String index, StringBuffer result);

    /**
     * 修改卡计数机器
     *
     * @param cardType 卡片类型
     * @param index    索引目录
     * @param count    计数次数
     * @param mac      传输MAC
     * @return 0-成功 其它-错误码
     */
    int changeCount(String cardType, String index, String count, String mac);

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
     * @return 0-成功 其它-失败
     */
    int getKeyData(String taskSign, String random2, String esamNo, String meterNo, String taskData,
                   String protectCode, StringBuffer cipherData);

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
     * @return 0-成功 其它-错误码
     */
    int commonCheckRand1AndEncryptRandom(String keyType, String meterNo, String esamNo,
                                         String random1Cipher, String random2, StringBuffer random2Cipher);

    /**
     * 安全单元固件升级
     *
     * @param fileName 固件文件绝对路径
     * @return 0-成功 其它-错误码
     */
    int fileUpdate(String fileName);

    /**
     * 获取操作员卡类型
     *
     * @return 操作员卡类型
     */
    String getUserCardType();

    /**
     * 获取业务卡类型
     *
     * @return 业务卡类型
     */
    String getWorkCardType();

    /**
     * 获取公钥类型
     *
     * @return 公钥类型
     */
    String getPublicKeyType();

    /**
     * 获取私钥类型
     *
     * @return 私钥类型
     */
    String getPrivateKeyType();

    /**
     * 获取红外权限类型
     *
     * @return 红外权限类型
     */
    String getInfraAuthorityType();

    /**
     * 获取控制权限类型
     *
     * @return 红外权限类型
     */
    String getControlAuthorityType();

    /**
     * 获取1类参数权限类型
     *
     * @return 1类参数权限类型
     */
    String getParam1AuthorityType();

    /**
     * 获取2类参数权限类型
     *
     * @return 2类参数权限类型
     */
    String getParam2AuthorityType();

    /**
     * 获取第一套费率设置权限类型
     *
     * @return 第一套费率权限类型
     */
    String getRate1AuthorityType();

    /**
     * 获取第二套费率设置权限类型
     *
     * @return 第二套费率权限类型
     */
    String getRate2AuthorityType();

    /**
     * 获取充值权限类型
     *
     * @return 充值权限类型
     */
    String getChargeAuthorityType();
}
