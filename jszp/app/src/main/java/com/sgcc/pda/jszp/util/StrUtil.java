package com.sgcc.pda.jszp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtil {

    public static String stripUnit(String src){
        if(src==null||"".equals(src))return src;
        String last="";
        while(true){
            last=src.substring(src.length()-1);

            try {
                int last_int=Integer.parseInt(last);
                break;
            } catch (NumberFormatException e) {
                src=src.substring(0,src.length()-1);
                e.printStackTrace();
            }
        }

        return src;
    }

    /**
     * long型转成yyyy-MM-dd
     * @param time
     * @return
     */
    public  static String longToString(long time){
        Date date=new Date(time);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);

    }
}
