package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public interface PmPacketDT {
    public PmPacketDT setValue(byte[] value);
    public byte[] getValue();
}
