package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class DoubleInitializer extends Initializer
{
    double value;
    
    DoubleInitializer(final double a1) {
        super();
        this.value = a1;
    }
    
    @Override
    void check(final String a1) throws CannotCompileException {
        if (!a1.equals("D")) {
            throw new CannotCompileException("type mismatch");
        }
    }
    
    @Override
    int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
        a3.addAload(0);
        a3.addLdc2w(this.value);
        a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
        return 3;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        a3.addLdc2w(this.value);
        a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
        return 2;
    }
    
    @Override
    int getConstantValue(final ConstPool a1, final CtClass a2) {
        if (a2 == CtClass.doubleType) {
            return a1.addDoubleInfo(this.value);
        }
        return 0;
    }
}
