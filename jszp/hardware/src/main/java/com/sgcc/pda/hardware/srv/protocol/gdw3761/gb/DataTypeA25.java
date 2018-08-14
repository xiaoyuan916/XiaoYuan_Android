package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA25 extends DataTypeA9A25Base {
    public DataTypeA25(double value){
        setValue(value);
    }

    public DataTypeA25(byte[] array){
        setArray(array,0);
    }

    public final void setValue(double value){
        this.value = (long) Math.round(value * 1000);
    }

    public double getValue(){
        return this.value/1000.0;
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(this.getValue())).toString();
    }
}
