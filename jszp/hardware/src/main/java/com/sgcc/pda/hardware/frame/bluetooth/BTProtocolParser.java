package com.sgcc.pda.hardware.frame.bluetooth;


import android.text.TextUtils;
import android.util.Log;

import com.sgcc.pda.hardware.frame.edevice.Frame;
import com.sgcc.pda.hardware.util.DataConvert;


public class BTProtocolParser {
    private static final String TAG = "BTProtocolParser";

    public byte BTProtocolCL(byte[] buf, StringBuilder strb) {
        Log.i(TAG, "run to here");
        byte fn = buf[1];
        int len = buf[3] | buf[4] << 8;
        Log.i(TAG, "fn====" + fn);
        Log.i(TAG, "len====" + len);
        byte[] datas = new byte[len];
        for (int i = 0; i < len; i++) {
            datas[i] = buf[6 + i];
        }
        switch (fn) {
            case 0x00:
                if (len > 10) {
                    strb.append("USIM卡号：");
                    for (int i = 0; i < len; i++) {
                        //String hex = Integer.toHexString((((int) datas[i]) & 0xFF));
                        strb.append(datas[i] - 0x30);
                    }
                    break;
                } else if (len == 0x01) {
                    return datas[0];
                } else if (len == 0x00) {

                } else {
                    return (byte) (datas[0] + 0x20);//带参数
                }
            case 0x02:
                if (len == 0x02) {
                    if (datas[0] == 0x03) {
                        strb.append("模块选择成功\n");
                        return 0x02;
                    } else if (datas[0] == 0x04) {
                        strb.append("抄表测试结束\n");
                        return 0x05;
                    }
                }
                //add wgj 2016-09-06
            case (byte) 0x82:
                if (len == 0x02) {
                    if (datas[len - 1] == 0x01) {
                        strb.append("不能识别插入的集中器模块\n");

                    } else if (datas[len - 1] == 0x02) {
                        strb.append("集中器模块异常\n");
                    }
                    return datas[len - 1];

                }
                break;
            case 0x03:
                if (len == 0x02) {
                    if (datas[1] == 0x00) {
                        strb.append("开始组网\n");
                    } else if (datas[1] == 0x01) {
                        strb.append("组网成功\n");
                    } else if (datas[1] == 0x02) {
                        strb.append("组网失败\n");
                    }
                }
                return 0x03;
            case 0x04://sim卡测试参数设置
                if (datas[0] == 0x01) {
                    strb.append("设置成功\n");
                }
                return 0x04;
            case (byte) 0x84:
                if (datas[0] == 0x01) {
                    strb.append("IP地址不合法\n");
                } else if (datas[0] == 0x02) {
                    strb.append("端口号不合法\n");
                }
                return (byte) 0x84;
            case 0x40:
                if (len == 0x01)
                    return (byte) (datas[0] + 0x10);
                else
                    return (byte) (datas[0] + 0x30);//带参数
            case 0x0a:// 读取参取
                if (datas[0] == 0x01) {
                    strb.append("软件版本：");
                    for (int i = 1; i < len; i++) {
                        String hex = Integer.toHexString(((int) datas[i] & 0xFF));
                        if (hex.length() == 1) {
                            hex = '0' + hex;
                        }
                        strb.append(hex + " ");
                    }
                    strb.append("\n");
                } else if (datas[0] == 0x02) {
                    strb.append("终端地址：");

                    String hex = Integer.toHexString(((int) datas[2] & 0xFF));
                    if (hex.length() == 1) {
                        hex = '0' + hex;
                    }
                    strb.append(hex + " ");
                    hex = Integer.toHexString(((int) datas[1] & 0xFF));
                    if (hex.length() == 1) {
                        hex = '0' + hex;
                    }
                    strb.append(hex + " ");
                    hex = Integer.toHexString(((int) datas[4] & 0xFF));
                    if (hex.length() == 1) {
                        hex = '0' + hex;
                    }
                    strb.append(hex + " ");
                    hex = Integer.toHexString(((int) datas[3] & 0xFF));
                    if (hex.length() == 1) {
                        hex = '0' + hex;
                    }
                    strb.append(hex + " ");

                    strb.append("\n");
                } else if (datas[0] == 0x03) {
                    strb.append("电池电量：");

                    if (datas[2] == 0) {
                        Log.i(TAG, "power = " + datas[1] + "\n");
                        strb.append((datas[1] & 0xFF) + "%");
                    } else if (datas[2] == 1) {
                        Log.i(TAG, "power = " + datas[1] + "\n");
                        strb.append("正在充电，无法读取");
                    } else if (datas[2] == 2)
                        strb.append("100%");
                    strb.append("\n");
                } else if (datas[0] == 0x04) {
                    strb.append("终端地址设置成功\n");
                } else if (datas[0] == 0x68)
                    return 0x40;
                break;
            case 0x0B:
                if (datas.length == 7) {
                    strb.append("抄表成功:\n");
                    if ((datas[datas.length - 1] & 0x01) == 0x01) {
                        strb.append("单相表测试成功");
                    }
                    if ((datas[datas.length - 1] & 0x02) == 0x02) {
                        strb.append("A相测试成功");
                    }
                    if ((datas[datas.length - 1] & 0x04) == 0x04) {
                        strb.append("B相测试成功");
                    }
                    if ((datas[datas.length - 1] & 0x08) == 0x08) {
                        strb.append("C相测试成功");
                    }
                }
//                if (datas.length == 7) {
//                    strb.append("抄表失败，未抄到数据");
//                    if (datas[datas.length - 1] == 0x00) {
//                        strb.append("\n测试结果:采集器无回应报文");
//                    } else if (datas[datas.length - 1] == 0x01) {
//                        strb.append("\n测试结果:清除载波数据错误");
//                    } else if (datas[datas.length - 1] == 0x02) {
//                        strb.append("\n测试结果:添加载波节点错误");
//                    } else if (datas[datas.length - 1] == 0x03) {
//                        strb.append("\n测试结果:同步载波数据错误");
//                    } else if (datas[datas.length - 1] == 0x04) {
//                        strb.append("\n测试结果:检查载波地址错误");
//                    } else if (datas[datas.length - 1] == 0x05) {
//                        strb.append("\n测试结果:查询载波节点总数错误");
//                    } else if (datas[datas.length - 1] == 0x06) {
//                        strb.append("\n测试结果:载波节点总数超范围或实际节点数大于载波支持节点总数");
//                    } else if (datas[datas.length - 1] == 0x07) {
//                        strb.append("\n测试结果:电表不存在");
//                    } else if (datas[datas.length - 1] == 0x08) {
//                        strb.append("\n测试结果:微功率无线组网失败");
//                    }
////                    strb.append("\n测试结果:被测设备异常");
//                } else {
//                    if ((datas[14] & 0xFF) == 0x81 || (datas[14] & 0xFF) == 0x91) {
//                        strb.append("抄表成功;");
//                        strb.append("\n电表日期:");
//                        strb.append(String.format("%02d", hextoint(datas[len - 3])) + "年");
//                        strb.append(hextoint(datas[len - 4]) + "月");
//                        strb.append(hextoint(datas[len - 5]) + "日");
//                        if (hextoint(datas[len - 6]) == 0) {
//                            strb.append(" 星期日;");
//                        } else {
//                            strb.append(" 星期" + hextoint(datas[len - 6]) + ";");
//                        }
//                        strb.append("\n测试结果:被测设备正常");
//                    } else if ((datas[14] & 0xFF) == 0xC1 || (datas[14] & 0xFF) == 0xD1) {
//                        strb.append("抄表失败，表计规约不匹配");
//                    }
//                }
                return 0x0B;
            //add wgj  2016-09-06
            case (byte) 0x8B:
                if (datas.length == 2) {
                    if (datas[0] == 0x01) {
                        strb.append("测试结果：调试仓错误\n");
                        if (datas[datas.length - 1] == 0x00) {
                            strb.append("采集器无回应报文");
                        } else if (datas[datas.length - 1] == 0x01) {
                            strb.append("清除载波数据错误");
                        } else if (datas[datas.length - 1] == 0x02) {
                            strb.append("添加载波节点错误");
                        } else if (datas[datas.length - 1] == 0x03) {
                            strb.append("同步载波数据错误");
                        } else if (datas[datas.length - 1] == 0x04) {
                            strb.append("检查载波地址错误");
                        } else if (datas[datas.length - 1] == 0x05) {
                            strb.append("查询载波节点总数错误");
                        } else if (datas[datas.length - 1] == 0x06) {
                            strb.append("载波节点总数超范围或实际节点数大于载波支持节点总数");
                        } else if (datas[datas.length - 1] == 0x07) {
                            strb.append("电表不存在");
                        } else if (datas[datas.length - 1] == 0x08) {
                            strb.append("电表不存在");
                        } else if (datas[datas.length - 1] == 0x09) {
                            strb.append("电表不存在");
                        } else if (datas[datas.length - 1] == 0x0A) {
                            strb.append("帧命令错误");
                        }
                    } else if (datas[0] == 0x02) {
                        strb.append("测试结果：错误\n");
                        if (datas[datas.length - 1] == 0x00) {
                            strb.append("通讯超时");
                        } else if (datas[datas.length - 1] == 0x01) {
                            strb.append("无效数据单元");
                        } else if (datas[datas.length - 1] == 0x02) {
                            strb.append("长度错误");
                        } else if (datas[datas.length - 1] == 0x03) {
                            strb.append("校验错误");
                        } else if (datas[datas.length - 1] == 0x04) {
                            strb.append("信息类不存在");
                        } else if (datas[datas.length - 1] == 0x05) {
                            strb.append("格式错误");
                        } else if (datas[datas.length - 1] == 0x06) {
                            strb.append("表号重复");
                        } else if (datas[datas.length - 1] == 0x07) {
                            strb.append("表号不存在");
                        } else if (datas[datas.length - 1] == 0x08) {
                            strb.append("电表应用层无应答");
                        } else if (datas[datas.length - 1] == 0x09) {
                            strb.append("主节点忙");
                        } else if (datas[datas.length - 1] == 0x0A) {
                            strb.append("主节点不支持此命令");
                        } else if (datas[datas.length - 1] == 0x0B) {
                            strb.append("从节点无应答");
                        } else if (datas[datas.length - 1] == 0x0C) {
                            strb.append("从节点不在网内");
                        }
                    }
                }
                return (byte) 0x8B;
            case BlueToothUtil.REC_UPGRADE_PATCH_RIGHT:
                return BlueToothUtil.REC_UPGRADE_PATCH_RIGHT;
            case BlueToothUtil.REC_UPGRADE_RIGHT:
                strb.append("升级成功，正在重启...\n");
                return BlueToothUtil.REC_UPGRADE_RIGHT;
            case BlueToothUtil.REC_UPGRADE_WRONG:
                strb.append("升级失败\n");
                return BlueToothUtil.REC_UPGRADE_WRONG;
            case 0x0d:
                break;
            default:
                break;
        }
        return 0;
    }


