package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public abstract class DataTypeLongBase {
    protected boolean isNull = true;
    protected long value;

    protected DataTypeLongBase(){
        super();
    }

    protected void setArray(byte[] array, int beginPosition, int len){
        if (array.length-beginPosition<len)
            throw new IllegalArgumentException();
        else
            try {
                this.value = BcdUtils.bcdToInt(array, beginPosition, len);
                this.isNull = false;
            } catch (Exception ex){
                this.isNull = true;
            }
    }

    protected byte[] getArray(int len){
        return BcdUtils.intTobcd(this.value, len);
    }

    public abstract void setArray(byte[] array, int beginPosition);

    public void setArray(byte[] array) {
        setArray(array, 0);
    }
}
