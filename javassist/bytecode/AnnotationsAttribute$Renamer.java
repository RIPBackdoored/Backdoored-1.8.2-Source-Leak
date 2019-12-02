package javassist.bytecode;

import java.util.*;

static class Renamer extends Walker
{
    ConstPool cpool;
    Map classnames;
    
    Renamer(final byte[] a1, final ConstPool a2, final Map a3) {
        super(a1);
        this.cpool = a2;
        this.classnames = a3;
    }
    
    @Override
    int annotation(final int a1, final int a2, final int a3) throws Exception {
        this.renameType(a1 - 4, a2);
        return super.annotation(a1, a2, a3);
    }
    
    @Override
    void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
        this.renameType(a1 + 1, a2);
        super.enumMemberValue(a1, a2, a3);
    }
    
    @Override
    void classMemberValue(final int a1, final int a2) throws Exception {
        this.renameType(a1 + 1, a2);
        super.classMemberValue(a1, a2);
    }
    
    private void renameType(final int v1, final int v2) {
        final String v3 = this.cpool.getUtf8Info(v2);
        final String v4 = Descriptor.rename(v3, this.classnames);
        if (!v3.equals(v4)) {
            final int a1 = this.cpool.addUtf8Info(v4);
            ByteArray.write16bit(a1, this.info, v1);
        }
    }
}
