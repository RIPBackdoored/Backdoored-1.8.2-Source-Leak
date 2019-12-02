package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class MultiArrayInitializer extends Initializer
{
    CtClass type;
    int[] dim;
    
    MultiArrayInitializer(final CtClass a1, final int[] a2) {
        super();
        this.type = a1;
        this.dim = a2;
    }
    
    @Override
    void check(final String a1) throws CannotCompileException {
        if (a1.charAt(0) != '[') {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
        a3.addAload(0);
        final int v1 = a3.addMultiNewarray(a1, this.dim);
        a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
        return v1 + 1;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        final int v1 = a3.addMultiNewarray(a1, this.dim);
        a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
        return v1;
    }
}
