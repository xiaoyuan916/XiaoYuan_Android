/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.buffer;

/**
 *
 * @author luxiaochung
 */
public class BufferDataException extends RuntimeException {
    private static final long serialVersionUID = -4138189188602563502L;

    public BufferDataException() {
        super();
    }

    public BufferDataException(String message) {
        super(message);
    }

    public BufferDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public BufferDataException(Throwable cause) {
        super(cause);
    }

}
