package javassist.bytecode;

static class SubWalker
{
    byte[] info;
    
    SubWalker(final byte[] a1) {
        super();
        this.info = a1;
    }
    
    final int targetInfo(final int v-1, final int v0) throws Exception {
        switch (v0) {
            case 0:
            case 1: {
                final int a1 = this.info[v-1] & 0xFF;
                this.typeParameterTarget(v-1, v0, a1);
                return v-1 + 1;
            }
            case 16: {
                final int a2 = ByteArray.readU16bit(this.info, v-1);
                this.supertypeTarget(v-1, a2);
                return v-1 + 2;
            }
            case 17:
            case 18: {
                final int v = this.info[v-1] & 0xFF;
                final int v2 = this.info[v-1 + 1] & 0xFF;
                this.typeParameterBoundTarget(v-1, v0, v, v2);
                return v-1 + 2;
            }
            case 19:
            case 20:
            case 21: {
                this.emptyTarget(v-1, v0);
                return v-1;
            }
            case 22: {
                final int v = this.info[v-1] & 0xFF;
                this.formalParameterTarget(v-1, v);
                return v-1 + 1;
            }
            case 23: {
                final int v = ByteArray.readU16bit(this.info, v-1);
                this.throwsTarget(v-1, v);
                return v-1 + 2;
            }
            case 64:
            case 65: {
                final int v = ByteArray.readU16bit(this.info, v-1);
                return this.localvarTarget(v-1 + 2, v0, v);
            }
            case 66: {
                final int v = ByteArray.readU16bit(this.info, v-1);
                this.catchTarget(v-1, v);
                return v-1 + 2;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                final int v = ByteArray.readU16bit(this.info, v-1);
                this.offsetTarget(v-1, v0, v);
                return v-1 + 2;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                final int v = ByteArray.readU16bit(this.info, v-1);
                final int v2 = this.info[v-1 + 2] & 0xFF;
                this.typeArgumentTarget(v-1, v0, v, v2);
                return v-1 + 3;
            }
            default: {
                throw new RuntimeException("invalid target type: " + v0);
            }
        }
    }
    
    void typeParameterTarget(final int a1, final int a2, final int a3) throws Exception {
    }
    
    void supertypeTarget(final int a1, final int a2) throws Exception {
    }
    
    void typeParameterBoundTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
    }
    
    void emptyTarget(final int a1, final int a2) throws Exception {
    }
    
    void formalParameterTarget(final int a1, final int a2) throws Exception {
    }
    
    void throwsTarget(final int a1, final int a2) throws Exception {
    }
    
    int localvarTarget(int v-2, final int v-1, final int v0) throws Exception {
        for (int v = 0; v < v0; ++v) {
            final int a1 = ByteArray.readU16bit(this.info, v-2);
            final int a2 = ByteArray.readU16bit(this.info, v-2 + 2);
            final int a3 = ByteArray.readU16bit(this.info, v-2 + 4);
            this.localvarTarget(v-2, v-1, a1, a2, a3);
            v-2 += 6;
        }
        return v-2;
    }
    
    void localvarTarget(final int a1, final int a2, final int a3, final int a4, final int a5) throws Exception {
    }
    
    void catchTarget(final int a1, final int a2) throws Exception {
    }
    
    void offsetTarget(final int a1, final int a2, final int a3) throws Exception {
    }
    
    void typeArgumentTarget(final int a1, final int a2, final int a3, final int a4) throws Exception {
    }
    
    final int typePath(int a1) throws Exception {
        final int v1 = this.info[a1++] & 0xFF;
        return this.typePath(a1, v1);
    }
    
    int typePath(int v-1, final int v0) throws Exception {
        for (int v = 0; v < v0; ++v) {
            final int a1 = this.info[v-1] & 0xFF;
            final int a2 = this.info[v-1 + 1] & 0xFF;
            this.typePath(v-1, a1, a2);
            v-1 += 2;
        }
        return v-1;
    }
    
    void typePath(final int a1, final int a2, final int a3) throws Exception {
    }
}
