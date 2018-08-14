package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA22 extends DataTypeLongBase{
    public DataTypeA22(float value){
        setValue(value);
    }

    public DataTypeA22(byte[] array){
        setArray(array,0);
    }

    public final void setValue(float value){
        this.value = Math.round(Math.floor(value*10));
    }

    public float getValue(){
        return (float) (this.value/10.0);
    }

    @Override
    public final void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 1);
    }

    public byte[] getArray(){
        return super.getArray(1);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Float(getValue())).toString();
    }
}
