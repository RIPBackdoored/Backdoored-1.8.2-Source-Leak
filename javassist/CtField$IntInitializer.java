package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class IntInitializer extends Initializer
{
    int value;
    
    IntInitializer(final int a1) {
        super();
        this.value = a1;
    }
    
    @Override
    void check(final String a1) throws CannotCompileException {
        final char v1 = a1.charAt(0);
        if (v1 != 'I' && v1 != 'S' && v1 != 'B' && v1 != 'C' && v1 != 'Z') {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
        a3.addAload(0);
        a3.addIconst(this.value);
        a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        a3.addIconst(this.value);
        a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
        return 1;
    }
    
    @Override
    int getConstantValue(final ConstPool a1, final CtClass a2) {
        return a1.addIntegerInfo(this.value);
    }
}
