package com.sgcc.pda.hardware.model.founation.safetyunit;

/**
 * 创建者 陶蕾
 * 创建时间 2017/10/10
 * 安全单元2.0功能接口
 */
public interface ISafetyUnit2 {

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
     * @return 0-成功 其他-错误码
     */
    int getSafeUnitMessage(StringBuffer status, StringBuffer appVersion, StringBuffer version, StringBuffer cEsamNum,
                           StringBuffer operator, StringBuffer operatorPower, StringBuffer cover, StringBuffer operatorInfo,
                           StringBuffer yEsamNum, StringBuffer yEsamVersion, StringBuffer mainStationCertificateVersion,
                           StringBuffer terminalCertificateVersion, StringBuffer mainStationCertificateNum,
                           StringBuffer terminalCertificateNum, StringBuffer counter, StringBuffer encryptNum,
                           StringBuffer labelSecretVersion, StringBuffer mainStationCertificate, StringBuffer terminalCertificate
    );


    /**
     * 验证操作员密码
     *
     * @param password    用户密码
     * @param remainCount 返回剩余验证次数
     * @return 0-成功 其它-错误码
     */
    int checkUserPassword(String password, StringBuffer remainCount);

    /**
     * 修改操作员密码
     *
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 0-成功 其它-错误码
     */
    int changePassword(String oldPassword, String newPassword);

    /**
     * 应用层身份认证
     *
     * @param m1 密文m1
     * @param s1 m1签名
     * @param m2 密文m2
     * @param s2 m2签名
     * @return 0-成功 其它-错误码
     */
    int loginIdauth(String m1, String s1, StringBuffer m2, StringBuffer s2);


    /**
     * 从安全单元获取随机数
     *
     * @param esamType     ESAM类型  01，操作员ESAM；02业务ESAM
     * @param randomLength 随机数长度  4，8，16
     * @param type         ESAM类型
     * @param random       随机数
     * @return 0-成功 其它-错误码
     */
    int getRandomNum(String esamType, String randomLength, StringBuffer type, StringBuffer random);

    /**
     * 锁定安全单元
     *
     * @param data 认证数据
     * @return 0-成功 其它-错误码
     */
    int lockingSafeUnit(String data);

    /**
     * 二次发行安全单元
     *
     * @param esamType esam类型  01，C-ESAM；02Y-ESAM；
     * @param fxData   发行数据  2+N
     * @param esamData ESAM返回数据内容
     * @return 0-成功 其它-错误码
     */
    int safyUnitTwoSend(String esamType, String fxData, StringBuffer esamData);

    /**
     * 透明转发ESAM指令
     *
     * @param esamType    esam类型 01，C-ESAM；02Y-ESAM；
     * @param data        转发数据内容 转发数据长度+转发数据内容
     * @param rtnEsamType esam类型 01，C-ESAM；02Y-ESAM；
     * @param rtnData     转发返回数据内容
     * @return 0-成功 其它-错误码
     */
    int transmitEsamOrder(String esamType, String data, StringBuffer rtnEsamType, StringBuffer rtnData);

    /**
     * 转加密初始化
     *
     * @param data   主站数据
     * @param result 执行结果数据
     * @return 0-成功 其它-错误码
     */
    int encryptInitialize(String data, StringBuffer result);

    /**
     * 置离线计数器
     *
     * @param data 数据
     * @return 0-成功 其它-错误码
     */
    int resetOflineCounter(String data);

    /**
     * 本地密钥计算MAC
     *
     * @param randomM 随机数M
     * @param data    2+N 数据长度+数据
     * @param mac     Mac
     * @return 0-成功 其它-错误码
     */
    int localKeyComputeMac(String randomM, String data, StringBuffer mac);


    /**
     * 本地密钥验证MAC
     *
     * @param randomM 主站身份认证时返回的随机数M
     * @param data    待计算数据
     * @param mac     已有传输MAC
     * @param result  验证结果
     * @return 0-成功 其它-错误码
     */
    int localKeyCheckMac(String randomM, String data, String mac, StringBuffer result);

    /**
     * 会话密钥计算MAC
     *
     * @param data 2+N 数据长度+数据
     * @param mac  Mac
     * @return 0-成功 其它-错误码
     */
    int sessionKeyComputeMac(String data, StringBuffer mac);


