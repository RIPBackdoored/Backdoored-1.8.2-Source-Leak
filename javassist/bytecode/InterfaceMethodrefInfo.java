package javassist.bytecode;

import java.io.*;

class InterfaceMethodrefInfo extends MemberrefInfo
{
    static final int tag = 11;
    
    public InterfaceMethodrefInfo(final int a1, final int a2, final int a3) {
        super(a1, a2, a3);
    }
    
    public InterfaceMethodrefInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a1, a2);
    }
    
    @Override
    public int getTag() {
        return 11;
    }
    
    @Override
    public String getTagName() {
        return "Interface";
    }
    
    @Override
    protected int copy2(final ConstPool a1, final int a2, final int a3) {
        return a1.addInterfaceMethodrefInfo(a2, a3);
    }
}