    /**
     * 解析外设返回数据
     *
     * @param buf    外设返回的数据域
     * @param result 解析结果
     * @return 功能码
     */
    public int decodeProtocol(byte[] buf, StringBuilder result) {
        Frame frame = Frame.fromBytes(buf);
        String data = frame.getData();
        int function = Integer.parseInt(frame.getFunction(), 16);
        switch (function) {
            case Frame.FN_SIMJC_HQSIMKZX://获取sim卡信息
                result.append(simInfo(data));
                return Frame.FN_SIMJC_HQSIMKZX;
            case Frame.FN_SIMJC_XFSIMJCCS://下发sim卡检测参数
                if (frame.getDataLength().equals("0000")) {
                    result.append("下发sim卡参数成功!");
                    return Frame.FN_SIMJC_XFSIMJCCS_YES;
                } else {
                    result.append("下发sim卡参数失败:" + errorCode_A2(frame.getData()));
                    return Frame.FN_SIMJC_XFSIMJCCS_NO;
                }
//                switch (frame.getDataLength()) {
//                    case "0000":
//                         result.append("下发sim卡参数成功!");
//                        break;
//                    case "0100":
//                        result.append("下发sim卡参数失败" + frame.getData());
//                        break;
//                }

            case Frame.FN_SIMJC_HQSIMKJCJG://获取sim卡检测结果
                result.append(simJCJG(data));
                return Frame.FN_SIMJC_HQSIMKJCJG;
            /***
             * 载波检测结果
             ***/
            case Frame.FN_ZBJC_DNBZJZBJC://电能表整机载波检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_DNBZJZBJC;
            case Frame.FN_ZBJC_CJQZJZBJC://采集器整机载波检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_CJQZJZBJC;
            case Frame.FN_ZBJC_JZQZJZBJC://集中器整机载波检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_JZQZJZBJC;
            case Frame.FN_ZBJC_DNBZBMKJC://电能表载波模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_DNBZBMKJC;
            case Frame.FN_ZBJC_CJQZBMKJC://采集器载波模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_CJQZBMKJC;
            case Frame.FN_ZBJC_JZQZBMKJC://集中器载波模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_JZQZBMKJC;
            case Frame.FN_ZBJC_DNBZJJC://电能表主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_DNBZJJC;
            case Frame.FN_ZBJC_CJQZJJC://采集器主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_CJQZJJC;
            case Frame.FN_ZBJC_JZQZJJC://集中器主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_ZBJC_JZQZJJC;
            case Frame.FN_ZBJC_LLJC://链路检测
                // TODO: 2016/10/11 未做
                return Frame.FN_ZBJC_LLJC;
            /******
             * 微功率无线检测
             ******/
            case Frame.FN_WGLWX_DNBZJWGLWXJC://电能表整机微功率无线检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_DNBZJWGLWXJC;
            case Frame.FN_WGLWX_CJQZJWGLWXJC://采集器整机微功率无线检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_CJQZJWGLWXJC;
            case Frame.FN_WGLWX_JZQZJWGLWXJC://集中器整机微功率无线检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_JZQZJWGLWXJC;
            case Frame.FN_WGLWX_DNBZJWGLWXMKJC://电能表整机微功率无线模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_DNBZJWGLWXMKJC;
            case Frame.FN_WGLWX_CJQZJWGLWXMKJC://采集器整机微功率无线模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_CJQZJWGLWXMKJC;
            case Frame.FN_WGLWX_JZQZJWGLWXMKJC://集中器整机微功率无线模块检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_JZQZJWGLWXMKJC;
            case Frame.FN_WGLWX_DNBZJJC://电能表主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_DNBZJJC;
            case Frame.FN_WGLWX_CJQZJJC://采集器主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_CJQZJJC;
            case Frame.FN_ENVIR_WD://获取温度数据
                result.append(getData(data, 0));
                return Frame.FN_ENVIR_WD;
            case Frame.FN_ENVIR_SD://获取湿度数据
                result.append(getData(data, 1));
                return Frame.FN_ENVIR_SD;
            case Frame.FN_WGLWX_JZQZJJC://集中器主机检测
                result.append(getCheckInfo(data));
                return Frame.FN_WGLWX_JZQZJJC;
            case Frame.FN_WGLWX_LLJC://链路检测
                // TODO: 2016/10/11 未做
                return Frame.FN_WGLWX_LLJC;

            //现场校验——获取电表误差数据
            case Frame.FN_XCJY_HQDNBWCSJ:
                result.append(getErrorData(data));
                return Frame.FN_XCJY_HQDNBWCSJ;
            //现场校验——获取电表谐波数据
            case Frame.FN_XCJY_HQDNBXBSJ:
                result.append(getMeterXBSJData(data));
                return Frame.FN_XCJY_HQDNBXBSJ;
            //现场校验——获取电表接线错误数据
            case Frame.FN_XCJY_HQDNBJXCWSJ:
                result.append(getMeterJXCUData(data));
                return Frame.FN_XCJY_HQDNBJXCWSJ;
            //现场校验——获取电表误差数据
            case Frame.FN_XCJY_HQCQJCSJ:
                result.append(getCQData(data));
                return Frame.FN_XCJY_HQCQJCSJ;

            //设置参数（华立扩展）
            case Frame.FN_SET_DNBLXXZ:
                if (frame.getDataLength().equals("0000")) {
                    result.append("设置电能表类型成功!");
                } else {
                    result.append("设置电能表类型失败");
                }
                return Frame.FN_SET_DNBLXXZ;
            case Frame.FN_SET_WCQD:
                if (frame.getDataLength().equals("0000")) {
                    result.append("设置误差启动参数成功!");
                } else {
                    result.append("设置误差启动参数失败");
                }
                return Frame.FN_SET_WCQD;

            //测量值校准
            case Frame.FN_CHECK_UA:
                if (frame.getDataLength().equals("0000")) {
                    result.append("Ua标准值校准成功!");
                } else {
                    result.append("Ua标准值校准失败");
                }
                return Frame.FN_CHECK_UA;

            case Frame.FN_CHECK_UB:
                if (frame.getDataLength().equals("0000")) {
                    result.append("Ub标准值校准成功!");
                } else {
                    result.append("Ub标准值校准失败");
                }
                return Frame.FN_CHECK_UB;

            case Frame.FN_CHECK_UC:
                if (frame.getDataLength().equals("0000")) {
                    result.append("Uc标准值校准成功!");
                } else {
                    result.append("Uc标准值校准失败");
                }
                return Frame.FN_CHECK_UC;

            case Frame.FN_CHECK_LA:
                if (frame.getDataLength().equals("0000")) {
                    result.append("La标准值校准成功!");
                } else {
                    result.append("La标准值校准失败");
                }
                return Frame.FN_CHECK_LA;

            case Frame.FN_CHECK_LB:
                if (frame.getDataLength().equals("0000")) {
                    result.append("Lb标准值校准成功!");
                } else {
                    result.append("Lb标准值校准失败");
                }
                return Frame.FN_CHECK_LB;

            case Frame.FN_CHECK_LC:
                if (frame.getDataLength().equals("0000")) {
                    result.append("Lc标准值校准成功!");
                } else {
                    result.append("Lc标准值校准失败");
                }
                return Frame.FN_CHECK_LC;

            case Frame.FN_CHECK_PFA:
                if (frame.getDataLength().equals("0000")) {
                    result.append("PFa标准值校准成功!");
                } else {
                    result.append("PFa标准值校准失败");
                }
                return Frame.FN_CHECK_PFA;

            case Frame.FN_CHECK_PFB:
                if (frame.getDataLength().equals("0000")) {
                    result.append("PFb标准值校准成功!");
                } else {
                    result.append("PFb标准值校准失败");
                }
                return Frame.FN_CHECK_PFB;

            case Frame.FN_CHECK_PFC:
                if (frame.getDataLength().equals("0000")) {
                    result.append("PFc标准值校准成功!");
                } else {
                    result.append("PFc标准值校准失败");
                }
                return Frame.FN_CHECK_PFC;

            //获取设置参数
            case Frame.FN_GET_PARAMS:
                result.append(getMeterFault(data));
                return Frame.FN_GET_PARAMS;

            //查询通用参数
            case Frame.FN_REQUEST_DEVICE_INFO:
                result.append(getDeviceInfo(data));
                return Frame.FN_REQUEST_DEVICE_INFO;

            case Frame.FN_REQUEST_DEVICE_STATUS:
                result.append(getDeviceStatus(data));
                return Frame.FN_REQUEST_DEVICE_STATUS;

            case Frame.FN_REQUEST_FAULT_INFO:
                result.append(getFaultInfo(data));
                return Frame.FN_REQUEST_FAULT_INFO;
            case Frame.FN_CGP_EPC_DATA:
                if (Integer.parseInt(frame.getControl(), 16) == Frame.CHUAN_HU_ERROR) {
                    result.append(errorEPC_Data(data));
                } else {
                    if (TextUtils.isEmpty(data)) {
                        result.append("获取EPC数据失败");
                    }
                    if (data.length() == 2) {
                        result.append("返回帧数据长度为空");
                    } else {
                        result.append(getEPCData(data));
                    }
                }
                return Frame.FN_CGP_EPC_DATA;

//            //数据透传
//            case Frame.FN_DATA_TRANSMIT:
//                result.append(decodeDl645(data));
//                return Frame.FN_DATA_TRANSMIT;

            //在线升级：
            //请求升级
            case Frame.FN_UPDATE_REQUEST:
                if (frame.getDataLength().equals("0000")) {
                    result.append("请求升级成功");
                } else {
                    result.append(decodeUpdateReq(data));
                }
                return Frame.FN_UPDATE_REQUEST;

            //文件传输
            case Frame.FN_UPDATE_FILE_TRANS:
                result.append(decodeFileTransfer(data));
                return Frame.FN_UPDATE_FILE_TRANS;

            //开始升级
            case Frame.FN_UPDATE_START:
                if (frame.getDataLength().equals("0000")) {
                    result.append("升级成功");
                } else {
                    result.append(decodeUpdate(data));
                }
                return Frame.FN_UPDATE_START;
            //R485查询参数
            case Frame.FN_RS485_CSCX:
                result.append(decodeRS485(data));
                return Frame.FN_RS485_CSCX;
            //R485参数设置
            case Frame.FN_RS485_CSSZ:
                if (frame.getDataLength().equals("0000")) {
                    result.append("RS485参数设置成功");
                } else {
                    errorCode(data);
                }
                return Frame.FN_RS485_CSSZ;
            //R232查询参数
            case Frame.FN_RS232_CSCX:
                result.append(decodeRS232(data));
                return Frame.FN_RS232_CSCX;
            //R232参数设置
            case Frame.FN_RS232_CSSZ:
                if (frame.getDataLength().equals("0000")) {
                    result.append("RS232参数设置成功");
                } else {
                    errorCode(data);
                }
                return Frame.FN_RS232_CSSZ;
            //通用设置，外设地址
            case Frame.FN_SET_ADDRESS:
                if (frame.getDataLength().equals("0000")) {
                    result.append("外设地址设置成功");
                } else {
                    errorCode(data);
                }
                return Frame.FN_SET_ADDRESS;
            //串户检测
            case Frame.FN_CHJCTQSB_CHJC:
                if (Integer.parseInt(frame.getControl(), 16) == Frame.CHUAN_HU_ERROR) {
                    result.append(errorCode_CHJC(data));
                } else {
                    if (TextUtils.isEmpty(data)) {
                        result.append("串户检测失败");
                    }
                    if (data.length() != 2) {
                        result.append("返回帧数据异常");
                    } else {
                        result.append(getCHJCData(data));
                    }
                }
                return Frame.FN_CHJCTQSB_CHJC;
            //串户检测-台区识别
            case Frame.FN_CHJCTQSB_TQSB:
                if (frame.getDataLength().equals("0000")) {
                    result.append("检测成功");
                } else {
                    result.append(errorCode_CHJC(data));
                }
                return Frame.FN_CHJCTQSB_TQSB;
            case Frame.FN_CHJCTQSB_TQSB_DO:
                if (Integer.parseInt(frame.getControl(), 16) == Frame.CHUAN_HU_ERROR) {
                    result.append(errorCode_CHJC(data));
                } else {
                    if (TextUtils.isEmpty(data)) {
                        result.append("获取台区识别数据失败");
                    }
                    if (data.length() != 26) {
                        result.append("返回帧数据异常");
                    } else {
                        result.append(getTQSBData(data));
                    }
                }
                return Frame.FN_CHJCTQSB_TQSB_DO;
            //场强检测-许继
            case Frame.FN_CQJC_HQCQJCSJ:
                result.append(getCQData_XJ(data));
                return Frame.FN_CQJC_HQCQJCSJ;
        }
        return 0;
    }

