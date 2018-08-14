/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.BcdUtils;

/**
 *
 * @author luxiaochung
 */
public class Authorize {
    private final byte[] authorizeBytes;

    public Authorize(){
        authorizeBytes = new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    }

    public byte[] getValue(){
        return authorizeBytes;
    }

    public Authorize setValue(byte[] value, int firstIndex){
        if (firstIndex+Authorize.length()<value.length){
            for (int i = 0; i<Authorize.length(); i++)
                this.authorizeBytes[i] = value[firstIndex+i];
        }

        return this;
    }

    public Authorize setValue(byte[] value){
        return this.setValue(value,0);
    }

    public static int length(){
        return 16;
    }

    @Override
    public String toString(){
        return BcdUtils.binArrayToString(authorizeBytes);
    }
}
