package javassist.bytecode;

import java.io.*;

class FieldrefInfo extends MemberrefInfo
{
    static final int tag = 9;
    
    public FieldrefInfo(final int a1, final int a2, final int a3) {
        super(a1, a2, a3);
    }
    
    public FieldrefInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a1, a2);
    }
    
    @Override
    public int getTag() {
        return 9;
    }
    
    @Override
    public String getTagName() {
        return "Field";
    }
    
    @Override
    protected int copy2(final ConstPool a1, final int a2, final int a3) {
        return a1.addFieldrefInfo(a2, a3);
    }
}
