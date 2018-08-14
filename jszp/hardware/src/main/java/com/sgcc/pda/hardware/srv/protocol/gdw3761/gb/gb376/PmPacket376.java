/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.gb376;


import com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.PmPacket;

/**
 *
 * @author luxiaochung
 */

public class PmPacket376 extends PmPacket {
    private static final byte protocolVersion = 2;

    @Override
    public PmPacket376 clone(){
        PmPacket376 result = new PmPacket376();
        result.setValue(this.getValue(),0);
        
        return result;
    }

    @Override
    protected byte getProtocolVersion(){
        return PmPacket376.protocolVersion;
    }
    
    public static int getMsgHeadOffset(byte[] msg, int firstIndex){
        return PmPacket.getMsgHeadOffset(msg, PmPacket376.protocolVersion, firstIndex);
    }

}
