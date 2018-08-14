package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA14 extends DataTypeLongBase{
    public DataTypeA14(double value){
        super();
        setValue(value);
    }

    public DataTypeA14(byte[] array){
        super();
        setArray(array,0);
    }

    public final void setValue(double value){
        this.value = Math.round(value*10000);
    }

    public double getValue(){
        return this.value/10000.0;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,5);
    }

    public byte[] getArray(){
        return getArray(5);
    }

    @Override
    public String toString(){
        if (this.isNull) return "";
        return (new Double(getValue())).toString();
    }
}
