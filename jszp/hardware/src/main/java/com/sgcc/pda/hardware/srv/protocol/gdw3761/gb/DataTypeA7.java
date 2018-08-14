package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public class DataTypeA7 extends DataTypeA7A8Base {
    public DataTypeA7(byte[] array){
        super.setArray(array);
    }

    public DataTypeA7(float value){
        setValue(value);
    }

    public final void setValue(float value){
        this.value = (int) Math.round(Math.floor(value*10));
    }

    public float getValue(){
        return (float) (this.value / 10.0);
    }

    @Override
    public String toString() {
        if (this.isNull) return "";
        return (new Double(value/ 10.0)).toString();
    }
}