    /**
     * 会话密钥验证MAC
     *
     * @param data   待计算数据
     * @param mac    已有传输MAC
     * @param result 验证结果
     * @return 0-成功 其它-错误码
     */
    int sessionKeyCheckMac(String data, String mac, StringBuffer result);


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
     * @return 0-成功 其它-错误码
     */
    int meterInfraredAuthentication(String meterNo, String esamNo, String mRandom1, String infraredAuthority1,
                                    String infraredAuthority2, String random2, String curTime, StringBuffer mRandom2);

    /**
     * 电能表红外认证（09、13）
     *
     * @param meterNo            表号  高位在前（非颠倒），高2字节补0x00
     * @param esamNo             ESAM序列号
     * @param mRandom1           随机数1密文
     * @param infraredAuthority1  红外认证权限
     * @param infraredAuthority2  身份认证权限
     * @param random2            随机数2
     * @param curTime            掌机当前时间  YYMMDDhhmmss
     * @param type               公私钥标志 0为公钥，1为私钥
     * @param mRandom2           返回-随机数2密文
     * @return 0-成功 其它-错误码
     */
    int meterInfraredAuthentication1(String meterNo, String esamNo, String mRandom1, String infraredAuthority1,
                                     String infraredAuthority2, String random2, String curTime, String type, StringBuffer mRandom2);

    /**
     * 远程身份认证（09、13）
     *
     * @param meterNo                 表号 高位在前（非颠倒），高2字节补0x00；
     * @param authenticationAuthority 身份认证权限
     * @param curTime                 掌机当前时间  YYMMDDhhmmss
     * @param keyType                 公私钥标志 0为公钥，1为私钥
     * @param cipher                  身份认证密文
     * @param random1                 随机数1
     * @return 0-成功 其它-错误码
     */
    int remoteAuthentication(String meterNo, String authenticationAuthority, String curTime, String keyType, StringBuffer cipher, StringBuffer random1);


    /**
     * 电能表控制（09、13）
     *
     * @param meterNo                表号  高位在前（非颠倒），高2字节补0x00
     * @param random                 随机数  身份认证产生的随机数
     * @param remoteControlAuthority 远程控制权限
     * @param orderCode              命令码
     * @param endTime                截止时间（yyMMddhhmmss 6字节）
     * @param mac1                   4字节MAC
     * @param curTime                掌机当前时间
     * @param cipher                 控制数据密文
     * @param mac                    MAC
     * @return 0-成功 其它-错误码
     */
    int meterControl(String meterNo, String random, String remoteControlAuthority, String orderCode, String endTime, String mac1, String curTime, StringBuffer cipher, StringBuffer mac);

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
     * @return 0-成功 其它-错误码
     */
    int meterParamSet(String meterNo, String random, String paramAuthority, String paramType, String dataSign, String paramValue, String mac1, String curTime,
                      StringBuffer length, StringBuffer param, StringBuffer mac);

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
     * @return 0-成功 其它-错误码
     */
    int meterCheckTime(String meterNo, String random, String checkAuthority, String dataSign, String paramValue, String mac1, String week, String curTime,
                       StringBuffer length, StringBuffer cipher, StringBuffer mac);

    /**
     * 电能表密钥更新(09)
     *
     * @param meterNo      表号  高位在前（非颠倒），高2字节补0x00
     * @param random       随机数  身份认证产生的随机数
     * @param athorityData 权限数据
     * @param secret       密钥密文
     * @param curTime      掌机当前时间  YYMMDDhhmmss
     * @param keySign      密钥状态标志位  00：密钥恢复  01：密钥下装
     * @param length       数据长度
     * @param cipher       密钥数据  密钥信息（4）+MAC（4）+密钥密文（32）
     * @return 0-成功 其它-错误码
     */
    int secretUpdate09(String meterNo, String random, String athorityData, String secret, String curTime, String keySign, StringBuffer length, StringBuffer cipher);

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
     * @return 0-成功 其它-错误码
     */
    int secretUpdate13(String meterNo, String random, String athorityData, String cipher, String curTime, StringBuffer secret, StringBuffer mac);



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
    int meterRecharge(String meterNo, String random, String athorityData1, String athorityData2, String taskData, String curTime,
                      StringBuffer rechargeData);

    /**
     * 698电能表会话协商
     *
     * @param secretData 密钥包
     * @param meterNo    若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param counter    会话计数器(698电表中读取)
     * @param rntData    会话数据
     * @return 0-成功 其它-错误码
     */
    int meterSessionTalk698(String secretData, String meterNo, String counter, StringBuffer rntData);

