package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA11 extends DataTypeLongBase{
    public DataTypeA11(double value){
        super();
        setValue(value);
    }

    public DataTypeA11(byte[] array){
        super();
        setArray(array,0);
    }

    public final void setValue(double value){
        this.value = Math.round(value*100);
    }

    public double getValue(){
        return this.value/100.0;
    }

    public final void setArray(byte[] array, int beginPosition){
        setArray(array,beginPosition,4);
    }

    public byte[] getArray(){
        return getArray(4);
    }

    @Override
    public String toString(){
        if (this.isNull) 
            return "";
        else
            return (new Double(getValue())).toString();
    }
}
