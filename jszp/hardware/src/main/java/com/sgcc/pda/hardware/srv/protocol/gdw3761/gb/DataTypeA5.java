package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA5 extends DataTypeA5A6Base {
    
    public DataTypeA5(float value){
        setValue(value);
    }
    
    public DataTypeA5(byte[] array){
        setArray(array,0);
    }
    
    public float getValue(){
        return (float) (this.value/10.0);
    }

    public final void setValue(float value) {
        this.value = (int) Math.round(Math.floor(value * 10));
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value/ 10.0)).toString();
    }
}
