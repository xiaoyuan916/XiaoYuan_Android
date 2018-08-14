package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author luxiaochung
 */

/**
 * A simplistic {@link IoBufferAllocator} which simply allocates a new buffer
 * every time.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SimpleBufferAllocator implements IoBufferAllocator {

	@Override
	public IoBuffer allocate(int capacity, boolean direct) {
		return wrap(allocateNioBuffer(capacity, direct));
	}

	@Override
	public ByteBuffer allocateNioBuffer(int capacity, boolean direct) {
		ByteBuffer nioBuffer;
		if (direct) {
			nioBuffer = ByteBuffer.allocateDirect(capacity);
		} else {
			nioBuffer = ByteBuffer.allocate(capacity);
		}
		return nioBuffer;
	}

	@Override
	public IoBuffer wrap(ByteBuffer nioBuffer) {
		return new SimpleBuffer(nioBuffer);
	}

	@Override
	public void dispose() {
		// Do nothing
	}

	private class SimpleBuffer extends AbstractIoBuffer {
		private ByteBuffer buf;

		protected SimpleBuffer(ByteBuffer buf) {
			super(SimpleBufferAllocator.this, buf.capacity());
			this.buf = buf;
			buf.order(ByteOrder.BIG_ENDIAN);
		}

		protected SimpleBuffer(SimpleBuffer parent, ByteBuffer buf) {
			super(parent);
			this.buf = buf;
		}

		@Override
		public ByteBuffer buf() {
			return buf;
		}

		@Override
		protected void buf(ByteBuffer buf) {
			this.buf = buf;
		}

		@Override
		protected IoBuffer duplicate0() {
			return new SimpleBuffer(this, this.buf.duplicate());
		}

		@Override
		protected IoBuffer slice0() {
			return new SimpleBuffer(this, this.buf.slice());
		}

		@Override
		protected IoBuffer asReadOnlyBuffer0() {
			return new SimpleBuffer(this, this.buf.asReadOnlyBuffer());
		}

		@Override
		public byte[] array() {
			return buf.array();
		}

		@Override
		public int arrayOffset() {
			return buf.arrayOffset();
		}

		@Override
		public boolean hasArray() {
			return buf.hasArray();
		}

		@Override
		public void free() {
			// Do nothing
		}
	}
}
