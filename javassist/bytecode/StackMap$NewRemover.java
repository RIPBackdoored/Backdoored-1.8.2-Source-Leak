package javassist.bytecode;

static class NewRemover extends SimpleCopy
{
    int posOfNew;
    
    NewRemover(final StackMap a1, final int a2) {
        super(a1);
        this.posOfNew = a2;
    }
    
    @Override
    public int stack(final int a1, final int a2, final int a3) {
        return this.stackTypeInfoArray(a1, a2, a3);
    }
    
    private int stackTypeInfoArray(int v-6, final int v-5, final int v-4) {
        int n = v-6;
        int n2 = 0;
        for (int a3 = 0; a3 < v-4; ++a3) {
            final byte a4 = this.info[n];
            if (a4 == 7) {
                n += 3;
            }
            else if (a4 == 8) {
                final int a5 = ByteArray.readU16bit(this.info, n + 1);
                if (a5 == this.posOfNew) {
                    ++n2;
                }
                n += 3;
            }
            else {
                ++n;
            }
        }
        this.writer.write16bit(v-4 - n2);
        for (int i = 0; i < v-4; ++i) {
            final byte v0 = this.info[v-6];
            if (v0 == 7) {
                final int v2 = ByteArray.readU16bit(this.info, v-6 + 1);
                this.objectVariable(v-6, v2);
                v-6 += 3;
            }
            else if (v0 == 8) {
                final int v2 = ByteArray.readU16bit(this.info, v-6 + 1);
                if (v2 != this.posOfNew) {
                    this.uninitialized(v-6, v2);
                }
                v-6 += 3;
            }
            else {
                this.typeInfo(v-6, v0);
                ++v-6;
            }
        }
        return v-6;
    }
}