    /**
     * 获取sim卡信息
     *
     * @param data 蓝牙返回数据
     * @return
     */
    public String simInfo(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (data.length() == 106) {
            String ccid = data.substring(0, 44);
            String imei = data.substring(44, 74);
            String ip = data.substring(74, 82);
            ip = DataConvert.toStringIp(DataConvert.strReverse(ip, 0, ip.length()));
            String signal = data.substring(82, 84);
            String simNo = data.substring(84, 106);
            stringBuffer.append("ccid:" + DataConvert.strReverse(ccid, 0, ccid.length()) + "\n");
            stringBuffer.append("imei:" + DataConvert.strReverse(imei, 0, imei.length()) + "\n");
            stringBuffer.append("ip:" + ip + "\n");
            stringBuffer.append("signal:" + signal + "\n");
            stringBuffer.append("simno:" + DataConvert.strReverse(simNo, 0, simNo.length()));
        } else {
            stringBuffer.append("SIM卡信息读取失败");
        }
        return stringBuffer.toString();
    }

    /**
     * SIM卡检测结果数据解析
     *
     * @param data
     * @return
     */
    public String simJCJG(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(data)) {
            stringBuffer.append("SIM卡检测失败");
            return stringBuffer.toString();
        }
        if (data.length() != 32) {
            stringBuffer.append("帧数据异常");
        } else {
            String gprs = data.substring(0, 2);//gprs模块标志
            if (gprs.equals("00")) {
                stringBuffer.append("GPRS模块正常\n");
            } else if (gprs.equals("01")) {
                stringBuffer.append("电源异常\n");
            } else if (gprs.equals("02")) {
                stringBuffer.append("串口失败\n");
            } else if (gprs.equalsIgnoreCase("FF")) {
                stringBuffer.append("未执行\n");
            } else {
                stringBuffer.append("GPRS模块-未知错误\n");
            }
            String sim_jcbz = data.substring(2, 4);//sim卡检测标志
            if (sim_jcbz.equals("00")) {
                stringBuffer.append("SIM卡检测-正常\n");
            } else if (sim_jcbz.equals("01")) {
                stringBuffer.append("SIM卡检测-无法识别SIM卡\n");
            } else if (sim_jcbz.equals("02")) {
                stringBuffer.append("SIM卡检测-欠费或停机\n");
            } else if (sim_jcbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("SIM卡检测-未执行\n");
            } else {
                stringBuffer.append("SIM卡检测-未知错误\n");
            }

            String signal = data.substring(4, 6);//信号强度标志
            if (signal.equals("00")) {
                stringBuffer.append("信号强度-正常\n");
            } else if (signal.equals("01")) {
                stringBuffer.append("信号强度-无信号\n");
            } else if (signal.equals("02")) {
                stringBuffer.append("信号强度-信号差\n");
            } else if (signal.equals("03")) {
                stringBuffer.append("信号强度-数值异常\n");
            } else if (signal.equalsIgnoreCase("FF")) {
                stringBuffer.append("信号强度-未执行\n");
            } else {
                stringBuffer.append("信号强度-未知错误\n");
            }
            String yysbz = data.substring(6, 8);//连接运营商标志
            if (yysbz.equals("00")) {
                stringBuffer.append("连接运营商-正常\n");
            } else if (yysbz.equals("01")) {
                stringBuffer.append("连接运营商-异常\n");
            } else {
                stringBuffer.append("连接运营商-未知错误\n");
            }
            String csszbz = data.substring(8, 10);//参数设置标志
            if (csszbz.equals("00")) {
                stringBuffer.append("参数设置-正常\n");
            } else if (csszbz.equals("01")) {
                stringBuffer.append("参数设置-APN设置失败\n");
            } else if (csszbz.equals("02")) {
                stringBuffer.append("参数设置-专网用户名设置失败\n");
            } else if (csszbz.equals("03")) {
                stringBuffer.append("参数设置-专用用户名密码设置失败\n");
            } else if (csszbz.equals("04")) {
                stringBuffer.append("参数设置-IP地址和端口号设置失败\n");
            } else if (csszbz.equals("05")) {
                stringBuffer.append("参数设置-传输方式（TCP/UDP）设置失败\n");
            } else if (csszbz.equals("06")) {
                stringBuffer.append("参数设置-其他参数设置失败\n");
            } else if (csszbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("参数设置-未执行\n");
            } else {
                stringBuffer.append("参数设置-未知错误\n");
            }
            String wlzcbz = data.substring(10, 12);//网络注册标志
            if (wlzcbz.equals("00")) {
                stringBuffer.append("网络注册-正常\n");
            } else if (wlzcbz.equals("01")) {
                stringBuffer.append("网络注册-异常\n");
            } else if (wlzcbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("网络注册-未执行\n");
            } else {
                stringBuffer.append("网络注册-未知错误\n");
            }
            String sjljbz = data.substring(12, 14);//数据连接标志
            if (sjljbz.equals("00")) {
                stringBuffer.append("数据连接-正常\n");
            } else if (sjljbz.equals("01")) {
                stringBuffer.append("数据连接-异常\n");
            } else if (sjljbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("数据连接-未执行\n");
            } else {
                stringBuffer.append("数据连接-未知错误\n");
            }
            String ljzzbz = data.substring(14, 16);//连接主站标志
            if (ljzzbz.equals("00")) {
                stringBuffer.append("连接主站-正常\n");
            } else if (ljzzbz.equals("01")) {
                stringBuffer.append("连接主站-异常\n");
            } else if (ljzzbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("连接主站-未执行\n");
            } else {
                stringBuffer.append("连接主站-未知错误\n");
            }
            String dlzzbz = data.substring(16, 18);//登录主站标志
            if (dlzzbz.equals("00")) {
                stringBuffer.append("登录主站-正常\n");
            } else if (dlzzbz.equals("01")) {
                stringBuffer.append("登录主站-异常\n");
            } else if (dlzzbz.equalsIgnoreCase("FF")) {
                stringBuffer.append("登录主站-未执行\n");
            } else {
                stringBuffer.append("登录主站-未知错误\n");
            }
            String ylcwbz1 = data.substring(18, 20);//预留错误标志1
            if (ylcwbz1.equals("00")) {
                stringBuffer.append("预留错误标志1-正常\n");
            } else if (ylcwbz1.equals("01")) {
                stringBuffer.append("预留错误标志1-异常\n");
            } else if (ylcwbz1.equalsIgnoreCase("FF")) {
                stringBuffer.append("预留错误标志1-未执行\n");
            } else {
                stringBuffer.append("预留错误标志1-未知错误\n");
            }
            String ylcwbz4 = data.substring(20, 22);//预留错误标志2
            if (ylcwbz4.equals("00")) {
                stringBuffer.append("预留错误标志4-正常\n");
            } else if (ylcwbz4.equals("01")) {
                stringBuffer.append("预留错误标志4-异常\n");
            } else if (ylcwbz4.equalsIgnoreCase("FF")) {
                stringBuffer.append("预留错误标志4-未执行\n");
            } else {
                stringBuffer.append("预留错误标志4-未知错误\n");
            }
            String xhqd = data.substring(22, 24);//信号强度
            stringBuffer.append("信号强度:" + xhqd + "\n");
            String ip = data.substring(24, 32);//获取的IP地址
            ip = DataConvert.toStringIp(DataConvert.strReverse(ip, 0, ip.length()));
            stringBuffer.append("IP地址:" + ip);
        }
        return stringBuffer.toString();
    }

    /**
     * 微功率无线--采集器整机检测（获取温度或湿度数据）
     *
     * @param data 外设返回数据
     * @param flag 0:获取温度数据，1：获取湿度数据
     * @return
     */
    public String getData(String data, int flag) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 2 * 2) {
            if (flag == 0) {
                String temp = data.substring(2, 4) + data.substring(0, 1) + "." + data.substring(1, 2);
                return Double.parseDouble(temp) + "℃";
            } else if (flag == 1) {
                String temp = data.substring(2, 4) + "." + data.substring(0, 1) + data.substring(1, 2);
                return Double.parseDouble(temp) + "%";
            }
        } else {
            return "数据异常";
        }
        return null;
    }

    /**
     * 电能表通用故障
     *
     * @param data 外设返回数据
     * @return
     */
    public String getMeterFault(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 10 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(12, 14);
            if (temp.equals("00")) {
                stringBuffer.append("正常 ");
            } else if (temp.equals("01")) {
                stringBuffer.append("故障 ");
            }

            temp = data.substring(14, 16);
            if (temp.equals("00")) {
                stringBuffer.append("正常 ");
            } else if (temp.equals("01")) {
                stringBuffer.append("故障 ");
            }

            temp = data.substring(16, 18);
            if (temp.equals("00")) {
                stringBuffer.append("正常 ");
            } else if (temp.equals("01")) {
                stringBuffer.append("欠压 ");
            }

            temp = data.substring(18, 20);
            if (temp.equals("00")) {
                stringBuffer.append("正常");
            } else if (temp.equals("01")) {
                stringBuffer.append("欠压");
            }
            return stringBuffer.toString();
        } else {
            return errorCode_A4(data);
        }
    }

    /**
     * 获取电表误差数据
     *
     * @param data 外设返回数据
     * @return
     */
    public String getErrorData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 56 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(14, 18);
            stringBuffer.append("从电表读到的A相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(18, 22);
            stringBuffer.append("从电表读到的B相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(22, 26);
            stringBuffer.append("从电表读到的C相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(26, 30);
            stringBuffer.append("从外设读到的A相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(30, 34);
            stringBuffer.append("从外设读到的B相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(34, 38);
            stringBuffer.append("从外设读到的C相电压:" + decodeBCDData(temp, 2, 1, "V") + "\n");
            temp = data.substring(38, 44);
            stringBuffer.append("从电表读到的A相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(44, 50);
            stringBuffer.append("从电表读到的B相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(50, 56);
            stringBuffer.append("从电表读到的C相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(56, 62);
            stringBuffer.append("从外设读到的A相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(62, 68);
            stringBuffer.append("从外设读到的B相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(68, 74);
            stringBuffer.append("从外设读到的C相电流:" + decodeErrorBCDData(temp, 3, 3, "A") + "\n");
            temp = data.substring(74, 80);
            stringBuffer.append("从电表读到的总有功功率:" + decodeErrorBCDData(temp, 3, 4, "kW") + "\n");
            temp = data.substring(80, 86);
            stringBuffer.append("从外设读到的总有功功率:" + decodeErrorBCDData(temp, 3, 4, "kW") + "\n");
            temp = data.substring(86, 92);
            stringBuffer.append("从电表读到的总无功功率:" + decodeErrorBCDData(temp, 3, 4, "kW") + "\n");
            temp = data.substring(92, 98);
            stringBuffer.append("从外设读到的总无功功率:" + decodeErrorBCDData(temp, 3, 4, "kW") + "\n");
            temp = data.substring(98, 102);
            stringBuffer.append("从电表读到的总功率因数:" + decodeBCDData(temp, 2, 3, "") + "\n");
            temp = data.substring(102, 106);
            stringBuffer.append("从外设读到的总功率因数:" + decodeBCDData(temp, 2, 3, "") + "\n");
            temp = data.substring(106, 112);//误差值
            String highBytesStr = temp.substring(4, 6);
            byte[] highBytes = DataConvert.toBytes(highBytesStr);
            if (((highBytes[0] >> 7) & 0x01) == 0)//为正值
            {
                highBytesStr = (highBytes[0] & 0x7F) + "";
                String value = highBytesStr + temp.substring(2, 4) + "." + temp.substring(0, 2);
                stringBuffer.append("误差值：" + Double.parseDouble(value) + "%");
            } else if (((highBytes[0] >> 7) & 0x01) == 1) {//为负值
                highBytesStr = (highBytes[0] & 0x7F) + "";
                String value = highBytesStr + temp.substring(2, 4) + "." + temp.substring(0, 2);
                stringBuffer.append("误差值：-" + Double.parseDouble(value) + "%");
            }
            return stringBuffer.toString();
        } else if (data.length() == 8 * 2) {
            String err = data.substring(14, 16);
            return errorCode(err);
        } else {
            return "帧结构异常";
        }
    }

    /**
     * 获取电表谐波数据
     *
     * @param data 外设返回数据
     * @return
     */
    public String getMeterXBSJData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 8 * 2) {
            return errorCode(data);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 2);
            if (temp.equals("00")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("A相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("A相电流" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }
            } else if (temp.equals("01")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("B相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("B相电流" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }

            } else if (temp.equals("02")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("C相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("C相电流" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }

            } else if (temp.equals("0F")) {
                data.substring(2, 127 * 2);
//                stringBuffer.append("电流谐波数据块：" + temp);
                for (int i = 2; i < 127 * 2; i = i + 4) {
                    temp = data.substring(i, i + 4);
                    stringBuffer.append("电流谐波数据块:" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                }

            } else if (temp.equals("10")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("A相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("A相电压" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }

            } else if (temp.equals("11")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("B相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("B相电压" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }

            } else if (temp.equals("12")) {
                for (int i = 2; i < 43 * 2; i = i + 4) {
                    if (i == 2) {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("C相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    } else {
                        temp = data.substring(i, i + 4);
                        stringBuffer.append("C相电压" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                    }
                }

            } else if (temp.equals("1F")) {
                for (int i = 2; i < 127 * 2; i = i + 4) {
                    temp = data.substring(i, i + 4);
                    stringBuffer.append("电压谐波数据块:" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                }
            } else if (temp.equals("FF")) {
                for (int i = 2; i < 253 * 2; i = i + 4) {
                    temp = data.substring(i, i + 4);
                    stringBuffer.append("谐波数据块:" + (i + 2) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
                }

            }
            return stringBuffer.toString();
        }
    }


//    /**
//     * 获取电表谐波数据
//     *
//     * @param data 外设返回数据
//     * @return
//     */
//    public String getMeterXBSJData(String data) {
//        if (TextUtils.isEmpty(data)) {
//            return null;
//        }
//        if (data.length() == 8 * 2) {
//            return errorCode(data);
//        } else {
//            StringBuffer stringBuffer = new StringBuffer();
//            String temp = data.substring(12, 14);
//            if (temp.equals("00")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("A相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("A相电流" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("01")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("B相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("B相电流" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("02")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("C相电流总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("C相电流" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("0F")) {
//                temp.substring(14, 313 * 2);
//                stringBuffer.append("电流谐波数据块：" + temp);
//
//            } else if (temp.equals("10")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("A相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("A相电压" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("11")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("B相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("B相电压" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("12")) {
//                for (int i = 14; i < 109 * 2; i = i + 4) {
//                    if (i == 14) {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("C相电压总谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    } else {
//                        temp = data.substring(i, i + 4);
//                        stringBuffer.append("C相电压" + (i - 10) / 4 + "次谐波含量:" + decodeBCDData(temp, 2, 2, "") + "\n");
//                    }
//                }
//
//            } else if (temp.equals("1F")) {
//                temp.substring(14, 313 * 2);
//                stringBuffer.append("电压谐波数据块：" + temp);
//
//            } else if (temp.equals("FF")) {
//                temp.substring(14, 719 * 2);
//                stringBuffer.append("谐波数据块：" + temp);
//
//            }
//            return stringBuffer.toString();
//        }
//    }


    /**
     * 获取电表接线错误数据
     *
     * @param data 外设返回数据
     * @return
     */

    public String getMeterJXCUData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (data.length() == 17 * 2) {
            String temp = data.substring(14, 16);
            if (temp.equals("01")) {
                stringBuffer.append("电表类型:三相三线" + "\n");
            } else if (temp.equals("02")) {
                stringBuffer.append("电表类型:三相四线" + "\n");
            }
//            //极性状态字
//            temp = data.substring(16, 18);
//            if (temp.equals("00")) {
//                stringBuffer.append("极性状态字:正常" + "\n");
//            } else if (temp.equals("01")) {
//                stringBuffer.append("极性状态字:A项电压极性反" + "\n");
//            } else if (temp.equals("02")) {
//                stringBuffer.append("极性状态字:B项电压极性反" + "\n");
//            } else if (temp.equals("04")) {
//                stringBuffer.append("极性状态字:C项电压极性反" + "\n");
//            }
            //异常状态字
            temp = data.substring(16, 18);
            stringBuffer.append("失压结果：" + "\n");
            String error = DataConvert.hexString2binaryString(temp);
            temp = error.substring(7, 8);
            if (temp.equals("1")) {
                stringBuffer.append("A相失压" + "\n");
            } else {
                stringBuffer.append("A相正常" + "\n");
            }
            temp = error.substring(6, 7);
            if (temp.equals("1")) {
                stringBuffer.append("B相失压" + "\n");
            } else {
                stringBuffer.append("B相正常" + "\n");
            }
            temp = error.substring(5, 6);
            if (temp.equals("1")) {
                stringBuffer.append("C相失压" + "\n");
            } else {
                stringBuffer.append("C相正常" + "\n");
            }
            stringBuffer.append("断相结果：" + "\n");
            temp = error.substring(4, 5);
            if (temp.equals("1")) {
                stringBuffer.append("A相断相" + "\n");
            } else {
                stringBuffer.append("A相正常" + "\n");
            }
            temp = error.substring(3, 4);
            if (temp.equals("1")) {
                stringBuffer.append("B相断相" + "\n");
            } else {
                stringBuffer.append("B相正常" + "\n");
            }
            temp = error.substring(2, 3);
            if (temp.equals("1")) {
                stringBuffer.append("C相断相" + "\n");
            } else {
                stringBuffer.append("C相正常" + "\n");
            }


            //A相电压接线
            temp = data.substring(18, 20);
            temp = DataConvert.hexString2binaryString(temp);

            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("A项电压接线相序：相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1010")) {
                    stringBuffer.append("A项电压接线相序：相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }

            //B相电压接线
            temp = data.substring(20, 22);
            temp = DataConvert.hexString2binaryString(temp);

            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("B项电压接线相序：相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1011")) {
                    stringBuffer.append("B项电压接线相序：相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }
            //C相电压接线
            temp = data.substring(22, 24);
            temp = DataConvert.hexString2binaryString(temp);

            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("C项电压接线相序：相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1100")) {
                    stringBuffer.append("C项电压接线相序：相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }
            //A相电流接线
            temp = data.substring(24, 26);
            temp = DataConvert.hexString2binaryString(temp);

            if (temp.substring(0, 1).equals("0")) {
                stringBuffer.append("A项电流接线相序(进出线)：正常" + "；");
            } else {
                stringBuffer.append("A项电流接线相序(进出线)：错误" + "；");
            }
            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("A项电压相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1010")) {
                    stringBuffer.append("A项电压相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }

            //B相电流接线
            temp = data.substring(26, 28);
            temp = DataConvert.hexString2binaryString(temp);
            if (temp.substring(0, 1).equals("0")) {
                stringBuffer.append("B项电流接线相序(进出线)：正常" + "；");
            } else {
                stringBuffer.append("B项电流接线相序(进出线)：错误" + "；");
            }
            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("B项电压相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1011")) {
                    stringBuffer.append("B项电压相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }
            //C相电流接线
            temp = data.substring(28, 30);
            temp = DataConvert.hexString2binaryString(temp);
            if (temp.substring(0, 1).equals("0")) {
                stringBuffer.append("C项电流接线相序(进出线)：正常" + "；");
            } else {
                stringBuffer.append("C项电流接线相序(进出线)：错误" + "；");
            }
            if (temp.substring(1, 2).equals("0")) {
                stringBuffer.append("C项电压相序正常" + "\n");
            } else {
                if (temp.substring(4, 8).equals("1100")) {
                    stringBuffer.append("C项电压相序异常" + "\n");
                } else {
                    stringBuffer.append("返回帧格式错误\n");
                }
            }
            //追补率
            temp = data.substring(30, 34);
            stringBuffer.append("追补率：" + decodeBCDData(temp, 2, 2, "") + "\n");
            return stringBuffer.toString();
        } else if (data.length() == 8 * 2) {
            return errorCode(data.substring(14, 16));
        } else {
            stringBuffer.append("帧格式错误");
            return stringBuffer.toString();
        }
    }

//    /**
//     * 获取电表接线错误数据
//     *
//     * @param data 外设返回数据
//     * @return
//     */
//
//    public String getMeterJXCUData(String data) {
//        if (TextUtils.isEmpty(data)) {
//            return null;
//        }
//        StringBuffer stringBuffer = new StringBuffer();
//        if (data.length() == 18 * 2) {
//            String temp = data.substring(14, 16);
//            if (temp.equals("01")) {
//                stringBuffer.append("电表类型:三相三线" + "\n");
//            } else if (temp.equals("02")) {
//                stringBuffer.append("电表类型:三相四线" + "\n");
//            }
//            //极性状态字
//            temp = data.substring(16, 18);
//            if (temp.equals("00")) {
//                stringBuffer.append("极性状态字:正常" + "\n");
//            } else if (temp.equals("01")) {
//                stringBuffer.append("极性状态字:A项电压极性反" + "\n");
//            } else if (temp.equals("02")) {
//                stringBuffer.append("极性状态字:B项电压极性反" + "\n");
//            } else if (temp.equals("04")) {
//                stringBuffer.append("极性状态字:C项电压极性反" + "\n");
//            }
//            //异常状态字
//            temp = data.substring(18, 20);
//            stringBuffer.append("异常状态字:" + "\n");
//            if (temp.equals("00000000")) {
//                stringBuffer.append("正常");
//            } else {
//                String error = DataConvert.hexString2binaryString(temp);
//                temp = error.substring(7, 8);
//                if (temp.equals("1")) {
//                    stringBuffer.append("A相失压" + "\n");
//                }
//                temp = error.substring(6, 7);
//                if (temp.equals("1")) {
//                    stringBuffer.append("B相失压" + "\n");
//                }
//                temp = error.substring(5, 6);
//                if (temp.equals("1")) {
//                    stringBuffer.append("C相失压" + "\n");
//                }
//                temp = error.substring(4, 5);
//                if (temp.equals("1")) {
//                    stringBuffer.append("A相断相" + "\n");
//                }
//                temp = error.substring(3, 4);
//                if (temp.equals("1")) {
//                    stringBuffer.append("B相断相" + "\n");
//                }
//                temp = error.substring(2, 3);
//                if (temp.equals("1")) {
//                    stringBuffer.append("C相断相" + "\n");
//                }
//                temp = error.substring(1, 2);
//                if (temp.equals("1")) {
//                    stringBuffer.append("B相电流接入" + "\n");
//                }
//            }
//
//            //A相电压接线
//            temp = data.substring(20, 22);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1010")) {
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("A项电压接线,相序正常");
//                } else {
//                    stringBuffer.append("A项电压接线,相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("A项电压未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//
//            //B相电压接线
//            temp = data.substring(22, 24);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1011")) {
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("B项电压接线,相序正常");
//                } else {
//                    stringBuffer.append("B项电压接线,相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("B项电压未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//            //C相电压接线
//            temp = data.substring(24, 26);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1100")) {
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("C项电压接线,相序正常");
//                } else {
//                    stringBuffer.append("C项电压接线,相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("C项电压未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//            //A相电流接线
//            temp = data.substring(26, 28);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1010")) {
//                stringBuffer.append("A项电流接线");
//                if (temp.substring(0, 1).equals("0")) {
//                    stringBuffer.append("," + "电流相序:进线");
//                } else {
//                    stringBuffer.append("," + "电流相序:出线");
//                }
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("," + "相序正常");
//                } else {
//                    stringBuffer.append("," + "相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("A项电流未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//            //B相电流接线
//            temp = data.substring(28, 30);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1011")) {
//                stringBuffer.append("B项电流接线");
//                if (temp.substring(0, 1).equals("0")) {
//                    stringBuffer.append("," + "电流相序:进线");
//                } else {
//                    stringBuffer.append("," + "电流相序:出线");
//                }
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("," + "相序正常");
//                } else {
//                    stringBuffer.append("," + "相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("B项电流未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//            //C相电流接线
//            temp = data.substring(30, 32);
//            temp = DataConvert.hexString2binaryString(temp);
//            if (temp.substring(4, 8).equals("1100")) {
//                stringBuffer.append("C项电流接线");
//                if (temp.substring(0, 1).equals("0")) {
//                    stringBuffer.append("," + "电流相序:进线");
//                } else {
//                    stringBuffer.append("," + "电流相序:出线");
//                }
//                if (temp.substring(1, 2).equals("0")) {
//                    stringBuffer.append("," + "相序正常");
//                } else {
//                    stringBuffer.append("," + "相序异常");
//                }
//            } else if (temp.substring(4, 8).equals("0000")) {
//                stringBuffer.append("C项电流未接线");
//            } else {
//                stringBuffer.append("返回帧格式异常");
//            }
//            //追补率
//            temp = data.substring(32, 36);
//            stringBuffer.append("追补率：" + decodeBCDData(temp, 2, 2, "") + "\n");
//            return stringBuffer.toString();
//        } else if (data.length() == 8 * 2) {
//            return errorCode(data.substring(14, 16));
//        } else {
//            stringBuffer.append("帧格式错误");
//            return stringBuffer.toString();
//        }
//    }

    /**
     * 获取场强检测数据
     *
     * @param data 外设返回数据
     * @return
     */
    public String getCQData(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 3 * 2) {

            return "场强数据为：" + data.substring(4, 6) + data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + "高斯" + "\n";
        } else {
            return errorCode(data);
        }
    }

    /**
     * 获取场强检测数据_许继
     *
     * @param data 外设返回数据
     * @return
     */
    public String getCQData_XJ(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 9 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 6);
//            stringBuffer.append("直流磁场："+decodeBCDData(temp,3,3,"特斯拉"));
            stringBuffer.append(decodeBCDData(temp, 3, 3, "毫特斯拉") + " ");
            temp = data.substring(6, 12);
//            stringBuffer.append("工频磁场："+decodeBCDData(temp,3,3,"特斯拉"));
            stringBuffer.append(decodeBCDData(temp, 3, 3, "毫特斯拉") + " ");
            temp = data.substring(12, 18);
//            stringBuffer.append("高频磁场强度："+decodeBCDData(temp,3,3,"瓦"));
            stringBuffer.append(decodeBCDData(temp, 3, 3, "毫特斯拉"));

            return stringBuffer.toString();
        } else {
            return errorCode(data);
        }
    }

    /**
     * 获取设置参数
     *
     * @param data ：外设返回数据（数据域内容）
     * @return
     */
    public String getParams(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 15 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 2);
            stringBuffer.append(decodeParam(temp, 1, 0));
            temp = data.substring(2, 4);
            stringBuffer.append(decodeParam(temp, 1, 1));
            temp = data.substring(4, 6);
            stringBuffer.append(decodeParam(temp, 1, 2));
            temp = data.substring(6, 10);
            stringBuffer.append(decodeParam(temp, 2, 3));
            temp = data.substring(10, 14);
            stringBuffer.append(decodeParam(temp, 2, 4));
            temp = data.substring(14, 16);
            stringBuffer.append(decodeParam(temp, 1, 5));
            temp = data.substring(16, 18);
            stringBuffer.append(decodeParam(temp, 1, 6));
            temp = data.substring(18, 22);
            stringBuffer.append(decodeParam(temp, 2, 7));
            temp = data.substring(22, 28);
            stringBuffer.append(decodeParam(temp, 3, 8));
            temp = data.substring(28, 30);
            stringBuffer.append(decodeParam(temp, 1, 9));
            return stringBuffer.toString();
        } else {
            return "数据异常";
        }
    }

    /**
     * 获取设备信息
     *
     * @param data ：返回的设备信息数据
     * @return
     */
    private String getDeviceInfo(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 133 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 2);
            stringBuffer.append("厂家代码为：" + temp + "\n");

            temp = data.substring(2, 10);
            String deviceType = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8);
            byte[] byteArr = DataConvert.toBytes(deviceType);
            String str = new String(byteArr);
            stringBuffer.append("设备型号为:" + str + "\n");

            temp = data.substring(10, 22);
            String deviceId = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8) + temp.substring(8, 10) + temp.substring(10, 12);
            stringBuffer.append("设备ID为：" + deviceId + "\n");

            temp = data.substring(22, 26);
            String version = temp.substring(0, 2) + "." + temp.substring(2, 4);
            stringBuffer.append("硬件版本号为：" + Double.parseDouble(version) + "\n");

            temp = data.substring(26, 32);
            //String date = temp.substring(0, 2) + "年" + temp.substring(2, 4) + "月" + temp.substring(4, 6) + "日";
            String date = temp.substring(4, 6) + "年" + temp.substring(2, 4) + "月" + temp.substring(0, 2) + "日";//add by songweijie 2017.3.2
            stringBuffer.append("硬件版本日期为：" + date + "\n");

            temp = data.substring(32, 40);
            String softVersion = Integer.parseInt(temp.substring(6, 8)) + "." + temp.substring(4, 6) + "." + temp.substring(2, 4) + "." + temp.substring(0, 2);
            stringBuffer.append("软件版本号为：" + softVersion + "\n");

            temp = data.substring(40, 46);
            //String softdate = temp.substring(0, 2) + "年" + temp.substring(2, 4) + "月" + temp.substring(4, 6) + "日";
            String softdate = temp.substring(4, 6) + "年" + temp.substring(2, 4) + "月" + temp.substring(0, 2) + "日";//add by songweijie 2017.3.2
            stringBuffer.append("软件版本日期为：" + softdate + "\n");

            temp = data.substring(46, 54);
            String dcrl = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8);
            stringBuffer.append("电池容量为：" + Integer.parseInt(dcrl) + "\n");

            temp = data.substring(54, 62);
            String gnpz = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8);
            stringBuffer.append("功能配置为：" + gnpz + "\n");

            temp = data.substring(62, 66);
            String time = temp.substring(0, 2) + temp.substring(2, 3) + "." + temp.substring(3, 4);
            stringBuffer.append("外设响应主设备时间为：" + Double.parseDouble(time) + "\n");

            temp = data.substring(66, 76);
            String esambb = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8) + temp.substring(8, 10);
            stringBuffer.append("WEsam版本号：" + esambb + "\n");

            temp = data.substring(76, 92);
            String esamxl = temp.substring(0, 2) + temp.substring(2, 4) + temp.substring(4, 6) + temp.substring(6, 8) + temp.substring(8, 10)
                    + temp.substring(10, 12) + temp.substring(12, 14) + temp.substring(14, 16);
            stringBuffer.append("WEsam序列号：" + esamxl + "\n");

            temp = data.substring(136, 144);
            String hhxsjsq = temp.substring(0, 2) + temp.substring(2, 4) + DataConvert.strReverse((temp.substring(4, 6) + temp.substring(6, 8)), 0, (temp.substring(4, 6) + temp.substring(6, 8)).length());
            stringBuffer.append("会话协商计数器：" + hhxsjsq + "\n");

            temp = data.substring(256, 258);
            stringBuffer.append("安全等级：" + temp + "\n");


            return stringBuffer.toString();
        } else {
            if ("01".equals(data)) {
                return "获取设备信息失败";
            } else {
                return "数据异常";
            }
        }
    }

    /**
     * 获取设备状态信息
     *
     * @param data 返回的状态信息数据
     * @return
     */
    private String getDeviceStatus(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 7 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 4);
            String dcdl = temp.substring(2, 4) + "." + temp.substring(0, 2);
            stringBuffer.append("电池电量为：" + Double.parseDouble(dcdl) + "V \n");

            temp = data.substring(4, 6);
            stringBuffer.append("电量百分比为：" + Integer.parseInt(temp) + "% \n");

            return stringBuffer.toString();
        } else {
            if ("01".equals(data)) {
                return "获取设备状态失败";
            } else {
                return "数据异常";
            }
        }
    }

    /**
     * 获取故障信息
     *
     * @param data 返回的故障信息数据
     * @return
     */
    private String getFaultInfo(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 2 * 2) {
            StringBuffer stringBuffer = new StringBuffer();
            String temp = data.substring(0, 4);
            String fault = temp.substring(2, 4) + temp.substring(0, 2);
            byte[] faultBytes = DataConvert.toBytes(fault);
            if ((faultBytes[0] & 0x01) == 1) {
                stringBuffer.append("硬件故障：异常\n");
            } else if ((faultBytes[0] & 0x01) == 0) {
                stringBuffer.append("硬件故障：正常\n");
            }

            if ((faultBytes[0] & 0x02) == 1) {
                stringBuffer.append("软件故障：异常\n");
            } else if ((faultBytes[0] & 0x01) == 0) {
                stringBuffer.append("软件故障：正常\n");
            }

            if ((faultBytes[0] & 0x04) == 1) {
                stringBuffer.append("电池故障：异常\n");
            } else if ((faultBytes[0] & 0x01) == 0) {
                stringBuffer.append("电池故障：正常\n");
            }

            if ((faultBytes[0] & 0x08) == 1) {
                stringBuffer.append("蓝牙故障：异常\n");
            } else if ((faultBytes[0] & 0x01) == 0) {
                stringBuffer.append("蓝牙故障：正常\n");
            }

            return stringBuffer.toString();
        } else {
            if ("01".equals(data)) {
                return "获取设备故障检测码失败";
            } else {
                return "数据异常";
            }
        }
    }

//    /**
//     * 解析返回的645帧数据
//     *
//     * @param data ：返回的645帧数据
//     * @return
//     */
//    private String decodeDl645(String data) {
//        if (TextUtils.isEmpty(data)) {
//            return null;
//        }
//        if (data.length() > 2) {
//            StringBuffer stringBuffer = new StringBuffer();
//
//            if (Constant.CUR_TRANSMIT == 0) {
//                String meterStr = GetBackData.getAmmeterNum(data);
//                stringBuffer.append("表地址为：" + meterStr + "\n");
//                Constant.METER_ADDRESS = meterStr;
//            } else if (Constant.CUR_TRANSMIT == 1) {
//                stringBuffer.append("A相电压为：" + GetBackData.analysisAVoltage(data) + "\n");
//            } else if (Constant.CUR_TRANSMIT == 2) {
//                stringBuffer.append("A相电流为：" + GetBackData.analysisAElectric(data) + "\n");
//            } else if (Constant.CUR_TRANSMIT == 3) {
//                stringBuffer.append("正向有功总为：" + GetBackData.analysis645PA(data) + "\n");
//            }
//
//            return stringBuffer.toString();
//        } else {
//            if ("01".equals(data)) {
//                return "通讯超时";
//            } else {
//                return "数据异常";
//            }
//        }
//    }


    private String decodeUpdateReq(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if ("01".equals(data)) {
            return "硬件版本错误";
        } else if ("02".equals(data)) {
            return "软件版本错误";
        } else if ("04".equals(data)) {
            return "厂家代码错误";
        } else if ("08".equals(data)) {
            return "设备型号错误";
        } else if ("10".equals(data)) {
            return "设备ID错误";
        } else if ("20".equals(data)) {
            return "升级密码错误";
        } else {
            return "数据异常";
        }
    }

    private String decodeFileTransfer(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (data.length() == 4) {
            return "0,文件传输成功";
        } else if (data.length() == 6) {
            String err = data.substring(0, 2);
            if ("01".equals(err)) {
                return "1,CRC校验错";
            } else if ("02".equals(err)) {
                return "1,长度错";
            } else if ("03".equals(err)) {
                return "1,写文件错";
            } else if ("04".equals(err)) {
                return "1,清空缓存区错";
            }
        } else {
            return "1,数据异常";
        }
        return "";
    }

    private String decodeUpdate(String data) {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if ("01".equals(data)) {
            return "总文件校验错";
        } else if ("02".equals(data)) {
            return "密码错";
        } else if ("03".equals(data)) {
            return "升级出错";
        } else {
            return "数据异常";
        }
    }

//    /**
//     * 载波检测和微功率检测结果
//     *
//     * @param data 外设返回数据
//     * @return
//     */
//    public String getCheckInfo(String data) {
//        StringBuffer stringBuffer = new StringBuffer();
//        if (TextUtils.isEmpty(data)) {
//            return null;
//        }
//        byte[] dataLen = DataConvert.toBytes(data.substring(0, 4));
//        int length = DataConvert.byteToInt(dataLen, 0, dataLen.length);
//        data = data.substring(4, data.length());
//        if (data.length() == 2 * length) {
//            int time = data.length() / 8;
//            for (int i = 0; i < time; i++) {
//                String dataDate = data.substring(0, data.length());
//                dataDate = DataConvert.strReverse(dataDate, 0, dataDate.length());
//                String year = "20" + dataDate.substring(0, 2);
//                String month = dataDate.substring(2, 4);
//                String day = dataDate.substring(4, 6);
//                String week = getWeek(dataDate.substring(6, 8));
//                dataDate = year + "年" + month + "月" + day + "日" + week;
//                stringBuffer.append(dataDate + ",");
//            }
//            return stringBuffer.substring(0, stringBuffer.length() - 1);
//        } else {
//            return errorCode_A4(data);
////            return "数据异常";
//        }
//    }


    /**
     * 载波检测和微功率检测结果
     *
     * @param data 外设返回数据
     * @return
     */
    public String getCheckInfo(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if ("00".equals(data.substring(0, 2).toUpperCase())) {
            byte[] dataLen = DataConvert.toBytes(data.substring(2, 6));
            int length = DataConvert.byteToInt(dataLen, 0, dataLen.length);
            data = data.substring(6, data.length());
            if (data.length() == 2 * length) {
                int time = data.length() / 8;
                for (int i = 0; i < time; i++) {
                    String dataDate = data.substring(i*8, (i+1)*8);
                    dataDate = DataConvert.strReverse(dataDate, 0, dataDate.length());
                    String year = "20" + dataDate.substring(0, 2);
                    String month = dataDate.substring(2, 4);
                    String day = dataDate.substring(4, 6);
                    String week = getWeek(dataDate.substring(6, 8));
                    dataDate = year + "年" + month + "月" + day + "日" + week;
                    stringBuffer.append(dataDate + ",");
                }
                return stringBuffer.substring(0, stringBuffer.length() - 1);
            }
        } else if ("01".equals(data.substring(0, 2).toUpperCase())) {
            byte[] dataLen = DataConvert.toBytes(data.substring(2, 6));
            int length = DataConvert.byteToInt(dataLen, 0, dataLen.length);
            int i = data.length() - 6;
            if (data.length() - 6 != length * 2 || length * 2 != 8) {
                return "帧格式错误";
            }
            byte[] bytes = DataConvert.toBytes(data.substring(6, 14));

            if ((bytes[0] & 0x80) == 0x80) {
                stringBuffer.append("电能表带负载异常");
            }
            if ((bytes[0] & 0x40) == 0x40) {
                stringBuffer.append("终端主机异常");
            }
            if ((bytes[0] & 0x20) == 0x20) {
                stringBuffer.append("终端带负载异常");
            }
            if ((bytes[0] & 0x10) == 0x10) {
                stringBuffer.append("模块功耗异常");
            }
            if ((bytes[0] & 0x08) == 0x08) {
                stringBuffer.append("模块通信能力异常");
            }
            if ((bytes[0] & 0x04) == 0x04) {
                stringBuffer.append("载波模块逻辑单元异常");
            }
            if ((bytes[0] & 0x02) == 0x02) {
                stringBuffer.append("组网异常");
            }
            if ((bytes[0] & 0x01) == 0x01) {
                stringBuffer.append("强电未插入");
            }
            if ((bytes[1] & 0x80) == 0x80) {
                stringBuffer.append("电能表/采集器模块未插入");
            }
            if ((bytes[1] & 0x40) == 0x40) {
                stringBuffer.append("集中器模块未插入");
            }
            if ((bytes[1] & 0x20) == 0x20) {
                stringBuffer.append("采集器整机通信异常");
            }
            if ((bytes[1] & 0x10) == 0x10) {
                stringBuffer.append("电能表整机通信异常");
            }
            if ((bytes[1] & 0x08) == 0x08) {
                stringBuffer.append("终端整机通信异常");
            }
            if ((bytes[1] & 0x04) == 0x04) {
                stringBuffer.append("采集器主机异常");
            }
            if ((bytes[1] & 0x02) == 0x02) {
                stringBuffer.append("采集器带负载异常");
            }
            if ((bytes[1] & 0x01) == 0x01) {
                stringBuffer.append("电能表主机异常");
            }
            if ((bytes[2] & 0x10) == 0x10) {
                stringBuffer.append("获取从节点中继路径异常");
            }
            if ((bytes[2] & 0x08) == 0x08) {
                stringBuffer.append("下发从节点固定中继路径异常");
            }
            if ((bytes[2] & 0x04) == 0x04) {
                stringBuffer.append("链路检测异常");
            }
            if ((bytes[2] & 0x02) == 0x02) {
                stringBuffer.append("电能表/采集器模块串口异常");
            }
            if ((bytes[2] & 0x01) == 0x01) {
                stringBuffer.append("集中器模块串口异常");
            }
            return stringBuffer.toString();

        }
        return errorCode_A4(data);
//      return "数据异常";
    }

    /**
     * 载波检测和微功率检测结果
     *
     * @param data 外设返回数据
     * @return
     */
    public String getEPCData(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        if (!"00".equals(data.substring(0, 2).toUpperCase())) {
            data = data.substring(0, 2);
            //国网计量中心
            stringBuffer.append("应用序列号：" + data.substring(0, 16) + "\n");
            stringBuffer.append("校验区：" + data.substring(16, 24) + "\n");

            //芯片厂家
            stringBuffer.append("版本：" + data.substring(24, 28) + "\n");
            stringBuffer.append("发行时间：" + data.substring(28, 40) + "\n");
            stringBuffer.append("封装厂家代码：" + data.substring(40, 44) + "\n");
            stringBuffer.append("互感器厂家代码：" + data.substring(44, 48) + "\n");
            stringBuffer.append("标签对象资产型号：" + data.substring(48, 70) + "\n");
            stringBuffer.append("型号：" + data.substring(70, 88) + "\n");
            stringBuffer.append("生产日期：" + data.substring(88, 96) + "\n");
            stringBuffer.append("预留位：" + data.substring(96, 104) + "\n");
            stringBuffer.append("校验区：" + data.substring(104, 112) + "\n");

            //省计量中心
            //------互感器参数密文
            stringBuffer.append("二次负荷上限值：" + data.substring(112, 116) + "\n");
            stringBuffer.append("电流比：" + data.substring(116, 122) + "\n");
            stringBuffer.append("准确度等级：" + data.substring(122, 124) + "\n");
            stringBuffer.append("额定一次电流扩大倍数：" + data.substring(124, 132) + "\n");
            stringBuffer.append("仪表保安系数：" + data.substring(132, 136) + "\n");
            stringBuffer.append("保留：" + data.substring(136, 148) + "\n");
            //----------
            stringBuffer.append("检定人员：" + data.substring(148, 154) + "\n");
            stringBuffer.append("检定结果：" + data.substring(154, 156) + "\n");
            stringBuffer.append("检定时间：" + data.substring(156, 164) + "\n");
            stringBuffer.append("预留位：" + data.substring(164, 172) + "\n");
            stringBuffer.append("校验位：" + data.substring(172, 180) + "\n");

            //现场操作
            stringBuffer.append("用户编号：" + data.substring(180, 196) + "\n");
            stringBuffer.append("操作次数：" + data.substring(196, 200) + "\n");

            stringBuffer.append("操作类型1：" + data.substring(200, 202) + "\n");
            stringBuffer.append("操作人员1：" + data.substring(202, 208) + "\n");
            stringBuffer.append("操作时间1：" + data.substring(208, 216) + "\n");
            stringBuffer.append("校验位1：" + data.substring(216, 224) + "\n");

            stringBuffer.append("操作类型2：" + data.substring(224, 226) + "\n");
            stringBuffer.append("操作人员2：" + data.substring(226, 232) + "\n");
            stringBuffer.append("操作时间2：" + data.substring(232, 240) + "\n");
            stringBuffer.append("校验位2：" + data.substring(240, 248) + "\n");

            stringBuffer.append("操作类型3：" + data.substring(248, 250) + "\n");
            stringBuffer.append("操作人员3：" + data.substring(250, 256) + "\n");
            stringBuffer.append("操作时间3：" + data.substring(256, 264) + "\n");
            stringBuffer.append("校验位3：" + data.substring(264, 272) + "\n");

            stringBuffer.append("操作类型4：" + data.substring(272, 274) + "\n");
            stringBuffer.append("操作人员4：" + data.substring(274, 280) + "\n");
            stringBuffer.append("操作时间4：" + data.substring(280, 288) + "\n");
            stringBuffer.append("校验位4：" + data.substring(288, 296) + "\n");

            stringBuffer.append("操作类型5：" + data.substring(296, 298) + "\n");
            stringBuffer.append("操作人员5：" + data.substring(298, 304) + "\n");
            stringBuffer.append("操作时间5：" + data.substring(304, 312) + "\n");
            stringBuffer.append("校验位5：" + data.substring(312, 320) + "\n");

            stringBuffer.append("操作类型6：" + data.substring(320, 322) + "\n");
            stringBuffer.append("操作人员6：" + data.substring(322, 328) + "\n");
            stringBuffer.append("操作时间6：" + data.substring(328, 336) + "\n");
            stringBuffer.append("校验位6：" + data.substring(336, 344) + "\n");
        }
        return "数据异常";
    }

    /**
     * 得到星期
     *
     * @param week 星期字符
     * @return
     */
    public String getWeek(String week) {
        if (TextUtils.isEmpty(week)) {
            return "数据异常";
        }
        switch (week) {
            case "00":
                return "星期日";
            case "01":
                return "星期一";
            case "02":
                return "星期二";
            case "03":
                return "星期三";
            case "04":
                return "星期四";
            case "05":
                return "星期五";
            case "06":
                return "星期六";
            default:
                return "";
        }
    }

    /**
     * 错误编码对应内容
     *
     * @param errCode 错误编码
     * @return 错误编码内容
     */
    public String errorCode_A2(String errCode) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(errCode) || errCode.length() != 2) {
            return "帧格式错误";
        }
        switch (errCode.toUpperCase()) {
            case "01":
                result.append("失败");
                break;
            case "FE":
                result.append("参数越限");
                break;
            case "FD":
                result.append("无此功能");
                break;
            case "FC":
                result.append("数据非法");
                break;
            case "FB":
                result.append("通讯超时");
                break;
            case "FA":
                result.append("外设地址非法");
                break;
            case "F9":
                result.append("初始化失败");
                break;
            case "F8":
                result.append("认证失败");
                break;
            default:
                result.append("未知错误");
                break;
        }
        return result.toString();
    }

    /**
     * 错误编码对应内容
     *
     * @param errCode 错误编码
     * @return 错误编码内容
     */
    public String errorCode_A4(String errCode) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(errCode) || errCode.length() != 2) {
            return "帧格式错误";
        }
        switch (errCode.toUpperCase()) {
            case "F0":
                result.append("备用");
                break;
            case "F1":
                result.append("失败");
                break;
            case "F2":
                result.append("数据已篡改");
                break;
            case "F3":
                result.append("MAC验证失败");
                break;
            case "F4":
                result.append("密钥更新失败");
                break;
            case "F5":
                result.append("密钥协商失败");
                break;
            case "F6":
                result.append("设置安全配置参数失败");
                break;
            case "F7":
                result.append("安全等级不匹配");
                break;
            case "F8":
                result.append("认证失败");
                break;
            case "F9":
                result.append("初始化失败");
                break;
            case "FA":
                result.append("参数非法");
                break;
            case "FB":
                result.append("通信超时");
                break;
            case "FC":
                result.append("数据格式不正确");
                break;
            case "FD":
                result.append("无此功能");
                break;
            case "FE":
                result.append("备用");
                break;
            default:
                result.append("未知错误");
                break;
        }
        return result.toString();
    }


