package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.buffer;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author luxiaochung
 */
public abstract class IoBuffer implements Comparable<IoBuffer> {
    /** The allocator used to create new buffers */
    private static IoBufferAllocator allocator = new SimpleBufferAllocator();

    /** A flag indicating which type of buffer we are using : heap or direct */
    private static boolean useDirectBuffer = false;

    public static IoBufferAllocator getAllocator() {
        return allocator;
    }

    public static void setAllocator(IoBufferAllocator newAllocator) {
        if (newAllocator == null) {
            throw new IllegalArgumentException("allocator");
        }

        IoBufferAllocator oldAllocator = allocator;

        allocator = newAllocator;

        if (null != oldAllocator) {
            oldAllocator.dispose();
        }
    }

    public static boolean isUseDirectBuffer() {
        return useDirectBuffer;
    }

    public static void setUseDirectBuffer(boolean useDirectBuffer) {
        IoBuffer.useDirectBuffer = useDirectBuffer;
    }

    public static IoBuffer allocate(int capacity) {
        return allocate(capacity, useDirectBuffer);
    }

    public static IoBuffer allocate(int capacity, boolean direct) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }

        return allocator.allocate(capacity, direct);
    }

    public static IoBuffer wrap(ByteBuffer nioBuffer) {
        return allocator.wrap(nioBuffer);
    }

    public static IoBuffer wrap(byte[] byteArray) {
        return wrap(ByteBuffer.wrap(byteArray));
    }

    public static IoBuffer wrap(byte[] byteArray, int offset, int length) {
        return wrap(ByteBuffer.wrap(byteArray, offset, length));
    }

    protected static int normalizeCapacity(int requestedCapacity) {
        if (requestedCapacity < 0) {
            return Integer.MAX_VALUE;
        }

        int newCapacity = Integer.highestOneBit(requestedCapacity);
        newCapacity <<= (newCapacity < requestedCapacity ? 1 : 0);
        return newCapacity < 0 ? Integer.MAX_VALUE : newCapacity;
    }

    protected IoBuffer() {
        // Do nothing
    }

    public abstract void free();

    public abstract ByteBuffer buf();

    public abstract boolean isDirect();

    public abstract boolean isDerived();

    public abstract boolean isReadOnly();

    public abstract int minimumCapacity();

    public abstract IoBuffer minimumCapacity(int minimumCapacity);

    public abstract int capacity();

    public abstract IoBuffer capacity(int newCapacity);

    public abstract boolean isAutoExpand();

    public abstract IoBuffer setAutoExpand(boolean autoExpand);

    public abstract boolean isAutoShrink();

    public abstract IoBuffer setAutoShrink(boolean autoShrink);

    public abstract IoBuffer expand(int expectedRemaining);

    public abstract IoBuffer expand(int position, int expectedRemaining);

    public abstract IoBuffer shrink();

    public abstract int position();

    public abstract IoBuffer position(int newPosition);

    public abstract int limit();

    public abstract IoBuffer limit(int newLimit);

    public abstract IoBuffer mark();

    public abstract int markValue();

    public abstract IoBuffer reset();

    public abstract IoBuffer clear();

    public abstract IoBuffer sweep();

    public abstract IoBuffer sweep(byte value);

    public abstract IoBuffer flip();

    public abstract IoBuffer rewind();

    public abstract int remaining();

    public abstract boolean hasRemaining();

    public abstract IoBuffer duplicate();

    public abstract IoBuffer slice();

    public abstract IoBuffer asReadOnlyBuffer();

    public abstract boolean hasArray();

    public abstract byte[] array();

    public abstract int arrayOffset();

    public abstract byte get();

    public abstract short getUnsigned();

    public abstract IoBuffer put(byte b);

    public abstract byte get(int index);

    public abstract short getUnsigned(int index);

    public abstract IoBuffer put(int index, byte b);

    public abstract IoBuffer get(byte[] dst, int offset, int length);

    public abstract IoBuffer get(byte[] dst);

    public abstract IoBuffer getSlice(int index, int length);

    public abstract IoBuffer getSlice(int length);

    public abstract IoBuffer put(ByteBuffer src);

    public abstract IoBuffer put(IoBuffer src);

    public abstract IoBuffer put(byte[] src, int offset, int length);

    public abstract IoBuffer put(byte[] src);

    public abstract IoBuffer compact();

    public abstract ByteOrder order();

    public abstract IoBuffer order(ByteOrder bo);

    public abstract char getChar();

    public abstract IoBuffer putChar(char value);

    public abstract char getChar(int index);

    public abstract IoBuffer putChar(int index, char value);

    public abstract CharBuffer asCharBuffer();

    public abstract short getShort();

    public abstract int getUnsignedShort();

    public abstract IoBuffer putShort(short value);

    public abstract short getShort(int index);

    public abstract int getUnsignedShort(int index);

    public abstract IoBuffer putShort(int index, short value);

    public abstract ShortBuffer asShortBuffer();

    public abstract int getInt();

    public abstract long getUnsignedInt();

    public abstract int getMediumInt();

    public abstract int getUnsignedMediumInt();

    public abstract int getMediumInt(int index);

    public abstract int getUnsignedMediumInt(int index);

    public abstract IoBuffer putMediumInt(int value);

    public abstract IoBuffer putMediumInt(int index, int value);

    public abstract IoBuffer putInt(int value);

    public abstract IoBuffer putUnsigned(byte value);

    public abstract IoBuffer putUnsigned(int index, byte value);

    public abstract IoBuffer putUnsigned(short value);

    public abstract IoBuffer putUnsigned(int index, short value);

    public abstract IoBuffer putUnsigned(int value);

    public abstract IoBuffer putUnsigned(int index, int value);

    public abstract IoBuffer putUnsigned(long value);

    public abstract IoBuffer putUnsigned(int index, long value);

    public abstract IoBuffer putUnsignedInt(byte value);

    public abstract IoBuffer putUnsignedInt(int index, byte value);

    public abstract IoBuffer putUnsignedInt(short value);

    public abstract IoBuffer putUnsignedInt(int index, short value);

    public abstract IoBuffer putUnsignedInt(int value);

    public abstract IoBuffer putUnsignedInt(int index, int value);

    public abstract IoBuffer putUnsignedInt(long value);

    public abstract IoBuffer putUnsignedInt(int index, long value);

    public abstract IoBuffer putUnsignedShort(byte value);

    public abstract IoBuffer putUnsignedShort(int index, byte value);

    public abstract IoBuffer putUnsignedShort(short value);

    public abstract IoBuffer putUnsignedShort(int index, short value);

    public abstract IoBuffer putUnsignedShort(int value);

    public abstract IoBuffer putUnsignedShort(int index, int value);

    public abstract IoBuffer putUnsignedShort(long value);

    public abstract IoBuffer putUnsignedShort(int index, long value);

    public abstract int getInt(int index);

    public abstract long getUnsignedInt(int index);

    public abstract IoBuffer putInt(int index, int value);

    public abstract IntBuffer asIntBuffer();

    public abstract long getLong();

    public abstract IoBuffer putLong(long value);

    public abstract long getLong(int index);

    public abstract IoBuffer putLong(int index, long value);

    public abstract LongBuffer asLongBuffer();

    public abstract float getFloat();

    public abstract IoBuffer putFloat(float value);

    public abstract float getFloat(int index);

    public abstract IoBuffer putFloat(int index, float value);

    public abstract FloatBuffer asFloatBuffer();

    public abstract double getDouble();

    public abstract IoBuffer putDouble(double value);

    public abstract double getDouble(int index);

    public abstract IoBuffer putDouble(int index, double value);

    public abstract DoubleBuffer asDoubleBuffer();

    public abstract InputStream asInputStream();

    public abstract OutputStream asOutputStream();

    public abstract String getHexDump();

    public abstract String getHexDump(int lengthLimit);

    // //////////////////////////////
    // String getters and putters //
    // //////////////////////////////

    public abstract String getString(CharsetDecoder decoder) throws CharacterCodingException;

    public abstract String getString(int fieldSize, CharsetDecoder decoder) throws CharacterCodingException;

    public abstract IoBuffer putString(CharSequence val, CharsetEncoder encoder) throws CharacterCodingException;

    public abstract IoBuffer putString(CharSequence val, int fieldSize, CharsetEncoder encoder)
            throws CharacterCodingException;

    public abstract String getPrefixedString(CharsetDecoder decoder) throws CharacterCodingException;

    public abstract String getPrefixedString(int prefixLength, CharsetDecoder decoder) throws CharacterCodingException;

    public abstract IoBuffer putPrefixedString(CharSequence in, CharsetEncoder encoder) throws CharacterCodingException;

    public abstract IoBuffer putPrefixedString(CharSequence in, int prefixLength, CharsetEncoder encoder)
            throws CharacterCodingException;

    public abstract IoBuffer putPrefixedString(CharSequence in, int prefixLength, int padding, CharsetEncoder encoder)
            throws CharacterCodingException;

    public abstract IoBuffer putPrefixedString(CharSequence val, int prefixLength, int padding, byte padValue,
            CharsetEncoder encoder) throws CharacterCodingException;

    public abstract Object getObject() throws ClassNotFoundException;

    public abstract Object getObject(final ClassLoader classLoader) throws ClassNotFoundException;

    public abstract IoBuffer putObject(Object o);

    public abstract boolean prefixedDataAvailable(int prefixLength);

    public abstract boolean prefixedDataAvailable(int prefixLength, int maxDataLength);

    // ///////////////////
    // IndexOf methods //
    // ///////////////////

    public abstract int indexOf(byte b);

    // ////////////////////////
    // Skip or fill methods //
    // ////////////////////////

    public abstract IoBuffer skip(int size);

    public abstract IoBuffer fill(byte value, int size);

    public abstract IoBuffer fillAndReset(byte value, int size);

    public abstract IoBuffer fill(int size);

    public abstract IoBuffer fillAndReset(int size);

    // ////////////////////////
    // Enum methods //
    // ////////////////////////

    public abstract <E extends Enum<E>> E getEnum(Class<E> enumClass);

    public abstract <E extends Enum<E>> E getEnum(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> E getEnumShort(Class<E> enumClass);

    public abstract <E extends Enum<E>> E getEnumShort(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> E getEnumInt(Class<E> enumClass);

    public abstract <E extends Enum<E>> E getEnumInt(int index, Class<E> enumClass);

    public abstract IoBuffer putEnum(Enum<?> e);

    public abstract IoBuffer putEnum(int index, Enum<?> e);

    public abstract IoBuffer putEnumShort(Enum<?> e);

    public abstract IoBuffer putEnumShort(int index, Enum<?> e);

    public abstract IoBuffer putEnumInt(Enum<?> e);

    public abstract IoBuffer putEnumInt(int index, Enum<?> e);

    // ////////////////////////
    // EnumSet methods //
    // ////////////////////////

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSet(Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSet(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetShort(Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetShort(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetInt(Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetInt(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetLong(Class<E> enumClass);

    public abstract <E extends Enum<E>> EnumSet<E> getEnumSetLong(int index, Class<E> enumClass);

    public abstract <E extends Enum<E>> IoBuffer putEnumSet(Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSet(int index, Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetShort(Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetShort(int index, Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetInt(Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetInt(int index, Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetLong(Set<E> set);

    public abstract <E extends Enum<E>> IoBuffer putEnumSetLong(int index, Set<E> set);
}
