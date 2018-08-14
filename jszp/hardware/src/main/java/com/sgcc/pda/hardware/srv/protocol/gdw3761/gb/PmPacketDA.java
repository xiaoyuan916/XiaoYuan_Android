package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

/**
 *
 * @author luxiaochung
 */
public interface PmPacketDA {
    public PmPacketDA setValue(byte[] value);
    public byte[] getValue();
}
