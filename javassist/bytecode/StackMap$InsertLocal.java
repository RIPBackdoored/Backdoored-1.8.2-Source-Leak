package javassist.bytecode;

static class InsertLocal extends SimpleCopy
{
    private int varIndex;
    private int varTag;
    private int varData;
    
    InsertLocal(final StackMap a1, final int a2, final int a3, final int a4) {
        super(a1);
        this.varIndex = a2;
        this.varTag = a3;
        this.varData = a4;
    }
    
    @Override
    public int typeInfoArray(int a3, final int a4, final int v1, final boolean v2) {
        if (!v2 || v1 < this.varIndex) {
            return super.typeInfoArray(a3, a4, v1, v2);
        }
        this.writer.write16bit(v1 + 1);
        for (int a5 = 0; a5 < v1; ++a5) {
            if (a5 == this.varIndex) {
                this.writeVarTypeInfo();
            }
            a3 = this.typeInfoArray2(a5, a3);
        }
        if (v1 == this.varIndex) {
            this.writeVarTypeInfo();
        }
        return a3;
    }
    
    private void writeVarTypeInfo() {
        if (this.varTag == 7) {
            this.writer.writeVerifyTypeInfo(7, this.varData);
        }
        else if (this.varTag == 8) {
            this.writer.writeVerifyTypeInfo(8, this.varData);
        }
        else {
            this.writer.writeVerifyTypeInfo(this.varTag, 0);
        }
    }
}
