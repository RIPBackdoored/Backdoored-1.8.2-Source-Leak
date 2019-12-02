package javassist.bytecode;

static class Walker
{
    byte[] info;
    
    Walker(final byte[] a1) {
        super();
        this.info = a1;
    }
    
    final void parameters() throws Exception {
        final int v1 = this.info[0] & 0xFF;
        this.parameters(v1, 1);
    }
    
    void parameters(final int v1, int v2) throws Exception {
        for (int a1 = 0; a1 < v1; ++a1) {
            v2 = this.annotationArray(v2);
        }
    }
    
    final void annotationArray() throws Exception {
        this.annotationArray(0);
    }
    
    final int annotationArray(final int a1) throws Exception {
        final int v1 = ByteArray.readU16bit(this.info, a1);
        return this.annotationArray(a1 + 2, v1);
    }
    
    int annotationArray(int v1, final int v2) throws Exception {
        for (int a1 = 0; a1 < v2; ++a1) {
            v1 = this.annotation(v1);
        }
        return v1;
    }
    
    final int annotation(final int a1) throws Exception {
        final int v1 = ByteArray.readU16bit(this.info, a1);
        final int v2 = ByteArray.readU16bit(this.info, a1 + 2);
        return this.annotation(a1 + 4, v1, v2);
    }
    
    int annotation(int a3, final int v1, final int v2) throws Exception {
        for (int a4 = 0; a4 < v2; ++a4) {
            a3 = this.memberValuePair(a3);
        }
        return a3;
    }
    
    final int memberValuePair(final int a1) throws Exception {
        final int v1 = ByteArray.readU16bit(this.info, a1);
        return this.memberValuePair(a1 + 2, v1);
    }
    
    int memberValuePair(final int a1, final int a2) throws Exception {
        return this.memberValue(a1);
    }
    
    final int memberValue(final int v-2) throws Exception {
        final int a2 = this.info[v-2] & 0xFF;
        if (a2 == 101) {
            final int a1 = ByteArray.readU16bit(this.info, v-2 + 1);
            final int v1 = ByteArray.readU16bit(this.info, v-2 + 3);
            this.enumMemberValue(v-2, a1, v1);
            return v-2 + 5;
        }
        if (a2 == 99) {
            final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
            this.classMemberValue(v-2, v2);
            return v-2 + 3;
        }
        if (a2 == 64) {
            return this.annotationMemberValue(v-2 + 1);
        }
        if (a2 == 91) {
            final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
            return this.arrayMemberValue(v-2 + 3, v2);
        }
        final int v2 = ByteArray.readU16bit(this.info, v-2 + 1);
        this.constValueMember(a2, v2);
        return v-2 + 3;
    }
    
    void constValueMember(final int a1, final int a2) throws Exception {
    }
    
    void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
    }
    
    void classMemberValue(final int a1, final int a2) throws Exception {
    }
    
    int annotationMemberValue(final int a1) throws Exception {
        return this.annotation(a1);
    }
    
    int arrayMemberValue(int v1, final int v2) throws Exception {
        for (int a1 = 0; a1 < v2; ++a1) {
            v1 = this.memberValue(v1);
        }
        return v1;
    }
}
