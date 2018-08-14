package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA9 extends DataTypeA9A25Base {
    public DataTypeA9(double value){
        setValue(value);
    }
    
    public DataTypeA9(byte[] array){
        setArray(array,0);
    }
    
    public final void setValue(double value){
        this.value = Math.round(Math.floor(value*10000));
    }

    public double getValue(){
        return this.value/10000.0;
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value/ 10000.0)).toString();
    }
}
