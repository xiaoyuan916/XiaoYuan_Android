package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA23 extends DataTypeLongBase{
    public DataTypeA23(float value){
        setValue(value);
    }

    public DataTypeA23(byte[] array){
        setArray(array,0);
    }

    public final void setValue(float value){
        this.value = Math.round(Math.floor(value*10000));
    }

    public float getValue(){
        return (float) (this.value/10000.0);
    }

    @Override
    public final void setArray(byte[] array, int beginPosition) {
        super.setArray(array, beginPosition, 3);
    }

    public byte[] getArray(){
        return super.getArray(3);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Float(getValue())).toString();
    }
}
