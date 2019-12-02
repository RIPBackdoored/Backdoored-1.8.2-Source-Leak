package org.spongepowered.asm.lib;

public class TypePath
{
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int WILDCARD_BOUND = 2;
    public static final int TYPE_ARGUMENT = 3;
    byte[] b;
    int offset;
    
    TypePath(final byte[] a1, final int a2) {
        super();
        this.b = a1;
        this.offset = a2;
    }
    
    public int getLength() {
        return this.b[this.offset];
    }
    
    public int getStep(final int a1) {
        return this.b[this.offset + 2 * a1 + 1];
    }
    
    public int getStepArgument(final int a1) {
        return this.b[this.offset + 2 * a1 + 2];
    }
    
    public static TypePath fromString(final String v-3) {
        if (v-3 == null || v-3.length() == 0) {
            return null;
        }
        final int length = v-3.length();
        final ByteVector byteVector = new ByteVector(length);
        byteVector.putByte(0);
        int v0 = 0;
        while (v0 < length) {
            char v2 = v-3.charAt(v0++);
            if (v2 == '[') {
                byteVector.put11(0, 0);
            }
            else if (v2 == '.') {
                byteVector.put11(1, 0);
            }
            else if (v2 == '*') {
                byteVector.put11(2, 0);
            }
            else {
                if (v2 < '0' || v2 > '9') {
                    continue;
                }
                int a1 = v2 - '0';
                while (v0 < length && (v2 = v-3.charAt(v0)) >= '0' && v2 <= '9') {
                    a1 = a1 * 10 + v2 - 48;
                    ++v0;
                }
                if (v0 < length && v-3.charAt(v0) == ';') {
                    ++v0;
                }
                byteVector.put11(3, a1);
            }
        }
        byteVector.data[0] = (byte)(byteVector.length / 2);
        return new TypePath(byteVector.data, 0);
    }
    
    @Override
    public String toString() {
        final int length = this.getLength();
        final StringBuilder v0 = new StringBuilder(length * 2);
        for (int v2 = 0; v2 < length; ++v2) {
            switch (this.getStep(v2)) {
                case 0: {
                    v0.append('[');
                    break;
                }
                case 1: {
                    v0.append('.');
                    break;
                }
                case 2: {
                    v0.append('*');
                    break;
                }
                case 3: {
                    v0.append(this.getStepArgument(v2)).append(';');
                    break;
                }
                default: {
                    v0.append('_');
                    break;
                }
            }
        }
        return v0.toString();
    }
}
