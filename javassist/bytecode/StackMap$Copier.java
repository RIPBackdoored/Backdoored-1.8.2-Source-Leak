package javassist.bytecode;

import java.util.*;

static class Copier extends Walker
{
    byte[] dest;
    ConstPool srcCp;
    ConstPool destCp;
    Map classnames;
    
    Copier(final StackMap a1, final ConstPool a2, final Map a3) {
        super(a1);
        this.srcCp = a1.getConstPool();
        this.dest = new byte[this.info.length];
        this.destCp = a2;
        this.classnames = a3;
    }
    
    @Override
    public void visit() {
        final int v1 = ByteArray.readU16bit(this.info, 0);
        ByteArray.write16bit(v1, this.dest, 0);
        super.visit();
    }
    
    @Override
    public int locals(final int a1, final int a2, final int a3) {
        ByteArray.write16bit(a2, this.dest, a1 - 4);
        return super.locals(a1, a2, a3);
    }
    
    @Override
    public int typeInfoArray(final int a1, final int a2, final int a3, final boolean a4) {
        ByteArray.write16bit(a3, this.dest, a1 - 2);
        return super.typeInfoArray(a1, a2, a3, a4);
    }
    
    @Override
    public void typeInfo(final int a1, final byte a2) {
        this.dest[a1] = a2;
    }
    
    @Override
    public void objectVariable(final int a1, final int a2) {
        this.dest[a1] = 7;
        final int v1 = this.srcCp.copy(a2, this.destCp, this.classnames);
        ByteArray.write16bit(v1, this.dest, a1 + 1);
    }
    
    @Override
    public void uninitialized(final int a1, final int a2) {
        this.dest[a1] = 8;
        ByteArray.write16bit(a2, this.dest, a1 + 1);
    }
    
    public StackMap getStackMap() {
        return new StackMap(this.destCp, this.dest);
    }
}
