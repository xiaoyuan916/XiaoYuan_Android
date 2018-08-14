package com.sgcc.pda.hardware.util;


import android.os.Build;

/**
 * Created by jiliangliang on 2017/8/1.
 */
public class NewProtocol {

    public static boolean isNewProtocol(){
        if (Build.MODEL.equals("HE5001-II")||Build.MODEL.equals("TCHN-106")||Build.MODEL.equals("TCHN-105")||Build.MODEL.equals("TCHN-105W")||Build.MODEL.equals("SC3402A")||Build.MODEL.equals("7202G3A")||Build.MODEL.equals("HT380")
                ||Build.MODEL.equals("7202G") || Build.MODEL.equals("newmobi6735_66t_v_l1")) {
            return true;
        }
        return false;
    }

}
