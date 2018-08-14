package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb.utils.buffer;

/**
 *
 * @author luxiaochung
 */
class IoBufferHexDumper {

    private static final byte[] highDigits;

    private static final byte[] lowDigits;

    static {
        final byte[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        int i;
        byte[] high = new byte[256];
        byte[] low = new byte[256];

        for (i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 0x0F];
        }

        highDigits = high;
        lowDigits = low;
    }

    public static String getHexdump(IoBuffer in, int lengthLimit) {
        if (lengthLimit == 0) {
            throw new IllegalArgumentException("lengthLimit: " + lengthLimit + " (expected: 1+)");
        }

        boolean truncate = in.remaining() > lengthLimit;
        int size;
        if (truncate) {
            size = lengthLimit;
        } else {
            size = in.remaining();
        }

        if (size == 0) {
            return "empty";
        }

        StringBuilder out = new StringBuilder(size * 3 + 3);

        int mark = in.position();

        // fill the first
        int byteValue = in.get() & 0xFF;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        size--;

        // and the others, too
        for (; size > 0; size--) {
            out.append(' ');
            byteValue = in.get() & 0xFF;
            out.append((char) highDigits[byteValue]);
            out.append((char) lowDigits[byteValue]);
        }

        in.position(mark);

        if (truncate) {
            out.append("...");
        }

        return out.toString();
    }
}