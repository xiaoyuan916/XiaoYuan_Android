package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA26 extends DataTypeLongBase{
    public DataTypeA26(float value){
        setValue(value);
    }

    public DataTypeA26(byte[] array){
        setArray(array,0);
    }

    public final void setValue(float value){
        this.value = Math.round(Math.floor(value*1000));
    }

    public float getValue(){
        return (float) (this.value/1000.0);
    }

    @Override
    public final void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 2);
    }

    public byte[] getArray(){
        return super.getArray(2);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Float(getValue())).toString();
    }
}
