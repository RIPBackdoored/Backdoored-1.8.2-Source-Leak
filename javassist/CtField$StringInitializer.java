package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class StringInitializer extends Initializer
{
    String value;
    
    StringInitializer(final String a1) {
        super();
        this.value = a1;
    }
    
    @Override
    int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
        a3.addAload(0);
        a3.addLdc(this.value);
        a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        a3.addLdc(this.value);
        a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
        return 1;
    }
    
    @Override
    int getConstantValue(final ConstPool a1, final CtClass a2) {
        if (a2.getName().equals("java.lang.String")) {
            return a1.addStringInfo(this.value);
        }
        return 0;
    }
}
