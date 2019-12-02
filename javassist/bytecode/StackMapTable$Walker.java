package javassist.bytecode;

public static class Walker
{
    byte[] info;
    int numOfEntries;
    
    public Walker(final StackMapTable a1) {
        this(a1.get());
    }
    
    public Walker(final byte[] a1) {
        super();
        this.info = a1;
        this.numOfEntries = ByteArray.readU16bit(a1, 0);
    }
    
    public final int size() {
        return this.numOfEntries;
    }
    
    public void parse() throws BadBytecode {
        final int numOfEntries = this.numOfEntries;
        int v0 = 2;
        for (int v2 = 0; v2 < numOfEntries; ++v2) {
            v0 = this.stackMapFrames(v0, v2);
        }
    }
    
    int stackMapFrames(int v2, final int v3) throws BadBytecode {
        final int v4 = this.info[v2] & 0xFF;
        if (v4 < 64) {
            this.sameFrame(v2, v4);
            ++v2;
        }
        else if (v4 < 128) {
            v2 = this.sameLocals(v2, v4);
        }
        else {
            if (v4 < 247) {
                throw new BadBytecode("bad frame_type in StackMapTable");
            }
            if (v4 == 247) {
                v2 = this.sameLocals(v2, v4);
            }
            else if (v4 < 251) {
                final int a1 = ByteArray.readU16bit(this.info, v2 + 1);
                this.chopFrame(v2, a1, 251 - v4);
                v2 += 3;
            }
            else if (v4 == 251) {
                final int a2 = ByteArray.readU16bit(this.info, v2 + 1);
                this.sameFrame(v2, a2);
                v2 += 3;
            }
            else if (v4 < 255) {
                v2 = this.appendFrame(v2, v4);
            }
            else {
                v2 = this.fullFrame(v2);
            }
        }
        return v2;
    }
    
    public void sameFrame(final int a1, final int a2) throws BadBytecode {
    }
    
    private int sameLocals(int v1, final int v2) throws BadBytecode {
        final int v3 = v1;
        int v4 = 0;
        if (v2 < 128) {
            final int a1 = v2 - 64;
        }
        else {
            v4 = ByteArray.readU16bit(this.info, v1 + 1);
            v1 += 2;
        }
        final int v5 = this.info[v1 + 1] & 0xFF;
        int v6 = 0;
        if (v5 == 7 || v5 == 8) {
            v6 = ByteArray.readU16bit(this.info, v1 + 2);
            this.objectOrUninitialized(v5, v6, v1 + 2);
            v1 += 2;
        }
        this.sameLocals(v3, v4, v5, v6);
        return v1 + 2;
    }
    
    public void sameLocals(final int a1, final int a2, final int a3, final int a4) throws BadBytecode {
    }
    
    public void chopFrame(final int a1, final int a2, final int a3) throws BadBytecode {
    }
    
    private int appendFrame(final int v2, final int v3) throws BadBytecode {
        final int v4 = v3 - 251;
        final int v5 = ByteArray.readU16bit(this.info, v2 + 1);
        final int[] v6 = new int[v4];
        final int[] v7 = new int[v4];
        int v8 = v2 + 3;
        for (int a2 = 0; a2 < v4; ++a2) {
            final int a3 = this.info[v8] & 0xFF;
            v6[a2] = a3;
            if (a3 == 7 || a3 == 8) {
                this.objectOrUninitialized(a3, v7[a2] = ByteArray.readU16bit(this.info, v8 + 1), v8 + 1);
                v8 += 3;
            }
            else {
                v7[a2] = 0;
                ++v8;
            }
        }
        this.appendFrame(v2, v5, v6, v7);
        return v8;
    }
    
    public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) throws BadBytecode {
    }
    
    private int fullFrame(final int a1) throws BadBytecode {
        final int v1 = ByteArray.readU16bit(this.info, a1 + 1);
        final int v2 = ByteArray.readU16bit(this.info, a1 + 3);
        final int[] v3 = new int[v2];
        final int[] v4 = new int[v2];
        int v5 = this.verifyTypeInfo(a1 + 5, v2, v3, v4);
        final int v6 = ByteArray.readU16bit(this.info, v5);
        final int[] v7 = new int[v6];
        final int[] v8 = new int[v6];
        v5 = this.verifyTypeInfo(v5 + 2, v6, v7, v8);
        this.fullFrame(a1, v1, v3, v4, v7, v8);
        return v5;
    }
    
    public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) throws BadBytecode {
    }
    
    private int verifyTypeInfo(int a4, final int v1, final int[] v2, final int[] v3) {
        for (int a5 = 0; a5 < v1; ++a5) {
            final int a6 = this.info[a4++] & 0xFF;
            v2[a5] = a6;
            if (a6 == 7 || a6 == 8) {
                this.objectOrUninitialized(a6, v3[a5] = ByteArray.readU16bit(this.info, a4), a4);
                a4 += 2;
            }
        }
        return a4;
    }
    
    public void objectOrUninitialized(final int a1, final int a2, final int a3) {
    }
}
