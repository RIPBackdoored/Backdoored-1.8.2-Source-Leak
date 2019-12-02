package javassist.compiler;

import javassist.*;
import javassist.bytecode.*;

public static class Method
{
    public CtClass declaring;
    public MethodInfo info;
    public int notmatch;
    
    public Method(final CtClass a1, final MethodInfo a2, final int a3) {
        super();
        this.declaring = a1;
        this.info = a2;
        this.notmatch = a3;
    }
    
    public boolean isStatic() {
        final int v1 = this.info.getAccessFlags();
        return (v1 & 0x8) != 0x0;
    }
}
