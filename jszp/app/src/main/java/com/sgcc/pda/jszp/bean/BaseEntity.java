package com.sgcc.pda.jszp.bean;

import java.io.Serializable;

/**
 * 返回码信息
 */
public class BaseEntity implements Serializable {

    /**
     * 返回码说明
     * 1 成功
       2 无效UID
       3 命令帧中有非法参数
       4 效验和错
       5 设备不支持本命令
       6 传输MAC错
       7 其他
       8 UID超时
       80 登录失败
       90 服务异常
     */
    //返回码
    public String RT_F;
    //信息描述
    public String RT_D;
    //传输MAC
    public String MAC;


    public String getRT_F() {
        return RT_F;
    }

    public String  getRT_F_INFO(){
        String info ="";
        if ("1".equals(RT_F)){

        }else if ("2".equals(RT_F)){
            info ="无效UID";
        }else if ("3".equals(RT_F)){
            info ="命令帧中有非法参数";
        }else if ("4".equals(RT_F)){
            info ="效验和错";
        }else if ("5".equals(RT_F)){
            info ="设备不支持本命令";
        }else if ("6".equals(RT_F)){
            info ="传输MAC错";
        }else if ("7".equals(RT_F)){
            info ="其他";
        }else if ("8".equals(RT_F)){
            info ="UID超时";
        }else if ("80".equals(RT_F)){
            info ="登录失败";
        }else if ("90".equals(RT_F)){
            info ="服务异常";
        }
        return info;
    }

    public void setRT_F(String RT_F) {
        this.RT_F = RT_F;
    }

    public String getRT_D() {
        return RT_D;
    }

    public void setRT_D(String RT_D) {
        this.RT_D = RT_D;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