//    /**
//     * 错误编码对应内容
//     *
//     * @param errCode 错误编码
//     * @return 错误编码内容
//     */
//    public String errorCode_A4(String errCode) {
//        StringBuffer result = new StringBuffer();
//        if (TextUtils.isEmpty(errCode) || errCode.length() != 8) {
//            return "帧格式错误";
//        }
//        byte[] data = DataConvert.toBytes(errCode);
//        if ((data[0] & 0x80) == 0x80) {
//            result.append("电能表带负载异常");
//        }
//        if ((data[0] & 0x40) == 0x40) {
//            result.append("终端主机异常");
//        }
//        if ((data[0] & 0x20) == 0x20) {
//            result.append("终端带负载异常");
//        }
//        if ((data[0] & 0x10) == 0x10) {
//            result.append("模块功耗异常");
//        }
//        if ((data[0] & 0x08) == 0x08) {
//            result.append("模块通信能力异常");
//        }
//        if ((data[0] & 0x04) == 0x04) {
//            result.append("载波模块逻辑单元异常");
//        }
//        if ((data[0] & 0x02) == 0x02) {
//            result.append("组网异常");
//        }
//        if ((data[0] & 0x01) == 0x01) {
//            result.append("串口异常");
//        }
//        if ((data[1] & 0x20) == 0x20) {
//            result.append("采集器整机通信异常");
//        }
//        if ((data[1] & 0x10) == 0x10) {
//            result.append("电能表整机通信异常");
//        }
//        if ((data[1] & 0x08) == 0x08) {
//            result.append("终端整机通信异常");
//        }
//        if ((data[1] & 0x04) == 0x04) {
//            result.append("采集器主机异常");
//        }
//        if ((data[1] & 0x02) == 0x02) {
//            result.append("采集器带负载异常");
//        }
//        if ((data[1] & 0x01) == 0x01) {
//            result.append("电能表主机异常");
//        }
//        return result.toString();
//    }

    /**
     * 错误编码对应内容（获取误差数据的异常分析）
     *
     * @param errCode 错误编码
     * @return 错误编码内容
     */
    public String errorCode(String errCode) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(errCode) || errCode.length() != 2) {
            return "帧格式错误";
        }
        switch (errCode.toUpperCase()) {
            case "08":
                result.append("被测表计通讯失败");
                break;
            case "04":
                result.append("无此功能");
                break;
            case "02":
                result.append("无请求数据");
                break;
            case "01":
                result.append("其他错误");
                break;

            default:
                result.append("未知错误");
        }
        return result.toString();
    }

    /**
     * 解析的BCD数
     *
     * @param data   :被解析的数据（低字节在前，高字节在后）
     * @param bytes  :字节数
     * @param points ：小数位数
     * @param unit   : 数据单位（如：表示电流的数据为“A”，表示电压的数据为“V”）
     * @return
     */
    private String decodeBCDData(String data, int bytes, int points, String unit) {
        if (data.isEmpty() || data.length() != bytes * 2)
            return "数据错误";
        if (bytes == 1) {
            if (points == 1) {
                return data.substring(0, 1) + "." + data.substring(1, 2) + unit;
            }
        }
        if (bytes == 2) {
            if (points == 1) {
                return data.substring(2, 4) + data.substring(0, 1) + "." + data.substring(1, 2) + unit;
            } else if (points == 2) {
                return data.substring(2, 4) + "." + data.substring(0, 2) + unit;
            } else if (points == 3) {
                return data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + unit;
            }
        } else if (bytes == 3) {
            if (points == 3) {
                return data.substring(4, 6) + data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + unit;
            } else if (points == 4) {
                return data.substring(4, 6) + "." + data.substring(2, 4) + data.substring(0, 2) + unit;
            }
        }

        return null;
    }

    /**
     * 解析每个参数值
     *
     * @param data  ：被解析数据
     * @param bytes ：字节数
     * @param nums  ：第几个参数
     * @return
     */
    private String decodeParam(String data, int bytes, int nums) {
        if (data.isEmpty() || data.length() != bytes * 2)
            return "数据错误";
        switch (nums) {
            case 0:
                if ("00".equals(data)) {
                    return "电表类型为：3P4W有功" + "\n";
                } else if ("01".equals(data)) {
                    return "电表类型为：3P4W无功" + "\n";
                } else if ("02".equals(data)) {
                    return "电表类型为：3P3W有功" + "\n";
                } else if ("03".equals(data)) {
                    return "电表类型为：3P3W无功" + "\n";
                }

            case 1:
                if ("00".equals(data)) {
                    return "电压量程为：480V" + "\n";
                } else if ("01".equals(data)) {
                    return "电压量程为：240V" + "\n";
                } else if ("02".equals(data)) {
                    return "电压量程为：120V" + "\n";
                } else if ("03".equals(data)) {
                    return "电压量程为：60V" + "\n";
                } else if ("04".equals(data)) {
                    return "电压量程为：AUTO" + "\n";
                }
            case 2:
                if ("01".equals(data)) {
                    return "电流输入为：5A钳表" + "\n";
                } else if ("02".equals(data)) {
                    return "电流输入为：钳表II" + "\n";
                } else if ("03".equals(data)) {
                    return "电流输入为：钳表III" + "\n";
                }
            case 3:
                String temp = data.substring(2, 4) + data.substring(0, 2);
                return "钳表II量程：" + Integer.parseInt(temp) + "A" + "\n";
            case 4:
                temp = data.substring(2, 4) + data.substring(0, 2);
                return "钳表III量程：" + Integer.parseInt(temp) + "A" + "\n";
            case 5:
                if ("00".equals(data)) {
                    return "电能输出为：1倍" + "\n";
                } else if ("01".equals(data)) {
                    return "电能输出为：2倍" + "\n";
                } else if ("02".equals(data)) {
                    return "电能输出为：10倍" + "\n";
                } else if ("03".equals(data)) {
                    return "电能输出为：自动" + "\n";
                }
            case 6:
                if ("00".equals(data)) {
                    return "脉冲输出为：HIGH" + "\n";
                } else if ("01".equals(data)) {
                    return "脉冲输出为：LOW" + "\n";
                }
            case 7:
                temp = data.substring(2, 4) + data.substring(0, 2);
                return "校验圈数：" + Integer.parseInt(temp) + "\n";
            case 8:
                temp = data.substring(4, 6) + data.substring(2, 4) + data.substring(0, 2);
                return "电表常数：" + Integer.parseInt(temp) + "\n";
            case 9:
                if ("00".equals(data)) {
                    return "误差启动为：停止" + "\n";
                } else if ("01".equals(data)) {
                    return "误差启动为：启动" + "\n";
                }
        }
        return null;
    }

    public String decodeRS485(String data) {
        StringBuffer result = new StringBuffer();
        Log.i(TAG, "decodeRS485:" + data);
        if (TextUtils.isEmpty(data)) {
            result.append("RS485设置参数失败");

        }
        if (data.length() != 5 * 2) {
            result.append("返回帧数据异常");
        } else {
            String temp = data.substring(0, 2);
            result.append("通信波特率：" + 300 * Integer.valueOf(temp) + "bps");
            temp = data.substring(2, 4);
            result.append(",校验方式：" + temp);
            temp = data.substring(4, 6);
            result.append(",数据位：" + temp);
            temp = data.substring(6, 8);
            result.append(",停止位：" + temp);
            temp = data.substring(8, 10);
            result.append(",超时时间：" + temp);
        }

        return result.toString();
    }

    public String decodeRS232(String data) {
        StringBuffer result = new StringBuffer();
        Log.i(TAG, "decodeRS485:" + data);
        if (TextUtils.isEmpty(data)) {
            result.append("RS232设置参数失败");

        }
        if (data.length() != 5 * 2) {
            result.append("返回帧数据异常");
        } else {
            String temp = data.substring(0, 2);
            result.append("通信波特率：" + 300 * Integer.valueOf(temp) + "bps");
            temp = data.substring(2, 4);
            result.append(",校验方式：" + temp);
            temp = data.substring(4, 6);
            result.append(",数据位：" + temp);
            temp = data.substring(6, 8);
            result.append(",停止位：" + temp);
            temp = data.substring(8, 10);
            result.append(",超时时间：" + temp);
        }
        return result.toString();
    }

    /*
    * 串户检测_台区识别
    **/
    public String getTQSBData(String data) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(data)) {
            result.append("台区识别失败");
        }
        if (data.length() == 13 * 2) {
            String temp = data.substring(0, 12);
            result.append(temp.substring(10, 12) + temp.substring(8, 10) + temp.substring(6, 8) + temp.substring(4, 6) + temp.substring(2, 4) + temp.substring(0, 2) + " ");
            temp = data.substring(12, 14);
            result.append(temp + " ");
            temp = data.substring(14, 16);
            if (temp.equals("00")) {
                result.append("A相 ");
            } else if (temp.equals("01")) {
                result.append("B相 ");
            } else if (temp.equals("02")) {
                result.append("C相 ");
            }
            temp = data.substring(16, 18);
            if (temp.equals("00")) {
                result.append("零火线正接");
            } else if (temp.equals("01")) {
                result.append("零火线反接");
            }
        }
        return result.toString();
    }

    /*
   * 串户检测
   **/
    public String getCHJCData(String data) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(data)) {
            result.append("台区识别失败");
        }
        if (data.length() == 1 * 2) {
            String binData = DataConvert.hexString2binaryString(data);
            String temp = binData.substring(7, 8);
            if (temp.equals("0")) {
//                result.append("户表关系：户表对应"+"\n");
                result.append("对应 ");
            } else {
//                result.append("户表关系：户表不对应"+"\n");
                result.append("不对应 ");
            }
            temp = binData.substring(6, 7);
            if (temp.equals("0")) {
//                result.append("户台关系：用户归属本台区"+"\n");
                result.append("归属 ");
            } else {
//                result.append("户台关系：用户不属本台区"+"\n");
                result.append("不属于 ");
            }
            temp = binData.substring(5, 6);
            if (temp.equals("0")) {
//                result.append("零火线反接：零火线正接"+"\n");
                result.append("正接");
            } else {
//                result.append("零火线反接：零火线反接"+"\n");
                result.append("反接");
            }
        } else {
            result.append("帧格式错误");
        }
        return result.toString();
    }

    /**
     * 串户检测错误编码对应内容
     *
     * @param data 错误编码
     * @return 错误编码内容
     */
    public String errorEPC_Data(String data) {
        StringBuffer stringBuffer = new StringBuffer();
        if (data.length() != 8) {
            return "帧格式错误";
        }
        byte[] bytes = DataConvert.toBytes(data);

        if ((bytes[0] & 0x20) == 0x20) {
            stringBuffer.append("外设硬件异常");
        }
        if ((bytes[0] & 0x10) == 0x10) {
            stringBuffer.append("无此功能");
        }
        if ((bytes[0] & 0x08) == 0x08) {
            stringBuffer.append("参数越限");
        }
        if ((bytes[0] & 0x04) == 0x04) {
            stringBuffer.append("错误标志异常");
        }
        if ((bytes[0] & 0x02) == 0x02) {
            stringBuffer.append("主从标志错误");
        }
        if ((bytes[0] & 0x01) == 0x01) {
            stringBuffer.append("传输方向错误");
        }
        if ((bytes[1] & 0x80) == 0x80) {
            stringBuffer.append("其他错误");
        }
        if ((bytes[1] & 0x40) == 0x40) {
            stringBuffer.append("安全认证失败");
        }
        if ((bytes[1] & 0x20) == 0x20) {
            stringBuffer.append("未通过鉴别");
        }
        if ((bytes[1] & 0x10) == 0x10) {
            stringBuffer.append("口令错误");
        }
        if ((bytes[1] & 0x08) == 0x08) {
            stringBuffer.append("存储区锁定");
        }
        if ((bytes[1] & 0x04) == 0x04) {
            stringBuffer.append("存储区溢出");
        }
        if ((bytes[1] & 0x02) == 0x02) {
            stringBuffer.append("权限不足");
        }
        if ((bytes[1] & 0x01) == 0x01) {
            stringBuffer.append("功率不足");
        }
        if ((bytes[2] & 0x02) == 0x02) {
            stringBuffer.append("标签无应答");
        }
        if ((bytes[2] & 0x01) == 0x01) {
            stringBuffer.append("没有找到标签");
        }
        return stringBuffer.toString();

    }

    /**
     * 串户检测错误编码对应内容
     *
     * @param errCode 错误编码
     * @return 错误编码内容
     */
    public String errorCode_CHJC(String errCode) {
        StringBuffer result = new StringBuffer();
        if (TextUtils.isEmpty(errCode) || errCode.length() != 2) {
            return "帧格式错误";
        }
        switch (errCode.toUpperCase()) {
            case "F0":
                result.append("备用");
                break;
            case "F1":
                result.append("失败");
                break;
            case "F2":
                result.append("数据已篡改");
                break;
            case "F3":
                result.append("MAC验证失败");
                break;
            case "F4":
                result.append("密钥更新失败");
                break;
            case "F5":
                result.append("密钥协商失败");
                break;
            case "F6":
                result.append("设置安全配置参数失败");
                break;
            case "F7":
                result.append("安全等级不匹配");
                break;
            case "F8":
                result.append("认证失败");
                break;
            case "F9":
                result.append("初始化失败");
                break;
            case "FA":
                result.append("参数非法");
                break;
            case "FB":
                result.append("通信超时");
                break;
            case "FC":
                result.append("数据格式不正确");
                break;
            case "FD":
                result.append("无此功能");
                break;
            case "FE":
                result.append("备用");
                break;
            case "01":
                result.append("设备忙碌");
                break;
            case "02":
                result.append("线路干扰");
                break;
            case "03":
                result.append("其他设备测试中");
                break;
            case "04":
                result.append("电压过高");
                break;
            case "05":
                result.append("电压过低");
                break;
            case "06":
                result.append("保险断");
                break;
            case "07":
                result.append("内部异常1：供电异常");
                break;
            case "08":
                result.append("内部异常1：发送单元异常");
                break;
            case "09":
                result.append("内部异常1：接受单元异常");
                break;
            case "0A":
                result.append("内部异常1：其他异常");
                break;
            case "0E":
                result.append("未发现主机");
                break;
            case "0F":
                result.append("未发现从机");
                break;
        }
        return result.toString();
    }

    /**
     * 解析的电表误差数据BCD数
     *
     * @param data   :被解析的数据（低字节在前，高字节在后）
     * @param bytes  :字节数
     * @param points ：小数位数
     * @param unit   : 数据单位（如：表示电流的数据为“A”，表示电压的数据为“V”）
     * @return
     */
    private String decodeErrorBCDData(String data, int bytes, int points, String unit) {
        if (data.isEmpty() || data.length() != bytes * 2)
            return "数据错误";
        if (bytes == 1) {
            if (points == 1) {
                return data.substring(0, 1) + "." + data.substring(1, 2) + unit;
            }
        }
        if (bytes == 2) {
            if (points == 1) {
                return data.substring(2, 4) + data.substring(0, 1) + "." + data.substring(1, 2) + unit;
            } else if (points == 2) {
                return data.substring(2, 4) + "." + data.substring(0, 2) + unit;
            } else if (points == 3) {
                return data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + unit;
            }
        } else if (bytes == 3) {
            if (points == 3) {
                String signByte = DataConvert.hexString2binaryString(data.substring(4, 6)).substring(0, 1);
                if (signByte.equals("1")) {
                    return "-" + data.substring(5, 6) + data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + unit;
                }
                return data.substring(4, 6) + data.substring(2, 3) + "." + data.substring(3, 4) + data.substring(0, 2) + unit;
            } else if (points == 4) {
                String signByte = DataConvert.hexString2binaryString(data.substring(4, 6)).substring(0, 1);
                if (signByte.equals("1")) {
                    return "-" + data.substring(5, 6) + "." + data.substring(2, 4) + data.substring(0, 2) + unit;
                }
                return data.substring(5, 6) + "." + data.substring(2, 4) + data.substring(0, 2) + unit;
            }
        }

        return null;
    }
}
