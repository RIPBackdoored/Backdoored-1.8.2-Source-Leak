package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public abstract class Transformer implements Opcode
{
    private Transformer next;
    
    public Transformer(final Transformer a1) {
        super();
        this.next = a1;
    }
    
    public Transformer getNext() {
        return this.next;
    }
    
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
    }
    
    public void initialize(final ConstPool a1, final CtClass a2, final MethodInfo a3) throws CannotCompileException {
        this.initialize(a1, a3.getCodeAttribute());
    }
    
    public void clean() {
    }
    
    public abstract int transform(final CtClass p0, final int p1, final CodeIterator p2, final ConstPool p3) throws CannotCompileException, BadBytecode;
    
    public int extraLocals() {
        return 0;
    }
    
    public int extraStack() {
        return 0;
    }
}
