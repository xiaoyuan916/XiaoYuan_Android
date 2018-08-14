package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.buffer;

import java.nio.ByteBuffer;

/**
 *
 * @author luxiaochung
 */
public interface IoBufferAllocator {
    IoBuffer allocate(int capacity, boolean direct);

    ByteBuffer allocateNioBuffer(int capacity, boolean direct);

    IoBuffer wrap(ByteBuffer nioBuffer);

    void dispose();
}