    /**
     * 与外设进行秘钥协商，获取M1和Mac1
     *
     * @param kid      秘钥kid。
     * @param wesamNum wesam芯片序列号
     * @param hhjsq    外设返回的会话计数器
     * @param M1       安全单元返回的密文1
     * @param Mac1     安全单元返回的Mac1
     * @return 0：成功；其他-错误码
     */
    int wsSecretXieShang(String kid, String wesamNum, String hhjsq, StringBuffer M1, StringBuffer Mac1);

    /**
     * 与外设进行秘钥协商确认
     *
     * @param kid      秘钥kid。
     * @param wesamNum wesam芯片序列号
     * @param M2       外设返回的密文2
     * @param Mac2     外设返回的Mac2
     * @return 0：成功；其他-错误码
     */
    int wsSecretXieShangConfirm(String kid, String wesamNum, String M2,String Mac2);

    /**
     * 与外设秘钥协商后进行加密数据
     *
     * @param wsType     读取外设信息时返回的类型
     * @param data      要加密的数据
     * @param encryptDataLength 返回的加密后数据的长度  密文+mac的长度
     * @param encryptData       返回的加密后的数据  密文+mac的数据
     * @return 0：成功；其他-错误码
     */
    int wsEncryptData(String wsType,String data, StringBuffer encryptDataLength, StringBuffer encryptData);

    /**
     * 与外设秘钥协商后进行解密数据
     *
     * @param wsType     读取外设信息时返回的类型
     * @param encryptData      要解密的数据  密文+mac的数据
     * @param dataLength 返回的解密后数据的长度
     * @param data       返回的解密后的数据     明文
     * @return 0：成功；其他-错误码
     */
    int wsDecryptData(String wsType,String encryptData, StringBuffer dataLength, StringBuffer data);


    /**
     * 698电能表会话协商验证
     *
     * @param secretData  密钥包
     * @param meterNo     若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param secretSign  密钥标识
     * @param sessionData 会话数据
     * @return 0-成功 其它-错误码
     */
    int meterSessionTalk698Check(String secretData, String meterNo, String secretSign, String sessionData);

    /**
     * 698电能表安全数据生成
     *
     * @param taskData    主站任务数据   1字节安全模式字+任务参数类型+2字节应用层数据长度+应用层数据+4字节保护码（根据应用层数据长度可得知整个任务数据长度）
     * @param rtnData     返回数据
     * @return 0-成功 其它-错误码
     */
    int meterSafeData698(String taskData, StringBuffer rtnData);

    /**
     * 698电能表安全传输解密
     *
     * @param secretData      电表返回的密文链路数据
     * @param rtnData       应用层数据明文
     * @return 0-成功 其它-错误码
     */
    int meterSafeTransJieMi698(String secretData, StringBuffer rtnData);

    /**
     * 698电能表抄读数据验证
     *
     * @param disFactor 分散因子
     * @param data      应用层数据明文
     * @param rtnData   返回的应用层数据明文
     * @return 0-成功 其它-错误码
     */
    int meterDataCheck698(String disFactor, String data, StringBuffer rtnData);

    /**
     * 698电能表抄读ESAM参数验证
     *
     * @param meterNo 若为公钥：使用ESAM序列号   若为私钥：用表号高位在前（非颠倒），高2字节补0x00；
     * @param oad     OAD
     * @param data    应用层数据
     * @return 0-成功 其它-错误码
     */
    int meterEsamParamCheck698(String meterNo, String oad, String data);

    /**
     *  安全单元2.0 升级命令1
     */
    int upgradeCommand1(String blockCountHexStr, String compareCodeSumHexStr);

    /**
     *  安全单元2.0 升级命令2
     * @param blockNoHexStr 数据块块号
     * @param blockData 数据块
     * @return
     */
    int upgradeCommand2_send(String blockNoHexStr, String blockData);

    /**
     *  安全单元2.0 定程序跳转命令
     */


    int upgradeCommand_get(StringBuffer mainSign,StringBuffer control,StringBuffer data);

    /**
     *  安全单元2.0 切换 A ,B 区
     * @param area 01 A   02  B
     * @param data
     * @return
     */
    int switchArea(String area,StringBuffer data);
}
